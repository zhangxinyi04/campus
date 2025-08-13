<template>
  <div class="student-review-container" v-else>
    <div class="control-bar">
      <el-button type="primary" @click="restoreStudents">恢复列表</el-button>
    </div>
    <!-- 学生卡片列表 -->
    <transition-group name="list" tag="div" class="student-list">
      <div v-for="student in students" :key="student.studentId" class="student-card"
        :class="{ 'selected': selectedIds.includes(student.studentId) }" @click.stop="deleteStudent(student.studentId)">
        <div class="item-badge">{{ student.counts }}</div>
        <img :src="student.sex === '0' ? man : woman" alt="" style="width: 60px;height: 60px;">
        <h3 class="name">{{ student.studentName }}</h3>
      </div>
    </transition-group>
  </div>
</template>

<script>
import {
  selectStudents,
} from '@/api/Studentevaluation/Studentevaluation.js'
import {
  selectStudentBonusCount,
  classEvaluationlist,
  classEvaluation
} from '@/api/system/type.js'

// 生成学生数据
const generateStudents = () => {
  const names = ['陈小明', '李雨欣', '张浩然', '王诗雨', '刘子轩',
    '赵思雅', '黄俊杰', '周若雪', '吴天宇', '徐梦琪',
    '孙宇航', '马欣怡', '朱博文', '胡静雯', '郭子豪',
    '林晨曦', '何佳怡', '高俊熙', '罗雨萱', '梁志强'
  ];
  return names.map((name, index) => ({
    studentId: index + 1,
    studentName: name,
    sex: Math.random() > 0.5 ? '0' : '1', // 随机性别
    counts: Math.floor(Math.random() * 10) // 点评次数
  }));
};

export default {
  props: ['id'],
  data() {
    return {
      man: require('@/assets/img/man.png'),
      woman: require('@/assets/img/woman.png'),
      dialogVisible: false,
      isMultiSelect: false,
      selectedIds: [],
      students: [],
      originalStudents: [], // 存储原始学生数据
      obj: [],
      selectStudent: [],
      classEvaluationlistArr: {}
    }
  },
  mounted() {
    this.selectStudentBonusCount(this.id)
    this.classEvaluationlist0()
  },
  methods: {
    toggleMultiSelect() {
      this.isMultiSelect = !this.isMultiSelect;
      this.selectedIds = [];
    },
    SelectSelect() {
      this.obj = []
      const objs = this.id
      this.obj = this.selectedIds.map(id => ({
        deptId: objs.deptId,
        studentId: id,
        courseId: objs.courseId
      }));
      this.dialogVisible = true
    },
    selectStudentBonusCount(id) {
      selectStudentBonusCount({
        deptId: id.deptId,
        courseId: id.courseId
      }).then(response => {
        this.selectStudent = response.data
        this.selectStudents(this.id.deptId)
      });
    },
    selectStudents(deptId) {
      selectStudents({ deptId }).then(response => {
        this.originalStudents = response.data // 保存原始数据
        this.students = JSON.parse(JSON.stringify(this.originalStudents)) // 复制一份用于显示

        // 初始化点评次数
        this.students.forEach(student => {
          student.counts = 0
          const match = this.selectStudent.find(item => item.studentId === student.studentId)
          if (match) student.counts = match.counts
        })
      });
    },
    classEvaluationlist0() {
      classEvaluationlist({ type: 0 }).then(response => {
        this.classEvaluationlistArr = response.data
      });
    },
    handleSelect(id, student) {
      if (this.$store.state.Floating.count) {
        const objs = {
          deptId: this.id.deptId,
          courseId: this.id.courseId,
          studentId: id,
          bonusTypeId: this.classEvaluationlistArr[0]?.bonusTypeId || '',
          type: this.classEvaluationlistArr[0]?.type || '',
          mark: this.classEvaluationlistArr[0]?.mark || ''
        }
        classEvaluation([objs]).then(response => {
          this.$message({
            message: objs.type === '0' ?
              `${student.studentName} +${objs.mark}` : `${student.studentName} -${objs.mark}`,
            type: objs.type === '0' ? 'success' : 'warning'
          });
          this.$emit('message', 'ok')
          this.selectStudentBonusCount(this.id)
        })
      } else {
        this.obj = []
        this.obj.push({
          deptId: this.id.deptId,
          courseId: this.id.courseId,
          studentId: id
        })
        if (!this.isMultiSelect) {
          this.dialogVisible = true
        } else {
          const index = this.selectedIds.indexOf(id)
          index > -1 ? this.selectedIds.splice(index, 1) : this.selectedIds.push(id)
        }
      }
    },
    deleteStudent(studentId) {
      this.students = this.students.filter(student => student.studentId !== studentId)
      this.$message({
        message: '已删除该学生',
        type: 'success',
        duration: 1500
      })
    },
    restoreStudents() {
      // 从原始数据恢复
      this.selectStudentBonusCount(this.id)
      this.classEvaluationlist0()
    }
  }
}
</script>

<style lang="scss" scoped>
.control-bar {
  text-align: center;
  margin-bottom: 20px;

  .el-button {
    padding: 10px 20px;
    border-radius: 15px;
    transition: transform 0.2s;

    &:hover {
      transform: translateY(-2px);
    }
  }
}

.student-review-container {
  margin-top: -20px;
  padding: 20px;
  background: #fff;
  min-height: 100vh;
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

.delete-btn {
  position: absolute;
  top: 5px;
  right: 5px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: rgba(255, 71, 87, 0.8);
  color: white;
  font-size: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  opacity: 0;
  transition: opacity 0.3s;
  z-index: 10;
}

.student-card:hover .delete-btn {
  opacity: 1;
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-8px); }
}

.item-badge {
  transition: all 0.3s ease;
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
  box-shadow: 0 2px 5px rgba(255, 71, 87, 0.3);
}

.name {
  margin: 10px 0 0;
  font-size: 16px;
  color: #333;
}

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

.list-enter-active,
.list-leave-active {
  transition: all 0.4s ease;
}

.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>
