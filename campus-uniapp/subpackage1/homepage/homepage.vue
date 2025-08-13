<template>
	<view class="container">
		<!-- <u-button text="写实记录" style="width: 100rpx;" @click="showRecordModal"></u-button>
		<u-button text="班级圈" style="width: 100rpx;" @click="ClassCircle"></u-button>
		<u-button text="我的" style="width: 100rpx;" @click="PersonalCenter"></u-button> -->
		
		


		<!-- 优化后的云朵 -->
		<!-- <view class="cloud cloud1"></view>
		<view class="cloud cloud2"></view>
		<view class="cloud cloud3"></view> -->

		<!-- 向日葵容器 -->
		<!-- <view class="sunflower-box"> -->
			<!-- <image class="sunflower" src="/static/xrk2.jpg" mode="aspectFit"></image> -->
			
		<!-- </view> -->
		
		<view style="width: 100%;text-align: center;">
			<image src='../../static/page.jpg' style="width: 100%;"/>
		</view>

		<!-- 草地 -->
		<view class="grass"></view>

		<!-- 活动列表 -->
		<!-- 活动列表 -->
		<view class="activity-list">
			<view class="list-header">
				<text class="header-title">近期活动</text>
				<view class="header-actions">
					<text class="view-all" @click="viewAllActivities">查看全部</text>
					<u-icon name="arrow-right" size="28"></u-icon>
				</view>
			</view>

			<scroll-view class="list-content" scroll-x>
				<view class="activity-card" v-for="(activity, index) in activityList" :key="index">
					<image class="card-cover" :src="imgUrl+activity.eventPic" mode="aspectFill"></image>
					<view class="card-content">
						<text class="card-title">{{activity.eventName}}</text>
						<text class="card-title" style="font-size: 26rpx;">{{activity.eventIntro}}</text>
						<!--  <view class="card-meta">
             <u-icon name="calendar" size="24"></u-icon>
             <text class="meta-text">{{activity.date}}</text>
             <u-icon name="map" size="24"></u-icon>
             <text class="meta-text">{{activity.location}}</text>
           </view> -->
						<view class="card-footer">
							<view class="participants">
								<view class="level-tag" :class="activity.rank == 0 ? 'school' : 'class'">
									{{ activity.rank == 0 ? '校级' : '班级' }}
								</view>

								<text class="participants-text">分数{{activity.mark}}</text>
							</view>
							<button class="join-btn" @click="joinActivity(activity)">
								<u-icon name="plus-circle" size="24" color="#fff"></u-icon>
								立即报名
							</button>
						</view>
					</view>
					<view class="card-tag">进行中</view>
				</view>
			</scroll-view>
		</view>

		<view class="record-modal" :class="{show: modalVisible}">
			<view class="modal-header">
				<text class="title">成长记录</text>
				<text class="close" @click="closeModal">×</text>
			</view>
			<scroll-view class="record-list" scroll-y>
				<view class="record-item" v-for="(item, index) in recordList" :key="index">
					<!-- <text class="index">#{{item.index}}</text> -->
					<text class="item-title">{{item.title}}</text>
					<button class="record-btn" @click="goRecordPage(item)">去记录</button>
				</view>
			</scroll-view>
		</view>
		
		<view class="bottom-nav">
		  <view class="nav-buttons">
		    <!-- 写实记录按钮 -->
		    <view class="nav-item blossom-btn" @click="showRecordModal">
		      <image class="nav-icon" src="/static/czyj.png" mode="aspectFit"></image>
		      <text class="nav-text">成长印记</text>
		      <view class="blossom-effect"></view>
		    </view>
		
		    <!-- 班级圈按钮 -->
		    <view class="nav-item cloud-btn" @click="ClassCircle">
		      <image class="nav-icon" src="/static/bjq.png" mode="aspectFit"></image>
		      <text class="nav-text">班级圈</text>
		      <view class="cloud-effect"></view>
		    </view>
		
		    <!-- 个人中心按钮 -->
		    <view class="nav-item sunflower-btn" @click="PersonalCenter">
		      <image class="nav-icon" src="/static/wd.png" mode="aspectFit"></image>
		      <text class="nav-text">我的花园</text>
		      <view class="sun-effect"></view>
		    </view>
		  </view>
		</view>
		
	</view>
</template>

<script>
	import {
		studentgetInfo,
		teachergetInfo,
		eventslist
	} from '@/utils/index.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
		data() {
			return {
				imgUrl: img_url,
				modalVisible: false,

				recordList: [{
						index: 1,
						title: "参加创新活动获奖"
					},
					{
						index: 2,
						title: "参加体育赛事获奖"
					},
					{
						index: 3,
						title: "参加艺术赛事获奖"
					},
					{
						index: 2,
						title: "掌握专项运动"
					},
					{
						index: 3,
						title: "掌握艺术技能"
					}
				],
				activityList: [],
				// eventslistdata:[]
			}
		},
		onShow() {

		},
		onLoad() {
			try {
				_self = this
				this.studentgetInfo()
				this.eventslists()
			} catch (error) {
				console.error('onLoad error:', error);
			}
		},
		methods: {
			PersonalCenter() {
				// console.log('11')
				// let url = `/subpackage1/mypage/mypage`;
				uni.navigateTo({
					url: '/subpackage1/PersonalCenter/index'
				});
			},
			ClassCircle() {
				let url = `/subpackage1/homepage/ClassCircle`;
				uni.navigateTo({
					url: url
				});
			},
			eventslists() {
				// console.log('2222', 'res')
				var obj = {
					status: 1
				}
				var _this = this
				this.$request.post(eventslist, {}).then((res) => {
					_this.activityList = res.rows
					// console.log(_this.activityList, 'res')
					// "/profile/upload/2025/04/03/JHuhUQqCZlrX580c665db7af6c6045a591e102c12ce2_20250403114807A001.jpeg"
				}, (err) => {

				})
			},
			showRecordModal() {
				// this.eventslists()
				this.modalVisible = true
			},
			closeModal() {
				this.modalVisible = false
			},
			goRecordPage(item) {
				let params = Object.keys(item).map(key => `${key}=${encodeURIComponent(item[key])}`).join('&');
				let url = `/subpackage1/homepage/record?${params}`;
				uni.navigateTo({
					url: url
				});
			},
			studentgetInfo() {
				this.$request.post(studentgetInfo, {}).then((res) => {
					console.log(res, 'res')
				}, (err) => {

				})
			},
			joinActivity(activity) {
				// 将 activity 对象转换为 JSON 字符串
				let activityStr = encodeURIComponent(JSON.stringify(activity));
				let url = `/subpackage1/homepage/signup?activity=${activityStr}`;
				uni.navigateTo({
					url: url
				});
			},
			viewAllActivities() {
				uni.navigateTo({
					url: '/subpackage1/homepage/allactivities' // tabBar 页面路径，不能带参数
				});
				// 处理查看全部活动的逻辑
				// console.log('查看全部活动');
			}
		}
	}
</script>

<style scoped>
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
	    background: radial-gradient(circle, rgba(255,215,0,0.3) 10%, transparent 60%);
	    opacity: 0;
	    transition: opacity 0.3s;
	  }
	  .sunflower-btn:active .sun-effect {
	    opacity: 1;
	  }
	  
	.level-tag,
	.status-tag {
		font-size: 24rpx;
		padding: 6rpx 20rpx;
		border-radius: 8rpx;
	}

	.level-tag.school {
		background: #e6f4ff;
		color: #1677ff;
	}

	.level-tag.class {
		background: #fff7e6;
		color: #fa8c16;
	}

	.container {
		height: 100vh;
		background: linear-gradient(to bottom, #9CDCFB 30%, #83C8F7 60%, #FFE87A 100%);
		overflow: hidden;
		position: relative;
	}

	/* 优化云朵样式 */
	.cloud {
		position: absolute;
		background: rgba(255, 255, 255, 0.95);
		border-radius: 100rpx;
		animation: cloudMove 25s linear infinite;
		filter: drop-shadow(0 20rpx 30rpx rgba(255, 255, 255, 0.3));
	}

	.cloud::before,
	.cloud::after {
		content: '';
		position: absolute;
		background: inherit;
		border-radius: inherit;
	}

	.cloud1 {
		width: 320rpx;
		height: 120rpx;
		top: 10%;
		left: -20%;
	}

	.cloud1::before {
		width: 80rpx;
		height: 80rpx;
		top: -30rpx;
		left: 40rpx;
	}

	.cloud1::after {
		width: 100rpx;
		height: 100rpx;
		top: -50rpx;
		right: 40rpx;
	}

	/* 其他云朵类似样式调整... */

	/* 向日葵容器 */
	.sunflower-box {
		position: absolute;
		top:25%;
		left: 50%;
		transform: translate(-50%, -50%);
		z-index: 2;
		width: 500rpx;
		height: 700rpx;
		filter: drop-shadow(0 20rpx 15rpx rgba(255, 180, 0, 0.3));
	}

	/* 模态框样式 */
	.record-modal {
		position: fixed;
		bottom: -100vh;
		left: 0;
		width: 100%;
		height: 70vh;
		background: #fff;
		border-radius: 40rpx 40rpx 0 0;
		box-shadow: 0 -10rpx 30rpx rgba(0, 0, 0, 0.1);
		transition: all 0.3s ease;
		z-index: 100000
	}

	.record-modal.show {
		bottom: 0;
	}

	.modal-header {
		padding: 30rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
		border-bottom: 2rpx solid #eee;
	}

	.title {
		font-size: 36rpx;
		color: #333;
		font-weight: bold;
	}

	.close {
		font-size: 50rpx;
		color: #999;
		padding: 0 20rpx;
	}

	.record-list {
		height: calc(70vh - 100rpx);
		/* padding: 20rpx 30rpx; */
	}

	.record-item {
		display: flex;
		align-items: center;
		padding: 30rpx 0;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.index {
		width: 80rpx;
		font-size: 28rpx;
		color: #666;
	}

	.item-title {
		flex: 1;
		font-size: 32rpx;
		color: #333;
		padding: 0 20rpx;
	}

	.record-btn {
		background: linear-gradient(45deg, #83C8F7, #9CDCFB);
		color: #fff;
		font-size: 28rpx;
		/* padding: 8rpx 30rpx; */
		/* padding-right: -30rpx; */
		border-radius: 40rpx;
		box-shadow: 0 5rpx 10rpx rgba(131, 200, 247, 0.3);
	}

	.sunflower {
		width: 100%;
		height: 100%;
		animation: breathe 3s ease-in-out infinite;
	}

	/* 新增草地 */
	.grass {
		position: absolute;
		bottom: 0;
		width: 100%;
		height: 200rpx;
		background: linear-gradient(to top, #8BC34A, #AED581);
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

	@media (max-width: 750rpx) {
		.sunflower-box {
			width: 350rpx;
			height: 500rpx;
		}

		.grass {
			height: 150rpx;
		}
	}

	.activity-list {
		position: absolute;
		bottom: 180rpx;
		left: 0;
		right: 0;
		padding: 0 30rpx;
		z-index: 10;
	}

	.list-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 30rpx;
	}

	.header-title {
		font-size: 36rpx;
		font-weight: bold;
		color: #333;
		position: relative;
		padding-left: 20rpx;
	}

	.header-title::before {
		content: '';
		position: absolute;
		left: 0;
		top: 50%;
		transform: translateY(-50%);
		width: 8rpx;
		height: 36rpx;
		background: #83C8F7;
		border-radius: 4rpx;
	}

	.header-actions {
		display: flex;
		align-items: center;
		color: #666;
	}

	.view-all {
		font-size: 28rpx;
		margin-right: 10rpx;
	}

	.list-content {
		white-space: nowrap;
		padding-bottom: 20rpx;
	}

	.activity-card {
		display: inline-block;
		width: 560rpx;
		background: #fff;
		border-radius: 24rpx;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.06);
		margin-right: 30rpx;
		position: relative;
		overflow: hidden;
		transition: all 0.3s ease;
		margin-bottom: 10rpx;
	}

	.activity-card:active {
		transform: scale(0.98);
	}

	.card-cover {
		width: 100%;
		height: 320rpx;
		border-radius: 24rpx 24rpx 0 0;
	}

	.card-content {
		padding: 24rpx;
	}

	.card-title {
		display: block;
		font-size: 32rpx;
		font-weight: 600;
		color: #333;
		margin-bottom: 20rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 1;
		-webkit-box-orient: vertical;
		white-space: normal;
	}

	/* .card-content {
		display: block;
		font-size: 26rpx;
		font-weight: 600;
		color: #ccc;
		margin-bottom: 20rpx;
		overflow: hidden;
		text-overflow: ellipsis;
		display: -webkit-box;
		-webkit-line-clamp: 1;
		-webkit-box-orient: vertical;
		white-space: normal;
	} */
	.card-meta {
		display: flex;
		align-items: center;
		margin-bottom: 30rpx;
	}

	.card-meta .u-icon {
		margin-right: 8rpx;
	}

	.meta-text {
		font-size: 24rpx;
		color: #666;
		margin-right: 30rpx;
	}

	.card-footer {
		display: flex;
		justify-content: space-between;
		align-items: center;
		position: relative;
		margin-bottom: 20rpx;
		/* background: yellow; */

	}

	.participants {
		display: flex;
		align-items: center;
		margin-bottom: 10rpx;
	}

	.participants-text {
		font-size: 24rpx;
		color: #999;
		margin-left: 20rpx;
	}

	.join-btn {
		display: flex;
		align-items: center;
		position: absolute;
		right: 0;
		top: 0;
		background: linear-gradient(135deg, #83C8F7, #9CDCFB);
		color: #fff;
		font-size: 24rpx;
		padding: 6rpx 36rpx;
		border-radius: 40rpx;
		box-shadow: 0 4rpx 12rpx rgba(131, 200, 247, 0.3);
		transition: all 0.3s ease;
		float: right;
		margin-bottom: 20rpx;
	}

	.join-btn:active {
		transform: scale(0.95);
		opacity: 0.9;
	}

	.card-tag {
		position: absolute;
		top: 20rpx;
		right: -30rpx;
		background: #FF6B6B;
		color: #fff;
		font-size: 24rpx;
		padding: 8rpx 40rpx;
		transform: rotate(45deg);
		box-shadow: 0 4rpx 12rpx rgba(255, 107, 107, 0.3);
	}
</style>