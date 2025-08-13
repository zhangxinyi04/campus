<template>
  <div class="student-review-container">
    <!-- 功能操作栏 -->
    <div class="control-bar">
      <el-button type="success" @click="toggleMultiSelect" icon="el-icon-check">
        {{ isMultiSelect ? '退出多选' : '多选点评' }}
      </el-button>
      <el-button type="primary" @click="SelectSelect" icon="el-icon-star-off" v-if="isMultiSelect">
        去点评
      </el-button>
      <el-button type="primary" @click="randomSelect" icon="el-icon-star-off" v-if="!isMultiSelect">
        随机评选
      </el-button>
      <el-button type="warning" @click="showTimerclick" icon="el-icon-timer" v-if="!isMultiSelect">
        计时器 ({{ formattedTime }})
      </el-button>
    </div>

    <!-- 学生卡片列表 -->
    <transition-group name="list" tag="div" class="student-list">
      <div v-for="student in students" :key="student.studentId" class="student-card"
        :class="{ 'selected': selectedIds.includes(student.studentId) }" @click="handleSelect(student.studentId)">
        <div v-if="isMultiSelect" class="checkbox-wrapper">
          <el-checkbox v-model="selectedIds" :label="student.studentId"></el-checkbox>
        </div>
        <div class="item-badge">{{ student.counts }}</div>
        <img :src="student.sex ==='0' ? man : woman" alt="" style="width: 60px;height: 60px;">
        <h3 class="name">{{ student.studentName }}</h3>
      </div>
    </transition-group>

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
    <transition name="bounce">
      <div v-if="showRandomResult" class="random-result">
        <div class="result-content">
          <h2>🎉 本次随机选中 🎉</h2>
          <img :src="randomStudent.sex ==='0' ? man : woman" alt="" style="width: 60px;height: 60px;">
          <!-- <el-avatar :size="100" :src="randomStudent.avatar"></el-avatar> -->
          <h3>{{ randomStudent.studentName }}</h3>
          <el-button type="primary" @click="handleSelect(randomStudent.studentId)">去点评</el-button>
          <el-button @click="showRandomResult = false">取消</el-button>
        </div>
      </div>
    </transition>


    <!-- 点评弹窗组件 -->
    <el-dialog title="学生点评" :visible.sync="dialogVisible" width="70%" :before-close="handleClose" >
      <Studentreviews :obj='obj' v-if="dialogVisible" @message="receiveMessage" style="z-index: 1000000000;"></Studentreviews>
    </el-dialog>

  </div>
</template>

<script>
  import Studentreviews from './component/Studentreviews.vue'
  import {
    classEvaluation,
    selectClass,
    selectStudents,
  } from '@/api/Studentevaluation/Studentevaluation.js'
  import {
    selectStudentBonusCount
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
    computed: {
      formattedTime() {
        const hours = Math.floor(this.seconds / 3600);
        const minutes = Math.floor((this.seconds % 3600) / 60);
        const secs = this.seconds % 60;
        return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
      },
  floatingUserIds() {
    return this.$store.state.Floating.userIds;
  }
    },
    // props: [
    //   'id'
    // ],
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
        setMinutes: 5, // 保留分钟设置
        setSeconds: 0, // 新增秒设置
        showRandomResult: false,
        randomStudent: null,
        students: generateStudents(),
        obj: [],
        selectStudent: [],
        isPaused: false,
         deptId: null,
        courseId: null,
        id:null
      }
    },

    created() {
      // const storedData = localStorage.getItem('userIds');
       // this.selectStudentBonusCount(2)
      // console.log(storedData,'1111')
      // this.FloatingCom()
    },
    watch: {
       // floatingUserIds(newVal) {
       //      if (!newVal) return; // 避免空值解析
       //      try {
       //        this.selectStudentBonusCount(JSON.parse(newVal));
       //      } catch (error) {
       //        console.error("解析 userIds 失败:", error);
       //      }
       //    }
    },
    mounted() {
      this.id = this.$store.state.Floating.userIds
      // console.log(JSON.parse(this.id),'JSON.parse(this.id)')
      this.selectStudentBonusCount(JSON.parse(this.id))
    },
    methods: {
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
        this.showTimerDialog = true
        this.timerDialog = false
      },
      receiveMessage(msg) {
        if (msg == 'ok') {
          this.selectStudentBonusCount(JSON.parse(this.id))
        }
      },
      SelectSelect() {
        this.obj = []
        var objs = JSON.parse(this.id)
        console.log(this.obj,'this.obj')
        this.obj = this.selectedIds.map(id => {
          return {
            deptId: objs.deptId,
            studentId: id,
            courseId: objs.courseId
          };
        });
        // console.log( this.obj,'11')
        this.dialogVisible = true

      },
      // selectClass(selectedClass) {
      //   this.showClassSelect = false
      //   // 这里可以调用API获取该班级的学生数据
      //   this.$message.success(`已选择班级：${selectedClass.className}`)
      // },
      selectStudentBonusCount(id) {
        // console.log(id)
        selectStudentBonusCount({
          deptId: id.deptId,
          courseId: id.courseId
        }).then(response => {
          this.selectStudent = response.data
          this.selectStudents(id.deptId)
          // console.log(response, 'response22222nse')
        });
      },
      selectStudents(deptId) {
        selectStudents({
          deptId: deptId
        }).then(response => {
          this.students = []
          this.students = response.data
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
      handleSelect(id) {
        this.obj = []
        var objs = JSON.parse(this.id)
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
      },
      randomSelect() {
        const randomIndex = Math.floor(Math.random() * this.students.length);
        this.randomStudent = this.students[randomIndex];
        this.showRandomResult = true;
      },
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
    padding: 20px;
    // background: linear-gradient(135deg, #e3f2fd 0%, #f8f9fa 100%),
    //   repeating-linear-gradient(-45deg,
    //     rgba(149, 202, 255, 0.1) 0px,
    //     rgba(149, 202, 255, 0.1) 20px,
    //     transparent 20px,
    //     transparent 40px);
    background:#fff;
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
    box-shadow: 0 1px 3px rgba(0,0,0,0.12);
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
      box-shadow: 0 10px 30px rgba(64,158,255,0.2);
      opacity: 0;
      transition: opacity 0.3s ease;
    }

    &:hover {
      transform: translateY(-10px) scale(1.03);
      box-shadow: 0 10px 20px rgba(0,0,0,0.15);

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
        box-shadow: 0 2px 5px rgba(64,158,255,0.3);
      }
    }
  }

  @keyframes float {
    0%, 100% { transform: translateY(0); }
    50% { transform: translateY(-8px); }
  }

  .item-badge {
    transition: all 0.3s ease;
    box-shadow: 0 2px 5px rgba(255,71,87,0.3);
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
