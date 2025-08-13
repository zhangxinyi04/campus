<template>
  <div v-if="$store.state.Floating.userIds !== null">
    <transition name="empty-fade">
      <div class="empty-state-tip">
        <i class="fa-regular fa-file-circle-xmark empty-icon"></i>
        <div class="empty-content">
          <h3 class="empty-title">页面以浮窗形式展示</h3>
          <p class="empty-desc">如需恢复正常，关掉浮窗即可</p>
        </div>
      </div>
    </transition>
  </div>
  <div class="student-review-container" v-else>
    <!-- 功能操作栏 -->
    <div class="control-bar">
      <el-button type="success" @click="toggleMultiSelect" icon="el-icon-check">
        {{ isMultiSelect ? '退出多选' : '多选点评' }}
      </el-button>
      <!-- <el-button type="primary" @click="SelectSelect" icon="el-icon-star-off" v-if="isMultiSelect">
        去点评
      </el-button> -->

      <!-- <el-button type="primary" @click="selectAll" v-if="isMultiSelect">
          全选
        </el-button>
        <el-button type="danger" @click="deselectAll" v-if="isMultiSelect">
          取消全选
        </el-button> -->
      <el-button :type="isAllSelected ? 'danger' : 'primary'" @click="toggleSelectAll" v-if="isMultiSelect"
        icon="el-icon-finished">
        {{ isAllSelected ? '取消全选' : '全选' }}
      </el-button>

      <el-button type="primary" @click="handleBatchReview" icon="el-icon-star-off" v-if="isMultiSelect">
        去点评
      </el-button>

      <!-- <el-button type="primary" @click="randomSelect" icon="el-icon-star-off" v-if="!isMultiSelect">
        随机评选
      </el-button> -->
      <el-button type="primary" @click="openRandomCountDialog" icon="el-icon-star-off" v-if="!isMultiSelect">
        随机评选
      </el-button>
      <el-button type="warning" @click="showTimerclick" icon="el-icon-timer" v-if="!isMultiSelect">
        计时器 ({{ formattedTime }})
      </el-button>
      <el-button type="info" @click="toggleFloating">
        浮窗
      </el-button>
    </div>

    <!-- 学生卡片列表 -->
    <transition-group name="list" tag="div" class="student-list" >
      <div v-for="student in students" :key="student.studentId" class="student-card"
        :class="{ 'selected': selectedIds.includes(student.studentId) }"
        @click="handleSelect(student.studentId,student)">
        <div v-if="isMultiSelect" class="checkbox-wrapper">
          <el-checkbox v-model="selectedIds" :label="student.studentId"></el-checkbox>
        </div>
        <div class="item-badge">{{ student.counts }}</div>
        <img :src="student.sex ==='0' ? man : woman" alt="" style="width: 60px;height: 60px;">
        <h3 class="name">{{ student.studentName }}</h3>
      </div>
    </transition-group>
    <!-- 学生小组卡片列表 -->
  <!--  <transition-group name="list" tag="div" class="student-list">
      <div v-for="student in studentsgroup" :key="student.studentId" class="student-card"
        :class="{ 'selected': selectedIds.includes(student.studentId) }"
        @click="handleSelect(student.studentId,student)">
        <div v-if="isMultiSelect" class="checkbox-wrapper">
          <el-checkbox v-model="selectedIds" :label="student.studentId"></el-checkbox>
        </div>
        <div class="item-badge">{{ student.counts }}</div>
        <img :src="student.sex ==='0' ? man : woman" alt="" style="width: 60px;height: 60px;">
        <h3 class="name">{{ student.studentName }}</h3>
      </div>
    </transition-group> -->

    <el-dialog title="选择评选人数" :visible.sync="showRandomCountDialog" width="30%">
      <el-form :model="randomForm" :rules="randomRules" ref="randomForm">
        <el-form-item label="选择人数" prop="count">
          <el-input-number v-model="randomForm.count" :min="1" :max="students.length"
            controls-position="right"></el-input-number>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showRandomCountDialog = false">取消</el-button>
        <el-button type="primary" @click="confirmRandomCount">确定</el-button>
      </template>
    </el-dialog>

    <!-- 计时器设置弹窗 -->
    <el-dialog title="计时器设置" :visible.sync="showTimerDialog" width="35%">
      <div class="timer-dialog">
        <div class="input-group" v-if="!timerDialog">
          <div class="time-input-item">
            <label>时</label>
            <el-input-number v-model="setHours" :min="0" :max="23" controls-position="right"
              class="time-input"></el-input-number>
          </div>
          <div class="time-input-item">
            <label>分</label>
            <el-input-number v-model="setMinutes" :min="0" :max="59" controls-position="right"
              class="time-input"></el-input-number>
          </div>
          <div class="time-input-item">
            <label>秒</label>
            <el-input-number v-model="setSeconds" :min="0" :max="59" controls-position="right"
              class="time-input"></el-input-number>
          </div>
        </div>
        <div class="timer-display" v-if="timerDialog">
          <h1 :class="{ paused: isPaused }">{{ formattedTime }}</h1>
        </div>
        <div class="timer-controls">
          <el-button @click="resetTimer">重置</el-button>
          <el-button type="primary" @click="toggleTimer" v-if="timerActive">
            {{ isPaused ? '继续' : '暂停' }}
          </el-button>
          <el-button type="primary" @click="startTimer" v-if="!timerActive">
            开始
          </el-button>
        </div>
      </div>
    </el-dialog>

    <!-- 随机评选结果 -->
    <!-- <transition name="bounce">
      <div v-if="showRandomResult" class="random-result">
        <div class="result-content">
          <h2>🎉 本次随机选中 🎉</h2>
          <img :src="randomStudent.sex ==='0' ? man : woman" alt="" style="width: 60px;height: 60px;">
          <h3>{{ randomStudent.studentName }}</h3>
          <el-button type="primary" @click="handleSelect(randomStudent.studentId)">去点评</el-button>
          <el-button @click="showRandomResult = false">取消</el-button>
        </div>
      </div>
    </transition> -->
    <transition name="bounce">
      <div v-if="showRandomResult" class="random-result">
        <div class="result-content">
          <h2>🎉 本次随机选中 {{ randomStudents.length }} 人 🎉</h2>
          <div class="random-students-list">
            <div v-for="student in randomStudents" :key="student.studentId" class="random-student-item">
              <img :src="student.sex ==='0' ? man : woman" alt="" style="width: 60px;height: 60px;">
              <h3>{{ student.studentName }}</h3>
            </div>
          </div>
          <el-button type="primary" @click="handleBatchSelect">批量去点评</el-button>
          <el-button @click="showRandomResult = false">取消</el-button>
        </div>
      </div>
    </transition>


    <!-- 点评弹窗组件 -->
    <el-dialog title="学生点评" :visible.sync="dialogVisible" width="70%" :before-close="handleClose">
      <Studentreviews :obj='obj' v-if="dialogVisible" @message="receiveMessage"></Studentreviews>
    </el-dialog>

  </div>
</template>

<script>
  import Studentreviews from './component/Studentreviews.vue'
  import {
    selectClass,
    selectStudents,
  } from '@/api/Studentevaluation/Studentevaluation.js'
  import {
    selectStudentBonusCount,
    classEvaluationlist,
    classEvaluation
  } from '@/api/system/type.js'

  // 生成20条学生数据
  const generateStudents = () => {
    const names = ['陈小明', '李雨欣', '张浩然', '王诗雨', '刘子轩',
      '赵思雅', '黄俊杰', '周若雪', '吴天宇', '徐梦琪',
      '孙宇航', '马欣怡', '朱博文', '胡静雯', '郭子豪',
      '林晨曦', '何佳怡', '高俊熙', '罗雨萱', '梁志强'
    ];
    return names.map((name, index) => ({
      id: index + 1,
      name,
      avatar: `https://picsum.photos/100?random=${index}`, // 随机头像
      reviewCount: Math.floor(Math.random() * 10) // 随机点评次数
    }));
  };

  export default {
    components: {
      Studentreviews
    },
    props: [
      'id'
    ],
    data() {
      return {

        timerDialog: false,
        man: require('@/assets/img/man.png'),
        woman: require('@/assets/img/woman.png'),
        dialogVisible: false,
        isMultiSelect: false,
        selectedIds: [],
        showTimerDialog: false,
        timerActive: false,
        seconds: 0,
        timer: null,
        setHours: 0, // 新增小时设置
        setMinutes: 0, // 保留分钟设置
        setSeconds: 0, // 新增秒设置
        showRandomResult: false,
        randomStudent: null,
        students: generateStudents(),
        obj: [],
        selectStudent: [],
        isPaused: false,
        isFloating: false,
        userIds: null,
        classEvaluationlistArr: {},
        showRandomCountDialog: false,
        randomForm: {
          count: 1
        },
        randomRules: {
          count: [{
            required: true,
            message: '请选择评选人数',
            trigger: 'blur'
          }]
        },
        randomStudents: [], // 存储多个随机学生
        studentsgroup: []
      }
    },
    computed: {
      formattedTime() {
        const hours = Math.floor(this.seconds / 3600);
        const minutes = Math.floor((this.seconds % 3600) / 60);
        const secs = this.seconds % 60;
        return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
      },
      isAllSelected() {
        return this.selectedIds.length === this.students.length &&
          this.students.length > 0
      }
    },
    created() {
      // this.userIds = localStorage.getItem('userIds');
      // console.log(this.userIds)
    },
    mounted() {
      // this.$store.commit('Floating/increment',JSON.stringify(this.id))
      // this.id = this.$store.state.Floating.userIds
      // console.log(this.id,'JSON.parse(this.id)JSON.parse(this.id)')
      this.selectStudentBonusCount(this.id)
      this.classEvaluationlist0()
      // console.log(this.id)
    },

    methods: {
      toggleSelectAll() {
        if (this.isAllSelected) {
          this.selectedIds = []
        } else {
          this.selectedIds = this.students.map(student => student.studentId)
        }
      },
      handleBatchReview() {
        if (this.selectedIds.length === 0) {
          this.$message({
            message: '请先选择需要点评的学生',
            type: 'warning',
            duration: 2000
          });
          return;
        }
        this.SelectSelect(); // 调用原来的点评逻辑
      },
      selectAll() {
        this.selectedIds = this.students.map(student => student.studentId);
      },

      // 取消全选方法
      deselectAll() {
        this.selectedIds = [];
      },
      // 新增：打开人数选择弹窗
      openRandomCountDialog() {
        this.randomForm.count = 1 // 重置输入框
        this.$refs.randomForm && this.$refs.randomForm.resetFields()
        this.showRandomCountDialog = true
      },

      // 新增：确认选择人数
      confirmRandomCount() {
        this.$refs.randomForm.validate(valid => {
          if (valid) {
            this.showRandomCountDialog = false
            this.randomSelect(this.randomForm.count)
          }
        })
      },
      handleBatchSelect() {
        // 将选中的学生ID存入selectedIds（支持多选模式）
        this.selectedIds = this.randomStudents.map(student => student.studentId)
        // 自动切换到多选模式
        this.isMultiSelect = true
        // 关闭结果弹窗
        this.showRandomResult = false
        // 触发去点评逻辑（可根据需求调整）
        this.SelectSelect()
      },
      toggleFloating() {
        // console.log(this.$store.state.Floating,'this.$store.Floating')
        this.$store.commit('Floating/increment', JSON.stringify(this.id))
        // this.userIds = localStorage.setItem('userIds', JSON.stringify(this.id));
      },
      toggleTimer() {
        if (this.timerActive) {
          this.isPaused = !this.isPaused;
          if (this.isPaused) {
            clearInterval(this.timer);
          } else {
            this.startTimer(false); // 传入false表示继续而不是重新开始
          }
        }
      },
      showTimerclick() {
        // console.log(this.formattedTime,'1')
        // return
        if (this.formattedTime === '00:00:00') {
          console.log('wushuru')
          this.showTimerDialog = true
          this.timerDialog = false
        } else {
          // console.log('22222')
          this.showTimerDialog = true
          this.timerDialog = true
        }

      },
      receiveMessage(msg) {
        if (msg == 'ok') {
          this.selectStudentBonusCount(this.id)
        }
      },
      SelectSelect() {
        this.obj = []
        var objs = this.id
        this.obj = this.selectedIds.map(id => {
          return {
            deptId: objs.deptId,
            studentId: id,
            courseId: objs.courseId
          };
        });
        // console.log(this.obj,'this.obj')
        this.dialogVisible = true

      },
      // selectClass(selectedClass) {
      //   this.showClassSelect = false
      //   // 这里可以调用API获取该班级的学生数据
      //   this.$message.success(`已选择班级：${selectedClass.className}`)
      // },
      selectStudentBonusCount(id) {
        selectStudentBonusCount({
          deptId: id.deptId,
          courseId: id.courseId
        }).then(response => {
          this.selectStudent = response.data
          this.selectStudents(this.id.deptId)
          // console.log(response, 'response22222nse')
        });
      },
      groupStudents(students) {
        return students.reduce((acc, student) => {
          const group = student.group;
          acc[group] = acc[group] || [];
          acc[group].push(student);
          return acc;
        }, {});
      },
      selectStudents(deptId) {
        selectStudents({
          deptId: deptId
        }).then(response => {
          this.students = response.data
          // this.studentsgroup = this.groupStudents(this.students)
          // console.log(this.studentsgroup, 'this.studentsgroup')
          for (var i = 0; i < this.students.length; i++) {
            this.students[i].counts = 0; // 先初始化为0
            for (var j = 0; j < this.selectStudent.length; j++) {
              if (this.students[i].studentId == this.selectStudent[j].studentId) {
                this.students[i].counts = this.selectStudent[j].counts;
                break; // 找到匹配的就跳出内层循环，避免重复设置
              }
            }
          }
          // console.log(this.students,'this.students')
        });
      },
      // selectClass() {
      //   selectClass({}).then(response => {
      //     this.selectStudents('210')
      //     console.log(response, 'responseresponse')
      //   });
      // },
      handleClose(done) {
        done();
      },
      toggleMultiSelect() {
        this.isMultiSelect = !this.isMultiSelect;
        this.selectedIds = [];
      },
      classEvaluationlist0() {
        classEvaluationlist({
          type: 0
        }).then(response => {
          this.classEvaluationlistArr = response.data
        });
      },
      handleSelect(id, student) {

        if (this.$store.state.Floating.count) {
          var objs = {}
          var arr = []
          objs.deptId = this.id.deptId
          objs.courseId = this.id.courseId
          objs.studentId = id
          objs.bonusTypeId = this.classEvaluationlistArr[0] ? this.classEvaluationlistArr[0].bonusTypeId : ''
          objs.type = this.classEvaluationlistArr[0] ? this.classEvaluationlistArr[0].type : ''
          objs.mark = this.classEvaluationlistArr[0] ? this.classEvaluationlistArr[0].mark : ''
          arr.push(objs)
          classEvaluation(arr).then(response => {
            this.$message({
              message: arr[0].type === '0' ?
                `${student.studentName} +${arr[0].mark}` : `${student.studentName} -${arr[0].mark}`,
              type: arr[0].type === '0' ? 'success' : 'warning'
            });
            this.$emit('message', 'ok')
            this.selectStudentBonusCount(this.id)
            // this.showTextreview = false;
          })
        } else {
          this.obj = []
          var objs = this.id
          objs.studentId = id
          this.obj.push(objs)
          if (!this.isMultiSelect) {
            this.showRandomResult = false
            this.dialogVisible = true
          } else {
            // this.selectedIds = []
            const index = this.selectedIds.indexOf(id);
            index > -1 ? this.selectedIds.splice(index, 1) : this.selectedIds.push(id)
          };
        }

      },
      randomSelect(count) {
        // 打乱学生数组顺序
        const shuffled = [...this.students].sort(() => 0.5 - Math.random())
        // 取前count个学生
        this.randomStudents = shuffled.slice(0, count)
        this.showRandomResult = true
      },

      // randomSelect() {
      //   const randomIndex = Math.floor(Math.random() * this.students.length);
      //   this.randomStudent = this.students[randomIndex];
      //   this.showRandomResult = true;
      // },
      startTimer(isNewStart = true) {
        if (isNewStart) {
          // 只有全新开始时才重置时间
          const totalSeconds = this.setHours * 3600 + this.setMinutes * 60 + this.setSeconds;
          this.seconds = totalSeconds;
        }

        this.timerActive = true;
        this.isPaused = false;
        this.timerDialog = true;

        if (this.timer) clearInterval(this.timer);
        this.timer = setInterval(() => {
          if (this.seconds <= 0) {
            clearInterval(this.timer);
            this.$message.success('时间到！');
            this.timerActive = false;
            return;
          }
          this.seconds--;
        }, 1000);
      },
      resetTimer() {
        this.setHours = 0, // 新增小时设置
          this.setMinutes = 0,
          this.setSeconds = 0,
          clearInterval(this.timer);
        this.timerActive = false;
        this.isPaused = false;
        this.timerDialog = false;
        const totalSeconds = this.setHours * 3600 + this.setMinutes * 60 + this.setSeconds;
        this.seconds = totalSeconds;
      },
      // toggleTimer() {
      //   this.timerActive = !this.timerActive;
      //   if (!this.timerActive) clearInterval(this.timer);
      // }
    },
    beforeDestroy() {
      clearInterval(this.timer);
    }
  }
</script>

<style lang="scss" scoped>
  .control-bar .el-button {
    margin: 0 8px 8px 0;
  }

  .random-students-list {
    display: flex;
    gap: 20px;
    justify-content: center;
    margin: 20px 0;
  }

  .random-student-item {
    text-align: center;
  }

  .random-student-item h3 {
    margin: 10px 0 0;
    font-size: 14px;
    color: #333;
  }

  .empty-state-tip {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    min-width: 320px;
    padding: 24px;
    background: #f8fafc;
    /* 浅灰背景更柔和 */
    border-radius: 12px;
    // text-align: center;
    box-shadow: 0 2px 16px rgba(0, 0, 0, 0.06);
    z-index: 1000;
  }

  /* 空状态图标 */
  .empty-icon {
    font-size: 48px;
    color: #94a3b8;
    /* 中性灰图标 */
    margin-bottom: 16px;
  }

  /* 标题文字 */
  .empty-title {
    margin: 0 0 8px 0;
    font-size: 18px;
    font-weight: 500;
    color: #334155;
    /* 深灰主标题 */
  }

  /* 描述文字 */
  .empty-desc {
    margin: 0;
    font-size: 14px;
    color: #64748b;
    /* 次深灰描述 */
    line-height: 1.6;
  }

  /* 淡入过渡动画 */
  .empty-fade-enter-active,
  .empty-fade-leave-active {
    transition: all 0.3s ease;
  }

  .empty-fade-enter-from,
  .empty-fade-leave-to {
    opacity: 0;
    transform: translate(-50%, -50%) scale(0.95);
  }

  .empty-fade-enter-to,
  .empty-fade-leave-from {
    opacity: 1;
    transform: translate(-50%, -50%) scale(1);
  }

  /* 样式部分（需要添加的浮窗模式样式） */
  .main-container {
    transition: all 0.3s ease;
    min-height: 100vh;
    padding: 20px;
  }

  .floating-mode {
    position: fixed;
    right: 20px;
    bottom: 20px;
    width: 300px;
    height: 200px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    overflow: auto;
    z-index: 1000;
  }

  .timer-display {
    text-align: center;
    padding: 20px 0;
    margin: 15px 0;
    background: rgba(255, 255, 255, 0.9);
    border-radius: 12px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;

    h1 {
      font-family: 'Monospace', sans-serif;
      font-size: 4.5rem;
      color: #409EFF;
      margin: 0;
      text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
      letter-spacing: 2px;
      transition: all 0.3s ease;

      &.paused {
        color: #E6A23C;
        animation: pulse 1.5s infinite;
      }

      // 数字跳动动画
      @keyframes digitChange {
        0% {
          transform: scale(1);
        }

        50% {
          transform: scale(1.05);
        }

        100% {
          transform: scale(1);
        }
      }

      // 为每个数字单独添加动画
      span {
        display: inline-block;
        min-width: 0.6em;
        animation: digitChange 0.3s ease;
      }
    }

    // 响应式调整
    @media (max-width: 768px) {
      h1 {
        font-size: 3.5rem;
      }
    }

    @media (max-width: 480px) {
      h1 {
        font-size: 2.8rem;
        letter-spacing: 1px;
      }
    }

    // 暂停状态动画
    @keyframes pulse {
      0% {
        opacity: 1;
      }

      50% {
        opacity: 0.6;
      }

      100% {
        opacity: 1;
      }
    }
  }

  .student-review-container {
    margin-top: -20px;
    padding: 20px;
    // background: linear-gradient(135deg, #e3f2fd 0%, #f8f9fa 100%),
    //   repeating-linear-gradient(-45deg,
    //     rgba(149, 202, 255, 0.1) 0px,
    //     rgba(149, 202, 255, 0.1) 20px,
    //     transparent 20px,
    //     transparent 40px);
    background: #fff;
    min-height: 100vh;
  }

  .control-bar {
    text-align: center;
    margin-bottom: 20px;

    .el-button {
      padding: 10px 20px;
      margin: 0 10px;
      border-radius: 15px;
      transition: transform 0.2s;

      &:hover {
        transform: translateY(-2px);
      }
    }
  }

  .student-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(110px, 1fr));
    gap: 25px;
    padding: 20px;
  }

  .student-card {
    position: relative;
    background: white;
    border-radius: 15px;
    padding: 20px;
    text-align: center;
    transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
    box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12);
    cursor: pointer;
    overflow: visible;
    z-index: 1;

    &::before {
      content: '';
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(64, 158, 255, 0.2);
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    &:hover {
      transform: translateY(-10px) scale(1.03);
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.15);

      &::before {
        opacity: 1;
      }

      .item-badge {
        transform: scale(1.2) translateY(-5px);
      }
    }

    &.selected {
      background: linear-gradient(135deg, #e8f4ff 0%, #ffffff 100%);
      border: 2px solid #409EFF;
      animation: float 3s ease-in-out infinite;

      &::after {
        content: '✓';
        position: absolute;
        top: -10px;
        right: -10px;
        width: 30px;
        height: 30px;
        background: #409EFF;
        color: white;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 18px;
        box-shadow: 0 2px 5px rgba(64, 158, 255, 0.3);
      }
    }
  }

  @keyframes float {

    0%,
    100% {
      transform: translateY(0);
    }

    50% {
      transform: translateY(-8px);
    }
  }

  .item-badge {
    transition: all 0.3s ease;
    box-shadow: 0 2px 5px rgba(255, 71, 87, 0.3);
  }

  /* 响应式调整 */
  @media (max-width: 768px) {
    .student-list {
      grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
      gap: 15px;
    }

    .student-card {
      padding: 15px;

      &:hover {
        transform: translateY(-5px) scale(1.02);
      }
    }
  }

  .checkbox-wrapper {
    position: absolute;
    top: 8px;
    left: 8px;
  }

  .timer-dialog {
    text-align: center;

    .input-group {
      display: flex;
      justify-content: space-between;
      gap: 12px;
      padding: 8px 0;

      .time-input-item {
        flex: 1;
        display: flex;
        flex-direction: column;
        align-items: center;

        label {
          color: #606266;
          font-size: 14px;
          margin-bottom: 6px;
          font-weight: 500;
        }

        .time-input {
          width: 100%;

          ::v-deep .el-input-number__decrease,
          ::v-deep .el-input-number__increase {
            background: #f5f7fa;
            width: 28px;
          }

          ::v-deep .el-input__inner {
            text-align: center;
            padding-left: 15px;
            padding-right: 15px;
          }
        }
      }

      @media (max-width: 768px) {
        flex-direction: column;

        .time-input-item {
          width: 100%;
        }
      }
    }

    .timer-controls {
      display: flex;
      justify-content: space-around;
    }
  }

  .random-result {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 9999;

    .result-content {
      background: white;
      padding: 30px 40px;
      border-radius: 20px;
      text-align: center;

      h2 {
        color: #409EFF;
        margin: 0 0 15px;
      }

      h3 {
        margin: 15px 0;
      }
    }
  }

  /* 动画效果 */
  .bounce-enter-active {
    animation: bounce-in 0.5s;
  }

  .bounce-leave-active {
    animation: bounce-in 0.5s reverse;
  }

  @keyframes bounce-in {
    0% {
      transform: scale(0);
    }

    50% {
      transform: scale(1.1);
    }

    100% {
      transform: scale(1);
    }
  }

  .list-enter-active,
  .list-leave-active {
    transition: all 0.4s ease;
  }

  .list-enter,
  .list-leave-to {
    opacity: 0;
    transform: translateY(20px);
  }

  .item-badge {
    position: absolute;
    right: 16px;
    top: 10px;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: #ff4757;
    color: white;
    font-size: 14px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
</style>
