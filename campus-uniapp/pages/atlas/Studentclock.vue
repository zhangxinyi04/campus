<template>
    <view class="container">
        <!-- <view class="header">
            <text class="title">学生打卡记录</text>
        </view> -->
        <view class="student-grid">
            <view v-for="(student, index) in mergedList" :key="index" class="student-card">
                <view :class="['student-info', student.sex === '男' ? 'male' : 'female']">
                    <text class="name">{{student.studentName}}</text>
					{{ student.sex === '男' ? '♂' : '♀' }}
                </view>
                <view :class="['status', student.hasSigned ? 'signed' : 'unsigned']">
                     {{student.hasSigned ? '已打卡' : '未打卡'}}
                </view>
            </view>
        </view>
    </view>
</template>

<script>
import {
    cycleRecordsByStudent,
    teachergetStudentInfo
} from '@/utils/index.js'

export default {
    data() {
        return {
            records: [],
            Student: [],
            StudentAll: [],
            query: {
                eventCycleId: ''
            },
            querys: {
                pageNum: 1,
                pageSize: 10
            }
        }
    },
    computed: {
        // 合并学生数据和打卡状态
        mergedList() {
            return this.Student.map(student => ({
                ...student,
                hasSigned: this.StudentAll.some(s => s.studentId === student.studentId)
            }))
        }
    },
    onLoad(options) {
        this.query.eventCycleId = options.eventCycleId
        this.teachergetStudentInfo()
    },
    methods: {
        teachergetStudentInfo() {
            this.$request.post(teachergetStudentInfo, {}).then((res) => {
                this.Student = res.data
                this.cycleRecordsByStudent()
            })
        },
        // 已打卡学生
        cycleRecordsByStudent() {
            this.$request.get(cycleRecordsByStudent, this.query).then((res) => {
                this.StudentAll = res.data
            })
        },
    }
}
</script>

<style lang="scss" scoped>
.container {
    padding: 20rpx 30rpx;
    background-color: #f5f5f5;
    min-height: 100vh;
}

.header {
    padding: 30rpx 0;
    text-align: center;
   .title {
        font-size: 36rpx;
        font-weight: bold;
        color: #333;
    }
}

.student-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200rpx, 1fr));
    gap: 20rpx;
}

.student-card {
    background-color: #fff;
    border-radius: 16rpx;
    box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
    padding: 20rpx;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.student-info {
    width: 100%;
    text-align: center;
    padding: 10rpx;
    border-radius: 10rpx;
    margin-bottom: 10rpx;
}

.male {
    background-color: #e1f5fe;
    color: #039be5;
}

.female {
    background-color: #fce4ec;
    color: #e91e63;
}

.name {
    font-size: 32rpx;
    color: #333;
}

.status {
    font-size: 28rpx;
    padding: 8rpx 20rpx;
    border-radius: 20rpx;
}

.signed {
    background: #e8f4e4;
    color: #67c23a;
}

.unsigned {
    background: #fef0f0;
    color: #f56c6c;
}
</style>    