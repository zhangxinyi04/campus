<template>
  <div class="app-container home">
    <div class="stats-grid">
      <div class="stat-card student">
        <div class="icon-box">
          <i class="el-icon-user"></i>
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ Totaldata.studentCount }}</div>
          <div class="stat-label">学生人数</div>
        </div>
      </div>
      <div class="stat-card teacher">
        <div class="icon-box">
          <i class="el-icon-s-custom"></i>
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ Totaldata.teacherCount }}</div>
          <div class="stat-label">教师人数</div>
        </div>
      </div>
      <div class="stat-card course">
        <div class="icon-box">
          <i class="el-icon-notebook-1"></i>
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ Totaldata.courseCount }}</div>
          <div class="stat-label">课程数量</div>
        </div>
      </div>
      <div class="stat-card class">
        <div class="icon-box">
          <i class="el-icon-office-building"></i>
        </div>
        <div class="stat-info">
          <div class="stat-value">{{ Totaldata.deptCount }}</div>
          <div class="stat-label">班级数量</div>
        </div>
      </div>
    </div>

    <div>
      <Achievementstatistics :studentScore='Totaldata.studentScore'></Achievementstatistics>
    </div>
    <div>
      <Physicalexaminationresults :studentScorePe='Totaldata.studentScorePe'></Physicalexaminationresults>
    </div>
    <div style="width: 100%;">
       <Activitystatistics :sysEvents='Totaldata.sysEvents'></Activitystatistics>
    </div>
   <div>
      <Honorstatistics :develops='Totaldata.develops'></Honorstatistics>
    </div>
  </div>
</template>

<script>
  import {
    systemindex
  } from "@/api/index";
  import Achievementstatistics from '@/views/Visualization/Achievementstatistics.vue'
  import Physicalexaminationresults from '@/views/Visualization/Physicalexaminationresults.vue'
  import Activitystatistics from '@/views/Visualization/Activitystatistics.vue' //活动
  import Honorstatistics from '@/views/Visualization/Honorstatistics.vue' //荣誉

  export default {
    name: "Index",
    data() {
      return {
        loading: false,
        Totaldata: {
          studentCount: 0,
          teacherCount: 0,
          courseCount: 0,
          deptCount: 0,
          studentScore: null
        },
        queryParams: {
          pageNum: 1,
          pageSize: 10
        }
      };
    },
    components: {
      Achievementstatistics,
      Physicalexaminationresults,
      Honorstatistics,
      Activitystatistics
    },
    mounted() {
      this.getList();
    },
    methods: {
      getList() {
        this.loading = true;
        systemindex(this.queryParams)
          .then(response => {
            // 假设 response.data 就是 { studentCount, teacherCount, ... }
            if (response && response.data) {
              this.Totaldata = response.data;
            }
          })
          .catch(err => {
            console.error("获取统计数据失败", err);
          })
          .finally(() => {
            this.loading = false;
          });
      }
    }
  };
</script>

<style scoped lang="scss">
  .app-container {
    padding: 32px;
    background: #f5f7fa;
    min-height: 100vh;

    .stats-grid {
      display: flex;
      flex-wrap: wrap;
      gap: 24px;
    }

    .stat-card {
      display: flex;
      align-items: center;
      flex: 1 1 calc(25% - 24px);
      background: #fff;
      border-radius: 12px;
      padding: 20px;
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
      color: #fff;
      transition: transform 0.3s ease, box-shadow 0.3s ease;

      &:hover {
        transform: translateY(-6px);
        box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
      }

      .icon-box {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.25);
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 28px;
        margin-right: 20px;
      }

      .stat-info {
        flex: 1;

        .stat-value {
          font-size: 30px;
          font-weight: 700;
          margin-bottom: 6px;
        }

        .stat-label {
          font-size: 14px;
          opacity: 0.9;
        }
      }

      // 渐变背景定义
      &.student {
        background: linear-gradient(135deg, #42a5f5, #478ed1);
      }

      &.teacher {
        background: linear-gradient(135deg, #66bb6a, #43a047);
      }

      &.course {
        background: linear-gradient(135deg, #ffa726, #fb8c00);
      }

      &.class {
        background: linear-gradient(135deg, #ec407a, #d81b60);
      }
    }

    @media (max-width: 1024px) {
      .stat-card {
        flex: 1 1 calc(50% - 24px);
      }
    }

    @media (max-width: 600px) {
      .stat-card {
        flex: 1 1 100%;
      }
    }
  }
</style>
