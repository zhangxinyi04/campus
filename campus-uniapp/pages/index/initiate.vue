<template>
	<view class="create-container">
		<!-- 顶部渐变背景 -->
		<view class="header-bg"></view>

		<scroll-view scroll-y class="form-wrapper">
			<!-- 表单卡片 -->
			<view class="form-card">
				<u--form labelPosition="left" :model="formData" :rules="rules" ref="uForm">
					<!-- 基础信息 -->
					<view class="form-section">
						<text class="section-title">基本信息</text>
						<u-form-item label="活动标题" borderBottom>
							<u--input v-model="formData.eventName" border="none" placeholder="请输入活动标题"></u--input>
						</u-form-item>

						<u-form-item label="活动分数" borderBottom>
							<u--input v-model="formData.mark" border="none" placeholder="请输入活动分数"></u--input>
						</u-form-item>

						<u-form-item label="活动简介" borderBottom>
							<u--textarea v-model="formData.eventIntro" placeholder="请输入活动简介"></u--textarea>
						</u-form-item>

						<u-form-item label="注意事项" borderBottom>
							<u--textarea v-model="formData.precautions" placeholder="请输入注意事项"></u--textarea>
						</u-form-item>
					</view>

					<!-- 时间设置 -->
					<view class="form-section">
						<text class="section-title">时间设置</text>
						<view class="time-picker">
							<u-form-item label="开始时间" borderBottom @click="showStart = true">
								<u--input v-model="formData.startTime" disabled disabledColor="#ffffff"
									placeholder="请选择活动开始时间" border="none"></u--input>
								<u-icon slot="right" name="arrow-right"></u-icon>
							</u-form-item>

							<u-form-item label="结束时间" borderBottom @click="showEnd = true">
								<u--input v-model="formData.endTime" disabled disabledColor="#ffffff"
									placeholder="请选择活动结束时间" border="none"></u--input>
								<u-icon slot="right" name="arrow-right"></u-icon>
							</u-form-item>

						</view>
					</view>

					<!-- 活动设置 -->
					<view class="form-section">
						<text class="section-title">活动设置</text>
						<!-- 	<u-form-item label="级别类型" borderBottom>
							<u-radio-group v-model="formData.rank" placement="row">
								<u-radio :customStyle="{marginRight: '10px'}" label='校级' :name='0'></u-radio>
								<u-radio label='班级' :name='1'></u-radio>
							</u-radio-group>
						</u-form-item> -->

						<u-form-item label="活动类型" borderBottom @click="showeventType = true">
							<u--input v-model="eventType" disabled disabledColor="#ffffff" placeholder="请选择活动类型"
								border="none"></u--input>
							<u-icon slot="right" name="arrow-right"></u-icon>
						</u-form-item>

						<u-form-item label="活动标签" borderBottom>
							<u-radio-group v-model="formData.eventTag" placement="row">
								<u-radio :customStyle="{marginRight: '10px'}" label='学科活动' :name='0'></u-radio>
								<u-radio label='跨学科活动' :name='1'></u-radio>
							</u-radio-group>
						</u-form-item>
						<u-form-item label="标签类型" borderBottom @click="eventTagNameType = true">
							<u--input v-model="eventTagName" disabled disabledColor="#ffffff" placeholder="请选择标签类型"
								border="none"></u--input>
							<u-icon slot="right" name="arrow-right"></u-icon>
						</u-form-item>

						<u-form-item label="参与班级" borderBottom @click="showClass = true">
							<u--input v-model="classIds" disabled disabledColor="#ffffff" placeholder="请选择班级"
								border="none"></u--input>
							<u-icon slot="right" name="arrow-right"></u-icon>
						</u-form-item>

						<u-form-item label="活动奖章" borderBottom @click="showClassmedal = true">
							<!-- <view > -->
							<u--input disabled disabledColor="#ffffff" placeholder="请选择奖章" border="none"
								v-if="!selectedBadge.url"></u--input>
							<u-icon slot="right" name="arrow-right" v-if="!selectedBadge.url"></u-icon>
							<!-- </view> -->
							<view v-if="selectedBadge.url">
								<image :src="selectedBadge.url" mode="aspectFit"
									style="width: 64rpx; height: 64rpx; margin-right: 20rpx;" />
							</view>

						</u-form-item>

						<!-- 
					<uni-forms-item label="参与班级">
						<uni-data-checkbox v-model="formData.participateClasses" :localdata="classes" multiple />
					</uni-forms-item> -->
					</view>

					<!-- 高级设置 -->
					<view class="form-section">
						<text class="section-title">高级设置</text>
						<u-form-item label="活动类别" borderBottom>
							<u-radio-group v-model="formData.eventCategory" placement="row">
								<u-radio :customStyle="{marginRight: '10px'}" label='打卡类' :name='0'></u-radio>
								<u-radio label='记录类' :name='1'></u-radio>
							</u-radio-group>
						</u-form-item>

						<u-form-item label="打卡周期" borderBottom v-if="formData.eventCategory == 0">
							<u-radio-group v-model="formData.eventCycle" placement="row">
								<u-radio :customStyle="{marginRight: '10px'}" label='天' :name='0'></u-radio>
								<u-radio :customStyle="{marginRight: '10px'}" label='周' :name='1'></u-radio>
								<u-radio label='月' :name='2'></u-radio>
							</u-radio-group>

						</u-form-item>

						<u-form-item label="记录上传" borderBottom>
							<u-radio-group v-model="formData.records" placement="row">
								<u-radio :customStyle="{marginRight: '10px'}" label='不上传' :name='0'></u-radio>
								<u-radio label='上传' :name='1'></u-radio>
							</u-radio-group>
						</u-form-item>

						<u-form-item label="活动可见" borderBottom>
							<u-radio-group v-model="formData.visible" placement="row">
								<u-radio :customStyle="{marginRight: '10px'}" label='不可见' :name='0'></u-radio>
								<u-radio label='可见' :name='1'></u-radio>
							</u-radio-group>

						</u-form-item>
					</view>

					<!-- 文件上传 -->
					<view class="form-section">
						<text class="section-title">文件上传</text>
						<u-form-item label="活动封面" borderBottom>
							<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
								:maxCount="1"></u-upload>
						</u-form-item>

						<u-form-item label="活动附件" borderBottom>
							<!-- <view @click="goUpload">文件上传</view> -->
							<view class="uploadContent">
								<view class="uploadFileBox" v-if="pdfInfo.length!=0">
									<view class="uploadTexts" @click="jump(pdfInfo[0].url)">
										{{pdfInfo[0].name}}
									</view>
									<u-icon name="close" @click="deleteFile()"></u-icon>
								</view>
								<view class="uploadChoose" v-else @click="selectFile()">
									<u-button type="primary" :plain="true" text="文件上传"></u-button>
									<!-- <u-icon name="plus"></u-icon> -->
								</view>
							</view>
						</u-form-item>

					</view>
				</u--form>
			</view>

			<!-- 提交按钮 -->
			<button class="submit-btn" @click="submitForm">立即发布</button>
		</scroll-view>
         
		 <!-- 标签类型 选择-->
		 <u-picker :show="eventTagNameType" :columns="eventTagNameArr" keyName="name" @close='eventTagNameType = false'
		 	@confirm="eventTagNameClass" @cancel="eventTagNamecancelClass"></u-picker>
		<!-- 年级选择 -->
		<u-picker :show="showClass" :columns="classArr" keyName="deptName" @close='showClass = false'
			@confirm="confirmClass" @cancel="cancelClass"></u-picker>
		<!-- 活动类型 -->
		<u-picker :show="showeventType" :columns="columns" keyName="name" @close='showeventType = false'
			@confirm="confirm" @cancel="cancel"></u-picker>
		<!-- 开始时间 -->
		<u-datetime-picker :show="showStart" v-model="startTime" mode="date" @confirm='confirmstartTime'
			@cancel='showStart = false'></u-datetime-picker>
		<!-- 结束时间 -->
		<u-datetime-picker :show="showEnd" v-model="endTime" mode="date" @confirm='confirmendTime'
			@cancel='showStart = false'></u-datetime-picker>
		<!-- 奖章选择弹窗 -->
		<u-popup :show="showClassmedal" mode="bottom" border-radius="20">
			<view class="badge-container">
				<view v-for="(item, index) in badges" :key="index" class="badge-item" @click="handleSelectmedal(item)">
					<image :src="item.url"
						:class="['badge-img', selectedBadge.eventMedalId === item.eventMedalId ? 'active' : '']"
						mode="aspectFit" />
				</view>
			</view>
		</u-popup>

	</view>
</template>

<script>
	import {
		events,
		medallist,
		teachergetInfo
	} from '@/utils/index.js'
	import {
		token
	} from '@/utils/request.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self


	export default {
		data() {
			return {
				eventTagNameArr:[[
					{
						name: '品德发展',
						value: '0'
					},{
						name: '学业发展',
						value: '1'
					},{
						name: '身心发展',
						value: '2'
					},{
						name: '艺术素养',
						value: '3'
					},{
						name: '劳动与社会实践',
						value: '4'
					},
				]],
				showStart: false,
				showEnd: false,
				showeventType: false,
				uploadImgUrl: img_url + "/wx/common/upload",
				uploadOfficeUrl: img_url + "/wx/common/upload",
				token: token,
				columns: [
					[{
							name: '爱党爱国',
							value: '0'
						},
						{
							name: '队团活动',
							value: '1'
						},
						{
							name: '爱护公物',
							value: '2'
						},
						{
							name: '责任担当',
							value: '3'
						},
						{
							name: '文明朴素',
							value: '4'
						},
						{
							name: '尊老爱幼',
							value: '5'
						},
						{
							name: '自立勤快',
							value: '6'
						},
						{
							name: '学习方法',
							value: '7'
						},
						{
							name: '发明创造',
							value: '8'
						},
						{
							name: '问题解决',
							value: '9'
						},
						{
							name: '阅读习惯',
							value: '10'
						},
						{
							name: '动手动脑',
							value: '11'
						},
						{
							name: '卫生健康与安全',
							value: '12'
						},
						{
							name: '意志坚强',
							value: '13'
						},
						{
							name: '积极阳光',
							value: '14'
						},
						{
							name: '人际关系',
							value: '15'
						},
						{
							name: '艺术实践',
							value: '16'
						},
						{
							name: '劳动观念',
							value: '17'
						},
						{
							name: '家庭劳动',
							value: '18'
						},
						{
							name: '环保意识',
							value: '19'
						}
					]
				],
				rules: {
					'userInfo.name': {
						type: 'string',
						required: true,
						message: '请填写姓名',
						trigger: ['blur', 'change']
					},
					'userInfo.sex': {
						type: 'string',
						max: 1,
						required: true,
						message: '请选择男或女',
						trigger: ['blur', 'change']
					},
				},
				radio: '',
				switchVal: false,
				startTime: Number(new Date()),
				endTime: Number(new Date()),
				eventType: '',
				formData: {
					eventName: '',
					eventIntro: '',
					precautions: '',
					mark: '',
					startTime: '',
					endTime: '',
					rank: 1,
					eventType: '',
					eventTag: 0,
					eventCategory: 0,
					// cycle: 0,
					eventCycle: 0,
					records: 0,
					visible: 0,
					medalId: '',
					eventPic: [],
					attachments: [],
					classIds: [],
					eventTagType:''
				},
				classIds: '',


				fileList1: [],
				pdfInfo: [],
				fileList: [],
				classArr: [],
				showClass: false,
				notice: '1、有规则意识，服从组织安排，如：队员严禁超越先导队员或落后于收尾领队。2、 有安全意识， 禁止一切危险行为。3、 有卫生意识， 保持良好卫生习惯， 注意预防疾病。4、 有环保意识， 不乱丢垃圾， 爱护环境， 提倡环保。5、 有尊重意识， 人人在团队中都是平等的。6、 有团队意识， 保持良好的团队协作精神。7、 有互助意识， 有任何困难或疑问尽快和领队沟通。8、 自我服务意识， 整个活动过程必须明确意识， 照顾好自己和保管好随身物品。',
				badges: [],
				showClassmedal: false,
				selectedBadge: null, // 默认选中第一个
                eventTagName:'',
				eventTagNameType:false,
				configUrl: img_url,
			}
		},
		onLoad(options) {
			_self = this
			var option = decodeURIComponent(options);
			// console.log(options.title,'option')
			// if (option) {
			this.fileList = []
			this.formData.eventName = options.title ? decodeURIComponent(options.title) : '';
			this.formData.eventIntro = options.title ? decodeURIComponent(options.intro) : '';
			this.formData.precautions = _self.notice;
			if (options.image) {
				const image = img_url + decodeURIComponent(options.image)
				this.fileList1.push({
				  url: image, // 使用 configUrl 作为图片基础路径（而非 uploadImgUrl，后者是上传接口地址）
				  // 可选：添加其他属性如 name、type 等，根据需求
				});
			}
			// this.fileList1.push(this.fileList1)
			// console.log(this.fileList1)
			// }
			this.teachergetInfos()
			this.medallist()
		},
		onShow() {
			// this.teachergetInfos()
		},
		methods: {
			eventTagNamecancelClass(){
				this.eventTagNameType = false
			},
			eventTagNameClass(e){
				this.eventTagName = e.value[0].name
				this.formData.eventTagType = e.value[0].value
				this.eventTagNameType = false
			},
			handleSelectmedal(item) {
				this.selectedBadge = item
				this.formData.medalId = item.eventMedalId
				this.showClassmedal = false
			},
			medallist() {
				this.$request.get(medallist, {}).then((res) => {
					_self.badges = res.data
					for (var i = 0; i < _self.badges.length; i++) {
						const originalUrl = _self.badges[i].url.toLowerCase();
						if (!originalUrl.startsWith('http://') && !originalUrl.startsWith('https://')) {
							_self.badges[i].url = img_url + _self.badges[i].url;
						}
					}
					// _self.selectedBadge = _self.badges[0]
					// _self.selectedBadge.url = img_url+_self.selectedBadge.url
					// console.log(_self.badges, '_self.classArr')
				}, (err) => {

				})
			},
			confirmClass(e) {
				this.classIds = e.value[0].deptName
				this.formData.classIds.push(e.value[0].deptId)
				this.showClass = false
			},
			cancelClass() {
				this.showClass = false
			},
			teachergetInfos() {
				var _that = this
				this.$request.post(teachergetInfo, {}).then((res) => {
					// _self.classArr = res.data
					var arr = []
					let sysCourses = res.data.sysCourses
					for (var i = 0; i < sysCourses.length; i++) {
						for (var j = 0; j < sysCourses[i].depts.length; j++) {
							arr.push(sysCourses[i].depts[j])
						}
					}
					_that.classArr.push(arr)

					// console.log(_that.classArr,'_self.classArr')

				}, (err) => {

				})
			},
			convertTimestamp(timestamp) {
				// 创建 Date 对象
				const date = new Date(timestamp);
				// 获取年份
				const year = date.getFullYear();
				// 获取月份（0 - 11，加 1 并补零）
				const month = String(date.getMonth() + 1).padStart(2, '0');
				// 获取日期并补零
				const day = String(date.getDate()).padStart(2, '0');
				// // 获取小时并补零
				// const hours = String(date.getHours()).padStart(2, '0');
				// // 获取分钟并补零
				// const minutes = String(date.getMinutes()).padStart(2, '0');
				// // 获取秒数并补零
				// const seconds = String(date.getSeconds()).padStart(2, '0');

				// 拼接成指定格式的时间字符串
				return `${year}-${month}-${day}`;
			},
			confirmendTime(e) {
				this.formData.endTime = this.convertTimestamp(e.value)
				this.showEnd = false
			},
			confirmstartTime(e) {
				this.formData.startTime = this.convertTimestamp(e.value)
				this.showStart = false
			},
			cancel() {
				this.showeventType = false
			},
			confirm(e) {
				this.eventType = e.value[0].name
				this.formData.eventType = e.value[0].value
				this.showeventType = false
				// console.log(e,'11')
			},
			deleteFile() {
				this.pdfInfo = []
			},
			// 预览pdf
			jump(linkUrl) {
				// console.log("发送跳转页面地址112：" + linkUrl)
				if (linkUrl) {
					// let linkUrlNew = encodeURIComponent(linkUrl)
					// console.log("发送跳转页面地址111：" + linkUrlNew )
					// uni.navigateTo({
					// 	url: '/subPackages/home/claim/index?url='+ linkUrlNew
					// })
				}
			},
			// 上传pdf
			selectFile() {
				// console.log('111', this.pdfInfo)
				// var _that = this

				// if (this.pdfInfo.length != 0) { // this.pdfInfo 要求不可重复上传
				// 	this.$toast('如果重新上传请先删除已有的附件～')
				// 	return
				// }
				let that = this
				uni.chooseMessageFile({
					count: 1, //最多可以选择的文件个数，可以 1
					type: 'file', //所选的文件的类型，具体看官方文档
					extension: ["doc", "xls", "ppt", "txt", "pdf", "docx", "pptx"], //文件类型, '.docx''.doc', 
					success(res) {
						// console.log('上传', res)
						// // tempFilePath可以作为img标签的src属性显示图片
						const tempFilePaths = res.tempFiles[0].path
						let filename = res.tempFiles[0].name; //用于页面显示的名字

						// console.log(filename)
						// 这一步判断可以省略，如果需求没有格式要求的话
						if (filename.indexOf(".pdf") == -1) {
							// that.$toast('暂时仅支持pdf格式的文件')
							return
						} else if (res.tempFiles[0].size > (10 * 1024 *
								1024)) { //这里限制了文件的大小和具体文件类型,如果不限制文件类型则去掉'|| filename.indexOf(".pdf") == -1'
							// that.$toast('文件大小不能超过10MB')
							// wx.showToast({
							// 		title: '文件大小不能超过10MB',
							// 		icon: "none",
							// 		duration: 2000,
							// 		mask: true
							// })
						} else {

							uni.uploadFile({
								url: that.uploadOfficeUrl, // '这里是您后台提供文件上传的API':上传的路径
								filePath: tempFilePaths, //刚刚在data保存的文件路径
								name: 'file', //后台获取的凭据
								// formData: { //如果是需要带参数，请在formData里面添加，不需要就去掉这个就可以的
								// 	fileGroup: 'leasContract'
								// },
								header: {
									// 'Content-Type': 'multipart/form-data',
									'Authorization': uni.getStorageSync('token'),
								},
								success: (uploadFileRes) => {
									// console.log(JSON.parse(uploadFileRes.data),'11')
									if (uploadFileRes.statusCode == 200) {
										let result = JSON.parse(uploadFileRes.data)
										that.pdfInfo.push({
											name: result.fileName,
											url: result.fileName
										})
									}
								}
							})
							// console.log('上传到服务器')
						}
					},
					fail: (err) => {
						console.log(err, 'err');
						that.$forceUpdate()
					}
				})
			},
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1);
			},
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file);
				let fileListLen = this[`fileList${event.name}`].length;
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: "uploading",
						message: "上传中"
					});
				});

				for (let i = 0; i < lists.length; i++) {
					try {
						const result = await this.uploadFilePromise(lists[i].url);
						let item = this[`fileList${event.name}`][fileListLen];
						this[`fileList${event.name}`].splice(
							fileListLen,
							1,
							Object.assign(item, {
								status: "success",
								message: "",
								url: result
							})
						);
					} catch (error) {
						let item = this[`fileList${event.name}`][fileListLen];
						this[`fileList${event.name}`].splice(
							fileListLen,
							1,
							Object.assign(item, {
								status: "fail",
								message: "上传失败：" + error.errMsg
							})
						);
					}
					fileListLen++;
				}
			},
			uploadFilePromise(filePath) {
				return new Promise((resolve, reject) => {
					uni.uploadFile({
						url: this.uploadImgUrl,
						filePath: filePath,
						name: "file",
						header: {
							'Authorization': uni.getStorageSync('token')
						},
						success: (res) => {
							if (res.statusCode === 200) {
								// this.fileList.push(JSON.parse(res.data).url)
								// console.log(this.fileList,'this.fileList')
								try {
									const data = JSON.parse(res.data);
									this.fileList.push(JSON.parse(res.data).fileName)
									// console.log(this.fileList, 'fileList')
									resolve(data.data);
								} catch (parseError) {
									reject({
										errMsg: '解析响应数据失败'
									});
								}
							} else {
								reject({
									errMsg: '服务器返回状态码非 200'
								});
							}
						},
						fail: (err) => {
							reject(err);
						}
					});
				});
			},
			sexSelect(e) {

			},

			//old
			uploadSuccess(e) {
				this.formData.coverImage = e.tempFilePaths[0]
			},
			uploadAttachment(e) {
				this.formData.attachments = e.tempFilePaths
			},
			onCategoryChange(e) {
				if (e !== 0) this.formData.cycleType = null
			},
			submitForm() {
				this.formData.startTime = this.formData.startTime + ' ' + '00:00:00'
				this.formData.endTime = this.formData.endTime + ' ' + '23:59:59'
				this.formData.attachments = this.pdfInfo[0] ? this.pdfInfo[0].url : ''
				if(this.fileList1[0].url){
					this.formData.eventPic = this.fileList1[0].url
					this.formData.eventPic = this.formData.eventPic.startsWith(this.configUrl) ?  this.formData.eventPic.replace(this.configUrl, '') : this.formData.eventPic;
				}else{
					this.formData.eventPic = this.fileList[0]
				}
				this.$request.post(events, this.formData).then(res => {
					if (res.code == 200) {
						uni.showToast({
							title: '发布成功',
							icon: 'success'
						})
						uni.redirectTo({
							url: '/pages/components/success'
						})
					}
					console.log(res)
				})

				// this.$refs.form.validate().then(res => {
				// 	// console.log('提交数据:', this.formData)
				// 	uni.showToast({
				// 		title: '提交成功',
				// 		icon: 'success'
				// 	})
				// }).catch(err => {
				// 	uni.showToast({
				// 		title: '请完善必填信息',
				// 		icon: 'none'
				// 	})
				// })
			}
		}
	}
</script>

<style lang="scss">
	// @import "../..//index.scss"/
	@import "@/uview-ui/index.scss";

	.badge-container {
		padding: 30rpx;
		display: flex;
		flex-wrap: wrap;
		justify-content: space-around;
	}

	.badge-item {
		padding: 20rpx;
		margin: 10rpx;
		border-radius: 20rpx;
		transition: all 0.3s;
	}

	.badge-img {
		width: 120rpx;
		height: 120rpx;
		border-radius: 50%;
		border: 4rpx solid #eee;
		transition: all 0.3s;
	}

	.active {
		border: 4rpx solid #f56c6c;
		box-shadow: 0 0 20rpx rgba(245, 108, 108, 0.3);
		transform: scale(1.1);
	}

	.uploadTexts {
		width: 500rpx;
		overflow: hidden;
		white-space: nowrap;
		text-overflow: ellipsis;
		// overflow: hidden;
		float: left;
	}

	.create-container {
		background: #f8fafc;
		min-height: 100vh;
	}

	.header-bg {
		height: 200rpx;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
	}

	.form-wrapper {
		// padding: 30rpx;
		margin-top: -100rpx;
	}

	.form-card {
		background: #fff;
		border-radius: 24rpx;
		padding: 30rpx;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.05);
	}

	.form-section {
		margin-bottom: 40rpx;

		.section-title {
			display: block;
			font-size: 32rpx;
			color: #2a3c5e;
			font-weight: 600;
			margin-bottom: 30rpx;
			padding-left: 20rpx;
			border-left: 6rpx solid #007aff;
		}
	}

	.time-picker {
		::v-deep .uni-date {
			width: 100%;
		}
	}

	.submit-btn {
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
		color: #fff;
		border-radius: 50rpx;
		margin: 40rpx 0;
		height: 80rpx;
		line-height: 80rpx;
		font-size: 32rpx;
	}

	// 美化表单元素
	::v-deep .uni-forms-item__label {
		font-weight: 500 !important;
		color: #333 !important;
	}

	::v-deep .uni-easyinput__content {
		border-radius: 12rpx !important;
	}

	.uni-file-picker {
		::v-deep .uni-file-picker__container {
			border: 2rpx dashed #ddd !important;
			border-radius: 16rpx;
		}
	}
</style>