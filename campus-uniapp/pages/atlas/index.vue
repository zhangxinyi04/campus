<template>
  <view class="container">
    <!-- Tab切换 -->
    <view class="tabs">
      <view 
        class="tab-item" 
        :class="{ active: activeTab === 0 }"
        @click="switchTab(0)"
      >校级活动</view>
      <view 
        class="tab-item" 
        :class="{ active: activeTab === 1 }"
        @click="switchTab(1)"
      >班级活动</view>
      <view class="tab-line" :style="lineStyle"></view>
    </view>

    <!-- 搜索框 -->
    <view class="search-box">
      <input 
        class="search-input" 
        placeholder="搜索活动名称" 
        placeholder-class="placeholder"
      />
      <text class="iconfont icon-search"></text>
    </view>

    <!-- 活动列表 -->
    <scroll-view scroll-y class="activity-list">
      <view 
        class="activity-item" 
        v-for="(item,index) in eventslistdata" 
        :key="index"
		@click="EventDetailsclick(item)"
      >
        <image 
          class="cover" 
          :src="configUrl+item.eventPic" 
          mode="aspectFill"
        />
        <view class="content">
          <!-- 头部信息 -->
          <view class="header">
            <view class="level-tag" :class="item.rank == 0 ? 'school' : 'class'">
              {{ item.rank == 0 ? '校级' : '班级' }}
            </view>
           <!-- <view class="status-tag" :class="item.status == 0 ? 'ongoing' : 'ended'">
              {{ item.status == 0 ? '进行中' : '已结束' }}
            </view> -->
			<view class="status-tag Notstarted" v-if='item.status == 0'>
			    未开始
			</view>
			<view class="status-tag ongoing" v-if='item.status == 1'>
			    进行中
			</view>
			<view class="status-tag ended" v-if='item.status == 2'>
			    已结束
			</view>
			<!-- <view class="status-tag"  v-if='item.status == 1'>
			    进行中
			</view>
			<view class="status-tag" class="ended" v-if='item.status == 2'>
			    已结束
			</view> -->
          </view>

          <!-- 标题和简介 -->
          <text class="title">{{ item.eventName }}</text>
          <text class="description">{{ item.eventIntro || '暂无活动简介' }}</text>

          <!-- 时间信息 -->
          <view class="time-info">
            <view class="time-item">
              <text class="iconfont icon-clock"></text>
              <text>开始：{{ item.startTime }}</text>
            </view>
            <view class="time-item">
              <text class="iconfont icon-clock"></text>
              <text>结束：{{ item.endTime }}</text>
            </view>
          </view>

          <!-- 标签区域 -->
          <view class="tag-group">
            <view class="type-tag">
              {{ item.activityType === 0 ? '学科活动' : '跨学科活动' }}
            </view>
            <view 
              class="custom-tag"
              v-for="(tag,tIndex) in item.tags"
              :key="tIndex"
            >
              {{ tag }}
            </view>
          </view>
        </view>
      </view>
    </scroll-view>
  </view>
</template>

<script>
	import {
		teachergetInfo,
		eventslist
	} from '@/utils/index.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
	  data() {
	    return {
	      activeTab: 0,
	     lineLeft: '10%',
		 eventslistdata:null,
		 configUrl:'',
		 teachedetails:'',
		 gradeIds:[],
		 classIds:[]
	    }
	  },
	  computed: {
	    lineStyle() {
	      return `left: ${this.lineLeft};`
	    }
	  },
	  onShow(){
		   // _self.eventslist()
	  },
	  onLoad() {
	   	  _self = this
		  _self.configUrl = img_url
		  _self.teachergetInfos()
		   
	  },
	  methods: {
		  teachergetInfos() {
		  	this.$request.post(teachergetInfo, {}).then((res) => {
		  		_self.teachedetails = res.data
		  	    // console.log(_self.teachedetails,'_self.teachedetails')
				_self.teachedetails.sysCourses.forEach(function (course) {
				    course.depts.forEach(function (dept) {
				        // 将 parentId 添加到 gradeIds 数组中
				        if (!_self.gradeIds.includes(dept.parentId)) {
				            _self.gradeIds.push(dept.parentId);
				        }
				        // 将 deptId 添加到 classIds 数组中
				        if (!_self.classIds.includes(dept.deptId)) {
				            _self.classIds.push(dept.deptId);
				        }
				    });
				});
				 _self.eventslist()
		  
		  	}, (err) => {
		  
		  	})
		  },
		  EventDetailsclick(item){
			  let url = `/pages/atlas/EventDetails?eventId=${item.eventId}`;
			    uni.navigateTo({
			        url: url
			    });
		  },
		  eventslist() {
			  var obj
			  if(this.activeTab == 0){
				  obj = {
					  gradeIds: _self.gradeIds
				  }
			  }else{
				   obj = {
					  classIds: _self.classIds
				  } 
			  }
		  	this.$request.post(eventslist,obj).then((res) => {
			_self.eventslistdata = res.rows
		   // console.log(_self.eventslistdata, 'res')
		  	}, (err) => {
		  
		  	})
		  },
	    switchTab(index) {
	      this.activeTab = index
	      this.lineLeft = index === 0 ? '10%' : '60%'
		  this.eventslist()
	    }
	  }
	}
</script>

<style scoped>
/* 优化后的样式 */
.container {
  background: #f5f7fa;
  padding: 0 20rpx;
}

/* 活动项 */
.activity-item {
  background: #fff;
  border-radius: 24rpx;
  margin: 20rpx 0;
  padding: 24rpx;
  display: flex;
  box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.04);
}

.cover {
  width: 240rpx;
  height: 240rpx;
  border-radius: 16rpx;
  margin-right: 24rpx;
  background: #f0f2f5;
}

.content {
  flex: 1;
  min-width: 0;
}

/* 头部标签 */
.header {
  display: flex;
  gap: 16rpx;
  margin-bottom: 20rpx;
}

.level-tag, .status-tag {
  font-size: 24rpx;
  padding: 6rpx 20rpx;
  border-radius: 8rpx;
}

.level-tag.school {
  background: #e6f4ff;
  color: #1677ff;
}
.level-tag.class {
  background: #fff7e6;
  color: #fa8c16;
}
.status-tag.Notstarted {
  background: #fff;
}
.status-tag.ongoing {
  background: #e6fffb;
  color: #13c2c2;
}
.status-tag.ended {
  background: #f0f0f0;
  color: #666;
}

/* 标题和简介 */
.title {
  display: block;
  font-size: 32rpx;
  color: #1a1a1a;
  font-weight: 600;
  margin-bottom: 16rpx;
  line-height: 1.4;
}

.description {
  font-size: 26rpx;
  color: #666;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
  margin-bottom: 24rpx;
}

/* 时间信息 */
.time-info {
  background: #f8f9fb;
  border-radius: 12rpx;
  padding: 16rpx;
  margin-bottom: 24rpx;
}

.time-item {
  display: flex;
  align-items: center;
  font-size: 24rpx;
  color: #666;
  line-height: 1.6;
}

.icon-clock {
  margin-right: 8rpx;
  color: #999;
  font-size: 28rpx;
}

/* 标签组 */
.tag-group {
  display: flex;
  flex-wrap: wrap;
  gap: 12rpx;
}

.type-tag, .custom-tag {
  font-size: 24rpx;
  padding: 6rpx 20rpx;
  border-radius: 30rpx;
  background: #f0f2f5;
  color: #666;
}

.type-tag {
  background: #e6ffed;
  color: #389e0d;
}

/* .container {
  background: #f8f9fb;
  min-height: 100vh;
} */

/* Tab切换 */
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

/* 搜索框 */
.search-box {
  padding: 30rpx;
  position: relative;
}

.search-input {
  height: 80rpx;
  background: #fff;
  border-radius: 40rpx;
  padding: 0 80rpx 0 40rpx;
  font-size: 28rpx;
  box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.05);
}

.icon-search {
  position: absolute;
  right: 60rpx;
  top: 50%;
  transform: translateY(-50%);
  color: #999;
  font-size: 36rpx;
}

.placeholder {
  color: #ccc;
}

/* 活动列表 */
.activity-list {
  height: calc(100vh - 280rpx);
  /* padding: 0 30rpx; */
}

.section-title {
  font-size: 32rpx;
  color: #2a3c5e;
  font-weight: 600;
  margin: 40rpx 0 30rpx;
  padding-left: 20rpx;
  border-left: 6rpx solid #007aff;
}

.activity-item {
  background: #fff;
  border-radius: 20rpx;
  margin-bottom: 30rpx;
  padding: 30rpx;
  display: flex;
  box-shadow: 0 6rpx 20rpx rgba(42,60,94,0.05);
}

.activity-item.ended {
  opacity: 0.8;
}

.cover {
  width: 220rpx;
  height: 220rpx;
  border-radius: 12rpx;
  margin-right: 30rpx;
}

.content {
  flex: 1;
  position: relative;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.status {
  font-size: 24rpx;
  color: #fff;
  padding: 6rpx 20rpx;
  border-radius: 30rpx;
}

.status.Notstarted {
  background: #ccc;
}

.status.ongoing {
  background: #4cd964;
}

.status.ended {
  background: #999;
}

.time {
  font-size: 24rpx;
  color: #666;
}

.title {
  display: block;
  font-size: 32rpx;
  color: #2a3c5e;
  font-weight: 600;
  margin-bottom: 20rpx;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}

.info {
  margin-bottom: 20rpx;
}

.date, .author {
  display: block;
  font-size: 24rpx;
  color: #999;
  line-height: 1.6;
}

.participants {
  font-size: 24rpx;
  color: #007aff;
  margin-bottom: 10rpx;
  display: block;
}

.progress-bar {
  height: 8rpx;
  background: #eee;
  border-radius: 4rpx;
}

.progress-inner {
  height: 100%;
  background: #007aff;
  border-radius: 4rpx;
  transition: width 0.3s;
}

/* 文字截断样式 */
.ellipsis-2 {
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
}
</style>