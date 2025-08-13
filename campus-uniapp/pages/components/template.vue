<template>
	<view>
		打卡记录
	</view>
</template>

<script>
	import {
		teachergetStudentInfo
	} from '@/utils/index.js'
	var _self;
	export default {
		onLoad() {
			_self = this
		},
		methods: {
			teachergetStudentInfo(){
				this.$request.post(teachergetStudentInfo, {}).then((res) => {
					console.log(res, 'res')
				
				}, (err) => {
				
				})
			},
	     }
	}
</script>

<style>
</style>