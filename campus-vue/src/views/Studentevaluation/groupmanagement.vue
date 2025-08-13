<template>
  <div class="container">
    <!-- 未分组学生 -->
    <div class="left-panel">
      <h3 class="panel-title">未分组学生（{{ studentsWithoutGroup.length }}人）</h3>
      <div v-for="student in studentsWithoutGroup" :key="student.studentId" class="student-items"
        :class="{ selected: selectedStudent === student }" @click="selectStudentstudent(student)">
        <img :src="student.sex ==='0' ? man : woman" alt="" style="width: 30px;height: 30px;margin-right: 10px;">
        {{ student.studentName }}
      </div>
    </div>

    <!-- 操作按钮 -->
    <div class="action-center">
      <div class="action-buttons">
        <el-select v-model="targetGroup" placeholder="请选择小组">
          <el-option v-for="n in 10" :key="n" :label="`${n}小组`" :value="n" />
        </el-select>
        <el-button type="primary" icon="el-icon-right" circle :disabled="!selectedStudent" @click="moveToGroup" />
        <el-button type="info" icon="el-icon-back" circle :disabled="!selectedStudent" @click="moveToLeft" />

        <el-button type="primary" @click="Save">保存</el-button>
      </div>
    </div>

    <!-- 已分组学生 -->
    <div class="right-panel">
      <div v-for="n in 10" :key="n" class="group-card">
        <div class="group-header">{{ n }}组 （{{ groupStudents[n].length || 0 }}人）</div>
        <div v-for="student in groupStudents[n]" :key="student.studentId" class="student-item"
          :class="{ selected: selectedStudent === student }" @click="selectStudentstudent(student)">
          <img :src="student.sex ==='0' ? man : woman" alt="" style="width: 30px;height: 30px;">
          {{ student.studentName }}
          <el-tag v-if="student.group === n" type="info" size="mini" class="ml-2">已加入</el-tag>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import {
    selectStudents,
    classEvaluationeditGroup
  } from '@/api/Studentevaluation/Studentevaluation.js'
  import {
    selectStudentBonusCount
  } from '@/api/system/type.js'
  export default {
    data() {
      return {
        man: require('@/assets/img/man.png'),
        woman: require('@/assets/img/woman.png'),
        students: [],
        originalStudents: [], // 存储原始数据用于对比
        selectedStudent: null,
        targetGroup: 1,
        selectStudent: [],
      }
    },
    props: [
      'id'
    ],
    mounted() {
      console.log(this.id,'11')
      this.selectStudentBonusCount(this.id)
    },
    computed: {
      studentsWithoutGroup() {
        return this.students.filter(s => s.group === null)
      },
      groupStudents() {
        const groups = {}
        for (let i = 1; i <= 10; i++) {
          groups[i] = this.students.filter(s => s.group === i)
        }
        return groups
      }
    },
    methods: {
      Save() {
        // 找出有变化的学生
        const changedStudents = this.students.filter(student => {
          const originalStudent = this.originalStudents.find(s => s.studentId === student.studentId);
          return originalStudent && originalStudent.group !== student.group;
        });

        if (changedStudents.length === 0) {
          this.$message({
            message: '没有数据需要保存！',
            type: 'info'
          });
          return;
        }

        // 只提交有变化的数据
        const result = changedStudents.map(item => ({
          studentId: item.studentId,
          group: item.group
        }));

        classEvaluationeditGroup(result).then(response => {
          this.$message({
            message: '小组保存成功！',
            type: 'success'
          });
          // 更新原始数据为当前数据
          this.originalStudents = JSON.parse(JSON.stringify(this.students));
          this.selectStudentBonusCount(this.id)
        }).catch(error => {
          this.$message({
            message: '保存失败: ' + error.message,
            type: 'error'
          });
        });
      },
      selectStudents(deptId) {
        selectStudents({
          deptId
        }).then(response => {
          this.students = response.data.map(s => ({
            ...s,
            group: s.group ? Number(s.group) : null, // 转换分组号为数字
            counts: 0
          }));

          // 保存原始数据用于对比
          this.originalStudents = JSON.parse(JSON.stringify(this.students));

          // 移除原错误循环，改用更安全的匹配方式
          this.students.forEach(student => {
            const match = this.selectStudent.find(ss => ss.studentId === student.studentId);
            if (match) student.counts = match.counts;
          });
        });
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
      selectStudentstudent(student) {
        this.selectedStudent = student
      },
      moveToGroup() {
        if (this.selectedStudent && this.targetGroup) {
          this.selectedStudent.group = this.targetGroup
          this.selectedStudent = null
        }
      },
      moveToLeft() {
        if (this.selectedStudent) {
          this.selectedStudent.group = null
          this.selectedStudent = null
        }
      }
    }
  }
</script>

<style scoped>
  .container {
    display: flex;
    height: 100vh;
    padding: 20px;
    background: #f5f7fa;
    gap: 20px;
  }

  .left-panel {
    flex: 0 0 300px;
    background: white;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    overflow-y: auto;
  }

  .right-panel {
    flex: 1;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
    gap: 20px;
    overflow-y: auto;
  }

  .group-card {
    background: white;
    border-radius: 8px;
    padding: 15px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .group-header {
    padding-bottom: 10px;
    margin-bottom: 10px;
    border-bottom: 1px solid #ebeef5;
    font-weight: bold;
    color: #409eff;
  }

  .student-item {
    padding: 12px;
    margin: 6px 0;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: space-between;
    transition: all 0.3s;
  }

  .student-item:hover {
    background-color: #f5f7fa;
  }

  .student-item.selected {
    background-color: #ecf5ff;
    border-left: 4px solid #409eff;
  }

  .student-items {
    padding: 12px;
    margin: 6px 0;
    border-radius: 4px;
    cursor: pointer;
    display: flex;
    align-items: center;
    /* justify-content: space-between; */
    transition: all 0.3s;
  }

  .student-items:hover {
    background-color: #f5f7fa;
  }

  .student-items.selected {
    background-color: #ecf5ff;
    border-left: 4px solid #409eff;
  }

  .action-center {
    display: flex;
    align-items: center;
  }

  .action-buttons {
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 20px;
    background: white;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }

  .panel-title {
    margin-bottom: 15px;
    color: #606266;
  }

  .ml-2 {
    margin-left: 8px;
  }
</style>
