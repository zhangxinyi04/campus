<template>
	<view>
          <!-- <homepage></homepage> -->
		  <!-- <atlas v-if="value2 == 1"></atlas> -->

		<!-- <u-tabbar :value="value2" :placeholder="false" :fixed="true" @change="changetabbar" :safeAreaInsetBottom="true">
			<u-tabbar-item text="首页" icon="home"></u-tabbar-item>
			<u-tabbar-item text="活动" icon="photo"></u-tabbar-item>
			<u-tabbar-item text="我的" icon="account"></u-tabbar-item>
		</u-tabbar> -->
	</view>
</template>

<script>
	import {
		studentgetInfo
	} from '@/utils/index.js'
	// import homepage from './homepage/homepage.vue'
	// import atlas from '../pages/atlas/index.vue'
	var _self;
	export default {
		data() {
			return {
				value2: 0,
			}
		},
		// components:{
		// 	homepage
		// },
		onLoad() {
			_self = this
			// _self.studentgetInfo()
		},
		methods: {
			changetabbar(e) {
				_self.value2 = e
			},
			studentgetInfo() {
				this.$request.post(studentgetInfo, {}).then((res) => {
					console.log(res, 'res')

				}, (err) => {

				})
			},
		}
	}
</script>


<style scoped>

</style>