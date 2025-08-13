<template>
	<view class="container">
		<!-- 学期选择 -->
		<view class="semester-picker">
			<view class="picker-label">当前学期：</view>
			<view class="picker-content">
				<text @click="showClass = true">{{ Semestername }}</text>
			</view>
		</view>
		
		<view class="tabs">
			<view class="tab-item" :class="{ active: currentTab === 0 }" @click="switchTab(0)">未评价</view>
			<view class="tab-item" :class="{ active: currentTab === 1 }" @click="switchTab(1)">已评价</view>
			<view class="tab-line" :style="lineStyle"></view>
		</view>

		<!-- 学期选择 -->
		<u-picker :show="showClass" :columns="classArr" keyName="name" @close='showClass = false'
			@confirm="confirmClass" @cancel="cancelClass"></u-picker>

		<!-- 学生列表 -->
		<scroll-view scroll-y class="student-list"  v-show="currentTab === 0">
			<view class="student-grid">
				<view v-for="(student, index) in studetails" :key="index" class="student-card"
					@click="handleStudentClick(student)">
					<!-- 多选标记 -->

					<view :class="['student-info', student.sex === '0' ? 'male' : 'female']">
						<text class="name">{{student.studentName}}</text>
						{{ student.gender === '男' ? '♂' : '♀' }}
					</view>
				</view>
			</view>
		</scroll-view>
		
		<scroll-view scroll-y class="student-list"  v-show="currentTab === 1">
			<view class="student-grid">
				<view v-for="(student, index) in studetailsNew" :key="index" class="student-card"
					@click="handleStudentClick(student)">
					  <view class="evaluated-tag">已评价</view>
					<view :class="['student-info', student.sex === '0' ? 'male' : 'female']">
						<text class="name">{{student.studentName}}</text>
						{{ student.gender === '男' ? '♂' : '♀' }}
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	var _self;
	import {
		teachergetStudentInfo,
		commentlist,
		semesterList
	} from '@/utils/index.js'

	export default {
		data() {
			return {
				studetails: [],
				courseId: '',
				isMultiSelect: false,
				isAllSelected: false,
				selectedStudents: [],
				commentlistArr: [],
				semesters: [], // 学期id列表
				semesterId: 0, // 当前选中学期id
				Semestername: '', // 当前学期
				showClass: false,
				classArr: [],
				studetailsNew: [],
				activeTab: 0,
				lineLeft: '10%',
				currentTab: 0,
			};
		},
		computed: {
			lineStyle() {
				return `left: ${this.lineLeft};`
			}
		},

		onLoad(options) {
			_self = this
			this.courseId = options.courseId
			this.teachergetStudentInfos(options.deptId)
			// this.commentlist()
		},

		methods: {
			switchTab(index) {
				this.currentTab = index
				this.lineLeft = index === 0 ? '10%' : '60%'
				// this.lineLeft = index === 0 ? 40 : 340
			},
			confirmClass(e) {
				_self.semesterId = e.value[0].semesterId
				_self.Semestername = e.value[0].name
				_self.commentlist()
				_self.showClass = false
			},
			cancelClass() {
				_self.showClass = false
			},
			semesterList() {
				this.$request.get(semesterList, {}).then((res) => {
					_self.classArr.push(res.data)
					_self.semesterId = res.data[0].semesterId, // 当前选中学期id
						_self.Semestername = res.data[0].name
					_self.commentlist()
				}, (err) => {

				})
			},
			commentlist() {
				var numericStudentIds = _self.semesters.map(Number);
				let obj = {
					studentIds: numericStudentIds.join(','),
					semesterId: _self.semesterId
				}
				this.$request.get(commentlist, obj).then((res) => {
					_self.commentlistArr = res.data

					var commentIds = _self.commentlistArr.map(item => item.studentId);

					// 分离匹配的元素到新数组
					_self.studetailsNew = _self.studetails.filter(item => commentIds.includes(item.studentId));

					// 更新原数组为不匹配的元素
					_self.studetails = _self.studetails.filter(item => !commentIds.includes(item.studentId));

					console.log(_self.studetailsNew, _self.studetails, 'resres')
				}, (err) => {

				})
			},
			onSemesterChange(e) {
				// this.semesterIndex = e.detail.value;
				// this.currentSemester = this.semesters[this.semesterIndex].semesterId;
				// 重新加载相关数据
				// this.commentlist();
				// 可以根据需要重新加载学生列表
			},


			handleStudentClick(student) {
				uni.navigateTo({
					url: `/pages/PersonalCenter/teacherEvaluation/GrowthReport?studentId=` + student.studentId
				});
			},


			navigateToEvaluation(item) {

				// const params = `courseId=${this.courseId}&studentId=${item.studentId}`
				var arr = []
				var params = {
					courseId: this.courseId,
					studentId: item.studentId
				}
				arr.push(params)
				const paramsStr = encodeURIComponent(JSON.stringify(arr));
				uni.navigateTo({
					url: `/pages/index/Studentevaluation?params=${paramsStr}`
				});
			},

			teachergetStudentInfos(deptId) {
				this.$request.post(teachergetStudentInfo, {
					deptId
				}).then((res) => {
					this.studetails = res.data
					for (var i = 0; i < this.studetails.length; i++) {
						this.semesters.push(this.studetails[i].studentId)
					}

					this.semesterList()

				}, (err) => {

				})
			},
		}
	};
</script>

<style lang="scss">
	.evaluated-tag {
	  position: absolute;
	  top: 12rpx;
	  right: 12rpx; /* 可调整定位参数 */
	  padding: 6rpx 16rpx;
	  background-color: #ffd700; /* 金色背景 */
	  color: #333;
	  font-size: 24rpx;
	  border-radius: 20rpx; /* 圆角标签 */
	  z-index: 1; /* 确保层级在卡片上方 */
	}
	.semester-picker {
		display: flex;
		align-items: center;
		margin: 20rpx 0;
		padding: 20rpx 30rpx;
		background: #fff;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

		.picker-label {
			font-size: 28rpx;
			color: #666;
			margin-right: 20rpx;
		}

		.picker-content {
			flex: 1;
			display: flex;
			align-items: center;
			justify-content: space-between;
			padding: 20rpx 30rpx;
			background: #f5f7fa;
			border-radius: 12rpx;
			color: #333;
			font-size: 30rpx;
			transition: all 0.3s;

			&:active {
				background: #ebedf0;
				transform: scale(0.98);
			}

			text {
				margin-right: 20rpx;
			}
		}
	}
	
	.tabs {
		display: flex;
		position: relative;
		padding: 30rpx 0;
		background: #fff;
		margin-bottom: 20rpx;
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
	

	.container {
		padding: 20rpx 30rpx;
		background: #f5f7fa;
		min-height: 100vh;
	}

	.action-bar {
		margin: 20rpx 0;
		display: flex;
		justify-content: flex-end;

		.multi-btn {
			background: #007AFF;
			color: white;
			font-size: 28rpx;
			padding: 0 30rpx;
			height: 60rpx;
			line-height: 60rpx;
			border-radius: 30rpx;

			&::after {
				border: none;
			}
		}
	}

	.search-box {
		background: #fff;
		border-radius: 50rpx;
		padding: 20rpx 40rpx;
		margin-bottom: 30rpx;
		display: flex;
		align-items: center;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);

		.search-input {
			flex: 1;
			font-size: 28rpx;
			color: #333;
			margin-left: 20rpx;
		}
	}

	.student-list {
		height: calc(100vh - 240rpx);

		.student-grid {
			display: grid;
			grid-template-columns: repeat(3, 1fr);
			gap: 20rpx;
			padding: 10rpx 0;
		}
	}

	.student-card {
		position: relative;
		background: #fff;
		border-radius: 16rpx;
		padding: 30rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
		transition: all 0.2s;

		&.selected {
			background: #f0f8ff;
			border: 2rpx solid #007AFF;
		}

		.check-icon {
			position: absolute;
			right: 10rpx;
			top: 10rpx;
		}
	}

	.student-info {
		display: flex;
		flex-direction: column;
		align-items: center;
		font-size: 28rpx;
		padding: 20rpx;
		border-radius: 12rpx;

		.name {
			font-size: 32rpx;
			color: #333;
			margin-bottom: 10rpx;
		}

		&.male {
			background: #e1f5fe;
			color: #039be5;
		}

		&.female {
			background: #fce4ec;
			color: #e91e63;
		}
	}
</style>