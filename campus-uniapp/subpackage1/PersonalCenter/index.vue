<template>
	<view class="container">
		<!-- 头部信息 -->
		<view class="header bg-blue">
			<image class="avatar" src="/static/user.png" mode="aspectFill"></image>
			<view class="user-info">
				<text class="nickname">{{studentgetInfoObj.studentName}}</text>
				<text class="class-info">{{studentgetInfoObj.dept.deptName}} |
					学籍号：{{studentgetInfoObj.studentNumber}}</text>
			</view>
			<!-- <image class="school-logo" src="/static/logo.png"></image> -->
		</view>
		
		<view class="sunflower-box">
			<image class="sunflower" src="/static/xrk2.jpg" mode="aspectFit"></image>
		</view>

		<!-- 功能入口 -->
		<view class="grid-container">
			<view class="grid-item" v-for="(item, index) in gridList" :key="index" @click="gridListClick(item)">
				<image class="grid-icon" :src="item.icon"></image>
				<text class="grid-text">{{item.name}}</text>
			</view>
		</view>


		<!-- 客服入口 -->
		<!-- <view class="customer-service">
      <image class="service-icon" src="/static/logo.png"></image>
      <text>在线客服 08:30-17:30</text>
    </view> -->
	</view>
</template>

<script>
	import {
		studentgetInfo,
		studentclassCircle
	} from '@/utils/index.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
		data() {
			return {
				studentgetInfoObj: {},
				configUrl: img_url,
				gridList: [
					// { name: '活动总结', icon: '/static/logo.png' },
					{
						name: '写实记录',
						icon: '/static/xsjl.png'
					},
					// { name: '我的测评', icon: '/static/logo.png' },
					{
						name: '成绩单',
						icon: '/static/cjd.png'
					},
					{
						name: '体质测试',
						icon: '/static/tzce.png'
					},
					// { name: '优选内容', icon: '/static/logo.png' },
					{
						name: '成长报告',
						icon: '/static/czbg.png'
					},
					// { name: '我的等级', icon: '/static/czbg.png' },
					// { name: '成长画像', icon: '/static/logo.png' },
					// { name: '审核公示', icon: '/static/logo.png' },
					// { name: '我的申诉', icon: '/static/logo.png' },
					{
						name: '系统设置',
						icon: '/static/sz.png'
					},
					// { name: '校园公告', icon: '/static/logo.png' }
				]
			}
		},
		onLoad() {
			_self = this
			_self.studentgetInfo()
		},
		methods: {
			gridListClick(item) {
				if (item.name == '成长报告') {
					uni.navigateTo({
						url: '/subpackage1/PersonalCenter/GrowthReport'
					});
				} else if (item.name == '我的等级') {
					uni.navigateTo({
						url: '/subpackage1/PersonalCenter/Mylevel'
					});
				} else if (item.name == '成绩单') {
					uni.navigateTo({
						url: '/subpackage1/PersonalCenter/transcript'
					});
				}
			},
			studentgetInfo() {
				this.$request.post(studentgetInfo, {}).then((res) => {
					_self.studentgetInfoObj = res.data.student
					// console.log(res,'resres')
				}, (err) => {

				})
			}
		}
	}
</script>

<style lang="scss">
	.sunflower-box {
		position: absolute;
		top:40%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 2;
		width: 500rpx;
		height: 700rpx;
		filter: drop-shadow(0 20rpx 15rpx rgba(255, 180, 0, 0.3));
	}
	.sunflower {
		width: 100%;
		height: 100%;
		animation: breathe 3s ease-in-out infinite;
	}
	@keyframes beamRotate {
		0% {
			transform: rotate(0deg);
		}
	
		100% {
			transform: rotate(360deg);
		}
	}
	@keyframes cloudMove {
		0% {
			transform: translateX(0) translateY(0);
		}
	
		100% {
			transform: translateX(200%) translateY(20rpx);
		}
	}
	
	@keyframes breathe {
	
		0%,
		100% {
			transform: scale(0.98) rotate(-2deg);
		}
	
		50% {
			transform: scale(1.02) rotate(2deg);
		}
	}
	
	.bottom-nav {
		position: fixed;
		bottom: 10rpx;
		left: 50%;
		transform: translateX(-50%);
		z-index: 1000;
		width: 90%;
		background: rgba(255, 255, 255, 0.2);
		border-radius: 50rpx;
		box-shadow: 0 10rpx 30rpx rgba(131, 200, 247, 0.3);
		backdrop-filter: blur(10px);
		border: 1rpx solid rgba(255, 255, 255, 0.6);
	}

	.nav-buttons {
		display: flex;
		justify-content: space-around;
		padding: 20rpx 0;
	}

	/* 导航项通用样式 */
	.nav-item {
		position: relative;
		display: flex;
		flex-direction: column;
		align-items: center;
		padding: 15rpx 30rpx;
		transition: all 0.3s ease;
	}

	/* 图标样式 */
	.nav-icon {
		width: 58rpx;
		height: 58rpx;
		filter: drop-shadow(0 4rpx 6rpx rgba(131, 200, 247, 0.3));
		transition: transform 0.3s ease;
	}

	/* 文字样式 */
	.nav-text {
		font-size: 24rpx;
		color: #666;
		margin-top: 10rpx;
		font-weight: 500;
		text-shadow: 0 2rpx 4rpx rgba(131, 200, 247, 0.2);
	}

	/* 按钮交互效果 */
	.nav-item:active {
		transform: scale(0.92);
	}

	.nav-item:active .nav-icon {
		transform: rotate(15deg);
	}

	/* 特色动态效果 */
	.blossom-btn {
		/* background: linear-gradient(145deg, #FFB6C1, #FF69B4); */
		border-radius: 30rpx;
	}

	.blossom-effect {
		position: absolute;
		width: 100%;
		height: 100%;
		/* background: radial-gradient(circle, rgba(255,182,193,0.3) 20%, transparent 70%); */
		opacity: 0;
		transition: opacity 0.3s;
	}

	.blossom-btn:active .blossom-effect {
		opacity: 1;
	}

	.cloud-btn {
		/* background: linear-gradient(145deg, #87CEEB, #B0E0E6); */
		border-radius: 30rpx;
	}

	.cloud-effect {
		position: absolute;
		width: 120%;
		height: 120%;
		/* background: url('data:image/svg+xml;utf8,<svg viewBox="0 0 100 100" xmlns="http://www.w3.org/2000/svg"><path d="M20,50 Q30,35 50,30 Q70,25 80,40 Q85,55 70,70 Q55,85 35,80 Q15,75 20,50" fill="rgba(255,255,255,0.2)"/></svg>'); */
		opacity: 0;
		transition: opacity 0.3s;
	}

	.cloud-btn:active .cloud-effect {
		opacity: 0.6;
	}

	.sunflower-btn {
		/* background: linear-gradient(145deg, #FFD700, #FFEC8B); */
		border-radius: 30rpx;
	}

	.sun-effect {
		position: absolute;
		width: 100%;
		height: 100%;
		background: radial-gradient(circle, rgba(255, 215, 0, 0.3) 10%, transparent 60%);
		opacity: 0;
		transition: opacity 0.3s;
	}

	.sunflower-btn:active .sun-effect {
		opacity: 1;
	}

	.container {
		height: 100vh;
		background: linear-gradient(to bottom, #9CDCFB 30%, #83C8F7 60%, #FFE87A 100%);
		overflow: hidden;
		position: relative;
	}

	.header {
		padding: 40rpx 30rpx;
		display: flex;
		align-items: center;
		position: relative;
		border-radius: 0 0 30rpx 30rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
		

		.avatar {
			width: 120rpx;
			height: 120rpx;
			border-radius: 50%;
			border: 4rpx solid #fff;
		}

		.user-info {
			margin-left: 30rpx;
			display: flex;
			flex-direction: column;

			.nickname {
				font-size: 36rpx;
				color: #fff;
				font-weight: bold;
				margin-bottom: 12rpx;
			}

			.class-info {
				font-size: 24rpx;
				color: rgba(255, 255, 255, 0.9);
			}
		}

		.school-logo {
			position: absolute;
			right: 30rpx;
			width: 80rpx;
			height: 80rpx;
		}
	}

	.grid-container {
		position: fixed;
		bottom: 10rpx;
		left: 50%;
		transform: translateX(-50%);
		z-index: 1000;
		width: 90%;
		background: rgba(255, 255, 255, 0.2);
		border-radius: 50rpx;
		box-shadow: 0 10rpx 30rpx rgba(131, 200, 247, 0.3);
		backdrop-filter: blur(10px);
		border: 1rpx solid rgba(255, 255, 255, 0.6);
		
		display: grid;
		grid-template-columns: repeat(3, 1fr);
		gap: 20rpx;
		// padding: 30rpx;
		// background: #fff;
		// margin: 30rpx;
		// border-radius: 20rpx;
		// box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.08);
		

		.grid-item {
			display: flex;
			flex-direction: column;
			align-items: center;
			padding: 30rpx 0;

			.grid-icon {
				width: 80rpx;
				height: 80rpx;
				margin-bottom: 20rpx;
			}

			.grid-text {
				font-size: 24rpx;
				color: #333;
			}
		}
	}

	.customer-service {
		position: fixed;
		right: 30rpx;
		bottom: 100rpx;
		background: #fff;
		padding: 16rpx 24rpx;
		border-radius: 40rpx;
		display: flex;
		align-items: center;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);

		.service-icon {
			width: 40rpx;
			height: 40rpx;
			margin-right: 12rpx;
		}

		text {
			font-size: 24rpx;
			color: #666;
		}
	}

	.bg-blue {
		background: linear-gradient(135deg, #4a90e2, #63b8ff);
		// background: rgba(255, 255, 255, 0.5);
	}
</style>