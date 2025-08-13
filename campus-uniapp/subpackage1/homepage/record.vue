<template>
	<view>

		<view style="width: 94%;margin-left: 3%;">
			<u--form labelPosition="left" :model="formData" :rules="rules" ref="uForm">

				<u-form-item label="运动技能" borderBottom>
					<u--input v-model="formData.content" border="none" placeholder="请输入运动技能"></u--input>
				</u-form-item>

				<u-form-item label="机构级别" borderBottom @click="showClass = true"
					v-if="title == '参加创新活动获奖' || title == '参加体育赛事获奖' || title == '参加艺术赛事获奖'">
					<u--input v-model="institutionRank" disabled disabledColor="#ffffff" placeholder="请选择机构级别"
						border="none"></u--input>
					<u-icon slot="right" name="arrow-right"></u-icon>
				</u-form-item>

				<u-form-item label="获奖等级" borderBottom @click="showawardGrade = true"
					v-if="title == '参加创新活动获奖' || title == '参加体育赛事获奖' || title == '参加艺术赛事获奖'">
					<u--input v-model="awardGrade" disabled disabledColor="#ffffff" placeholder="请选择获奖等级"
						border="none"></u--input>
					<u-icon slot="right" name="arrow-right"></u-icon>
				</u-form-item>

				<u-form-item label="技能展示" borderBottom>
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="1" accept="video"></u-upload>
				</u-form-item>

				<button class="record-btn" @click="studentrealisticRecords(item)">放飞能量</button>

			</u--form>

		</view>

		<u-picker :show="showClass" :columns="classArr" keyName="name" @close='showClass = false'
			@confirm="confirmClass" @cancel="cancelClass"></u-picker>

		<u-picker :show="showawardGrade" :columns="awardGradeArr" keyName="name" @close='showawardGrade = false'
			@confirm="confirmawardGrade" @cancel="cancelawardGrade"></u-picker>
	</view>
</template>

<script>
	import {
		studentgetInfo,
		studentrealisticRecords
	} from '@/utils/index.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
		data() {
			return {
				uploadImgUrl: img_url + "/wx/common/upload",
				uploadOfficeUrl: img_url + "/common/uploadByLibreOffice",
				formData: {
					studentId: '',
					developIndex: '',
					institutionRank: '',
					awardGrade: '',
					content: '',
					url: '',
					semesterId: '',
					mark: ''
				},
				institutionRank: '',
				awardGrade: '',
				rules: [],
				fileList1: [],
				fileList: [],
				classArr: [
					[{
							name: '国际级',
							index: 0
						}, {
							name: '国家级',
							index: 1
						}, {
							name: '省级',
							index: 2
						}, {
							name: '市级',
							index: 3
						}, {
							name: '区县级',
							index: 4
						}, {
							name: '校级',
							index: 5
						}, {
							name: '班级',
							index: 6
						}

					]
				],
				showClass: false,
				showawardGrade: false,
				awardGradeArr: [
					[{
						name: '一等奖',
						index: 0
					}, {
						name: '二等奖',
						index: 1
					}, {
						name: '三等奖',
						index: 2
					}]
				],
				title: ''
			}
		},
		onLoad(options) {
			_self = this
			_self.formData.developIndex = decodeURIComponent(options.index);
			_self.title = decodeURIComponent(options.title);
			_self.studentgetInfo()
		},
		methods: {
			cancelawardGrade() {
				_self.showawardGrade = false
			},
			confirmawardGrade(e) {
				this.awardGrade = e.value[0].name
				this.formData.awardGrade = e.value[0].index
				_self.showawardGrade = false
			},
			cancelClass() {
				_self.showClass = false
			},
			confirmClass(e) {
				this.institutionRank = e.value[0].name
				this.formData.institutionRank = e.value[0].index
				_self.showClass = false
			},
			// 删除图片
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1);
			},
			// 新增图片
			async afterRead(event) {
				// console.log(event, 'event')
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
				console.log(lists, 'lists')
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
				// console.log(filePath, 'filePath')
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
			studentrealisticRecords() {
				// uni.redirectTo({
				// 	url: '/pages/components/success'
				// })
				// return
				// this.formData.attachments = this.pdfInfo[0].url
				this.formData.url = this.fileList.join(',')
				// console.log(this.formData)
				this.$request.post(studentrealisticRecords, this.formData).then(res => {
					if (res.code == 200) {
						uni.redirectTo({
							url: '/pages/components/success'
						})
					}
					// console.log(res)
				})
			},
			studentgetInfo() {
				this.$request.post(studentgetInfo, {}).then((res) => {
					_self.formData.semesterId = res.data.semester.semesterId
					_self.formData.studentId = res.data.student.studentId
					_self.formData.mark = res.data.mark
					// console.log(res, 'res')

				}, (err) => {

				})
			},
		}
	}
</script>

<style scoped>
	.record-btn {
		background: linear-gradient(45deg, #83C8F7, #9CDCFB);
		box-shadow: 0 5rpx 10rpx rgba(131, 200, 247, 0.3);
		color: #fff;
		border-radius: 50rpx;
		margin: 40rpx 0;
		height: 80rpx;
		line-height: 80rpx;
		font-size: 32rpx;
	}
</style>