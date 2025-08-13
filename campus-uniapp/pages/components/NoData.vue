<template>
  <view class="empty-container">
    <!-- 动态插画 -->
    <view class="empty-illustration animate__animated animate__fadeIn">
      <view class="book-icon">
        <view class="book-cover"></view>
        <view class="book-pages"></view>
        <view class="plus-icon">+</view>
      </view>
      <view class="floating-dots">
        <view class="dot dot1"></view>
        <view class="dot dot2"></view>
        <view class="dot dot3"></view>
      </view>
    </view>

    <!-- 提示文字 -->
    <view class="empty-text animate__animated animate__fadeInUp">
      <text class="title">{{ title }}</text>
      <!-- <text class="subtitle">{{ subtitle }}</text> -->
    </view>

    <!-- 操作按钮 -->
   <!-- <view 
      class="action-button animate__animated animate__fadeInUp"
      v-if="showButton"
      @click="handleClick"
    >
      <text>{{ buttonText }}</text>
    </view> -->
  </view>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: '暂无打卡记录'
    },
    subtitle: {
      type: String,
      default: '快去发布你的第一个活动吧！'
    },
    showButton: {
      type: Boolean,
      default: true
    },
    buttonText: {
      type: String,
      default: '立即创建'
    }
  },
  methods: {
    handleClick() {
      this.$emit('onAction')
    }
  }
}
</script>

<style scoped>
.empty-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60rpx 40rpx;
  
}

/* 书本插画样式 */
.book-icon {
  position: relative;
  width: 240rpx;
  height: 180rpx;
  margin-bottom: 60rpx;
}
.book-cover {
  position: absolute;
  width: 200rpx;
  height: 160rpx;
  background: #b3e5fc;
  border-radius: 12rpx;
  transform: rotate(10deg);
  box-shadow: 8rpx 8rpx 20rpx rgba(0,0,0,0.1);
}
.book-pages {
  position: absolute;
  width: 180rpx;
  height: 150rpx;
  background: #fff;
  border-radius: 8rpx;
  left: 20rpx;
  top: 5rpx;
  border: 2rpx solid #e0f2f1;
}
.plus-icon {
  position: absolute;
  width: 60rpx;
  height: 60rpx;
  background: #007AFF;
  border-radius: 50%;
  color: #fff;
  font-size: 40rpx;
  font-weight: 100;
  display: flex;
  align-items: center;
  justify-content: center;
  right: 0;
  bottom: -20rpx;
  z-index: 2;
  box-shadow: 4rpx 4rpx 12rpx rgba(0,122,255,0.2);
}

/* 漂浮圆点动画 */
.floating-dots {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
}
.dot {
  position: absolute;
  background: #80deea;
  border-radius: 50%;
  animation: float 3s infinite ease-in-out;
}
.dot1 {
  width: 20rpx;
  height: 20rpx;
  top: 30rpx;
  left: 40rpx;
  animation-delay: 0.2s;
}
.dot2 {
  width: 16rpx;
  height: 16rpx;
  top: 80rpx;
  right: 30rpx;
  animation-delay: 0.5s;
}
.dot3 {
  width: 12rpx;
  height: 12rpx;
  bottom: 20rpx;
  left: 60rpx;
  animation-delay: 0.8s;
}

@keyframes float {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-40rpx); }
}

/* 文字样式 */
.empty-text {
  text-align: center;
  margin-bottom: 60rpx;
}
.title {
  display: block;
  font-size: 36rpx;
  color: #333;
  margin-bottom: 20rpx;
  font-weight: 500;
}
.subtitle {
  font-size: 28rpx;
  color: #666;
  line-height: 1.6;
}

/* 操作按钮 */
.action-button {
  background: #007AFF;
  color: #fff;
  padding: 20rpx 60rpx;
  border-radius: 50rpx;
  font-size: 30rpx;
  transition: all 0.3s;
}
.action-button:active {
  transform: scale(0.95);
  opacity: 0.9;
}

/* 入场动画 */
.animate__fadeIn {
  animation-duration: 0.8s;
}
.animate__fadeInUp {
  animation-duration: 0.6s;
}
</style>