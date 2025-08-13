<template>
  <div class="chart-container" ref="chartRef"></div>
</template>

<script>
  import * as echarts from 'echarts';

  export default {
    name: 'ScoreStatistics',
    data() {
      return {
        chart: null,
        chartData: {
          students: [],
          mathScores: [],
          englishScores: [],
          chineseScores: []
        },
        rateData: []
      };
    },
    props: ['studentScore'],
    mounted() {
      this.parseData();
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
      studentScore: {
        handler() {
          this.parseData();
          this.updateChart();
        },
        deep: true
      }
    },
    methods: {
      parseData() {
        if (!this.studentScore || !this.studentScore.length) return;

        this.rateData = this.studentScore.map(item => {
          const subjectMap = {
            'chinese': '语文',
            'math': '数学',
            'english': '英语',
            'art': '美术',
            'music': '音乐',
            'sports': '体育与健康',
            'practice': '综合实践',
            'labour': '劳动与技术',
            'computer': '信息科技',
            'morality': '道德与法治',
            'health': '心理健康',
            'science': '科学',
            'calligraphy': '书法',
            'dance': '舞蹈',
            'readbook': '阅读',
          };

          return {
            subject: subjectMap[item.subject] || item.subject,
            excellentRate: parseFloat(item.excellentRate),
            goodRate: parseFloat(item.goodRate),
            passRate: parseFloat(item.passRate),
            failRate: parseFloat(item.failRate)
          };
        });
      },
      initChart() {
        const chartDom = this.$refs.chartRef;
        this.chart = echarts.init(chartDom);

        const option = this.getChartOption();
        this.chart.setOption(option);
      },
      getChartOption() {
        const subjects = this.rateData.map(item => item.subject);
        const excellentRates = this.rateData.map(item => item.excellentRate);
        const goodRates = this.rateData.map(item => item.goodRate);
        const passRates = this.rateData.map(item => item.passRate);
        const failRates = this.rateData.map(item => item.failRate);

        return {
          backgroundColor: 'rgba(255, 255, 255, 0.8)',
          title: {
            text: '成绩统计',
            left: 'center',
            textStyle: {
              color: '#333',
              fontSize: 18,
              fontWeight: 'bold',
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
            axisPointer: {
              type: 'cross',
              crossStyle: {
                color: '#999',
                width: 1
              }
            },
            formatter: (params) => {
              let result = `<div style="font-weight:bold;margin-bottom:5px;">${params[0].name}</div>`;
              params.forEach(param => {
                result += `<div style="margin-bottom:2px;">
                  <span style="display:inline-block;width:10px;height:10px;border-radius:5px;background-color:${param.color}"></span>
                  ${param.seriesName}: ${param.value}%
                </div>`;
              });
              return result;
            }
          },
          // 图例配置：放到底部并居中
          legend: {
            data: ['优秀率', '良好率', '及格率', '不及格率'],
            bottom: '5%', // 调整到底部
            left: 'center', // 水平居中
            textStyle: {
              color: '#666',
              fontSize: 14
            },
            pageButtonItemGap: 5,
            pageButtonStyle: {
              color: '#666'
            }
          },
          // 调整网格底部边距，避免与图例重叠
          grid: {
            left: '5%',
            right: '5%',
            bottom: '15%', // 增大底部边距，给图例留出空间
            top: '15%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: subjects,
            axisLabel: {
              interval: 0,
              color: '#666',
              fontSize: 14,
              rotate: 0
            },
            axisLine: {
              lineStyle: {
                color: '#ccc'
              }
            },
            axisTick: {
              show: false
            }
          },
          yAxis: {
            type: 'value',
            min: 0,
            max: 100,
            interval: 20,
            name: '比率(%)',
            nameGap: 30,
            nameTextStyle: {
              color: '#666',
              fontSize: 14
            },
            axisLabel: {
              color: '#666',
              fontSize: 14,
              formatter: '{value}%'
            },
            axisLine: {
              lineStyle: {
                color: '#ccc'
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              lineStyle: {
                color: '#eee'
              }
            }
          },
          series: [{
              name: '优秀率',
              type: 'line',
              data: excellentRates,
              symbol: 'circle',
              symbolSize: 8,
              showSymbol: true,
              smooth: true,
              lineStyle: {
                width: 3,
                color: '#5793f3'
              },
              itemStyle: {
                color: '#5793f3',
                borderWidth: 2,
                borderColor: '#fff'
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
                      color: 'rgba(87, 147, 243, 0.4)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(87, 147, 243, 0.05)'
                    }
                  ]
                }
              }
            },
            {
              name: '良好率',
              type: 'line',
              data: goodRates,
              symbol: 'circle',
              symbolSize: 8,
              showSymbol: true,
              smooth: true,
              lineStyle: {
                width: 3,
                color: '#d14a61'
              },
              itemStyle: {
                color: '#d14a61',
                borderWidth: 2,
                borderColor: '#fff'
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
                      color: 'rgba(209, 74, 97, 0.4)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(209, 74, 97, 0.05)'
                    }
                  ]
                }
              }
            },
            {
              name: '及格率',
              type: 'line',
              data: passRates,
              symbol: 'circle',
              symbolSize: 8,
              showSymbol: true,
              smooth: true,
              lineStyle: {
                width: 3,
                color: '#675bba'
              },
              itemStyle: {
                color: '#675bba',
                borderWidth: 2,
                borderColor: '#fff'
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
                      color: 'rgba(103, 91, 186, 0.4)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(103, 91, 186, 0.05)'
                    }
                  ]
                }
              }
            },
            {
              name: '不及格率',
              type: 'line',
              data: failRates,
              symbol: 'circle',
              symbolSize: 8,
              showSymbol: true,
              smooth: true,
              lineStyle: {
                width: 3,
                color: '#ff7f50'
              },
              itemStyle: {
                color: '#ff7f50',
                borderWidth: 2,
                borderColor: '#fff'
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
                      color: 'rgba(255, 127, 80, 0.4)'
                    },
                    {
                      offset: 1,
                      color: 'rgba(255, 127, 80, 0.05)'
                    }
                  ]
                }
              }
            }
          ]
        };
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
