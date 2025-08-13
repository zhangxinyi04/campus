<template>
	<view class="container">
		<!-- 页面标题 -->
	<!-- 	<view class="page-header">
			<text class="page-title">{{ obj.courseName || '学生分组管理' }}</text>
		</view> -->

		<!-- 未分组学生区域 -->
		<view class="group-section">
			<view class="section-header">
				<text class="section-title">未分组学生</text>
				<text class="section-count">({{ ungroupedStudents.length }}人)</text>
			</view>

			<view class="student-list">
				<view class="student-item" v-for="student in ungroupedStudents" :key="student.studentId">
					<text class="student-name">{{ student.studentName }}</text>
					<view class="student-actions">
						<view class="remove-btn" style="background-color: #409eff;" @click="allocationGroup(student)">
							<text>分配小组</text>
						</view>
					</view>
				</view>
			</view>
		</view>

		<!-- 已分组学生区域 -->
		<view class="group-section">
			<view class="section-header">
				<text class="section-title">已分组学生</text>
				<text class="section-count">({{ groupedStudents.length }}人)</text>
			</view>

			<!-- 按小组分组显示 -->
			<view class="grouped-students-container">
				<view class="group-container" v-for="groupNumber in groupNumbers" :key="groupNumber">
					<view class="group-header">
						<text class="group-title">第{{ groupNumber }}组</text>
						<text class="group-count">({{ getStudentsInGroup(groupNumber).length }}人)</text>
					</view>

					<view class="student-list">
						<view class="student-item" v-for="student in getStudentsInGroup(groupNumber)"
							:key="student.studentId">
							<text class="student-name">{{ student.studentName }}</text>
							<view class="student-actions">
								<view class="remove-btn" style="background-color: #409eff;"
									@click="allocationGroup(student)">
									<text>分配小组</text>
								</view>
								<view class="remove-btn" @click="removeFromGroup(student)">
									<text>移出小组</text>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>


		<!-- 保存按钮 -->
		<view class="save-section">
			<button class="submit-btn" @click="saveChanges">保存更改</button>
		</view>

		<u-picker :show="groupShow" :columns="groupList" keyName="name" @close='groupShow = false'
			@confirm="groupconfirm" @cancel="groupcancel"></u-picker>
	</view>
</template>

<script>
	import {
		teachergetStudentInfo,
		classEvaluationselectStudentBonusCount,
		classEvaluationselectStudents,
		classEvaluationeditGroup
	} from '@/utils/index.js'
	var _self;

	export default {
		data() {
			return {
				selectedStudent: null,
				groupShow: false,
				groupList: [
					[{
							name: '1小组',
							value: '1'
						},
						{
							name: '2小组',
							value: '2'
						},
						{
							name: '3小组',
							value: '3'
						},
						{
							name: '4小组',
							value: '4'
						},
						{
							name: '5小组',
							value: '5'
						},
						{
							name: '6小组',
							value: '6'
						},
						{
							name: '7小组',
							value: '7'
						},
						{
							name: '8小组',
							value: '8'
						},
						{
							name: '9小组',
							value: '9'
						},
						{
							name: '10小组',
							value: '10'
						}
					]
				],
				obj: {},
				selectStudent: [],
				students: [],
				originalStudents: [],
				groups: Array.from({
					length: 10
				}, (_, i) => `第${i + 1}组`),
				groupNumbers: Array.from({
					length: 10
				}, (_, i) => i + 1)
			}
		},
		onLoad(options) {
			_self = this
			this.obj = JSON.parse(decodeURIComponent(options.params));
			this.classEvaluationselectStudentBonusCount();
		},
		computed: {
			// 计算属性：未分组学生
			ungroupedStudents() {
				return this.students.filter(student => student.group === null);
			},
			// 计算属性：已分组学生
			groupedStudents() {
				return this.students.filter(student => student.group !== null);
			}
		},
		methods: {
			allocationGroup(student) {
				this.selectedStudent = student;
				this.groupShow = true;
			},
			groupconfirm(e) {
			    this.groupShow = false;
				for(var i=0;i<this.students.length;i++){
					if(this.selectedStudent.studentId == this.students[i].studentId){
						this.students[i].group = e.value[0].value * 1
					}
				}
			  },

			groupcancel() {
				this.groupShow = false;
			},
			// 获取指定组的学生
			getStudentsInGroup(groupNumber) {
				return this.students.filter(student => student.group === groupNumber);
			},
			// 从服务器获取学生列表
			selectStudents(deptId) {
				this.$request.post(classEvaluationselectStudents, {
					deptId
				}).then((res) => {
					this.students = res.data.map(s => ({
						...s,
						group: s.group ? Number(s.group) : null,
						counts: 0
					}));

					// 保存原始数据用于对比
					this.originalStudents = JSON.parse(JSON.stringify(this.students));

					// 匹配奖励计数
					this.students.forEach(student => {
						const match = this.selectStudent.find(ss => ss.studentId === student.studentId);
						if (match) student.counts = match.counts;
					});

				}, (err) => {
					console.error('获取学生列表失败', err);
				});
			},
			// 获取学生奖励计数
			classEvaluationselectStudentBonusCount() {
				this.$request.post(classEvaluationselectStudentBonusCount, {
					deptId: this.obj.deptId,
					courseId: this.obj.courseId
				}).then((res) => {
					this.selectStudent = res.data;
					this.selectStudents(this.obj.deptId);
				}, (err) => {
					console.error('获取学生奖励计数失败', err);
				});
			},

			// 从组中移除学生
			removeFromGroup(selectedStudent) {
			    uni.showModal({
			      title: '提示',
			      content: `确定要将${selectedStudent.studentName}移出小组吗？`,
			      success: res => {
			        if (res.confirm) {
			          for(var i=0;i<_self.students.length;i++){
			          	if(selectedStudent.studentId == _self.students[i].studentId){
			          		_self.students[i].group = null
							 uni.showToast({ title: '已移出小组', icon: 'success' });
			          	}
			          }
			        }
			      }
			    });
			  },
			// 保存更改
			saveChanges() {
				const changedStudents = this.students.filter(student => {
				  const originalStudent = this.originalStudents.find(s => s.studentId === student.studentId);
				  return originalStudent && originalStudent.group !== student.group;
				});
				const result = changedStudents.map(item => ({
				  studentId: item.studentId,
				  group: item.group
				}));
				// 提交更改
				this.$request.put(classEvaluationeditGroup, result).then((res) => {
					uni.showToast({
						title: '保存成功',
						icon: 'success'
					});
					this.classEvaluationselectStudentBonusCount();
					
				}, (err) => {
					uni.showToast({
						title: '保存失败',
						icon: 'none'
					});
					console.error('保存分组失败', err);
				});
			}
		}
	}
</script>

<style>
	.submit-btn {
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
		color: #fff;
		border-radius: 50rpx;
		margin: 40rpx 0;
		height: 80rpx;
		line-height: 80rpx;
		font-size: 32rpx;
	}
	.container {
		padding: 20rpx;
		background-color: #f5f5f5;
	}

	.page-header {
		padding: 20rpx 0;
		text-align: center;
	}

	.page-title {
		font-size: 36rpx;
		font-weight: bold;
		color: #333;
	}

	.group-section {
		margin-bottom: 30rpx;
		background-color: #fff;
		border-radius: 12rpx;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.05);
	}

	.section-header {
		display: flex;
		align-items: center;
		padding: 24rpx 30rpx;
		border-bottom: 1rpx solid #eee;
	}

	.section-title {
		font-size: 30rpx;
		font-weight: 500;
		color: #333;
	}

	.section-count {
		margin-left: 15rpx;
		font-size: 24rpx;
		color: #999;
	}

	.student-list {
		padding: 15rpx 0;
	}

	.student-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 30rpx;
		border-bottom: 1rpx solid #f5f5f5;
	}

	.student-item:last-child {
		border-bottom: none;
	}

	.student-name {
		font-size: 28rpx;
		color: #333;
	}

	.student-actions {
		display: flex;
		align-items: center;
	}

	.assign-btn,
	.change-btn,
	.remove-btn {
		padding: 12rpx 24rpx;
		border-radius: 8rpx;
		font-size: 24rpx;
		margin-left: 15rpx;
	}

	.assign-btn {
		background-color: #409eff;
		color: #fff;
	}

	.change-btn {
		background-color: #67c23a;
		color: #fff;
	}

	.remove-btn {
		background-color: #f56c6c;
		color: #fff;
	}

	.grouped-students-container {
		padding: 15rpx;
	}

	.group-container {
		margin-bottom: 20rpx;
		background-color: #f9f9f9;
		border-radius: 10rpx;
	}

	.group-header {
		display: flex;
		align-items: center;
		padding: 20rpx 25rpx;
		background-color: #eef1f6;
		border-radius: 10rpx 10rpx 0 0;
	}

	.group-title {
		font-size: 28rpx;
		font-weight: 500;
		color: #333;
	}

	.group-count {
		margin-left: 15rpx;
		font-size: 24rpx;
		color: #666;
	}

	.save-section {
		padding: 30rpx 20rpx;
	}

	.save-btn {
		width: 100%;
		padding: 24rpx 0;
		background-color: #409eff;
		color: #fff;
		font-size: 30rpx;
		border-radius: 12rpx;
	}
</style>