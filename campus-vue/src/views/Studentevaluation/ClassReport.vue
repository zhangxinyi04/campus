<template>
  <div class="class-report-container" v-loading='loading'>
    <!-- 日期筛选 -->
    <el-card shadow="hover" class="mb-20">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          时间选择：{{ timeText }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="week">本周</el-dropdown-item>
          <el-dropdown-item command="month">本月</el-dropdown-item>
          <el-dropdown-item command="custom">自定义</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <!-- <div v-show="timeText === '自定义'"  style="float: left;"> -->
      <el-date-picker v-if="timeText === '自定义'" v-model="dateRange" type="datetimerange" range-separator="至"
        start-placeholder="开始日期" end-placeholder="结束日期" :picker-options="pickerOptions"
        value-format="yyyy-MM-dd HH:mm:ss" class="filter-item" @change="dateRangechange"
        style="width: 400px;margin-left: 20px;">
      </el-date-picker>
      <!-- </div> -->

      <el-dropdown @command="handleCommandClass" style="margin-left: 20px;">
        <span class="el-dropdown-link">
          学科选择：{{ classText }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item :command="item.courseName" v-for="(item,index) in classes" :key="index">{{item.courseName}}</el-dropdown-item>
         <!-- <el-dropdown-item command="month">本月</el-dropdown-item>
          <el-dropdown-item command="custom">自定义</el-dropdown-item> -->
        </el-dropdown-menu>
      </el-dropdown>

    </el-card>

    <el-row :gutter="5" class="main-content">
      <!-- 左侧学生列表 -->
      <el-col :span="4" class="student-list-col">
        <el-card shadow="hover" class="student-list-card fixed-height-container">
          <div slot="header" class="list-header">学生列表</div>
          <div class="student-list">
            <div v-for="student in students" :key="student.studentId" class="student-item"
              @click="studentschange(student.studentId)" :class="{active: selectedStudent === student.studentId}">
              <img :src="student.sex === '0' ? man : woman" class="student-avatar">
              <div class="student-name">{{ student.studentName }}</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 中间内容 -->
      <el-col :span="9">
        <!-- 环形图 -->
        <el-card shadow="hover" class="mb-20 chart-card fixed-height">
          <div id="chartPie" style="height: 300px;"></div>
          <div class="chart-footer">
            <div class="total-count">
              <div class="total-item">
                <span class="label">总点评次数</span>
                <span class="value">{{ scoreDetail }}</span>
              </div>
              <el-divider direction="vertical"></el-divider>
              <div class="count-detail">
                <div class="detail-item positive">
                  <i class="el-icon-success"></i>
                  <span>表扬：{{typeOne}}</span>
                </div>
                <div class="detail-item negative">
                  <i class="el-icon-warning"></i>
                  <span>待改进：{{typeTwo}}</span>
                </div>
              </div>
            </div>
          </div>
        </el-card>

        <!-- 学生排行 -->
        <el-card shadow="hover" class="ranking-card">
          <div slot="header" class="card-header">
            <i class="el-icon-trophy"></i>
            学生点评次数排行
          </div>
          <div class="ranking-list">
            <div v-for="(student, index) in students" :key="student.studentId" class="ranking-item"
              :class="{'top3': index < 3}">
              <div class="rank">
                <el-tag v-if="index < 3" type="warning">{{ index + 1 }}</el-tag>
                <span v-else>{{ index + 1 }}</span>
              </div>
              <img :src="student.sex === '0' ? man : woman" class="avatar">
              <div class="info">
                <div class="name">{{ student.studentName }}</div>
                <div class="count">总次数：{{ student.counts }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 右侧内容 -->
      <el-col :span="10">
        <!-- 全部点评类型 -->
        <!-- <el-card shadow="hover" class="mb-20 type-card">
          <div slot="header" class="card-header">
            <i class="el-icon-collection-tag"></i>
            全部点评类型
          </div>
          <div class="tag-cloud">
            <el-tag
              v-for="(item, index) in sortedArray"
              :key="index"
              :type="item.type == 0 ? 'success' : 'danger'"
              effect="dark"
              class="tag-item"
            >
              {{ item.title }} ({{ item.counts }}次)
            </el-tag>
          </div>
        </el-card> -->
        <el-card shadow="hover" class="mb-20 type-card">
          <div slot="header" class="card-header">
            <i class="el-icon-collection-tag"></i>
            全部点评类型
          </div>
          <div class="tag-cloud">
            <div v-for="(item, index) in sortedArray" :key="index" class="type-item">
              <div class="image-container">
                <img :src="baseUrl+item.image" alt="类型图标" class="type-image">
                <span class="count-badge">{{ item.counts }}</span> <!-- 次数小红圈 -->
              </div>
              <p class="type-name">
                <el-tag :type="item.type == 0 ? 'success' : 'danger'" effect="dark" class="tag-item">
                  {{ item.title }}
                </el-tag>
              </p> <!-- 类型名称 -->
            </div>
          </div>
        </el-card>


        <!-- 操作明细 -->
        <el-card shadow="hover" class="detail-card">
          <div slot="header" class="card-header">
            <i class="el-icon-document"></i>
            操作明细
          </div>
          <div class="detail-list">
           <!-- <div v-for="(detail, index) in rawData.details" :key="index" class="detail-item">
              <div class="content">
                <img src="@/assets/img/teacher.png" class="teacher-avatar">
                <div class="text">
                  <div class="main-text">{{ detail.teacherName }}点评{{detail.studentName}}{{detail.bonusName}}</div>
                  <div class="time">{{ detail.createTime }}</div>
                </div>
              </div>
              <el-tag :type="detail.mark > 0 ? 'success' : 'danger'" size="mini" class="score">
                {{ detail.mark > 0 ? '+' : '-' }}{{ detail.mark }}
              </el-tag>
            </div> -->
            <div v-for="(detail, index) in rawData.details" :key="index" class="detail-item">
              <div class="content">
                <img src="@/assets/img/teacher.png" class="teacher-avatar">
                <div class="text">
                  <div class="main-text">{{ detail.teacherName }}点评{{detail.studentName}}{{detail.bonusName}}</div>
                  <div class="time">{{ detail.createTime }}</div>
                </div>
              </div>
              <div class="actions">
                <el-tag :type="detail.mark > 0 ? 'success' : 'danger'" size="mini" class="score">
                  {{ detail.mark > 0 ? '+' : '' }}{{ detail.mark }}
                </el-tag>
                <el-button
                  type="danger"
                  icon="el-icon-delete"
                  circle
                  size="mini"
                  class="delete-btn"
                  @click="handleDelete(detail)"
                ></el-button>
              </div>
            </div>


          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>


<script>
  import * as echarts from 'echarts';
  import {
    classEvaluationstatistics,
    selectStudentBonusCount,
    classEvaluationlist
  } from '@/api/system/type.js'
  import {
    selectStudents,
    selectClass,
    classEvaluationremove
  } from '@/api/Studentevaluation/Studentevaluation.js'

  export default {
    props: [
      'id'
    ],
    data() {
      return {
        loading: false,
        man: require('@/assets/img/man.png'),
        woman: require('@/assets/img/woman.png'),
        // 筛选相关数据
        selectedStudent: null,
        dateRange: [],
        pickerOptions: {
          shortcuts: [{
            text: '今天',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setDate(start.getDate() - start.getDay());
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '本月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setDate(1);
              picker.$emit('pick', [start, end]);
            }
          }]
        },

        // 原始数据
        rawData: {
          details: []
        },
        selectStudent: [],
        students: [],
        scoreDetail: [],
        typeOne: 0,
        typeTwo: 0,
        selectStudentArr: [],
        items: {
          praise: [],
          improve: []
        },
        sortedArray: [],
        dates: null,
        baseUrl: process.env.VUE_APP_BASE_API,
        timeText: '本周',
        showDatePicker: false,
        classes:[],
        classText:'全部',
        classid:null
      }
    },
    computed: {

    },
    watch: {

    },
    mounted() {
      // this.initChart();
      this.dates = this.getThisWeekDates()
      this.id.startTime = this.dates.startDate
      this.id.endTime = this.dates.endDate
      this.dateRange.push(this.id.startTime)
      this.dateRange.push(this.id.endTime)
      this.classEvaluationstatistics()
      this.selectClassApi()
      this.selectStudentBonusCount(this.id)
    },

    methods: {
      handleCommandClass(command){
        this.classText = command
        for(var i=0;i<this.classes.length;i++){
          if(this.classText == this.classes[i].courseName){
             this.id.courseId = this.classes[i].courseId
          }
        }
        this.classEvaluationstatistics()
         this.selectStudentBonusCount(this.id)
      },
      selectClassApi() {
        selectClass({}).then(response => {
          // this.
          var obj = {
            courseName:'全部',
            courseId:null
          }
           this.classes = response.data
           this.classes.unshift(obj)
           this.classText = this.classes[0].courseName
           this.classid = this.classes[0].classid
           this.id.courseId = this.classid
        });
      },
      handleDelete(detail) {
          this.$confirm('确定删除该记录吗？', '提示', {
            type: 'warning'
          }).then(() => {
            classEvaluationremove({
              mark:detail.mark,
              studentId:detail.studentId,
              recordId:detail.id
            }).then(response => {
              this.$message({
                message: '删除成功！',
                type: 'success'
              });
              this.dates = this.getThisWeekDates()
              this.id.startTime = this.dates.startDate
              this.id.endTime = this.dates.endDate
              this.dateRange.push(this.id.startTime)
              this.dateRange.push(this.id.endTime)
              this.classEvaluationstatistics()
              this.selectStudentBonusCount(this.id)
               // console.log(response, 'this.selectStudent')
            });
          }).catch(() => {});
        },
      handleCommand(command) {
        if (command === 'custom') {
          this.showDatePicker = true
          this.timeText = '自定义',
            this.dateRange = []
        } else {
          this.showDatePicker = false
          this.timeText = command === 'week' ? '本周' : '本月'
          if (this.timeText == '本周') {
            this.dates = this.getThisWeekDates()
            this.id.startTime = this.dates.startDate
            this.id.endTime = this.dates.endDate
          } else {
            this.dates = this.getThisMonthDates()
            this.id.startTime = this.dates.startDate
            this.id.endTime = this.dates.endDate
          }
          this.classEvaluationstatistics()
        }
      },
      getThisMonthDates() {
        const today = new Date(); // 获取当前完整时间（包含时分秒）

        // 获取当月第一天并设置为00:00:00
        const startOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
        startOfMonth.setHours(0, 0, 0, 0);

        // 统一格式化函数（处理完整时间格式）
        const formatDateTime = (date) => {
          const year = date.getFullYear();
          const month = String(date.getMonth() + 1).padStart(2, '0');
          const day = String(date.getDate()).padStart(2, '0');
          const hours = String(date.getHours()).padStart(2, '0');
          const minutes = String(date.getMinutes()).padStart(2, '0');
          const seconds = String(date.getSeconds()).padStart(2, '0');
          return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        };

        return {
          startDate: formatDateTime(startOfMonth), // 当月第一天 00:00:00
          endDate: formatDateTime(today) // 当前完整时间
        };
      },
      dateRangechange() {
        this.id.startTime = this.dateRange[0]
        this.id.endTime = this.dateRange[1]
        this.classEvaluationstatistics()
        // console.log(this.dateRange,'dateRange')
      },
      getThisWeekDates() {
        const today = new Date(); // 获取当前完整时间（包含时分秒）
        const dayOfWeek = today.getDay(); // 0-6，0是周日，1是周一
        let daysAgo = dayOfWeek === 0 ? 6 : dayOfWeek - 1; // 计算到周一的天数差

        // 计算周一日期并设置为00:00:00
        const startOfWeek = new Date(today.getTime() - daysAgo * 24 * 60 * 60 * 1000);
        startOfWeek.setHours(0, 0, 0, 0); // 重置为当天凌晨

        // 今日日期保持当前完整时间（包含用户调用时的时分秒）
        const endOfWeek = today;

        // 统一格式化函数（处理完整时间格式）
        const formatDateTime = (date) => {
          const year = date.getFullYear();
          const month = String(date.getMonth() + 1).padStart(2, '0');
          const day = String(date.getDate()).padStart(2, '0');
          const hours = String(date.getHours()).padStart(2, '0');
          const minutes = String(date.getMinutes()).padStart(2, '0');
          const seconds = String(date.getSeconds()).padStart(2, '0');
          return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        };

        return {
          startDate: formatDateTime(startOfWeek), // 周一 00:00:00
          endDate: formatDateTime(endOfWeek) // 今日当前完整时间
        };
      },
      studentschange(studentId) {
        this.id.studentId = studentId
        this.classEvaluationstatistics()
      },
      classEvaluationlist0() {
        // this.queryParams.type = 0
        classEvaluationlist({
          type: 0
        }).then(response => {
          this.items.praise = response.data
          const countMap = new Map();
          this.selectStudentArr.forEach(item => {
            countMap.set(item.bonusTypeId, item.counts);
          });
          this.items.praise = this.items.praise.map(item => ({
            ...item,
            counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
          }));
          this.classEvaluationlist1()
          // console.log(this.items.praise)
        });
      },
      classEvaluationlist1() {
        // this.queryParams.type = 1
        classEvaluationlist({
          type: 1
        }).then(response => {
          this.items.improve = response.data
          const countMap = new Map();
          this.selectStudentArr.forEach(item => {
            countMap.set(item.bonusTypeId, item.counts);
          });
          this.items.improve = this.items.improve.map(item => ({
            ...item,
            counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
          }));
          const mergedArray = this.items.praise.concat(this.items.improve);

          // 按counts降序排序（注意将字符串转为数字进行比较）
          this.sortedArray = mergedArray.sort((a, b) => {
            const countA = Number(a.counts);
            const countB = Number(b.counts);
            return countB - countA; // 降序排列，大数在前
          });
          // console.log(this.sortedArray)
        });
      },
      selectStudentBonusCount(id) {
        selectStudentBonusCount({
          deptId: id.deptId,
          courseId: id.courseId
        }).then(response => {
          this.selectStudent = response.data
          this.selectStudents(this.id.deptId)
          // console.log(this.selectStudent, 'this.selectStudent')
        });
      },
      selectStudents(deptId) {
        // this.students = []
        selectStudents({
          deptId: deptId
          // courseId: this.id.courseId
        }).then(response => {
          this.students = response.data
          console.log(this.students,'11')
          for (var i = 0; i < this.students.length; i++) {
            this.students[i].counts = 0; // 先初始化为0
            for (var j = 0; j < this.selectStudent.length; j++) {
              if (this.students[i].studentId == this.selectStudent[j].studentId) {
                this.students[i].counts = this.selectStudent[j].counts;
                break; // 找到匹配的就跳出内层循环，避免重复设置
              }
            }
          }
          this.students = this.students.sort((a, b) => {
            const countA = Number(a.counts);
            const countB = Number(b.counts);
            return countB - countA; // 降序排列，大数在前
          });
          this.students.forEach((item, index) => {
            item.rank = index + 1; // 索引从0开始，排名从1开始
          });
          // console.log(this.students,'this.students')
        });
      },
      classEvaluationstatistics() {
        this.loading = true
        classEvaluationstatistics(this.id).then(response => {
          this.rawData.details = response.data.evaluation //点评详情
          this.selectStudentArr = response.data.bonusDetail //点评类型
          // 安全获取并处理数据
          const scoreDetails = response.data.scoreDetail || []; // 确保始终有数组
          const [firstItem = {
            counts: 0
          }, secondItem = {
            counts: 0
          }] = scoreDetails;

          // 转换为数值型并设置默认值
          const safeNumberConvert = (value) => Math.max(0, Number(value) || 0);

          const typeOne = safeNumberConvert(firstItem.counts);
          const typeTwo = safeNumberConvert(secondItem.counts);

          // 赋值操作
          this.scoreDetail = typeOne + typeTwo;
          this.typeOne = typeOne;
          this.typeTwo = typeTwo;

          // 安全初始化图表（传递已校验的数组）
          // console.log(scoreDetails,'scoreDetails')
          this.initChart(scoreDetails);
          this.classEvaluationlist0()
          this.loading = false
        });
      },

      // 初始化图表
      initChart(scoreDetail) {
        const chart = echarts.init(document.getElementById('chartPie'));
        const option = {
          tooltip: {
            trigger: 'item'
          },
          legend: {
            bottom: '5%',
            left: 'center'
          },
          series: [{
            name: '分数分布',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['50%', '40%'],
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 24,
                fontWeight: 'bold',
                // formatter: `111`,
                rich: {
                  total: {
                    fontSize: 28,
                    color: '#E6A23C'
                  },
                  hr: {
                    height: 0,
                    borderWidth: 1,
                    width: '100%'
                  },
                  name: {
                    fontSize: 14,
                    padding: [5, 0]
                  }
                }
              }
            },
            data: [{
                value: scoreDetail[0] ? scoreDetail[0].counts : 0,
                name: '表扬',
                itemStyle: {
                  color: '#67C23A'
                }
              },
              {
                value: scoreDetail[1] ? scoreDetail[1].counts : 0,
                name: '待改进',
                itemStyle: {
                  color: '#F56C6C'
                }
              }
            ]
          }]
        };
        chart.setOption(option);
      }
    }
  }
</script>

<style scoped>
  .detail-item {
    position: relative;
    transition: all 0.3s;
  }

  .actions {
    display: flex;
    align-items: center;
    gap: 8px;
  }

  .delete-btn {
    opacity: 0;
    transition: opacity 0.3s;
  }

  .detail-item:hover .delete-btn {
    opacity: 1;
  }
  .type-card {
    height: 200px;
  }

  .tag-cloud {
    display: flex;
    flex-wrap: nowrap;
    /* 禁止换行 */
    overflow-x: auto;
    /* 横向滚动条 */
    padding: 10px;
    height: 130px;
    /* 固定高度，配合滚动条 */
    /* background: yellow; */
  }

  .type-item {
    display: inline-flex;
    flex-direction: column;
    align-items: center;
    margin: 0 10px;
    /* 项目间隔 */
    white-space: nowrap;
    /* 防止文字换行 */
    min-width: 80px;
    /* 最小宽度 */
  }

  .image-container {
    position: relative;
    width: 60px;
    height: 60px;
  }

  .type-image {
    width: 60px;
    height: 60px;
    object-fit: cover;
    border-radius: 50%;
    /* 图片圆角 */
  }

  .count-badge {
    position: absolute;
    top: -5px;
    right: -5px;
    width: 20px;
    height: 20px;
    background-color: #F56C6C;
    /* 红色背景 */
    color: white;
    border-radius: 50%;
    /* 圆形 */
    font-size: 12px;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    /* 阴影效果 */
  }

  .type-name {
    margin-top: 8px;
    font-size: 14px;
    text-align: center;
    white-space: nowrap;
    /* 防止名称换行 */
    /* overflow: hidden;   */
    text-overflow: ellipsis;
    /* 超出省略 */
    max-width: 80px;
    /* 名称最大宽度 */
    color: black;
  }

  .class-report-container {
    padding: 20px;
    background-color: #f8f9fa;
  }

  /* .type-card{
  height: 200px;
  overflow: auto;
} */
  .detail-card {
    height: 780px;
    /* 自定义高度 */
    overflow: auto;
    /* 溢出显示滚动条 */
  }

  /* 学生列表样式 */
  .student-list-col {
    margin-right: 20px;
  }

  .fixed-height-container {
    height: 1000px;
    /* 自定义高度 */
    overflow: auto;
    /* 溢出显示滚动条 */
  }

  .student-list-card {
    height: 1000px;
    /* 自定义高度 */
    overflow: auto;
    /* 溢出显示滚动条 */
    /* background: yellow; */
  }

  .list-header {
    font-weight: bold;
    color: #409EFF;
  }

  .student-list {
    height: calc(100% - 57px);
    overflow-y: auto;
  }

  .student-item {
    padding: 12px;
    margin: 8px 0;
    display: flex;
    align-items: center;
    cursor: pointer;
    border-radius: 8px;
    transition: all 0.3s;
  }

  .student-item:hover {
    background-color: #ecf5ff;
  }

  .student-item.active {
    background-color: #409EFF;
    color: white;
  }

  .student-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    margin-right: 12px;
  }

  .student-name {
    font-size: 14px;
  }

  /* 图表样式 */
  .chart-card {
    position: relative;
  }

  .fixed-height {
    height: 500px;
    /* 自定义高度 */
    overflow: auto;
    /* 溢出显示滚动条 */
  }

  .chart-footer {
    padding: 15px;
    background: #f8f9fa;
    border-top: 1px solid #ebeef5;
  }

  .total-count {
    display: flex;
    align-items: center;
    justify-content: space-around;
  }

  .total-item .value {
    font-size: 24px;
    color: #E6A23C;
    font-weight: bold;
  }

  .total-item .label {
    color: #909399;
    font-size: 14px;
  }

  /* 排行榜样式 */
  .ranking-card {
    height: 480px;
    margin-top: 20px;
  }

  .ranking-item {
    display: flex;
    align-items: center;
    padding: 12px;
    margin: 8px 0;
    background: #fff;
    border-radius: 8px;
    transition: all 0.3s;
  }

  .ranking-item.top3 {
    background: #fdf6ec;
  }

  .avatar {
    width: 40px;
    height: 40px;
    margin: 0 15px;
  }

  .rank {
    width: 40px;
    text-align: center;
  }

  .info {
    flex: 1;
  }

  .name {
    font-weight: 500;
  }

  .count {
    color: #909399;
    font-size: 12px;
  }

  /* 标签云样式 */
  /* .tag-cloud {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  padding: 10px;
} */
  /* .tag-item {
  margin: 5px;
  font-size: 14px;
  border-radius: 15px;
  padding: 0 12px;
} */

  /* 操作明细样式 */
  .detail-item {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px;
    margin: 8px 0;
    background: #fff;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  }

  .teacher-avatar {
    width: 32px;
    height: 32px;
    margin-right: 12px;
  }

  .content {
    display: flex;
    align-items: center;
  }

  .main-text {
    font-size: 14px;
  }

  .time {
    color: #909399;
    font-size: 12px;
  }

  .score {
    font-weight: bold;
    min-width: 50px;
    text-align: center;
  }

  /* 公共样式 */
  .mb-20 {
    margin-bottom: 20px;
  }

  .card-header {
    font-weight: bold;
    display: flex;
    align-items: center;
  }

  .card-header i {
    margin-right: 8px;
    font-size: 18px;
  }
</style>
