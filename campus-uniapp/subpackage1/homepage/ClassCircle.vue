<template>
	<view class="container">
		<scroll-view class="circle-list" scroll-y>
			<view class="circle-item" v-for="(item, index) in circleList" :key="index">
				<!-- 头部信息 -->
				<view class="item-header">
					<image class="avatar" src="/static/user.png" mode="aspectFill"></image>
					<view class="user-info">
						<view class="top-line">
							<text class="username">{{item.studentName}}</text>
							<text class="course-name">{{item.courseName}}</text>
						</view>
						<view class="bottom-line">
							<text class="dept-name">{{item.deptName}}</text>
							<text class="time">{{item.createTime}}</text>
						</view>
					</view>
				</view>

				<!-- 正文内容 -->
				<view class="content-box">
					<text class="content-text">我通过{{getDescription(item.type * 1)}}，获得{{item.mark}}分。</text>
					<view class="image-grid" v-if="item.image">
						<image class="content-image" 
                               :src="configUrl+item.image"
                               mode="aspectFill" 
                               @click="previewImage(item.image)"></image>
					</view>
					<view class="teacher-comment" v-if="item.content">
						<text class="comment-text">教师评语：{{item.content}}</text>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import {
		studentgetInfo,
		studentclassCircle
	} from '@/utils/index.js'
	import {
		img_url
	} from '@/utils/baseUrl.js'
	
	export default {
		data() {
			return {
				configUrl: img_url,
				deptId: '',
				circleList: []
			}
		},
		onLoad() {
			this.studentgetInfo()
		},
		methods: {
			getDescription(type) {
				const descriptions = {
					1: '预先学习 夯实基础',
					2: '勇于质疑 大胆提问',
					3: '专心听讲 踊跃互动',
					4: '静心思考 自主探究',
					5: '深度互动 智慧碰撞',
					6: '高效合作 共学共进',
					7: '积极阅读 持之以恒',
					8: '日练日清 细查无遗',
					9: '声音洪亮 自信发声',
					10: '条理清晰 逻辑严密',
					11: '学融文本 多维表达'
				}
				return descriptions[type] || '无匹配内容'
			},
			studentgetInfo() {
				this.$request.post(studentgetInfo).then(res => {
					this.deptId = res.data.student.deptId
					this.studentclassCircle()
				})
			},
			studentclassCircle() {
				this.$request.post(studentclassCircle, { deptId: this.deptId }).then(res => {
					this.circleList = res.data
				})
			},
			previewImage(image) {
				uni.previewImage({
					urls: [this.configUrl + image]
				})
			}
		}
	}
</script>

<style lang="scss">
	.container {
		padding: 20rpx;
		background: #f5f6f7;
	}

	.circle-list {
		height: calc(100vh - 40rpx);
	}

	.circle-item {
		background: #fff;
		border-radius: 16rpx;
		margin-bottom: 20rpx;
		padding: 24rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.04);
	}

	.item-header {
		display: flex;
		gap: 20rpx;
		margin-bottom: 24rpx;

		.avatar {
			width: 80rpx;
			height: 80rpx;
			border-radius: 50%;
			flex-shrink: 0;
		}

		.user-info {
			flex: 1;
			min-width: 0;

			.top-line {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-bottom: 8rpx;

				.username {
					font-size: 30rpx;
					color: #333;
					font-weight: 600;
					max-width: 65%;
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}

				.course-name {
					font-size: 24rpx;
					color: #666;
					background: #f5f5f5;
					padding: 4rpx 16rpx;
					border-radius: 24rpx;
				}
			}

			.bottom-line {
				display: flex;
				align-items: center;
				gap: 12rpx;

				.dept-name {
					font-size: 24rpx;
					color: #666;
					padding: 4rpx 12rpx;
					background: #f8f8f8;
					border-radius: 6rpx;
				}

				.time {
					font-size: 22rpx;
					color: #999;
				}
			}
		}
	}

	.content-box {
		.content-text {
			font-size: 28rpx;
			color: #444;
			line-height: 1.6;
			margin-bottom: 16rpx;
		}

		.image-grid {
			display: grid;
			grid-template-columns: repeat(3, 1fr);
			gap: 10rpx;
			margin-top: 20rpx;

			.content-image {
				width: 100%;
				height: 220rpx;
				border-radius: 8rpx;
				background: #f0f0f0;
			}
		}

		.teacher-comment {
			background: #fff9e6;
			border-radius: 8rpx;
			padding: 16rpx;
			margin-top: 20rpx;

			.comment-text {
				font-size: 24rpx;
				color: #666;
				line-height: 1.5;
			}
		}
	}
</style>