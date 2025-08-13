<template>
	<view class="container">
		<view class="header-tip">
			<text class="tip-text">选择你喜欢的活动模板开始创建吧！</text>
		</view>

		<view class="template-grid">
			<view v-for="(item, index) in templates" :key="index" class="template-card"
				@tap="navigateToDetail(item)">
				<image :src="imgUrl+item.image" mode="aspectFill" class="cover-image" />
				<view class="content">
					<text class="title">{{ item.title }}</text>
					<text class="tag">{{ item.tags }}</text>
				</view>
			</view>
			<u-loadmore :loadmoreText="loadmoreText" @loadmore='loadmoreClick' color="#1CD29B" lineColor="#1CD29B"
				dashed line />
		</view>

		<view class="custom-create" @tap="navigateToCreate">
			<text class="create-text">没有心仪模板？立即自由创建</text>
			<image src="/static/images/arrow-right.png" class="arrow-icon" />
		</view>
	</view>
</template>

<script>
	import {
		eventslistTemplate
	} from '@/utils/index.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
		data() {
			return {
				templates: [

				],
				configUrl: '',
				imgUrl:'',
				loadmoreText: '点击查看更多',
				query:{
					pageNum: 1,
					pageSize: 10
				},
				total:0
			};
		},
		onLoad() {
			_self = this
			_self.eventslistTemplate()
		},
		methods: {
			loadmoreClick() {
				if (_self.total == _self.templates.length) {
					_self.loadmoreText = '暂无更多数据'
					uni.showToast({
						icon: 'none',
						duration: 3000,
						title: '暂无更多数据',
					})
					return
				}
				_self.query.pageSize += 10
				_self.eventslistTemplate()
			},
			eventslistTemplate() {
				this.$request.get(eventslistTemplate,_self.query).then((res) => {
					_self.configUrl = config_url
					_self.imgUrl = img_url
					_self.total = res.total
					_self.templates = res.rows
					// console.log(res, 'res')

				}, (err) => {

				})
			},
			navigateToDetail(item) {	
				// console.log(item,'1')
				let obj = {
					title: item.title,
					intro: item.intro,
					image: item.image
				};
				// 将对象转换为 URL 参数格式
				let params = Object.keys(obj).map(key => `${key}=${encodeURIComponent(obj[key])}`).join('&');
				uni.navigateTo({
					url: `/pages/index/initiate?${params}`
				});
			},
			navigateToCreate() {
				let url = `/pages/index/initiate`;
				uni.navigateTo({
					url: url
				});
			}
		}
	};
</script>

<style lang="scss">
	.header-tip {
		padding: 24rpx;
		background: rgba(64, 158, 255, 0.1);
		border-radius: 16rpx;
		margin-bottom: 32rpx;

		.tip-text {
			color: #409EFF;
			font-size: 28rpx;
			font-weight: 500;
		}
	}

	.container {
		padding: 32rpx;
		background: #f8f9fa;
		min-height: 100vh;
		box-sizing: border-box;
		padding-bottom: 140rpx;
	}

	.template-grid {
		display: flex;
		flex-wrap: wrap;
		gap: 24rpx;
		/* 列间距 */
	}

	.template-card {
		flex: 0 0 calc(50% - 12rpx);
		/* 精确计算宽度 */
		box-sizing: border-box;
		background: #fff;
		border-radius: 24rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.06);

		/* 保证卡片等高等宽 */
		aspect-ratio: 3/4;
		display: flex;
		flex-direction: column;
	}

	.cover-image {
		width: 100%;
		flex: 1;
		/* 图片区域自适应 */
		min-height: 240rpx;
	}

	.content {
		padding: 24rpx;
		flex-shrink: 0;
	}

	.title {
		font-size: 28rpx;
		color: #303133;
		font-weight: 600;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 1;
		overflow: hidden;
		text-overflow: ellipsis;
		margin-bottom: 16rpx;
	}

	.tag {
		display: inline-block;
		padding: 8rpx 16rpx;
		background: rgba(64, 158, 255, 0.1);
		border-radius: 8rpx;
		font-size: 24rpx;
		color: #409EFF;
	}

	.custom-create {
		position: fixed;
		bottom: 32rpx;
		left: 32rpx;
		right: 32rpx;
		background: #fff;
		border-radius: 16rpx;
		padding: 24rpx 32rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.08);

		.create-text {
			font-size: 28rpx;
			color: #303133;
			font-weight: 500;
		}

		.arrow-icon {
			width: 40rpx;
			height: 40rpx;
		}
	}
</style>