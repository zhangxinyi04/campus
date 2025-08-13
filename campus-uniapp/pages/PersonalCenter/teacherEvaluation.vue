<template>
	<view class="container">
		<!-- 标题 -->
		<view class="header">
			<text class="title">选择班级</text>
		</view>

		<!-- 课程列表 -->
		<view class="course-list" v-for="(item,index) in teachedetails.sysCourses">
			<!-- 数学课程 -->
			<view class="course-card">
				<view class="course-title bg-math">
					<text>{{item.courseName}}</text>
				</view>
				<view class="class-list">
					<view v-for="(i,d) in item.depts" :key="d" class="class-item" @click="goToStudentList(item,i)">
						{{i.deptName}}
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	var _self;
	import {
		teachergetInfo
	} from '@/utils/index.js'
	export default {
		data() {
			return {
				// mathClasses: ['一年级一班', '一年级二班'],
				// chineseClasses: ['一年级三班', '二年级二班'],
				teachedetails: ''
			}
		},
		onLoad() {
			_self = this
			_self.teachergetInfos()
		},
		methods: {
			teachergetInfos() {
				this.$request.post(teachergetInfo, {}).then((res) => {
					_self.teachedetails = res.data
					uni.setStorageSync('teacherId', _self.teachedetails.userId);
				}, (err) => {

				})
			},
			goToStudentList(item,i) {
				console.log(item,i)
					return
				// let obj = {
				// 	courseId: item.courseId,
				// 	deptId: i.deptId
				// }
				// return
				
				// // 将对象转换为 URL 参数格式
				// let params = Object.keys(obj).map(key => `${key}=${encodeURIComponent(obj[key])}`).join('&');
				// // let url = `/pages/index/classroomassessment?${params}`;
				// let url = `/pages/PersonalCenter/teacherEvaluation/classroomassessment?${params}`;
				// uni.navigateTo({
				// 	url: url
				// });
			}
		}
	}
</script>

<style lang="scss">
	.container {
		padding: 30rpx;
		background: #f5f7fa;
		min-height: 100vh;
	}

	.header {
		text-align: center;
		padding: 40rpx 0;

		.title {
			font-size: 36rpx;
			color: #303133;
			font-weight: 600;
			position: relative;

			&::after {
				content: '';
				position: absolute;
				bottom: -10rpx;
				left: 50%;
				transform: translateX(-50%);
				width: 80rpx;
				height: 6rpx;
				background: #409eff;
				border-radius: 3rpx;
			}
		}
	}

	.course-card {
		background: #fff;
		border-radius: 20rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.04);
		overflow: hidden;
	}

	.course-title {
		padding: 24rpx 30rpx;
		color: #fff;
		font-size: 32rpx;
		font-weight: 500;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);

		// &.bg-math {
		//   background: linear-gradient(135deg, #36a1ff, #245bff);
		// }
		// &.bg-chinese {
		//   background: linear-gradient(135deg, #ff8c37, #ff4d43);
		// }
	}

	.class-list {
		padding: 20rpx 30rpx;
		display: flex;
		flex-wrap: wrap;
	}

	.class-item {
		width: 35%;
		margin: 2%;
		padding: 30rpx;
		background: #f5f7fa;
		border-radius: 16rpx;
		text-align: center;
		font-size: 28rpx;
		color: #606266;
		transition: all 0.3s;

		&:active {
			transform: scale(0.98);
			background: #ebedf0;
		}

		&::after {
			content: '›';
			margin-left: 15rpx;
			color: #c0c4cc;
			font-size: 36rpx;
			vertical-align: middle;
		}
	}
</style>