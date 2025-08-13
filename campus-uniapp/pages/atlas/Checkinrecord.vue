<template>
	<view class="container">
		<!-- <view class="tabs">
            <view class="tab-item" :class="{ active: activeTab === 0 }" @click.stop="switchTab(0)">打卡记录</view>
            <view class="tab-item" :class="{ active: activeTab === 1 }" @click.stop="switchTab(1)">学生打卡记录</view>
            <view class="tab-line" :style="lineStyle"></view>
        </view> -->

		<!-- 打卡列表 -->
		<view class="record-list">
			<view v-for="(group, cycle) in groupedRecords" :key="cycle">
				<view class="cycle-title" @click="toggleCycle(cycle)">
					第 {{ cycle }} 周期
					<view class="arrow" :class="{ expanded: expandedCycles[cycle] }">▼</view>
				</view>
				<view v-show="expandedCycles[cycle]">
					<view v-for="(item, index) in group" :key="index" class="record-card" @click="Studentclock(item)">
						<view class="activity-info">
							<view class="status-tag" v-if="item.status != null" :class="{
                            'not-started': item.status === 0,
                            'in-progress': item.status === 0,
                            'completed': item.status === 2
                        }">
								{{ getStatusText(item.status) }}
							</view>
						</view>
						<!-- 时间信息 -->
						<view class="time-info">
							<view class="time-item">
								<text class="time-label">打卡时间：</text>
								<text class="time-value">{{ item.startTime }} - {{ item.endTime }}</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		cycleRecords,
		cycleRecordsByStudent
	} from '@/utils/index.js'
	import NoData from '../components/NoData.vue'
	var _self;
	export default {
		components: {
			NoData
		},
		data() {
			return {
				activeTab: 0,
				lineLeft: '10%',
				query: {
					eventId: '',
					pageNum: 1,
					pageSize: 10
				},
				total: 0,
				records: [],
				groupedRecords: {},
				expandedCycles: {}
				// eventId:''
			}
		},
		onLoad(options) {
			_self = this
			_self.query.eventId = options.eventId
			_self.cycleRecords()
			// _self.cycleRecordsByStudent()
		},
		computed: {
			lineStyle() {
				return `left: ${this.lineLeft};`
			}
		},
		methods: {
			Studentclock(item) {
				let url = `/pages/atlas/Studentclock?eventCycleId=${item.eventCycleId}`;
				uni.navigateTo({
					url: url
				});
			},
			// switchTab(index) {
			// 	this.activeTab = index
			// 	this.lineLeft = index === 0 ? '10%' : '60%'
			// 	if (this.activeTab === 0) {
			// 		_self.cycleRecords()
			// 	} else {
			// 		_self.cycleRecordsByStudent()
			// 	}
			// },
			getStatusText(status) {
				return ['未开始', '进行中', '已结束'][status]
			},
			// cycleRecordsByStudent() {
			// 	this.$request.get(cycleRecordsByStudent, this.query).then((res) => {
			// 		_self.records = res.data
			// 		// console.log(_self.records, 'res')
			// 		_self.groupData()
			// 	}, (err) => {

			// 	})
			// },
			cycleRecords() {
				this.$request.get(cycleRecords, this.query).then((res) => {
					_self.records = res.data
					_self.groupData()
				}, (err) => {

				})
			},
			toggleCycle(cycle) {
				this.$set(this.expandedCycles, cycle, !this.expandedCycles[cycle]);
			},
			groupData() {
				const grouped = {}
				this.records.forEach(item => {
					const cycle = item.eventCycle
					if (!grouped[cycle]) {
						grouped[cycle] = []
						// 初始化时默认展开
						this.$set(this.expandedCycles, cycle, true);
					}
					grouped[cycle].push(item)
				})
				this.groupedRecords = grouped
			}
		}
	}
</script>

<style scoped>
	/* 新增箭头样式 */
	.cycle-title {
		position: relative;
		padding-right: 60rpx;
		cursor: pointer;
	}

	.arrow {
		position: absolute;
		right: 30rpx;
		top: 50%;
		transform: translateY(-50%);
		transition: transform 0.3s;
		font-size: 24rpx;
		color: #666;
	}

	.arrow.expanded {
		transform: translateY(-50%) rotate(180deg);
	}

	.tabs {
		display: flex;
		position: relative;
		padding: 30rpx 0;
		background: #fff;
	}

	.tab-item {
		flex: 1;
		text-align: center;
		font-size: 34rpx;
		color: #666;
		transition: all 0.3s;
		position: relative;
	}

	.tab-item.active {
		color: #2a3c5e;
		font-weight: 600;
	}

	.tab-line {
		position: absolute;
		bottom: 0;
		width: 30%;
		height: 6rpx;
		background: #007aff;
		transition: all 0.3s ease;
	}

	/* 页面容器 */
	.container {
		padding: 20rpx 30rpx;
		background-color: #f8f8f8;
		min-height: 100vh;
	}

	/* 头部标题 */
	.header {
		padding: 40rpx 0;
		text-align: center;
	}

	.header-text {
		font-size: 40rpx;
		color: #007AFF;
		font-weight: bold;
		letter-spacing: 2rpx;
	}

	/* 周期标题 */
	.cycle-title {
		font-size: 32rpx;
		color: #2a3c5e;
		font-weight: 600;
		margin: 40rpx 0 20rpx;
		padding: 24rpx 32rpx;
		background: linear-gradient(90deg, #f8f9fa 0%, #ffffff 100%);
		border-left: 6rpx solid #007AFF;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.04);
	}

	/* 打卡卡片 */
	.record-card {
		background: #fff;
		border-radius: 16rpx;
		padding: 32rpx;
		margin-bottom: 24rpx;
		position: relative;
		display: flex;
		flex-direction: column;
		gap: 24rpx;
		border: 2rpx solid #f1f3f4;
	}

	/* 状态标签 */
	.status-tag {
		position: absolute;
		right: -2rpx;
		top: -2rpx;
		padding: 8rpx 24rpx;
		border-radius: 0 16rpx 0 16rpx;
		font-size: 24rpx;
		font-weight: 500;
		box-shadow: -2rpx 2rpx 4rpx rgba(0, 0, 0, 0.08);
	}

	/* 时间信息容器 */
	.time-info {
		/* display: grid;
    grid-template-columns: 1fr; */
		/* gap: 16rpx; */
	}

	/* 时间条目 */
	.time-item {
		font-size: 28rpx;
		line-height: 1.6;
	}

	/* 时间标签 */
	.time-label {
		color: #909399;
		width: 100%;
		float: left;
		margin-top: -20rpx;
		margin-bottom: 10rpx;
	}

	/* 时间值 */
	.time-value {
		width: 100%;
		float: left;
		color: #303133;
		font-weight: 500;
		/* flex: 1; */
	}

	/* 图标样式 */
	.icon-clock {
		width: 28rpx;
		height: 28rpx;
		background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIyNCIgaGVpZ2h0PSIyNCIgdmlld0JveD0iMCAwIDI0IDI0IiBmaWxsPSJub25lIiBzdHJva2U9IiM5MDkzOTkiIHN0cm9rZS13aWR0aD0iMiIgc3Ryb2tlLWxpbmVjYXA9InJvdW5kIiBzdHJva2UtbGluZWpvaW49InJvdW5kIj48Y2lyY2xlIGN4PSIxMiIgY3k9IjEyIiByPSIxMCIvPjxwb2x5bGluZSBwb2ludHM9IjEyIDYgMTIgMTIgMTYgMTQiLz48L3N2Zz4=') center/contain no-repeat;
	}

	/* 状态颜色 */
	.not-started {
		background: #f8f9fa;
		color: #909399;
		border: 2rpx solid #e9ecef;
	}

	.in-progress {
		background: #007AFF;
		color: white;
	}

	.completed {
		background: #34c759;
		color: white;
	}
</style>