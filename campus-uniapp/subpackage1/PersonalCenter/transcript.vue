<template>
	<view class="container">
		<!-- 渐变标题 -->
		<view class="semester-picker">
			<view class="picker-label">当前学期：</view>
			<view class="picker-content">
				<text @click="showClass = true">{{ Semestername }}</text>
			</view>
		</view>
		<!-- 学期选择 -->
		<u-picker :show="showClass" :columns="classArr" keyName="name" @close='showClass = false'
			@confirm="confirmClass" @cancel="cancelClass"></u-picker>

		<view class="tabs">
			<view class="tab-item" :class="{ active: currentTab === 0 }" @click="switchTab(0)">📚 学科成绩</view>
			<view class="tab-item" :class="{ active: currentTab === 1 }" @click="switchTab(1)">🏃 体育成绩</view>
			<view class="tab-line" :style="lineStyle"></view>
		</view>

		<!-- 学科成绩 -->
		<view class="sports-list" v-show="currentTab === 0">
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">语文</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.chinese}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">数学</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.math}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">英语</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.english}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">美术</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.art}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">音乐</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.music}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">体育与健康</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.sports}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">综合实践</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.practice}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">信息科技</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.computer}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">劳动与技术</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.labour}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">道德与法治</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.morality}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">心理健康</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.health}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">科学</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.science}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">书法</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.calligraphy}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">舞蹈</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.dance}}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">阅读</text>
				</view>
				<view class="sport-score">
					<text class="number">{{subjects.readbook}}</text>
					<text class="unit">分</text>
				</view>
			</view>
		</view>

		<!-- 体能成绩 -->
		<view class="sports-list" v-show="currentTab === 1">
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">总分</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.score }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<!-- <view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">等级</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.level }}</text>
					<text class="unit"></text>
				</view>
			</view> -->
			 <view class="sport-item">
			        <view class="sport-left">
			            <text class="sport-name">等级</text>
			        </view>
			        <view class="sport-score">
						
			            <!-- 根据sports.level的值直接渲染对应样式 -->
			            <text v-if="sports.level == '0'" class="level-tag excellent">优秀</text>
			            <text v-else-if="sports.level =='1'" class="level-tag good">良好</text>
			            <text v-else-if="sports.level =='2'" class="level-tag pass">及格</text>
			            <text v-else-if="sports.level =='3'" class="level-tag fail">不及格</text>
			            <text v-else class="level-tag unknown">未知</text>
			        </view>
			    </view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">测试成绩</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.testScore }}</text>
					<text class="unit"></text>
				</view>
			</view>
			<!-- <view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">测试等级</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.testLevel }}</text>
					<text class="unit"></text>
				</view>
			</view> -->
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">测试等级</text>
				</view>
				<view class="sport-score">
					<!-- 根据sports.level的值直接渲染对应样式 -->
					<text v-if="sports.testLevel == '0'" class="level-tag excellent">优秀</text>
					<text v-else-if="sports.testLevel =='1'" class="level-tag good">良好</text>
					<text v-else-if="sports.testLevel =='2'" class="level-tag pass">及格</text>
					<text v-else-if="sports.testLevel =='3'" class="level-tag fail">不及格</text>
					<text v-else class="level-tag unknown">未知</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">附加分数</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.appendScore }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">身高</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.height }}cm</text>
					<text class="unit"></text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">体重</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.weight }}kg</text>
					<text class="unit"></text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">肺活量</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.vitalcapacity }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">身高体重指数</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.bmi }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">50米跑</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.run }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">体前屈</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.sitReach }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">跳绳</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.jump }}</text>
					<text class="unit">分</text>
				</view>
			</view>
			<view class="sport-item">
				<view class="sport-left">
					<text class="sport-name">仰卧起坐</text>
				</view>
				<view class="sport-score">
					<text class="number">{{ sports.sitUp }}</text>
					<text class="unit">分</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	var _self;
	import {
		selectStudentScore,
		selectStudentPeScore,
		semesterList,
		studentgetInfo
	} from '@/utils/index.js'
	export default {
		data() {
			return {
				activeTab: 0,
				lineLeft: '10%',
				currentTab: 0,
				// lineLeft: 0,
				tabItemWidth: 375, // 750rpx设计稿下每个Tab宽度375rpx
				lineWidth: 100, // 高亮线宽度
				subjects: [],
				sports: [],
				semesters: [], // 学期id列表
				semesterId: 0, // 当前选中学期id
				Semestername: '', // 当前学期
				showClass: false,
				classArr: [],
				studentId: ''
			}
		},
		computed: {
			lineStyle() {
				return `left: ${this.lineLeft};`
			}
		},
		onLoad() {
			_self = this;
			this.studentgetInfo()
		},
		methods: {
			getLevelText(level) {
				const levelMap = {
					0: '优秀',
					1: '良好',
					2: '及格',
					3: '不及格'
				}
				return levelMap[level] || '未知'
			},
			// 获取等级样式类
			getLevelClass(level) {
				const classMap = {
					0: 'excellent',
					1: 'good',
					2: 'pass',
					3: 'fail'
				}
				return classMap[level] || ''
			},
			studentgetInfo() {
				this.$request.post(studentgetInfo, {}).then((res) => {
					_self.studentId = res.data.student.studentId
					this.semesterList()
				}, (err) => {

				})
			},
			confirmClass(e) {
				_self.subjects = []
				_self.sports = []
				_self.semesterId = e.value[0].semesterId
				_self.Semestername = e.value[0].name
				// _self.commentlist()
				_self.selectStudentPeScore()
				_self.selectStudentScore()
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
					_self.selectStudentPeScore()
					_self.selectStudentScore()
				}, (err) => {

				})
			},
			selectStudentPeScore() {
				this.$request.post(selectStudentPeScore, {
					semesterId: _self.semesterId,
					studentId: _self.studentId
				}).then((res) => {
					// console.log(res, 'selectStudentPeScore')
					_self.sports = res.data[0]
					console.log(_self.sports, '_self.sports')

				}, (err) => {

				})
			},
			selectStudentScore() {
				this.$request.post(selectStudentScore, {
					semesterId: _self.semesterId,
					studentId: _self.studentId
				}).then((res) => {
					// console.log(res, 'selectStudentScore')
					_self.subjects = res.data[0]

				}, (err) => {

				})
			},
			switchTab(index) {
				this.currentTab = index
				this.lineLeft = index === 0 ? '10%' : '60%'
				// this.lineLeft = index === 0 ? 40 : 340
			},

		}
	}
</script>

<style lang="scss">
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

	.container {
		padding: 0 20rpx;
		background: #f8fafe;
	}

	.header {
		margin-bottom: 60rpx;

		.title {
			font-size: 48rpx;
			color: #2d3436;
			font-weight: bold;
			position: relative;
			display: inline-block;

			&::after {
				content: '';
				position: absolute;
				bottom: -10rpx;
				left: 0;
				width: 80%;
				height: 8rpx;
				background: #5d9eff;
				border-radius: 4rpx;
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


	.subject-item {
		background: #fff;
		padding: 30rpx;
		margin-bottom: 24rpx;
		border-radius: 16rpx;
		box-shadow: 0 6rpx 20rpx rgba(93, 158, 255, 0.08);

		.subject-header {
			display: flex;
			justify-content: space-between;
			align-items: center;
			margin-bottom: 20rpx;

			.subject-name {
				font-size: 34rpx;
				color: #333;
				font-weight: 500;
			}

			.score-tag {
				padding: 8rpx 24rpx;
				border-radius: 40rpx;
				color: #fff;
				font-size: 28rpx;
				font-weight: bold;
			}
		}

		.progress-bar {
			height: 16rpx;
			background: #eee;
			border-radius: 8rpx;
			overflow: hidden;

			.progress-fill {
				height: 100%;
				border-radius: 8rpx;
				transition: width 0.6s ease;
			}
		}
	}

	.sport-item {
	    display: flex;
	    justify-content: space-between;
	    align-items: center;
	    background: #fff;
	    padding: 30rpx;
	    margin-bottom: 24rpx;
	    border-radius: 16rpx;
	    box-shadow: 0 6rpx 20rpx rgba(93, 158, 255, 0.08);
	
	    .sport-left {
	        display: flex;
	        align-items: center;
	
	        .sport-dot {
	            width: 20rpx;
	            height: 20rpx;
	            border-radius: 50%;
	            margin-right: 20rpx;
	        }
	
	        .sport-name {
	            font-size: 32rpx;
	            color: #333;
	        }
	    }
	
	    .sport-score {
	        display: flex;
	        align-items: center;
	
	        // 基础标签样式
	        .level-tag {
	            font-size: 28rpx;
	            font-weight: 500;
	            padding: 8rpx 20rpx;
	            border-radius: 24rpx;
	            line-height: 1.4;
	        }
	
	        // 各等级具体样式
	        .excellent {
	            background: #e3f9f1;
	            color: #31c27c;
	        }
	        .good {
	            background: #e6f4ff;
	            color: #2589ff;
	        }
	        .pass {
	            background: #fff4e2;
	            color: #ff9c01;
	        }
	        .fail {
	            background: #ffe8e8;
	            color: #ff4d4f;
	        }
	        .unknown {
	            background: #f5f5f5;
	            color: #666;
	        }
	    }
	}
</style>