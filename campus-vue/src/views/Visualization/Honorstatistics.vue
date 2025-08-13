<template>
 <div ref="chartContainer" class="chart-container"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  props: ['develops'],
  data() {
    return {
      chart: null,
      rankMap: {
        '0': '国际级',
        '1': '国家级',
        '2': '省级',
        '3': '市级',
        '4': '区县级',
        '5': '校级',
        '6': '班级'
      }
    };
  },
  mounted() {
    this.initChart();
    this.updateChart();
  },
  watch: {
    develops: {
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
      if (!this.chart || !this.develops) return;

      // 处理数据 - 确保包含所有级别（0-6），无数据则显示0
      const allRanks = Object.keys(this.rankMap).map(rankKey => {
        const rankNum = parseInt(rankKey);
        // 找到对应级别的数据
        const rankItem = this.develops.find(item => item.institutionRank === rankNum);
        return {
          name: this.rankMap[rankKey], // 级别名称（如"国际级"）
          value: rankItem ? (rankItem.count || 0) : 0 // 数量，无数据则为0
        };
      });

      // 图表配置（线状图）
      const option = {
        // backgroundColor: '#f8f9fa',
         backgroundColor: 'white',
        title: {
          text: '荣誉统计',
          left: 'center',
          textStyle: {
            color: '#333',
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'axis', // 线状图适合axis触发
          axisPointer: {
            type: 'shadow' // 阴影指示器，增强交互
          },
          backgroundColor: 'rgba(255,255,255,0.9)',
          textStyle: {
            color: '#333'
          },
          formatter: '{b}: {c} 次' // 格式化提示内容：级别名称 + 数量
        },
        legend: {
          data: ['数量'], // 图例名称
          bottom: '5%', // 图例位置在底部
          textStyle: {
            color: '#666'
          }
        },
        grid: {
          left: '5%',
          right: '5%',
          bottom: '15%', // 底部留出空间放x轴标签
          top: '10%',
          containLabel: true // 包含标签，避免溢出
        },
        xAxis: {
          type: 'category',
          data: allRanks.map(item => item.name), // x轴为级别名称
          axisLabel: {
            rotate: 45, // 标签旋转45度，避免重叠
            interval: 0, // 强制显示所有标签
            textStyle: {
              color: '#666',
              fontSize: 12
            }
          },
          axisLine: {
            lineStyle: {
              color: '#ddd'
            }
          },
          axisTick: {
            show: false // 隐藏刻度线
          }
        },
        yAxis: {
          type: 'value',
          name: '数量', // y轴名称
          nameTextStyle: {
            color: '#666'
          },
          min: 0, // 最小值为0，避免数据失真
          axisLine: {
            lineStyle: {
              color: '#ddd'
            }
          },
          splitLine: {
            lineStyle: {
              color: '#f0f0f0'
            }
          }
        },
        series: [
          {
            name: '数量',
            type: 'line', // 图表类型改为线状图
            data: allRanks.map(item => item.value), // y轴数据为数量
            symbol: 'circle', // 标记点为圆形
            symbolSize: 8, // 标记点大小
            itemStyle: {
              color: '#409eff' // 标记点颜色
            },
            lineStyle: {
              color: '#409eff', // 线条颜色
              width: 2 // 线条粗细
            },
            areaStyle: {
              // 区域填充（可选，增强视觉效果）
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
                  { offset: 1, color: 'rgba(64, 158, 255, 0)' }
                ]
              }
            },
            emphasis: {
              focus: 'series' // 高亮整个系列
            }
          }
        ]
      };

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
