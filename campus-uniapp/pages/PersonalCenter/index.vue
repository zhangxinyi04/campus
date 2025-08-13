<template>
	<view class="PersonalCenter">
		<!-- 头部信息 -->
		<view class="header-bg">
			<view class="header">
				<view class="avatar">
					<image src="/static/user.png" mode="aspectFill" class="avatar-img"></image>
				</view>
				<view class="user-info">
					<view class="nickname-wrapper">
						<text class="nickname">{{teachedetails.nickName}}</text>
						<!-- <text class="identity-tag">老师</text> -->
					</view>
					<text class="address">
						<text>{{teachedetails.phonenumber}}</text>
						<!-- <text v-for="(item,index) in teachedetails.sysCourses" :key="index"></text> -->
					</text>
				</view>
				<view class="decor-line"></view>
			</view>
		</view>

		<!-- 活动和审核记录 -->
		<view class="record-container" style="margin-top: 15rpx;">
			<navigator url="/pages/myActivities/myActivities" class="record-item card-hover">
				<text class="record-title">我发起的活动</text>
				<text class="record-sub">共5条</text>
			</navigator>
			<navigator url="/pages/pendingReviews/pendingReviews" class="record-item card-hover">
				<text class="record-title">待审核记录</text>
				<text class="record-sub">共3条</text>
			</navigator>
		</view>

		<!-- 选项栏 -->
		<view class="option-container" style="margin-top: 15rpx;">
			<!-- <navigator url="/pages/myActivities/myActivities" class="option-item card-hover">
				<text class="option-title">我的活动</text>
			</navigator>
			<navigator url="/pages/reviewProcessing/reviewProcessing" class="option-item card-hover">
				<text class="option-title">审核处理</text>
			</navigator>
			<navigator url="/pages/appealProcessing/appealProcessing" class="option-item card-hover">
				<text class="option-title">申诉处理</text>
			</navigator>
			<navigator url="/pages/honorGranting/honorGranting" class="option-item card-hover">
				<text class="option-title">荣誉授予</text>
			</navigator> -->
			<navigator @click="teacherEvaluationClick" url='/pages/PersonalCenter/teacherEvaluation/teacherEvaluation' class="option-item card-hover">
				<text class="option-title">教师寄语</text>
			</navigator>
			<!-- <navigator url="/pages/teachingClass/teachingClass" class="option-item card-hover">
				<text class="option-title">任教班级</text>
			</navigator> -->
			<navigator url="/pages/settings/settings" class="option-item card-hover">
				<text class="option-title">设置</text>
			</navigator>
		</view>

		<!-- 底部装饰元素 -->
		<view class="decor-bottom">
			<view class="wave"></view>
		</view>
	</view>
</template>

<script>
	import {
		teachergetInfo,
		teachergetStudentInfo
	} from '@/utils/index.js'
	var _self;
	export default {
		data() {
			return {
				teachedetails:''
			};
		},
		onLoad() {
			_self = this;
			_self.teachergetInfos()
			_self.teachergetStudentInfo()
		},
		methods: {
			teacherEvaluationClick(){
				uni.navigateTo({
					url: '/pages/PersonalCenter/teacherEvaluation/teacherEvaluation'
				});
			},
			teachergetStudentInfo(){
				this.$request.post(teachergetStudentInfo, {}).then((res) => {
					// console.log(res, 'res')
				
				}, (err) => {
				
				})
			},
			teachergetInfos() {
				this.$request.post(teachergetInfo, {}).then((res) => {
					_self.teachedetails = res.data
					// console.log(_self.teachedetails, 'res')

				}, (err) => {

				})
			}
		}
	};
</script>

<style lang="scss" scoped>
	/* 主题色 */
	$primary-color: #2a3c5e;
	$secondary-color: #007aff;
	$accent-color: #f5a623;

	.PersonalCenter {
		background: linear-gradient(180deg, #f8f9fb 0%, #eef1f6 100%);
		min-height: 100vh;
		position: relative;
	}

	/* 头部区域 */
	.header-bg {
		background: linear-gradient(135deg, $primary-color 0%, #3a5075 100%);
		padding: 40rpx 40rpx 80rpx;
		border-radius: 0 0 40rpx 40rpx;
		box-shadow: 0 10rpx 30rpx rgba(42, 60, 94, 0.1);

		.header {
			display: flex;
			align-items: center;
			position: relative;

			.avatar {
				width: 120rpx;
				height: 120rpx;
				border-radius: 50%;
				background: #fff;
				padding: 4rpx;
				box-shadow: 0 8rpx 20rpx rgba(0, 0, 0, 0.1);

				&-img {
					width: 100%;
					height: 100%;
					border-radius: 50%;
				}
			}

			.user-info {
				margin-left: 30rpx;
				color: #fff;

				.nickname-wrapper {
					display: flex;
					align-items: center;
					margin-bottom: 10rpx;

					.nickname {
						font-size: 40rpx;
						font-weight: 600;
					}

					.identity-tag {
						font-size: 28rpx;
						margin-left: 10rpx;
						opacity: 0.9;
					}
				}

				.address {
					font-size: 28rpx;
					opacity: 0.9;
				}
			}

			.decor-line {
				position: absolute;
				bottom: -50rpx;
				left: 50%;
				transform: translateX(-50%);
				width: 80%;
				height: 2rpx;
				background: rgba(255, 255, 255, 0.2);
			}
		}
	}

	/* 记录容器 */
	.record-container {
		display: grid;
		grid-template-columns: repeat(2, 1fr);
		gap: 30rpx;
		padding: 40rpx;

		.record-item {
			background: #fff;
			border-radius: 24rpx;
			padding: 40rpx;
			position: relative;
			transition: all 0.3s ease;
			box-shadow: 0 6rpx 20rpx rgba(42, 60, 94, 0.05);

			.record-title {
				display: block;
				font-size: 34rpx;
				color: $primary-color;
				font-weight: 600;
				margin-bottom: 15rpx;
			}

			.record-sub {
				font-size: 26rpx;
				color: #999;
			}
		}

		.card-hover {
			&:active {
				transform: scale(0.98);
				box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
			}
		}
	}

	/* 选项容器 */
	.option-container {
		display: grid;
		grid-template-columns: repeat(2, 1fr);
		gap: 30rpx;
		padding: 40rpx;

		.option-item {
			background: #fff;
			border-radius: 24rpx;
			padding: 40rpx;
			position: relative;
			transition: all 0.3s ease;
			box-shadow: 0 6rpx 20rpx rgba(42, 60, 94, 0.05);

			.option-title {
				display: block;
				font-size: 34rpx;
				color: $primary-color;
				font-weight: 600;
				text-align: center;
			}
		}

		.card-hover {
			&:active {
				transform: scale(0.98);
				box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
			}
		}
	}

	/* 底部波浪装饰 */
	.decor-bottom {
		position: absolute;
		bottom: 0;
		left: 0;
		right: 0;
		height: 80rpx;
		overflow: hidden;

		.wave {
			position: absolute;
			top: -20rpx;
			left: -10%;
			right: -10%;
			height: 100rpx;
			background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAxMjAwIDEyMCI+PHBhdGggZmlsbD0iI2Y4ZjlmYiIgZD0iTTAgNjQuNUMyMDAgMCA0MDAgMCA2MDAgNjQuNVgxMjAwVjEyMEgwVjY0LjV6Ii8+PC9zdmc+') repeat-x;
			background-size: 240rpx 120rpx;
			animation: wave 12s linear infinite;
		}
	}

	@keyframes wave {
		0% {
			transform: translateX(0);
		}

		100% {
			transform: translateX(-240rpx);
		}
	}
</style>