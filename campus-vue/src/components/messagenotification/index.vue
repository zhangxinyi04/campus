<template>
  <el-dropdown trigger="click" @visible-change="handleDropdownVisible">
    <div class="notification-trigger">
      <svg-icon class="tongzhi" icon-class="tongzhi" />
      <span v-if="unreadCount > 0" class="unread-badge">{{ unreadCount }}</span>
    </div>
    <el-dropdown-menu slot="dropdown" class="custom-dropdown-menu">
      <div class="tabs">
        <button class="tab-button" :class="{ 'active': activeTab === 'unread' }" @click="switchTab('unread')">
          未读
          <span class="count-badge" v-if="unreadCount !== 0">{{ unreadCount }}</span>
        </button>
        <button class="tab-button" :class="{ 'active': activeTab === 'read' }" @click="switchTab('read')">
          已读
        </button>
      </div>
      <div class="notification-list">
        <el-dropdown-item
          v-for="(item, index) in sizeOptions"
          :key="index"
          :class="['notification-item', item.status === '0' ? 'unread' : 'read']">
          <div class="notification-content">
            <span class="type-tag">下载</span>
            {{ item.sysUser.nickName }}下载了您的{{ item.fileName }}
          </div>
          <div class="notification-footer">
            <span class="notification-time">{{ formatTime(item.createTime) }}</span>
            <el-button
              v-if="activeTab === 'unread'"
              class="mark-read-btn"
              type="text"
              @click.stop="markAsRead(item)">
              标为已读
            </el-button>
          </div>
        </el-dropdown-item>
        <div v-if="sizeOptions.length === 0" class="empty-state">
          暂无{{ activeTab === 'unread' ? '未读' : '已读' }}通知
        </div>
      </div>
    </el-dropdown-menu>
  </el-dropdown>
</template>
<script>
import {
  documentdownloadNotification,
  documentread
} from "@/api/message/message";

export default {
  data() {
    return {
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: '0' // 0未读 1已读
      },
      sizeOptions: [],
      unreadCount: 0,
      loading: false,
      activeTab: 'unread' // 新增当前选项卡状态
    };
  },
  mounted() {
    this.documentdownloadNotification();
  },
  methods: {
   async markAsRead(row) {
      // console.log(row,'async')
      // return
      // documentread({id:id}).then(response => {
      //       console.log(response,'responseresponse')
      // }).catch(error => {
      // });
          try {
            await documentread({ id:row.id });
            this.documentdownloadNotification();
            // 如果当前在未读标签，更新未读计数
            if (this.activeTab === 'unread') {
              this.unreadCount = Math.max(0, this.unreadCount - 1);
            }
          } catch (error) {
            console.error('标记已读失败:', error);
          }
        },
        handleDropdownVisible(visible) {
          if (visible && this.activeTab === 'unread') {
            this.documentdownloadNotification();
          }
        },
    switchTab(tab) {
      this.activeTab = tab;
      this.queryParams.status = tab === 'unread' ? '0' : '1';
      this.queryParams.pageNum = 1; // 切换时重置页码
      this.documentdownloadNotification();
    },
    documentdownloadNotification() {
      documentdownloadNotification(this.queryParams).then(response => {
        this.sizeOptions = response.rows;
        // 根据当前选项卡更新对应数量（需要根据实际接口返回调整）
        if (this.activeTab === 'unread') {
          this.unreadCount = response.total;
        }
      }).catch(error => {
      });
    },
    formatTime(time) {
      const date = new Date(time);
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hour = String(date.getHours()).padStart(2, '0');
      const minute = String(date.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day} ${hour}:${minute}`;
    }
  }
};
</script>

<style scoped>
.notification-trigger {
  position: relative;
  padding: 0 15px;
  height: 100%;
  display: flex;
  align-items: center;
  transition: background 0.3s;
}

.notification-trigger:hover {
  background: rgba(0, 0, 0, 0.025);
}

.unread-badge {
  position: absolute;
  top: 12px;
  right: 5px;
  background: #ff4d4f;
  color: white;
  font-size: 12px;
  min-width: 18px;
  height: 18px;
  line-height: 18px;
  border-radius: 9px;
  padding: 0 6px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.custom-dropdown-menu {
  width: 360px;
  padding: 0;
}

.tabs {
  display: flex;
  padding: 0 16px;
  border-bottom: 1px solid #eee;
}

.tab-button {
  position: relative;
  flex: 1;
  padding: 12px 0;
  border: none;
  background: none;
  cursor: pointer;
  font-size: 14px;
  color: #666;
  transition: all 0.3s;
}

.tab-button.active {
  color: #1890ff;
  font-weight: 500;
}

.tab-button.active::after {
  content: "";
  position: absolute;
  bottom: -1px;
  left: 0;
  right: 0;
  height: 2px;
  background: #1890ff;
}

.count-badge {
  margin-left: 6px;
  font-size: 12px;
  background: #ff4d4f;
  color: white;
  padding: 1px 6px;
  border-radius: 10px;
}

.notification-list {
  max-height: 400px;
  overflow-y: auto;
}

.notification-item {
  padding: 12px 16px;
  border-bottom: 1px solid #f0f0f0;
  transition: background 0.3s;
}

.notification-item.unread {
  background: #f9fcff;
}

.notification-item:hover {
  background: #f5f7fa;
}

.type-tag {
  display: inline-block;
  padding: 2px 6px;
  margin-right: 8px;
  font-size: 12px;
  border-radius: 3px;
  background: #1890ff20;
  color: #1890ff;
}

.notification-content {
  line-height: 1.5;
  color: #333;
  margin-bottom: 4px;
}

.notification-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.notification-time {
  font-size: 12px;
  color: #999;
}

.mark-read-btn {
  padding: 4px 8px;
  font-size: 12px;
  color: #666;
}

.mark-read-btn:hover {
  color: #1890ff;
}

.empty-state {
  padding: 24px 0;
  text-align: center;
  color: #999;
  font-size: 14px;
}
</style>
