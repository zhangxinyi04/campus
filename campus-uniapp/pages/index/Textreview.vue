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
					
						<u-form-item label="评价" borderBottom>
							<u--textarea v-model="formData.content" placeholder="请输入学生评价"></u--textarea>
						</u-form-item>
						
						<u-form-item label="图片" borderBottom>
							<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
								:maxCount="1"></u-upload>
						</u-form-item>
					</view>	
				</u--form>
			</view>

			<!-- 提交按钮 -->
			<button class="submit-btn" @click="submitForm">立即发布</button>
		</scroll-view>
       
	</view>
</template>

<script>
	import {
		classEvaluation
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
				rules:[
					
				],
				showStart: false,
				showEnd: false,
				showeventType: false,
				uploadImgUrl: img_url + "/wx/common/upload",
				uploadOfficeUrl: img_url + "/wx/common/upload",
				token: token,
				
				radio: '',
				switchVal: false,
				startTime: Number(new Date()),
				endTime: Number(new Date()),
				eventType: '',
				formData: {
					content:'',
					image:''
				},
				classIds: '',
				fileList1: [],
				pdfInfo: [],
				fileList: [],
				classArr: [],
				showClass: false,
				showClassmedal: false,
				selectedBadge: null, // 默认选中第一个
                eventTagName:'',
				eventTagNameType:false,
				showTextreviewObj:[]
			}
		},
		onLoad(options) {
			_self = this
			 _self.showTextreviewObj = JSON.parse(decodeURIComponent(options.params));
			// console.log(option, '111');
			
		},
		onShow() {
			
		},
		methods: {
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
				var newShowTextreviewObj = this.showTextreviewObj.map(item => {
				  return {
				    ...item,
				    content: _self.formData.content,
				    image: _self.fileList.join(',')
				  };
				});
				this.$request.post(classEvaluation,newShowTextreviewObj).then((res) => {
					 uni.showToast({
					         title: '点评成功',
					         icon: 'success',
					         duration: 1000 // 显示2秒
					     });
					     // 2秒后关闭当前页面返回上一页
					     setTimeout(() => {
					         uni.redirectTo({
					                    url: '/pages/index/classroomassessment' // 修改为新的跳转地址
					                });
					     }, 1500);
				}, (err) => {
				
				})
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