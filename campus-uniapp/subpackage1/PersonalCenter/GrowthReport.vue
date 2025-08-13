<template>
	<view class="container">
		<!-- 学期列表 -->
		<view v-for="(term, index) in commentlistArr" :key="index" class="term-card">
			<view class="term-header">
				<text class="term-title">{{term.semesterId}}</text>
				<text class="term-status">进行中</text>
			</view>

			<!-- 功能入口 -->
			<view class="entry-container">
				<view class="entry-item" @tap="navTo('parent',term)">
					<view class="entry-left">
						<uni-icons type="email" size="24" color="#4CAF50"></uni-icons>
						<text class="entry-text">家长寄语</text>
					</view>
					<uni-icons type="arrowright" size="18" color="#999"></uni-icons>
				</view>

				<view class="divider"></view>

				<view class="entry-item" @tap="navTo('report',term)" :class="{disabled: !term.canGenerate}">
					<view class="entry-left">
						<uni-icons type="flag" size="24" color="#2196F3"></uni-icons>
						<text class="entry-text">成长报告</text>
					</view>
					<uni-icons v-if="term.canGenerate" type="arrowright" size="18" color="#999"></uni-icons>
					<text v-else class="tip-text">未生成</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		commentlist
	} from '@/utils/index.js'
	var _self;
	export default {
		data() {
			return {
				commentlistArr:[],
				termList: [{
						name: "2023-2024学年 第一学期",
						status: "进行中",
						canGenerate: false
					},
					{
						name: "2022-2023学年 第二学期",
						status: "已结束",
						canGenerate: true
					},
					{
						name: "2022-2023学年 第一学期",
						status: "已结束",
						canGenerate: true
					}
				]
			};
		},
		onLoad() {
			_self = this
			_self.commentlist()
		},
		methods: {
			commentlist() {
			  this.$request.get(commentlist, {}).then((res) => {
			  	_self.commentlistArr = res.data
			  	// console.log(_self.commentlistArr,'resres')
			  }, (err) => {
			  		
			  })
			},
			navTo(type, term) {
			    if (type ==='report' &&!this.term.canGenerate) return;
			    let url;
			    if (type === 'parent') {
			        url = `/subpackage1/PersonalCenter/Parentsmessage?studentCommentId=${term.studentCommentId}`;
			    } else {
			        url = `/pages/report/generate?studentCommentId=${term.studentCommentId}`;
			    }
			    uni.navigateTo({
			        url: url
			    });
			}
		}
	};
</script>

<style lang="scss">
	.container {
		padding: 30rpx;
		background: #f5f5f5;
		min-height: 100vh;
	}

	.term-card {
		background: #fff;
		border-radius: 16rpx;
		margin-bottom: 30rpx;
		padding: 30rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.08);
	}

	.term-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding-bottom: 20rpx;
		margin-bottom: 20rpx;
		border-bottom: 2rpx solid #eee;
	}

	.term-title {
		font-size: 32rpx;
		color: #333;
		font-weight: 500;
	}

	.term-status {
		font-size: 26rpx;
		color: #666;
		background: #f0f0f0;
		padding: 8rpx 20rpx;
		border-radius: 40rpx;
	}

	.entry-item {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 30rpx 20rpx;
		transition: all 0.3s;

		&:active {
			background-color: #f8f8f8;
		}
	}

	.entry-left {
		display: flex;
		align-items: center;
	}

	.entry-text {
		font-size: 30rpx;
		color: #333;
		margin-left: 20rpx;
	}

	.divider {
		height: 2rpx;
		background: #f0f0f0;
		margin: 0 20rpx;
	}

	.disabled {
		opacity: 0.6;

		.entry-text {
			color: #999;
		}
	}

	.tip-text {
		font-size: 26rpx;
		color: #999;
	}
</style>