<template>
  <view class="success-container">
    <!-- 星球动画容器 -->
    <view class="planet-container">
      <!-- 星球主体 -->
      <view class="planet animate__animated animate__zoomIn">
        <!-- 对勾轨道 -->
        <view class="orbit animate__animated animate__rotateIn">
          <view class="checkmark animate__animated animate__fadeInUp"></view>
        </view>
        <!-- 星球细节 -->
        <view class="crater"></view>
        <view class="crater small"></view>
      </view>
      <!-- 环绕星星 -->
      <view class="star star-1 animate__animated animate__fadeIn"></view>
      <view class="star star-2 animate__animated animate__fadeIn"></view>
    </view>

    <!-- 文字内容 -->
    <view class="content">
      <!-- 标题动画 -->
      <view class="title animate__animated animate__fadeInUp">
        <text class="gradient-text">发布成功!</text>
        <view class="confetti"></view>
        <view class="confetti delay"></view>
      </view>
      
      <!-- 趣味提示 -->
      <view class="funny-tip animate__animated animate__fadeIn">
        <text>发布成功！</text>
      </view>

      <!-- 动态倒计时 -->
      <view class="countdown animate__animated animate__fadeIn">
        <view class="progress-bar">
          <view class="progress" :style="{width: (100 - countDown*20) + '%'}"></view>
        </view>
        <text>{{ countDown }}秒后自动返回</text>
      </view>
    </view>

    <!-- 操作按钮组 -->
    <view class="action-buttons animate__animated animate__fadeInUp">
      <view class="btn home-btn" @tap="goToHome">
        <image src="/static/icon-home.png" class="btn-icon"></image>
        <text>返回首页</text>
      </view>
     <!-- <view class="btn activity-btn" @tap="viewActivity">
        <image src="/static/icon-rocket.png" class="btn-icon"></image>
        <text>探索更多</text>
      </view> -->
    </view>
  </view>
</template>


<script>
export default {
  data() {
    return {
      countDown: 5,
      timer: null
    }
  },
  onLoad() {
    this.startCountDown()
  },
  methods: {
    startCountDown() {
      this.timer = setInterval(() => {
        if (this.countDown <= 1) {
          clearInterval(this.timer)
          this.goToHome()
        } else {
          this.countDown--
        }
      }, 1000)
    },
    goToHome() {
		uni.navigateBack({ delta: 2 })
    },
    viewActivity() {
      uni.navigateTo({
        url: '/pages/activity/list'
      })
    }
  }
}
</script>

<style>
/* 背景容器 */
.success-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #e0f7fa 0%, #f3e5f5 100%);
  padding: 40rpx;
  position: relative;
  overflow: hidden;
}

/* 星球动画样式 */
.planet-container {
  position: relative;
  margin: 100rpx 0 80rpx;
}

.planet {
  width: 280rpx;
  height: 280rpx;
  background: linear-gradient(145deg, #00e676 30%, #00c853 70%);
  border-radius: 50%;
  position: relative;
  box-shadow: 0 20rpx 50rpx rgba(0, 200, 83, 0.2);
}

.orbit {
  position: absolute;
  width: 180%;
  height: 180%;
  border: 4rpx dashed rgba(255, 255, 255, 0.3);
  border-radius: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation: rotate 12s linear infinite;
}

.checkmark {
  position: absolute;
  left: 20%;
  top: 50%;
  width: 80rpx;
  height: 50rpx;
  border-left: 12rpx solid #fff;
  border-bottom: 12rpx solid #fff;
  transform: translateY(-50%) rotate(-45deg);
}

/* 动态星星 */
.star {
  position: absolute;
  width: 24rpx;
  height: 24rpx;
  background: #fff;
  clip-path: polygon(50% 0%, 61% 35%, 98% 35%, 68% 57%, 79% 91%, 50% 70%, 21% 91%, 32% 57%, 2% 35%, 39% 35%);
  animation: twinkle 1.5s infinite alternate;
}
.star-1 { top: -30rpx; right: 60rpx; }
.star-2 { bottom: 40rpx; left: 30rpx; }

/* 文字内容 */
.content {
  text-align: center;
  margin-bottom: 80rpx;
}

.gradient-text {
  font-size: 56rpx;
  background: linear-gradient(45deg, #00c853, #00e676);
  -webkit-background-clip: text;
  color: transparent;
  font-weight: bold;
  position: relative;
  display: inline-block;
  margin-bottom: 30rpx;
}

.funny-tip {
  font-size: 32rpx;
  color: #666;
  margin: 30rpx 0;
}

/* 进度条动画 */
.progress-bar {
  width: 400rpx;
  height: 12rpx;
  background: #e0e0e0;
  border-radius: 10rpx;
  margin: 30rpx auto;
  overflow: hidden;
}

.progress {
  height: 100%;
  background: linear-gradient(90deg, #00e676, #00c853);
  transition: width 1s linear;
}

/* 按钮样式 */
.action-buttons {
  display: flex;
  gap: 40rpx;
}

.btn {
  padding: 20rpx 40rpx;
  border-radius: 50rpx;
  display: flex;
  align-items: center;
  transition: all 0.3s;
}

.home-btn {
  background: #00c853;
  box-shadow: 0 8rpx 20rpx rgba(0, 200, 83, 0.3);
}

.activity-btn {
  background: #2196F3;
  box-shadow: 0 8rpx 20rpx rgba(33, 150, 243, 0.3);
}

.btn text {
  color: white;
  font-size: 32rpx;
  margin-left: 15rpx;
}

.btn-icon {
  width: 40rpx;
  height: 40rpx;
}

/* 自定义动画 */
@keyframes rotate {
  from { transform: translate(-50%, -50%) rotate(0deg); }
  to { transform: translate(-50%, -50%) rotate(360deg); }
}

@keyframes twinkle {
  from { opacity: 0.6; transform: scale(0.9); }
  to { opacity: 1; transform: scale(1.1); }
}
</style>