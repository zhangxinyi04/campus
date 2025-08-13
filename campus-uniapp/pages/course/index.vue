<template>
	<view class="container">
		<view class="tabs">
		  <view 
		    class="tab-item" 
		    :class="{ active: activeTab === 0 }"
		    @click="switchTab(0)"
		  >平台课程</view>
		  <view 
		    class="tab-item" 
		    :class="{ active: activeTab === 1 }"
		    @click="switchTab(1)"
		  >校本课程</view>
		  <view class="tab-line" :style="lineStyle"></view>
		</view>
	</view>
</template>

<script>
	export default {
	  data() {
	    return {
	      activeTab: 0,
	     lineLeft: '10%'
	    }
	  },
	  computed: {
	    lineStyle() {
	      return `left: ${this.lineLeft};`
	    }
	  },
	  methods: {
	    switchTab(index) {
	      this.activeTab = index
	      this.lineLeft = index === 0 ? '10%' : '60%'
	    }
	  }
	}
</script>

<style scoped>
	.container {
	  background: #f8f9fb;
	  min-height: 100vh;
	}
	
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
	
</style>