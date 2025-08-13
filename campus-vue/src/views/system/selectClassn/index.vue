<template>
  <div v-if="showClassSelect" class="class-select-container">
    <div class="school-header">
      <h1>欢迎使用课堂点评系统</h1>
      <div class="school-icon">
        <i class="el-icon-school"></i>
      </div>
    </div>

    <div class="class-list-container">
      <h2 class="select-title">请选择您的班级</h2>
      <div class="class-grid" v-if="classes.length > 0">
        <div v-for="cls in classes" :key="cls.id" class="class-card">
          <!-- <div v-for="(item,index) in cls.depts" :key="index" > -->
          <div class="class-icon">
            <i class="el-icon-notebook-2"></i>
          </div>
          <div class="class-info">
            <h3>{{ cls.courseName }}</h3>
            <p class="dept-tag" @click="selectClass(item, cls)" v-for="(item, index) in cls.depts" :key="index">
              {{ item.deptName }}
            </p>
          </div>
        </div>
      </div>
      <div v-else class="empty-state">
        <!-- <i class="el-icon-info-circle" style="font-size: 4rem; color: #909399; margin-bottom: 20px;"></i> -->
        <p style="font-size: 1.2rem; color: #606266; line-height: 1.5;">暂无班级信息，请联系管理员</p>
      </div>
    </div>

  </div>

  <div v-else class="app-container home">
    <!-- 返回按钮 -->
    <div class="back-button">
      <el-button @click="goBackToClassSelect">返回选择班级</el-button>
      <el-switch v-model="isClassMode" :active-text="'上课模式'" @change="handleClassModeChange"
        style="margin-left: 15px" />
      <el-switch v-model="groupmodel" :active-text="'小组模式'" @change="groupmodelChange" style="margin-left: 15px" />
      <el-switch v-model="Taskmode" :active-text="'任务模式'" @change="TaskmodeChange" style="margin-left: 15px" />
       <!-- <el-button type="primary" plain style="margin-left: 15px" v-if="$store.state.Floating.Taskmode == true" @click="groupmanagement">重制列表</el-button> -->
      <el-button type="primary" plain style="float: right;" @click="groupmanagement">小组管理</el-button>
      <!-- <el-button @click="goBackToClassSelect">小组管理</el-button> -->
    </div>
    <!-- Tab导航 -->
    <div class="tabs-container">
      <div v-for="tab in tabs" :key="tab.id" class="tab-item" :class="{ 'active': activeTab === tab.id }"
        @click="switchTab(tab.id)">
        {{ tab.label }}
        <div class="tab-indicator"></div>
      </div>
    </div>


    <el-dialog title="小组管理" :visible.sync="dialogVisible" width="60%" :before-close="handleClose">
      <groupmanagement :id='id'></groupmanagement>
    </el-dialog>

    <!-- 内容区域带动画 -->
    <transition :name="transitionName" mode="out-in">
      <div v-if="activeTab === 'student'" key="student" class="tab-content">
        <Studentevaluation :id='id'
          v-if="$store.state.Floating.groupmodel == false && $store.state.Floating.Taskmode == false" />
        <groupmodel :id='id'
          v-if="$store.state.Floating.groupmodel == true && $store.state.Floating.Taskmode == false" />
        <Taskmode :id='id'
          v-if="$store.state.Floating.Taskmode == true && $store.state.Floating.groupmodel == false" />
        <!-- <FloatingWindow  :id='id'  /> -->
      </div>
      <div v-if="activeTab === 'report'" key="report" class="tab-content">
        <ClassReport :id='id' />
      </div>
    </transition>
  </div>
</template>

<script>
  import Studentevaluation from '../../Studentevaluation/index.vue'
  import FloatingWindow from '../../Studentevaluation/FloatingWindow.vue'
  import ClassReport from '../../Studentevaluation/ClassReport.vue'
  import groupmanagement from '../../Studentevaluation/groupmanagement.vue'
  import groupmodel from '../../Studentevaluation/groupmodel.vue'
  import Taskmode from '../../Studentevaluation/Taskmode.vue'

  import {
    classEvaluation,
    selectClass,
    selectStudents
  } from '@/api/Studentevaluation/Studentevaluation.js'

  export default {
    name: "Index",
    components: {
      Studentevaluation,
      ClassReport,
      FloatingWindow,
      groupmanagement,
      groupmodel,
      Taskmode
    },
    data() {
      return {
        Taskmode: false,
        groupmodel: false,
        dialogVisible: false,
        isClassMode: false,
        id: {
          courseId: null,
          deptId: null
        },
        showClassSelect: true,
        classes: [],
        activeTab: 'student',
        transitionName: 'slide-left',
        tabs: [{
            id: 'student',
            label: '点评学生'
          },
          {
            id: 'report',
            label: '班级报告'
          }
        ]
      };
    },
    mounted() {
      // if(this.$store.state.Floating.userIds == null){
      //   this.showClassSelect = true
      // }else{
      //    this.showClassSelect = false
      // }
      this.$store.commit('Floating/increment', null)
      this.selectClassApi()
    },
    methods: {
      TaskmodeChange(val) {
        this.$store.commit('Floating/TaskmodeClick', val)
        this.$store.commit('Floating/groupmodelClick', false)
        this.$message({
          type: 'success',
          message: `已${val ? '开启' : '关闭'}任务模式`
        });
      },
      groupmodelChange(val) {
        this.$store.commit('Floating/groupmodelClick', val)
        this.$message({
          type: 'success',
          message: `已${val ? '开启' : '关闭'}小组模式`
        });
      },
      handleClose() {
        this.dialogVisible = false
      },
      groupmanagement() {
        this.dialogVisible = true
      },
      handleClassModeChange(val) {
        // console.log(val,'valval')
        this.$store.commit('Floating/handleClassMode', val)
        this.$message({
          type: 'success',
          message: `已${val ? '开启' : '关闭'}上课模式`
        });
        // 这里可以添加更多模式切换逻辑
      },
      selectClassApi() {
        selectClass({}).then(response => {
          this.classes = response.data
        });
      },
      switchTab(tabId) {
        if (tabId === this.activeTab) return
        this.transitionName = tabId === 'report' ? 'slide-left' : 'slide-right'
        this.activeTab = tabId
      },
      selectClass(item, cls) {
        this.id.courseId = cls.courseId
        this.id.deptId = item.deptId
        // this.$store.commit('Floating/increment',JSON.stringify(this.id))
        this.showClassSelect = false;
      },
      goBackToClassSelect() {
        this.$store.commit('Floating/increment', null)
        this.showClassSelect = true;
      }
    }
  };
</script>

<style scoped lang="scss">
  .dept-tag {
    display: inline-block;
    padding: 6px 12px;
    margin: 15px 8px 8px 0;
    border: 1px solid #e4ebf5;
    border-radius: 24px;
    font-size: 0.9rem;
    color: #409eff;
    background: #f0f9ff;
    transition: all 0.3s ease;
    cursor: pointer;
    position: relative;
    top: 0;
  }

  .dept-tag:hover {
    border-color: #409eff;
    background: #409eff;
    color: white;
    transform: scale(1.05);
    box-shadow: 0 2px 8px rgba(64, 158, 255, 0.15);
  }

  .dept-tag:last-child {
    margin-right: 0;
  }

  .empty-state {
    text-align: center;
    padding: 40px 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 400px;
    /* 保持与内容区高度一致 */
  }

  .class-select-container {
    min-height: 100vh;
    background: linear-gradient(135deg, #f0f9ff 0%, #ebf5ff 100%);
    padding: 40px 20px;
  }

  .school-header {
    text-align: center;
    margin-bottom: 40px;

    h1 {
      color: #2c3e50;
      font-size: 2.5rem;
      text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    }

    .school-icon {
      margin-top: 20px;

      i {
        font-size: 4rem;
        color: #409EFF;
        background: #fff;
        padding: 20px;
        border-radius: 50%;
        box-shadow: 0 4px 12px rgba(64, 158, 255, 0.2);
      }
    }
  }

  .class-list-container {
    max-width: 1200px;
    margin: 0 auto;

    .select-title {
      text-align: center;
      color: #606266;
      margin-bottom: 40px;
      font-weight: normal;
      position: relative;

      &::after {
        content: '';
        width: 80px;
        height: 3px;
        background: #409EFF;
        position: absolute;
        bottom: -10px;
        left: 50%;
        transform: translateX(-50%);
      }
    }
  }

  .class-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
    gap: 30px;
    padding: 20px;
  }

  .class-card {
    margin-bottom: 15px;
    background: white;
    border-radius: 15px;
    padding: 20px;
    display: flex;
    align-items: center;
    transition: all 0.3s;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
    cursor: pointer;
    position: relative;

    &:hover {
      transform: translateY(-5px);
      box-shadow: 0 8px 16px rgba(64, 158, 255, 0.15);

      .class-icon i {
        transform: rotate(10deg);
      }
    }

    .class-icon {
      flex: 0 0 60px;

      i {
        font-size: 2.5rem;
        color: #67C23A;
        transition: transform 0.3s;
      }
    }

    .class-info {
      flex: 1;
      margin-left: 20px;

      h3 {
        color: #303133;
        margin-bottom: 8px;
        font-size: 1.2rem;
      }

      .course-name {
        color: #909399;
        font-size: 0.9rem;
        margin-bottom: 10px;
      }

      .meta-info {
        display: flex;
        justify-content: space-between;

        span {
          font-size: 0.85rem;
          color: #606266;

          i {
            margin-right: 5px;
          }
        }
      }
    }

    .enter-button {
      position: absolute;
      right: 20px;
      bottom: 20px;
      opacity: 0;
      transition: opacity 0.3s;
    }

    &:hover .enter-button {
      opacity: 1;
    }
  }

  .app-container {
    padding: 20px;
  }

  .back-button {
    margin-bottom: 20px;
  }

  .tabs-container {
    display: flex;
    position: relative;
    // margin-bottom: 30px;
    border-bottom: 2px solid #f0f0f0;
    margin-top: -20px;

    .tab-item {
      position: relative;
      padding: 15px 40px;
      cursor: pointer;
      font-size: 16px;
      color: #666;
      transition: all 0.3s ease;

      &:hover {
        color: #409EFF;
      }

      &.active {
        color: #409EFF;
        font-weight: 500;

        .tab-indicator {
          width: 100%;
          background: #409EFF;
        }
      }
    }

    .tab-indicator {
      position: absolute;
      bottom: -2px;
      left: 0;
      width: 0;
      height: 3px;
      background: transparent;
      transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
    }
  }

  .tab-content {
    padding: 20px;
  }

  /* 滑动动画 */
  .slide-left-enter-active,
  .slide-left-leave-active,
  .slide-right-enter-active,
  .slide-right-leave-active {
    transition: all 0.4s cubic-bezier(0.55, 0, 0.1, 1);
  }

  .slide-left-enter-from {
    opacity: 0;
    transform: translateX(50px);
  }

  .slide-left-leave-to {
    opacity: 0;
    transform: translateX(-50px);
  }

  .slide-right-enter-from {
    opacity: 0;
    transform: translateX(-50px);
  }

  .slide-right-leave-to {
    opacity: 0;
    transform: translateX(50px);
  }

  /* 轻微浮动动画 */
  @keyframes float {

    0%,
    100% {
      transform: translateY(0);
    }

    50% {
      transform: translateY(-5px);
    }
  }

  .tab-item:hover {
    animation: float 1.5s ease-in-out infinite;
  }
</style>
