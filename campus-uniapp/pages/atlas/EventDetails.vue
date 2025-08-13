<template>
	<view class="detail-container">
		<!-- 顶部渐变背景 -->
		<view class="header-bg"></view>

		<!-- 主要内容区域 -->
		<scroll-view scroll-y class="content-wrapper">
			<!-- 标题区域 -->
			<view class="title-section">
				<view class="title-row">
					<text class="main-title">{{ detail.eventName }}</text>
					<view class="score-badge">
						<text class="score">{{ detail.mark || 0 }}</text>
						<text class="score-label">分</text>
					</view>
				</view>
				<!-- <view class="status-tag" :class="detail.status === '0' ? 'ongoing' : 'ended'">
					{{ detail.status === '0' ? '进行中' : '已结束' }}
				</view> -->
				<view class="status-tag" v-if="detail.status == 0">
					未开始
				</view>
				<view class="status-tag ongoing" v-if="detail.status == 1">
					进行中
				</view>
				<view class="status-tag ended" v-if="detail.status == 2">
					已结束
				</view>
			</view>

			<!-- 活动图片 -->
			<image class="event-image" :src="configUrl + detail.eventPic" mode="aspectFill" />

			<!-- 基本信息卡片 -->
			<view class="info-card">
				<!-- 时间信息 -->
				<view class="info-item">
					<!-- <view class="icon-box calendar">
            <text class="iconfont icon-calendar">
				<image
				  class="event-image" 
				  :src="configUrl + detail.eventMedal.url" 
				  mode="aspectFill"
				/>
			</text>
          </view> -->
					<view class="time-group">
						<text class="time-label">开始时间</text>
						<text class="time-value">{{ detail.startTime }}</text>
						<text class="time-label">结束时间</text>
						<text class="time-value">{{ detail.endTime }}</text>
					</view>
				</view>

				<!-- 类型标签 -->
				<view class="tag-group">
					<view class="type-tag level" :class="detail.rank == 0 ? 'school' : 'class'">
						{{ detail.rank == 0 ? '校级' : '班级' }}
					</view>
					<view class="type-tag category">
						{{ detail.eventCategory === 0 ? '打卡类' : '记录类' }}
					</view>
					<view class="type-tag tag-type">
						{{ detail.eventTag === 0 ? '学科活动' : '跨学科活动' }}
					</view>
				</view>
			</view>

			<!-- 详细信息卡片 -->
			<view class="info-card">
				<view class="section-title">活动详情</view>
				<text class="description">{{ detail.eventIntro }}</text>

				<view class="section-title">注意事项</view>
				<text class="notice">{{ detail.precautions || '暂无特别注意事项' }}</text>
			</view>

			<!-- 参与班级 -->
			<view class="info-card" v-if="detail.depts">
				<view class="section-title">参与班级</view>
				<scroll-view scroll-x class="class-list">
					<text v-for="(cls, index) in detail.depts" :key="index" class="class-tag">
						{{ cls.deptName }}
					</text>
				</scroll-view>
			</view>

			<!-- 活动设置 -->
			<view class="info-card">
				<view class="section-title">活动设置</view>
				<view class="settings-grid">
					<view class="setting-item" v-if="detail.eventCategory == 0">
						<text class="iconfont icon-clock"></text>
						<text>打卡周期：{{ ['天', '周', '月'][detail.eventCycle] }}</text>
					</view>
					<view class="setting-item">
						<text class="iconfont icon-upload"></text>
						<text>上传记录：{{ detail.records === '0' ?  '不需要':'需要'  }}</text>
					</view>
					<view class="setting-item">
						<text class="iconfont icon-eye"></text>
						<text>活动可见：{{ detail.visible == "0" ? '私密':'公开' }}</text>
					</view>
				</view>
			</view>
			
			<view class="info-card" v-if="detail.depts">
				<view class="section-title">活动奖章</view>
				<image style="width: 150rpx;height: 150rpx;" :src="configUrl + detail.eventMedal.url" mode="aspectFit" />
				<!-- <scroll-view scroll-x class="class-list">
					<text v-for="(cls, index) in detail.depts" :key="index" class="class-tag">
						{{ cls.deptName }}
					</text>
				</scroll-view> -->
			</view>

			<!-- <view class="action-section">
				<view class="medal-box" v-if="detail.eventMedal">
					<image class="medal-image" :src="configUrl + detail.eventMedal.url" mode="aspectFit" />
					<text class="medal-label">活动奖章</text>
				</view>
			</view> -->
			<!-- 附件及奖章 -->
			<view class="action-section">
				<button class="attach-btn" v-if="detail.attachments">
					<text class="iconfont icon-attachment"></text>
					查看附件
				</button>
				<button class="attach-btn" @click="Checkinrecordclick" v-if="detail.eventCategory == 0">
				    <text class="iconfont icon-clock"></text>
				    查看打卡记录
				  </button>

			</view>
		</scroll-view>
	</view>
</template>

<script>
	import {
		events
	} from '@/utils/index.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
		data() {
			return {
				configUrl: '',
				detail: {}
			}
		},
		onLoad(options) {
			_self = this
			_self.configUrl = img_url
			_self.events(options.eventId)
		},
		methods: {
			Checkinrecordclick(){
				
				let url = `/pages/atlas/Checkinrecord?eventId=${_self.detail.eventId}`;
				uni.navigateTo({
				    url: url
				});
			},
			events(eventId) {
				this.$request.get(`/events/${eventId}`).then(res => {
					this.detail = res.data
					console.log()
				})
			}
		}
	}
</script>

<style lang="scss">
	
	.detail-container {
		position: relative;
		min-height: 100vh;
		background: #f8fafc;
	}

	.header-bg {
		position: absolute;
		top: 0;
		left: 0;
		right: 0;
		height: 300rpx;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
		z-index: 0;
	}

	.content-wrapper {
		position: relative;
		z-index: 1;
		// padding: 30rpx;
		padding-top: 160rpx;
	}

	.title-section {
		background: rgba(255, 255, 255, 0.9);
		border-radius: 16rpx;
		padding: 30rpx;
		margin-bottom: 30rpx;
		backdrop-filter: blur(10px);

		.title-row {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 20rpx;
		}

		.main-title {
			font-size: 40rpx;
			color: #2a3c5e;
			font-weight: 600;
			flex: 1;
			margin-right: 30rpx;
		}

		.score-badge {
			background: #ffd700;
			border-radius: 40rpx;
			padding: 10rpx 30rpx;
			text-align: center;

			.score {
				font-size: 36rpx;
				font-weight: bold;
				color: #2a3c5e;
				display: block;
				line-height: 1;
			}

			.score-label {
				font-size: 20rpx;
				color: #666;
			}
		}

		.status-tag {
			align-self: flex-start;
			padding: 8rpx 24rpx;
			border-radius: 30rpx;
			font-size: 24rpx;

			&.ongoing {
				background: #e6fffb;
				color: #13c2c2;
			}

			&.ended {
				background: #f0f0f0;
				color: #666;
			}
		}
	}

	.event-image {
		width: 100%;
		height: 400rpx;
		border-radius: 24rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.1);
	}

	.info-card {
		background: #fff;
		border-radius: 24rpx;
		padding: 30rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

		.info-item {
			display: flex;
			align-items: center;
			margin-bottom: 30rpx;

			.icon-box {
				width: 80rpx;
				height: 80rpx;
				border-radius: 16rpx;
				display: flex;
				align-items: center;
				justify-content: center;
				margin-right: 20rpx;

				&.calendar {
					background: #e6f4ff;
					color: #1677ff;
				}
			}

			.time-group {
				flex: 1;

				.time-label {
					font-size: 24rpx;
					color: #999;
					display: block;
				}

				.time-value {
					font-size: 28rpx;
					color: #333;
					display: block;
					margin-bottom: 15rpx;
				}
			}
		}
	}

	.tag-group {
		display: flex;
		flex-wrap: wrap;
		gap: 16rpx;

		.type-tag {
			padding: 8rpx 24rpx;
			border-radius: 30rpx;
			font-size: 24rpx;

			&.level.school {
				background: #e6f4ff;
				color: #1677ff;
			}

			&.level.class {
				background: #fff7e6;
				color: #fa8c16;
			}

			&.category {
				background: #f6ffed;
				color: #389e0d;
			}

			&.tag-type {
				background: #fff0f6;
				color: #eb2f96;
			}
		}
	}

	.section-title {
		font-size: 30rpx;
		color: #2a3c5e;
		font-weight: 600;
		margin: 30rpx 0 20rpx;
		position: relative;
		padding-left: 20rpx;

		&::before {
			content: '';
			position: absolute;
			left: 0;
			top: 50%;
			transform: translateY(-50%);
			width: 6rpx;
			height: 28rpx;
			background: #007aff;
			border-radius: 4rpx;
		}
	}

	.description,
	.notice {
		font-size: 28rpx;
		color: #666;
		line-height: 1.6;
		margin-bottom: 30rpx;
		display: block;
	}

	.class-list {
		white-space: nowrap;

		.class-tag {
			display: inline-block;
			padding: 12rpx 30rpx;
			background: #f0f2f5;
			border-radius: 30rpx;
			margin-right: 20rpx;
			font-size: 26rpx;
			color: #666;
		}
	}

	.settings-grid {
		display: grid;
		grid-template-columns: repeat(2, 1fr);
		gap: 20rpx;

		.setting-item {
			display: flex;
			align-items: center;
			font-size: 26rpx;
			color: #666;
			padding: 20rpx;
			background: #f8f9fb;
			border-radius: 12rpx;

			.iconfont {
				margin-right: 12rpx;
				color: #007aff;
			}
		}
	}

	.action-section {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx 0;

		.attach-btn {
			background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
			color: #fff;
			border-radius: 50rpx;
			padding: 20rpx 40rpx;
			font-size: 28rpx;
			display: flex;
			align-items: center;

			.iconfont {
				margin-right: 12rpx;
			}
		}

		.medal-box {
			text-align: center;

			.medal-image {
				width: 120rpx;
				height: 120rpx;
				margin-bottom: 10rpx;
			}

			.medal-label {
				font-size: 24rpx;
				color: #666;
			}
		}
	}
</style>