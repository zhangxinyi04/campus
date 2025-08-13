<template>
	<view class="content">
		<!-- 头部信息 -->
		<view class="header-bg">
			<view class="header">
				<view class="avatar">
					<image src="/static/user.png" mode="aspectFill" class="avatar-img"></image>
				</view>
				<view class="user-info">
					<text class="nickname">{{teachedetails.nickName}}</text>
					<text class="date">{{ currentDate }} {{ weekDay }}</text>
				</view>
				<view class="decor-line"></view>
			</view>
		</view>

		<!-- 选项栏 -->
		<view class="grid-container">
			<navigator url="/pages/evaluation/evaluation" class="grid-item card-hover">
				<view class="eval-content">
					<view class="item-header">
						<!-- <view class="item-icon eval-icon">
							<img src="../../static/ktpj.png"/>
						</view> -->
						<text class="item-title">课堂评价</text>
					</view>
					<view class="class-list">
						<view class="subject-item" v-for="(item,index) in teachedetails.sysCourses" :key="index">
							<text class="subject-tag chinese">{{item.courseName}}</text>
							<view class="class-group">
								<text class="class-tag" v-for="(i,d) in item.depts"
									@click="teachergetStudentInfoclick(item,i)">{{i.deptName}}</text>
								<!-- <text class="class-tag">一年级三班</text> -->
							</view>
						</view>
						<!-- <view class="subject-item">
              <text class="subject-tag math">数学</text>
              <view class="class-group">
                <text class="class-tag">一年级一班</text>
                <text class="class-tag">二年级六班</text>
              </view>
            </view> -->
					</view>
					<!-- <text class="item-sub">本周已完成</text> -->
				</view>
			</navigator>
		</view>

		<!-- 功能按钮组 -->
		<view class="action-group">
			<!-- <navigator url="/pages/history/history" class="action-button history-btn">
				<text class="iconfont icon-history"></text>
				历史记录
			</navigator> -->
			<navigator url="/pages/initiate/initiate" class="action-button create-btn" @click="initiateclick">
				<view >
					<text class="iconfont icon-add"></text>
					发起新活动
				</view>
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
		teachergetInfo
	} from '@/utils/index.js'
	var _self;
	export default {
		data() {
			return {
				currentDate: '',
				weekDay: '',
				teachedetails: ''
			};
		},

		onLoad() {
			_self = this
			this.getCurrentDate()
			this.teachergetInfos()
			const token = uni.getStorageSync('token');
			if (token) {

			} else {
				uni.reLaunch({
					url: '/pages/login/index'
				})
			}
		},
		methods: {
			initiateclick() {
				// let url = `/pages/index/initiate`;
				let url = `/pages/index/ActivityTemplate`;
				uni.navigateTo({
					url: url
				});
			},
			teachergetStudentInfoclick(item, i) {
				let obj = {
					courseId: item.courseId,
					deptId: i.deptId
				};
				// 将对象转换为 URL 参数格式
				let params = Object.keys(obj).map(key => `${key}=${encodeURIComponent(obj[key])}`).join('&');
				let url = `/pages/index/classroomassessment?${params}`;
				uni.navigateTo({
					url: url
				});
			},
			teachergetInfos() {

				this.$request.post(teachergetInfo, {}).then((res) => {
					_self.teachedetails = res.data
					uni.setStorageSync('teacherId', _self.teachedetails.userId);
					// console.log(_self.teachedetails, 'res')

				}, (err) => {

				})
			},
			getCurrentDate() {
				const date = new Date();
				const year = date.getFullYear();
				const month = String(date.getMonth() + 1).padStart(2, '0');
				const day = String(date.getDate()).padStart(2, '0');
				const weekDays = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
				this.currentDate = `${year}.${month}.${day}`;
				this.weekDay = weekDays[date.getDay()];
			}
		}
	};
</script>

<style lang="scss" scoped>
	/* 主题色 */
	$primary-color: #2a3c5e;
	$secondary-color: #007aff;
	$accent-color: #f5a623;

	.content {
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

				.nickname {
					font-size: 40rpx;
					font-weight: 600;
					display: block;
					margin-bottom: 10rpx;
				}

				.date {
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

	/* 网格布局 */
	.grid-container {
		display: grid;
		grid-template-columns: repeat(2, 1fr);
		gap: 30rpx;
		padding: 40rpx;
		margin-top: -40rpx;

		.grid-item {
			background: #fff;
			border-radius: 24rpx;
			padding: 40rpx;
			position: relative;
			transition: all 0.3s ease;
			box-shadow: 0 6rpx 20rpx rgba(42, 60, 94, 0.05);

			.item-icon {
				width: 80rpx;
				height: 80rpx;
				border-radius: 16rpx;
				margin-bottom: 30rpx;
				background-size: 60%;
				background-position: center;
				background-repeat: no-repeat;

			}

			.item-title {
				display: block;
				font-size: 34rpx;
				color: $primary-color;
				font-weight: 600;
				margin-bottom: 15rpx;
			}

			.item-sub {
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

	/* 操作按钮组 */
	.action-group {
		padding: 0 40rpx;

		.action-button {
			height: 120rpx;
			border-radius: 20rpx;
			margin-bottom: 30rpx;
			display: flex;
			align-items: center;
			padding: 0 40rpx;
			font-size: 32rpx;
			font-weight: 500;
			transition: all 0.3s ease;
			position: relative;

			.iconfont {
				font-size: 44rpx;
				margin-right: 25rpx;
				
			}

			&.history-btn {
				background: linear-gradient(90deg, #f0f4ff 0%, #ffffff 100%);
				
				color: $primary-color;
				border: 2rpx solid #e1e6f0;

				.badge {
					position: absolute;
					right: 40rpx;
					background: $accent-color;
					color: #fff;
					padding: 6rpx 20rpx;
					border-radius: 40rpx;
					font-size: 24rpx;
				}
			}

			&.create-btn {
				background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
				// background: linear-gradient(90deg, $secondary-color 0%, #4d8eff 100%);
				color: #fff;
				// box-shadow: 0 8rpx 24rpx rgba(0, 122, 255, 0.3);
			}

			&:active {
				transform: scale(0.98);
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

	/* 新增样式 */
	.eval-content {
		width: 100%;

		.item-header {
			display: flex;
			align-items: center;
			margin-bottom: 30rpx;

			.eval-icon {
				// background: #4CAF50 url('/static/eval-icon.png') no-repeat center/60%;
				width: 80rpx;
				height: 80rpx;
				border-radius: 16rpx;
			}
		}

		.class-list {
			margin: 20rpx 0;

			.subject-item {
				margin-bottom: 30rpx;

				&:last-child {
					margin-bottom: 0;
				}
			}

			.subject-tag {
				display: inline-block;
				padding: 8rpx 20rpx;
				border-radius: 8rpx;
				font-size: 26rpx;
				margin-right: 20rpx;

				&.chinese {
					background: #E8F5E9;
					color: #4CAF50;
				}

				&.math {
					background: #E3F2FD;
					color: #2196F3;
				}
			}

			.class-group {
				display: flex;
				flex-wrap: wrap;
				margin-top: 15rpx;
			}

			.class-tag {
				padding: 6rpx 16rpx;
				background: #f5f5f5;
				border-radius: 6rpx;
				font-size: 24rpx;
				color: #666;
				margin: 10rpx 10rpx 0 0;
			}
		}

		.item-sub {
			display: block;
			color: #4CAF50;
			font-size: 26rpx;
			margin-top: 20rpx;
		}
	}

	/* 调整网格容器 */
	.grid-container {
		grid-template-columns: 1fr;
		padding: 30rpx;

		.grid-item {
			padding: 30rpx;
		}
	}

	/* 其他保持原有样式 */
</style>