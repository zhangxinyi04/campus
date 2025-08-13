<template>
	<view class="container">
		<!-- 顶部操作栏 -->
		<view class="switch-container">
			<view class="switch-card">
				<view class="switch-item">
					<text class="switch-label">小组模式</text>
					<u-switch v-model="groupMode" @change="handleGroupModeChange" active-color="#007AFF"
						inactive-color="#E5E9F2"></u-switch>
				</view>
				<view class="switch-divider"></view>
				<view class="switch-item">
					<text class="switch-label">上课模式</text>
					<u-switch v-model="classMode" @change="handleClassModeChange" active-color="#007AFF"
						inactive-color="#E5E9F2"></u-switch>
				</view>
				<view class="switch-divider"></view>
				<view class="switch-item">
					<text class="switch-label">任务模式</text>
					<u-switch v-model="Taskmode" @change="handleTaskmodeChange" active-color="#007AFF"
						inactive-color="#E5E9F2"></u-switch>
				</view>
				<view class="switch-divider"></view>
				<view class="switch-item">
					<text class="switch-label">小组管理</text>
					<view class="navigate-icon" @click="navigateToGroupManagement">
					  <text>前往 > </text>
					</view>
				</view>
			</view>
		</view>

		<!-- 优化后的操作按钮区域 -->
		<view class="action-bar">
			<view class="action-button primary" 
				@click="toggleMultiSelect" 
				:class="{ 'active': isMultiSelect }">
				{{ isMultiSelect ? '取消多选' : '多选点评' }}
			</view>
			
			<view class="action-button secondary" 
				@click="selectAll" 
				v-if="isMultiSelect"
				:class="{ 'active': isAllSelected }">
				{{ isAllSelected ? '取消全选' : '全选' }}
			</view>
			
			<view class="action-button accent" 
				@click="gotoEvaluation" 
				v-if="isMultiSelect"
				:class="{ 'disabled': !selectedStudents.length }">
				去点评
			</view>
			
			<view class="action-button secondary" 
				v-if="!isMultiSelect" 
				:class="{ 'active': true }" @click="handleRandomSelect">
				<text class="icon-random">🎲</text>
				<text>随机点评</text>
				
			</view>
			
			<view class="action-button tertiary" 
				v-if="!isMultiSelect" 
				:class="{ 'disabled': timerRunning }"
				@click="openTimerDialog">
				<text class="icon-timer">⏱️</text>
				<text>计时器</text>
			</view>
		</view>
		
		<view v-if="Taskmode == true">
			<scroll-view scroll-y class="student-list">
				<view class="student-grid">
					<view v-for="student in studetails" :key="student.studentId" class="student-card"
						:class="{selected: isSelected(student)}" @click="deleteStudent(student.studentId)">
						<view class="evaluation-badge" :class="{ 'badge-loading': !student.countsLoaded }">
							{{ student.counts || '加载中' }}
						</view>
						<view :class="['student-info', student.sex === '0' ? 'male' : 'female']">
							<text class="name">{{ student.studentName }}</text>
							{{ student.gender === '男' ? '♂' : '♀' }}
						</view>
					</view>
				</view>
			</scroll-view>
		</view>

		<!-- 学生列表 / 小组列表 - 添加加载状态 -->
		<view v-if="Taskmode == false">
		<view v-if="!isLoading" class="student-list-container">
			<scroll-view scroll-y class="student-list" v-if="!groupMode">
				<view class="student-grid">
					<view v-for="student in studetails" :key="student.studentId" class="student-card"
						:class="{selected: isSelected(student)}" @click="handleStudentClick(student)">
						<uni-icons v-if="isMultiSelect" class="check-icon"
							:type="isSelected(student) ? 'checkbox-filled' : 'circle'"
							:color="isSelected(student) ? '#007AFF' : '#666'" size="24"></uni-icons>
						<view class="evaluation-badge" :class="{ 'badge-loading': !student.countsLoaded }">
							{{ student.counts || '加载中' }}
						</view>
						<view :class="['student-info', student.sex === '0' ? 'male' : 'female']">
							<text class="name">{{ student.studentName }}</text>
							{{ student.gender === '男' ? '♂' : '♀' }}
						</view>
					</view>
				</view>
			</scroll-view>

			<scroll-view scroll-y class="student-list" v-else>
				<view v-for="(members, group) in studentsgroup" :key="group" class="group-section">
					<view class="group-header">
						<view class="group-title-container">
							<text class="group-title">{{ group === 'null' ? '未分' : group }}组</text>
							<text class="group-count">({{ members.length }}人)</text>
						</view>
						<view class="group-evaluate-btn" @click="handleGroupEvaluation(group)">
							小组点评
						</view>
					</view>
					<view class="student-grid">
						<view v-for="student in members" :key="student.studentId" class="student-card"
							:class="{selected: isSelected(student)}" @click="handleStudentClick(student)">
							<uni-icons v-if="isMultiSelect" class="check-icon"
								:type="isSelected(student) ? 'checkbox-filled' : 'circle'"
								:color="isSelected(student) ? '#007AFF' : '#666'" size="24"></uni-icons>
							<view class="evaluation-badge" :class="{ 'badge-loading': !student.countsLoaded }">
								{{ student.counts || '加载中' }}
							</view>
							<view :class="['student-info', student.sex === '0' ? 'male' : 'female']">
								<text class="name">{{ student.studentName }}</text>
								{{ student.gender === '男' ? '♂' : '♀' }}
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
		<view v-else class="loading-container">
			<view class="loading-spinner"></view>
			<text class="loading-text">加载中...</text>
		</view>
		</view>
		
		<!-- 弹窗部分保持原有逻辑 -->
		<!-- 随机点评弹窗 -->
		<view class="dialog-mask" v-if="randomDialogVisible" @click.stop>
			<view class="dialog-container">
				<view class="dialog-header"><text class="dialog-title">选择随机人数</text></view>
				<view class="dialog-body">
					<view class="input-group">
						<text class="input-label">人数:</text>
						<input class="input-number" type="number" v-model.number="randomCount" min="1"
							:max="studetails.length" />
					</view>
					<text class="tip-text">最多可选 {{ studetails.length }} 人</text>
				</view>
				<view class="dialog-footer">
					<view class="dialog-btn cancel-btn" @click="randomDialogVisible = false">取消</view>
					<view class="dialog-btn confirm-btn" @click="confirmRandomCount">确定</view>
				</view>
			</view>
		</view>
		<!-- 随机结果弹窗 -->
		<view class="dialog-mask" v-if="randomResultDialogVisible" @click.stop>
			<view class="dialog-container">
				<view class="dialog-header"><text class="dialog-title">随机点评结果</text></view>
				<view class="dialog-body">
					<view class="random-result-grid" v-if="randomSelectedStudents.length > 0">
						<view v-for="student in randomSelectedStudents" :key="student.studentId" class="student-card">
							<view :class="['student-info', student.sex === '0' ? 'male' : 'female']">
								<text
									class="name">{{ student.studentName }}</text>{{ student.gender === '男' ? '♂' : '♀' }}
							</view>
						</view>
					</view>
					<view class="empty-tip" v-else>未选中任何学生</view>
				</view>
				<view class="dialog-footer">
					<button class="dialog-btn cancel-btn" @click="cancelRandom">取消</button>
					<button class="dialog-btn confirm-btn" @click="goToEvaluationFromRandom">去点评</button>
				</view>
				<!-- <view class="dialog-footer">
					<view class="dialog-btn cancel-btn" @click="randomDialogVisible = false">取消</view>
					<view class="dialog-btn confirm-btn" @click="confirmRandomCount">确定</view>
				</view> -->
			</view>
		</view>
		<!-- 计时器弹窗 -->
		<view class="dialog-mask" v-if="timerDialogVisible" @click.stop>
			<view class="dialog-container">
				<view class="dialog-header"><text class="dialog-title">设置计时器</text></view>
				<view class="dialog-body">
					<view class="timer-display">
						<text class="timer-number">{{ formatTime(timerHours) }}</text><text
							class="timer-separator">:</text>
						<text class="timer-number">{{ formatTime(timerMinutes) }}</text><text
							class="timer-separator">:</text>
						<text class="timer-number">{{ formatTime(timerSeconds) }}</text>
					</view>
					<view class="timer-controls">
						<view class="timer-control-group"><text class="timer-label">小时</text>
							<view class="timer-inputs"><view class="timer-btn" @click="decreaseHours">-</view><text
									class="timer-value">{{ timerHours }}</text><view class="timer-btn"
									@click="increaseHours">+</view></view>
						</view>
						<view class="timer-control-group"><text class="timer-label">分钟</text>
							<view class="timer-inputs"><view class="timer-btn"
									@click="decreaseMinutes">-</view><text
									class="timer-value">{{ timerMinutes }}</text><view class="timer-btn"
									@click="increaseMinutes">+</view></view>
						</view>
						<view class="timer-control-group"><text class="timer-label">秒钟</text>
							<view class="timer-inputs"><view class="timer-btn"
									@click="decreaseSeconds">-</view><text
									class="timer-value">{{ timerSeconds }}</text><view class="timer-btn"
									@click="increaseSeconds">+</view></view>
						</view>
					</view>
				</view>
				<view class="dialog-footer timer-buttons"><view class="dialog-btn cancel-btn"
						@click="closeTimer">取消</view><view class="dialog-btn confirm-btn" @click="startTimer"
						:class="{ 'disabled': !hasTimerValue }">开始</view></view>
			</view>
		</view>
		<!-- 计时器运行弹窗 -->
		<view class="dialog-mask" v-if="timerRunningDialogVisible" @click.stop>
			<view class="dialog-container">
				<view class="dialog-header"><text class="dialog-title">计时器运行中</text></view>
				<view class="dialog-body">
					<view class="timer-display">
						<text class="timer-number">{{ formatTime(currentHours) }}</text><text
							class="timer-separator">:</text>
						<text class="timer-number">{{ formatTime(currentMinutes) }}</text><text
							class="timer-separator">:</text>
						<text class="timer-number">{{ formatTime(currentSeconds) }}</text>
					</view>
				</view>
				<view class="dialog-footer timer-buttons"><view class="dialog-btn cancel-btn"
						@click="resetTimer">重置</view><view class="dialog-btn confirm-btn"
						@click="toggleTimer">{{ timerPaused ? '继续' : '暂停' }}</view></view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		teachergetStudentInfo,
		classEvaluationselectStudentBonusCount,
		classEvaluationlist,
		classEvaluationclassEvaluatio
	} from '@/utils/index.js';

	export default {
		data() {
			return {
				studetails: [],
				studentsgroup: {},
				courseId: '',
				isMultiSelect: false,
				isAllSelected: false,
				selectedStudents: [],
				groupMode: false,
				classMode: false,
				classEvaluationlistArr: [],
				selectStudent: [],
				randomDialogVisible: false,
				randomResultDialogVisible: false,
				randomCount: 1,
				randomSelectedStudents: [],
				timerDialogVisible: false,
				timerRunningDialogVisible: false,
				timerHours: 0,
				timerMinutes: 0,
				timerSeconds: 0,
				currentHours: 0,
				currentMinutes: 0,
				currentSeconds: 0,
				timerPaused: false,
				timerInterval: null,
				timerRunning: false,
				isLoading: true, // 新增加载状态
				selectStudentLoaded: false ,// 标记selectStudent是否已加载
				Taskmode:false
			};
		},
		onLoad(options) {
			this.courseId = options;
			// 使用Promise.all确保所有数据都加载完成
			Promise.all([
				this.fetchBonusCounts(),
				this.fetchStudentList(),
				this.fetchEvaluationList()
			]).then(() => {
				this.isLoading = false;
				uni.showToast({
					title: '数据加载完成',
					icon: 'success',
					duration: 1500
				});
			}).catch(err => {
				console.error('数据加载失败:', err);
				this.isLoading = false;
				uni.showToast({
					title: '数据加载失败',
					icon: 'none'
				});
			});
		},
		computed: {
			hasTimerValue() {
				return this.timerHours > 0 || this.timerMinutes > 0 || this.timerSeconds > 0;
			}
		},
		methods: {
			deleteStudent(studentId){
				this.studetails = this.studetails.filter(student => student.studentId !== studentId)
			},
			handleTaskmodeChange(){
				
			},
			navigateToGroupManagement(){
				uni.navigateTo({
					url: `/pages/index/groupmanagement?params=${encodeURIComponent(JSON.stringify(this.courseId))}`
				});
			},
			fetchEvaluationList() {
				return this.$request.post(classEvaluationlist, {
					type: 0
				}).then(res => {
					this.classEvaluationlistArr = res.data;
				});
			},
			fetchBonusCounts() {
				return this.$request.post(classEvaluationselectStudentBonusCount, {
					deptId: this.courseId.deptId,
					courseId: this.courseId.courseId
				}).then(res => {
					this.selectStudent = res.data;
					this.selectStudentLoaded = true;
					// 数据加载后更新学生计数
					this.updateStudentCounts();
				});
			},
			fetchStudentList() {
				return this.$request.post(teachergetStudentInfo, {
					deptId: this.courseId.deptId
				}).then(res => {
					this.studetails = res.data.map(s => ({
						...s,
						counts: 0,
						countsLoaded: false, // 新增标记，表示counts是否已加载
						evaluationCount: s.evaluationCount || 0
					}));
					// 如果selectStudent已加载，立即更新counts
					if (this.selectStudentLoaded) {
						this.updateStudentCounts();
					}
					// 处理分组
					if (this.groupMode) {
						this.studentsgroup = this.groupStudents(this.studetails);
					}
				});
			},
			// 统一更新学生计数的方法
			updateStudentCounts() {
				this.studetails.forEach(student => {
					const match = this.selectStudent.find(item => item.studentId === student.studentId);
					if (match) {
						student.counts = match.counts;
					}
					student.countsLoaded = true; // 标记counts已加载
				});
				// 如果是小组模式，更新分组数据
				if (this.groupMode) {
					this.studentsgroup = this.groupStudents(this.studetails);
				}
			},
			handleGroupModeChange(value) {
				this.Taskmode = false
				this.groupMode = value;
				if (value) {
					this.studentsgroup = this.groupStudents(this.studetails);
				}
				uni.showToast({
					title: `已${value ? '开启' : '关闭'}小组模式`,
					icon: 'none'
				});
			},
			handleClassModeChange(value) {
				this.Taskmode = false
				this.classMode = value;
				uni.showToast({
					title: `已${value ? '开启' : '关闭'}上课模式`,
					icon: 'none'
				});
			},
			groupStudents(students) {
				return students.reduce((acc, student) => {
					const key = student.group == null ? 'null' : student.group;
					(acc[key] || (acc[key] = [])).push(student);
					return acc;
				}, {});
			},
			toggleMultiSelect() {
				this.isMultiSelect = !this.isMultiSelect;
				if (!this.isMultiSelect) this.selectedStudents = [];
			},
			selectAll() {
				if (!this.isMultiSelect) return;
				if (this.isAllSelected) this.selectedStudents = [];
				else this.selectedStudents = this.studetails.map(s => s.studentId);
				this.isAllSelected = !this.isAllSelected;
			},
			isSelected(student) {
				return this.selectedStudents.includes(student.studentId);
			},
			toggleSelect(student) {
				const i = this.selectedStudents.indexOf(student.studentId);
				if (i === -1) this.selectedStudents.push(student.studentId);
				else this.selectedStudents.splice(i, 1);
				this.isAllSelected = this.selectedStudents.length === this.studetails.length;
			},
			handleStudentClick(student) {
				this.isMultiSelect ? this.toggleSelect(student) : this.navigateToEvaluation(student);
			},
			gotoEvaluation() {
				if (!this.selectedStudents.length) {
					return uni.showToast({
						title: '请先选择学生',
						icon: 'none'
					});
				}
				const arr = this.selectedStudents.map(id => {
					if (this.classMode) {
						const t = this.classEvaluationlistArr[0] || {};
						return {
							deptId: this.courseId.deptId,
							courseId: this.courseId.courseId,
							studentId: id,
							bonusTypeId: t.bonusTypeId,
							type: t.type,
							mark: t.mark
						};
					} else {
						return {
							courseId: this.courseId.courseId,
							studentId: id
						};
					}
				});
				if (this.classMode) {
					this.$question.post(classEvaluationclassEvaluatio, arr).then(() => {
						uni.showToast({
							title: `${arr[0].type==='0'?'+':'-'}${arr[0].mark}`,
							icon: 'none'
						});
						this.fetchBonusCounts(); // 更新计数
					});
				} else {
					const params = encodeURIComponent(JSON.stringify(arr));
					uni.navigateTo({
						url: `/pages/index/evaluate?params=${params}`
					});
				}
			},
			navigateToEvaluation(item) {
				if (this.classMode) {
					const t = this.classEvaluationlistArr[0] || {};
					const obj = {
						deptId: this.courseId.deptId,
						courseId: this.courseId.courseId,
						studentId: item.studentId,
						bonusTypeId: t.bonusTypeId,
						type: t.type,
						mark: t.mark
					};
					this.$request.post(classEvaluationclassEvaluatio, [obj]).then(() => {
						uni.showToast({
							title: `${item.studentName} ${t.type==='0'?'+':'-'}${t.mark}`,
							icon: 'none'
						});
						this.fetchBonusCounts(); // 更新计数
					});
				} else {
					const params = encodeURIComponent(JSON.stringify([{
						courseId: this.courseId.courseId,
						studentId: item.studentId
					}]));
					uni.navigateTo({
						url: `/pages/index/evaluate?params=${params}`
					});
				}
			},
			handleRandomSelect() {
				if (!this.studetails.length) return uni.showToast({
					title: '暂无学生数据',
					icon: 'none'
				});
				this.randomDialogVisible = true;
			},
			confirmRandomCount() {
				if (this.randomCount < 1 || this.randomCount > this.studetails.length) return uni.showToast({
					title: '请选择有效人数',
					icon: 'none'
				});
				this.randomDialogVisible = false;
				this.generateRandomStudents();
				this.randomResultDialogVisible = true;
			},
			generateRandomStudents() {
				const list = [...this.studetails];
				this.randomSelectedStudents = [];
				for (let i = 0; i < this.randomCount; i++) {
					if (!list.length) break;
					const idx = Math.floor(Math.random() * list.length);
					this.randomSelectedStudents.push(list[idx]);
					list.splice(idx, 1);
				}
			},
			goToEvaluationFromRandom() {
				if (!this.randomSelectedStudents.length) return uni.showToast({
					title: '请先选择学生',
					icon: 'none'
				});
				const arr = this.randomSelectedStudents.map(s => this.classMode ? {
					deptId: this.courseId.deptId,
					courseId: this.courseId.courseId,
					studentId: s.studentId,
					bonusTypeId: this.classEvaluationlistArr[0]?.bonusTypeId,
					type: this.classEvaluationlistArr[0]?.type,
					mark: this.classEvaluationlistArr[0]?.mark
				} : {
					courseId: this.courseId.courseId,
					studentId: s.studentId
				});
				if (this.classMode) {
					this.$request.post(classEvaluationclassEvaluatio, arr).then(() => {
						uni.showToast({
							title: `${arr[0].type==='0'?'+':'-'}${arr[0].mark}`,
							icon: 'none'
						});
						this.fetchBonusCounts(); // 更新计数
						this.randomResultDialogVisible = false;
					});
				} else {
					const params = encodeURIComponent(JSON.stringify(arr));
					uni.navigateTo({
						url: `/pages/index/evaluate?params=${params}`
					});
					this.randomResultDialogVisible = false;
				}
			},
			cancelRandom() {
				this.randomResultDialogVisible = false;
			},
			handleGroupEvaluation(group) {
				const members = this.studentsgroup[group] || [];
				if (!members.length) return uni.showToast({
					title: '该组暂无成员',
					icon: 'none'
				});
				const arr = members.map(s => ({
					courseId: this.courseId.courseId,
					studentId: s.studentId
				}));
				uni.navigateTo({
					url: `/pages/index/evaluate?params=${encodeURIComponent(JSON.stringify(arr))}`
				});
			},
			formatTime(v) {
				return String(v).padStart(2, '0');
			},
			openTimerDialog() {
				this.timerDialogVisible = true;
			},
			closeTimer() {
				this.timerDialogVisible = false;
			},
			increaseHours() {
				if (this.timerHours < 23) this.timerHours++;
			},
			decreaseHours() {
				if (this.timerHours > 0) this.timerHours--;
			},
			increaseMinutes() {
				if (this.timerMinutes < 59) this.timerMinutes++;
				else if (this.timerHours < 23) {
					this.timerHours++;
					this.timerMinutes = 0;
				}
			},
			decreaseMinutes() {
				if (this.timerMinutes > 0) this.timerMinutes--;
				else if (this.timerHours > 0) {
					this.timerHours--;
					this.timerMinutes = 59;
				}
			},
			increaseSeconds() {
				if (this.timerSeconds < 59) this.timerSeconds++;
				else if (this.timerMinutes < 59) {
					this.timerMinutes++;
					this.timerSeconds = 0;
				} else if (this.timerHours < 23) {
					this.timerHours++;
					this.timerMinutes = 0;
					this.timerSeconds = 0;
				}
			},
			decreaseSeconds() {
				if (this.timerSeconds > 0) this.timerSeconds--;
				else if (this.timerMinutes > 0) {
					this.timerMinutes--;
					this.timerSeconds = 59;
				} else if (this.timerHours > 0) {
					this.timerHours--;
					this.timerMinutes = 59;
					this.timerSeconds = 59;
				}
			},
			startTimer() {
				if (!this.hasTimerValue) return;
				this.currentHours = this.timerHours;
				this.currentMinutes = this.timerMinutes;
				this.currentSeconds = this.timerSeconds;
				this.timerPaused = false;
				this.timerRunning = true;
				this.timerDialogVisible = false;
				this.timerRunningDialogVisible = true;
				this.timerInterval = setInterval(() => {
					if (this.timerPaused) return;
					if (this.currentSeconds > 0) this.currentSeconds--;
					else if (this.currentMinutes > 0) {
						this.currentMinutes--;
						this.currentSeconds = 59;
					} else if (this.currentHours > 0) {
						this.currentHours--;
						this.currentMinutes = 59;
						this.currentSeconds = 59;
					} else {
						this.stopTimer();
						uni.showToast({
							title: '倒计时结束!',
							icon: 'none',
							duration: 2000
						});
					}
				}, 1000);
			},
			toggleTimer() {
				this.timerPaused = !this.timerPaused;
			},
			resetTimer() {
				this.stopTimer();
				this.timerRunningDialogVisible = false;
				this.timerDialogVisible = true;
			},
			stopTimer() {
				if (this.timerInterval) {
					clearInterval(this.timerInterval);
					this.timerInterval = null;
				}
				this.timerRunning = false;
			}
		},
		beforeDestroy() {
			this.stopTimer();
		}
	};
</script>

<style lang="scss" scoped>
	.switch-item {
	  display: flex;
	  align-items: center;
	  justify-content: space-between;
	  padding: 20rpx 0;
	
	  &:first-child {
	    padding-top: 0;
	  }
	
	  &:last-child {
	    padding-bottom: 0;
	  }
	}
	
	.navigate-icon {
	  display: flex;
	  align-items: center;
	  color: #007AFF;
	  font-size: 28rpx;
	  padding: 12rpx 20rpx;
	  border-radius: 36rpx;
	  background: rgba(0, 122, 255, 0.08);
	  transition: all 0.3s ease;
	  position: relative;
	  overflow: hidden;
	  
	  &::before {
	    content: "";
	    position: absolute;
	    top: 50%;
	    left: -100%;
	    width: 100%;
	    height: 100%;
	    background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
	    transition: 0.5s;
	    transform: translateY(-50%);
	  }
	  
	  &:active::before {
	    left: 100%;
	  }
	  
	  &:active {
	    transform: translateY(2rpx);
	  }
	}
	.group-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin: 30rpx 0 20rpx;
		padding: 20rpx 0;
		border-bottom: 1px solid #eef0f4;
		position: relative;

		&::after {
			content: '';
			position: absolute;
			left: 0;
			bottom: -10rpx;
			width: 100%;
			height: 10rpx;
			background: linear-gradient(to bottom, rgba(238, 240, 244, 0.5), rgba(238, 240, 244, 0));
		}
	}

	.group-title-container {
		display: flex;
		align-items: center;
		gap: 15rpx;
	}

	.group-icon {
		width: 50rpx;
		height: 50rpx;
		border-radius: 50%;
		background: linear-gradient(135deg, #4e54c8, #8f94fb);
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 4rpx 12rpx rgba(78, 84, 200, 0.25);
	}

	.group-title {
		font-size: 36rpx;
		font-weight: 700;
		color: #2c3e50;
		letter-spacing: 0.5rpx;
	}

	.group-count {
		font-size: 28rpx;
		color: #7f8c8d;
		margin-left: 10rpx;
	}

	.group-evaluate-btn {
		background: linear-gradient(135deg, #3498db, #1abc9c);
		color: #fff;
		font-size: 28rpx;
		font-weight: 500;
		display: flex;
		align-items: center;
		gap: 10rpx;
		box-shadow: 0 6rpx 16rpx rgba(52, 152, 219, 0.25);
		border: none;
		transition: all 0.3s ease;
		position: absolute;
		overflow: hidden;
		padding: 12rpx 24rpx;
		border-radius: 30rpx;
		right: 0;

		&::before {
			content: '';
			position: absolute;
			top: 0;
			left: -100%;
			width: 100%;
			height: 100%;
			background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
			transition: 0.5s;
		}

		&:active::before {
			left: 100%;
		}

		&:active {
			transform: translateY(2rpx);
			box-shadow: 0 3rpx 8rpx rgba(52, 152, 219, 0.3);
		}
	}

	.switch-container {
		padding: 20rpx 0;

		.switch-card {
			background: #ffffff;
			border-radius: 16rpx;
			padding: 30rpx;
			box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.08);
			margin-bottom: 20rpx;

			.switch-item {
				display: flex;
				align-items: center;
				justify-content: space-between;
				padding: 20rpx 0;

				&:first-child {
					padding-top: 0;
				}

				&:last-child {
					padding-bottom: 0;
				}
			}

			.switch-divider {
				height: 1rpx;
				background-color: #E5E9F2;
				margin: 10rpx 0;
			}

			.switch-label {
				font-size: 28rpx;
				color: #333333;
				font-weight: 500;
			}
		}
	}

	.container {
		padding: 20rpx 30rpx;
		background: #f5f7fa;
		min-height: 100vh;
		position: relative;
	}

	/* 优化后的按钮样式 */
	.action-bar {
		display: flex;
		justify-content: flex-end;
		align-items: center;
		margin: 20rpx 0;
		gap: 16rpx;
		flex-wrap: wrap;
	}

	.action-button {
		padding: 0 24rpx;
		height: 70rpx;
		border-radius: 35rpx;
		font-size: 28rpx;
		font-weight: 500;
		display: flex;
		justify-content: center;
		align-items: center;
		transition: all 0.3s ease;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		gap: 8rpx;
		
		&::before {
			content: '';
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background: rgba(255, 255, 255, 0.1);
			opacity: 0;
			transition: opacity 0.3s;
		}
		
		&:active::before {
			opacity: 1;
		}
		
		&.disabled {
			opacity: 0.6;
			pointer-events: none;
			transform: none !important;
			box-shadow: none !important;
		}
		
		&.primary {
			background: #007AFF;
			color: #FFFFFF;
			box-shadow: 0 4rpx 12rpx rgba(0, 122, 255, 0.2);
			
			&.active {
				background: #0056D2;
				box-shadow: 0 6rpx 16rpx rgba(0, 86, 210, 0.3);
			}
			
			&:active {
				transform: translateY(2rpx);
				box-shadow: 0 2rpx 8rpx rgba(0, 122, 255, 0.2);
			}
		}
		
		&.secondary {
			background: #FFFFFF;
			color: #333333;
			border: 1px solid #E5E9F2;
			box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
			
			&.active {
				background: #f0f7ff;
				color: #007AFF;
				border-color: #cce0ff;
			}
			
			&:active {
				transform: translateY(2rpx);
				box-shadow: 0 1rpx 4rpx rgba(0, 0, 0, 0.05);
			}
		}
		
		&.accent {
			background: #FF7D00;
			color: #FFFFFF;
			box-shadow: 0 4rpx 12rpx rgba(255, 125, 0, 0.2);
			
			&:active {
				transform: translateY(2rpx);
				box-shadow: 0 2rpx 8rpx rgba(255, 125, 0, 0.2);
			}
		}
		
		&.tertiary {
			background: #F5F7FA;
			color: #333333;
			box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.05);
			
			&:active {
				transform: translateY(2rpx);
				background: #EEEEEE;
			}
		}
	}

	.icon-random, .icon-timer {
		margin-right: 6rpx;
		font-size: 30rpx;
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

	/* 点评次数徽章样式 */
	.evaluation-badge {
		position: absolute;
		top: 10rpx;
		right: 10rpx;
		background-color: #ff4500;
		color: white;
		width: 40rpx;
		height: 40rpx;
		border-radius: 20rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 24rpx;
		box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.2);
		z-index: 10;
		transition: all 0.3s;
	}

	/* 加载中的徽章样式 */
	.badge-loading {
		background-color: #E5E9F2;
		color: #999;
	}

	/* 弹窗样式 */
	.dialog-mask {
		position: fixed;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 1000;
	}

	.dialog-container {
		background-color: #fff;
		border-radius: 20rpx;
		width: 80%;
		max-width: 600rpx;
		overflow: hidden;
	}

	.dialog-header {
		padding: 30rpx 40rpx;
		border-bottom: 1rpx solid #E5E9F2;
		display: flex;
		justify-content: center;
	}

	.dialog-title {
		font-size: 32rpx;
		font-weight: 600;
		color: #333;
	}

	.dialog-body {
		padding: 30rpx 40rpx;
	}

	.input-group {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.input-label {
		font-size: 28rpx;
		color: #666;
		width: 100rpx;
	}

	.input-number {
		flex: 1;
		height: 70rpx;
		line-height: 70rpx;
		border: 1rpx solid #E5E9F2;
		border-radius: 10rpx;
		padding: 0 20rpx;
		font-size: 28rpx;
	}

	.tip-text {
		font-size: 24rpx;
		color: #999;
		margin-top: 10rpx;
		text-align: center;
	}

	.random-result-grid {
		display: grid;
		grid-template-columns: repeat(2, 1fr);
		gap: 20rpx;
	}

	.empty-tip {
		text-align: center;
		padding: 30rpx 0;
		color: #999;
		font-size: 28rpx;
	}

	.dialog-footer {
		display: flex;
		border-top: 1rpx solid #E5E9F2;
	}

	.dialog-btn {
		flex: 1;
		height: 88rpx;
		line-height: 88rpx;
		font-size: 28rpx;
		text-align: center;
		cursor: pointer;
		transition: background-color 0.2s;
		
		&:active {
			background-color: #f5f5f5;
		}
	}

	.cancel-btn {
		color: #666;
		border-right: 1rpx solid #E5E9F2;
	}

	.confirm-btn {
		color: #007AFF;
		font-weight: 500;
		
		&.disabled {
			color: #999;
			opacity: 0.7;
			pointer-events: none;
		}
	}

	/* 计时器样式 */
	.timer-display {
		display: flex;
		justify-content: center;
		align-items: center;
		margin-bottom: 40rpx;
	}

	.timer-number {
		font-size: 80rpx;
		font-weight: bold;
		color: #333;
		min-width: 120rpx;
		text-align: center;
	}

	.timer-separator {
		font-size: 60rpx;
		font-weight: bold;
		color: #333;
		margin: 0 10rpx;
	}

	.timer-controls {
		display: flex;
		justify-content: space-between;
	}

	.timer-control-group {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	.timer-label {
		font-size: 28rpx;
		color: #666;
		margin-bottom: 10rpx;
	}

	.timer-inputs {
		display: flex;
		align-items: center;
	}

	.timer-btn {
		width: 60rpx;
		height: 60rpx;
		border-radius: 50%;
		background-color: #F2F3F5;
		color: #333;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 32rpx;
		cursor: pointer;
		transition: background-color 0.2s;
		
		&:active {
			background-color: #E5E6EB;
		}
	}

	.timer-value {
		width: 80rpx;
		text-align: center;
		font-size: 36rpx;
		color: #333;
	}

	.timer-buttons {
		padding: 10rpx 0;
	}

	/* 加载状态样式 */
	.student-list-container {
		position: relative;
		min-height: calc(100vh - 240rpx);
	}

	.loading-container {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		min-height: calc(100vh - 240rpx);
	}

	.loading-spinner {
		width: 80rpx;
		height: 80rpx;
		border: 6rpx solid #f3f3f3;
		border-top: 6rpx solid #007AFF;
		border-radius: 50%;
		animation: spin 1s linear infinite;
	}

	.loading-text {
		margin-top: 20rpx;
		font-size: 28rpx;
		color: #666;
	}

	@keyframes spin {
		0% { transform: rotate(0deg); }
		100% { transform: rotate(360deg); }
	}
</style>