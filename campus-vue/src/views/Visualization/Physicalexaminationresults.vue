<template>
  <div class="chart-container" ref="chartRef"></div>
</template>

<script>
  import * as echarts from 'echarts';

  export default {
    name: 'PhysicalTestStatistics',
    data() {
      return {
        chart: null,
        levelMap: {
          '0': '优秀',
          '1': '良好',
          '2': '达标',
          '3': '待达标'
        },
        colorMap: {
          '0': '#52c41a', // 绿色
          '1': '#1890ff', // 蓝色
          '2': '#faad14', // 橙色
          '3': '#ff4d4f'  // 红色
        }
      };
    },
    props: ['studentScorePe'],
    mounted() {
      this.initChart();
      window.addEventListener('resize', this.resizeChart);
    },
    beforeDestroy() {
      window.removeEventListener('resize', this.resizeChart);
      if (this.chart) {
        this.chart.dispose();
        this.chart = null;
      }
    },
    watch: {
      studentScorePe: {
        handler() {
          this.updateChart();
        },
        deep: true
      }
    },
    methods: {
      initChart() {
        const chartDom = this.$refs.chartRef;
        this.chart = echarts.init(chartDom);

        const option = this.getChartOption();
        this.chart.setOption(option);
      },
      getChartOption() {
        // 处理数据，确保"优秀"始终在第一位
        const processedData = this.processData(this.studentScorePe);

        // 计算总数
        const total = processedData.reduce((sum, item) => sum + item.value, 0);

        // 提取等级和对应人数
        const categories = processedData.map(item => item.name);
        const values = processedData.map(item => item.value);
        const colors = processedData.map(item => item.itemStyle.color);

        return {
          backgroundColor: 'rgba(255, 255, 255, 0.8)',
          title: {
            text: '体测成绩统计',
            left: 'center',
            textStyle: {
              color: '#333',
              fontSize: 18,
              fontWeight: 'bold'
            }
          },
          tooltip: {
            trigger: 'axis',
            backgroundColor: 'rgba(0, 0, 0, 0.7)',
            padding: 10,
            textStyle: {
              color: '#fff',
              fontSize: 14
            },
            formatter: (params) => {
              let result = `<div style="font-weight:bold;margin-bottom:5px;">${params[0].name}</div>`;
              params.forEach(param => {
                const percent = ((param.value / total) * 100).toFixed(1);
                result += `
                  <div style="display:flex;align-items:center;">
                    <span style="display:inline-block;width:10px;height:10px;border-radius:50%;background-color:${param.color};margin-right:5px;"></span>
                    <span>${param.seriesName}: ${param.value} (${percent}%)</span>
                  </div>
                `;
              });
              return result;
            }
          },
          legend: {
            type: 'scroll',
            orient: 'horizontal',
            bottom: '5%',
            left: 'center',
            textStyle: {
              color: '#666',
              fontSize: 14
            },
            itemWidth: 12,
            itemHeight: 12,
            pageButtonItemGap: 5,
            pageButtonStyle: {
              color: '#666'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '15%',
            top: '15%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: categories,
            axisTick: {
              alignWithLabel: true
            },
            axisLabel: {
              color: '#666',
              fontSize: 14
            },
            axisLine: {
              lineStyle: {
                color: '#999'
              }
            }
          },
          yAxis: {
            type: 'value',
            name: '学生人数',
            nameGap: 30,
            min: 0,
            axisLabel: {
              color: '#666',
              fontSize: 14,
              formatter: '{value}'
            },
            axisLine: {
              lineStyle: {
                color: '#999'
              }
            },
            splitLine: {
              lineStyle: {
                color: '#eee'
              }
            }
          },
          series: [{
            name: '学生人数',
            type: 'line',
            data: values,
            symbol: 'circle',
            symbolSize: 10,
            itemStyle: {
              color: (params) => colors[params.dataIndex]
            },
            lineStyle: {
              width: 3,
              color: (params) => colors[params.dataIndex]
            },
            areaStyle: {
              opacity: 0.2,
              color: (params) => colors[params.dataIndex]
            },
            label: {
              show: true,
              position: 'top',
              color: '#333',
              fontSize: 14
            }
          }]
        };
      },
      processData(rawData) {
        // 确保数据存在
        if (!rawData || rawData.length === 0) {
          return [
            { value: 0, name: '优秀', itemStyle: { color: this.colorMap['0'] } },
            { value: 0, name: '良好', itemStyle: { color: this.colorMap['1'] } },
            { value: 0, name: '达标', itemStyle: { color: this.colorMap['2'] } },
            { value: 0, name: '待达标', itemStyle: { color: this.colorMap['3'] } }
          ];
        }

        // 创建一个映射表，确保每个等级都有对应的数据
        const dataMap = {
          '0': { value: 0, name: '优秀', itemStyle: { color: this.colorMap['0'] } },
          '1': { value: 0, name: '良好', itemStyle: { color: this.colorMap['1'] } },
          '2': { value: 0, name: '达标', itemStyle: { color: this.colorMap['2'] } },
          '3': { value: 0, name: '待达标', itemStyle: { color: this.colorMap['3'] } }
        };

        // 填充实际数据
        rawData.forEach(item => {
          if (this.levelMap[item.level] !== undefined) {
            dataMap[item.level] = {
              value: item.count || 0,
              name: this.levelMap[item.level],
              itemStyle: { color: this.colorMap[item.level] }
            };
          }
        });

        // 按等级顺序排列，确保"优秀"始终在第一位
        return [
          dataMap['0'],
          dataMap['1'],
          dataMap['2'],
          dataMap['3']
        ];
      },
      updateChart() {
        if (!this.chart) return;

        const option = this.getChartOption();
        this.chart.setOption(option);
      },
      resizeChart() {
        if (this.chart) {
          this.chart.resize();
        }
      }
    }
  };
</script>

<style scoped>
  .chart-container {
    width: 100%;
    height: 500px;
    margin-top: 20px;
    border-radius: 8px;
    background-color: rgba(255, 255, 255, 0.8);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    overflow: hidden;
  }
</style>
