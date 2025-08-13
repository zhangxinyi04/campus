<template>
  <div class="notification-container">
    <!-- 标题 -->
    <el-row class="header">
      <el-col :span="24">
        <h2>消息通知</h2>
      </el-col>
    </el-row>

    <!-- 通知列表 -->
    <el-table
      :data="notices"
      style="width: 100%"
      :row-class-name="tableRowClassName"
      @row-click="handleRowClick"
    >
      <el-table-column prop="title" label="标题" width="180">
        <template slot-scope="scope">
          <span :class="{ 'unread-text': !scope.row.isRead }">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="date" label="日期" width="180"></el-table-column>
      <el-table-column label="状态" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isRead ? 'info' : 'success'" size="small">
            {{ scope.row.isRead ? '已读' : '未读' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button size="small" @click.stop="viewDetail(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 详情对话框 -->
    <el-dialog
      :title="currentNotice ? currentNotice.title : ''"
      :visible.sync="dialogVisible"
      width="50%"
    >
      <div v-if="currentNotice">
        <div class="detail-meta">
          <span class="time">{{ currentNotice.date }}</span>
        </div>
        <div class="detail-content">
          {{ currentNotice.content }}
        </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogVisible = false">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      dialogVisible: false,
      currentNotice: null,
      notices: [
        {
          id: 1,
          title: '系统维护通知',
          content: '系统将于2023-10-01 00:00至06:00进行例行维护...',
          isRead: false,
          date: '2023-09-28'
        },
        {
          id: 2,
          title: '新功能上线',
          content: '新增数据统计模块，欢迎使用反馈...',
          isRead: true,
          date: '2023-09-27'
        }
      ]
    }
  },
  methods: {
    viewDetail(row) {
      this.currentNotice = row
      this.dialogVisible = true
      if (!row.isRead) {
        this.markAsRead(row)
      }
    },
    markAsRead(row) {
      const index = this.notices.findIndex(item => item.id === row.id)
      this.$set(this.notices[index], 'isRead', true)
    },
    tableRowClassName({ row }) {
      return row.isRead ? '' : 'unread-row'
    },
    handleRowClick(row) {
      this.viewDetail(row)
    }
  }
}
</script>

<style scoped>
.notification-container {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,.1);
}

.header {
  margin-bottom: 20px;
}

.unread-text {
  font-weight: bold;
}

.detail-meta {
  margin-bottom: 20px;
  color: #909399;
  font-size: 0.9em;
}

.detail-content {
  line-height: 1.6;
  color: #606266;
}

::v-deep .unread-row {
  background-color: #fafafa;
  cursor: pointer;
}

::v-deep .el-table__row:hover {
  background-color: #f5f7fa !important;
}
</style>
