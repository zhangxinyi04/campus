<template>
  <div ref="chartContainer" class="chart-container"></div>
</template>

<script>
  import * as echarts from 'echarts';

  export default {
    props: ['sysEvents'],
    data() {
      return {
        chart: null,
        eventTypeMap: {
          0: '爱党爱国',
          1: '队团活动',
          2: '爱护公物',
          3: '责任担当',
          4: '文明朴素',
          5: '尊老爱幼',
          6: '自立勤快',
          7: '学习方法',
          8: '发明创造',
          9: '问题解决',
          10: '阅读习惯',
          11: '动手动脑',
          12: '卫生健康与安全',
          13: '意志坚强',
          14: '积极阳光',
          15: '人际关系',
          16: '艺术实践',
          17: '劳动观念',
          18: '家庭劳动',
          19: '环保意识'
        }
      };
    },
    mounted() {
      this.initChart();
      this.updateChart();
    },
    watch: {
      sysEvents: {
        handler: function() {
          this.updateChart();
        },
        deep: true
      }
    },
    beforeDestroy() {
      if (this.chart) {
        this.chart.dispose();
        this.chart = null;
      }
    },
    methods: {
      initChart() {
        this.chart = echarts.init(this.$refs.chartContainer);

        window.addEventListener('resize', () => {
          if (this.chart) {
            this.chart.resize();
          }
        });
      },
      updateChart() {
        if (!this.chart) return;

        // 处理数据 - 确保包含所有类型，没有的显示为0
        const allTypesData = Object.keys(this.eventTypeMap).map(typeKey => {
          const typeNum = parseInt(typeKey);
          const eventData = this.sysEvents.find(event => event.eventType === typeNum);
          return {
            value: eventData ? eventData.count || 0 : 0,
            name: this.eventTypeMap[typeNum] || `未知类型${typeNum}`
          };
        });

        // 图表配置
        const option = {
          title: {
            text: '活动统计',
            left: 'center',
            textStyle: {
              color: '#333',
              fontSize: 18,
              fontWeight: 'bold'
            }
          },
          backgroundColor: 'white',
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999'
              }
            },
            backgroundColor: 'rgba(255,255,255,0.9)',
            textStyle: {
              color: '#333'
            }
          },
          legend: {
            data: ['数量'],
            bottom: '3%',
            textStyle: {
              color: '#666',
              fontSize: 12
            }
          },
          grid: {
            bottom: '18%', // 增加底部间距
            left: '3%',
            right: '4%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: allTypesData.map(item => item.name),
            axisLabel: {
              rotate: 45, // 垂直显示标签
              interval: 0,
              textStyle: {
                color: '#666',
                fontSize: 11, // 增大字体
                align: 'right'
              }
            },
            axisTick: {
              show: false
            },
            axisLine: {
              lineStyle: {
                color: '#ccc'
              }
            }
          },
          yAxis: {
            type: 'value',
            min: 0,
            axisLabel: {
              textStyle: {
                color: '#666'
              }
            },
            axisLine: {
              lineStyle: {
                color: '#ccc'
              }
            },
            splitLine: {
              lineStyle: {
                color: '#eee'
              }
            }
          },
          series: [{
            name: '数量',
            type: 'line',
            data: allTypesData.map(item => item.value),
            symbol: 'circle',
            symbolSize: 8,
            itemStyle: {
              color: '#409eff'
            },
            lineStyle: {
              color: '#409eff',
              width: 2
            },
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [{
                    offset: 0,
                    color: 'rgba(64, 158, 255, 0.3)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(64, 158, 255, 0)'
                  }
                ]
              }
            },
            emphasis: {
              focus: 'series'
            }
          }]
        };

        // 应用配置
        this.chart.setOption(option);
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
