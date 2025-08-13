<template>
	<view class="evaluate-container">
		<!-- 顶部渐变背景 -->
		<view class="header-bg"></view>

		<scroll-view scroll-y class="content-wrapper">
			<!-- 评价卡片 -->
			<view class="evaluate-card">
				
				<!-- 教师寄语展示 -->
							<!-- 	<view class="section" v-if="teacherContent !== null">
									<text class="section-title">家长寄语</text>
									<view class="teacher-comment-box">
										<text class="teacher-comment-text">{{teacherContent}}</text>
									</view>
								</view> -->


				<!-- 评价内容 -->
				<view class="section">
					<text class="section-title">您的寄语</text>
					<u--textarea v-model="formData.teacherContent" placeholder="您可以从学习、生活习惯、成长等方面对孩子进行评价." height="200"
						maxlength="200" count border="none" class="textarea"></u--textarea>
				</view>
				<!-- 提交按钮 -->
				<u-button type="primary" shape="circle" @click="handleSubmit" :disabled="!formData.teacherContent"
					class="submit-btn">
					<text class="btn-text">提交评价</text>
				</u-button>
			</view>
		</scroll-view>

	</view>
</template>

<script>
	import {
		teacherclassEvaluation,
		teachergetInfo,
		comment,
		studentgetInfo
	} from '@/utils/index.js'
	import {
		token
	} from '@/utils/request.js'
	import {
		config_url,
		img_url
	} from '@/utils/baseUrl.js'
	var _self;
	export default {
		data() {
			return {
				classArr: [
					[{
							name: '预先学习 夯实基础',
							value: 1
						},
						{
							name: '勇于质疑 大胆提问',
							value: 2
						},
						{
							name: '专心听讲 踊跃互动',
							value: 3
						},
						{
							name: '静心思考 自主探究',
							value: 4
						},
						{
							name: '深度互动 智慧碰撞',
							value: 5
						},
						{
							name: '高效合作 共学共进',
							value: 6
						},
						{
							name: '积极阅读 持之以恒',
							value: 7
						},
						{
							name: '日练日清 细查无遗',
							value: 8
						},
						{
							name: '声音洪亮 自信发声',
							value: 9
						},
						{
							name: '条理清晰 逻辑严密',
							value: 10
						},
						{
							name: '学融文本 多维表达',
							value: 11
						}
					]
				],
				showClass: false,
				type: '',
				formData: {
					studentId:'',
					teacherContent: '',
					studentCommentId:''
				},
				uploadImgUrl: img_url + "/wx/common/upload",
				fileList: [],
				fileList1: [],
				commentdetails:null,
				teacherContent:null
			};
		},
		onLoad(options) {
			_self = this
			_self.commentdel(options.studentCommentId)
			console.log(options,'options')
			 _self.formData.studentCommentId = options.studentCommentId
			 _self.formData.studentId = options.studentId
		},
		methods: {
			commentdel(studentCommentId){
				this.$request.get(`/comment/${studentCommentId}`).then((res) => {
					_self.commentdetails = res.data
					if(_self.commentdetails){
						// _self.formData.content = _self.commentdetails.content
						_self.formData.teacherContent = _self.commentdetails.teacherContent
					}
					
				}, (err) => {
				
				})
			},
			// studentgetInfo() {
			// 	this.$request.post(studentgetInfo, {}).then((res) => {
			// 		  _self.formData.studentId = res.data.student.studentId
			// 	}, (err) => {
			
			// 	})
			// },
			// teachergetInfos() {
			// 	this.$request.post(teachergetInfo, {}).then((res) => {
			// 		_self.formData.deptId = res.data.deptId
			// 		// console.log(res.data)
			// 	}, (err) => {

			// 	})
			// },
			cancelClass() {
				_self.showClass = false
			},
			confirmClass(e) {
				this.type = e.value[0].name
				this.formData.type = e.value[0].value
				_self.showClass = false
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
				// console.log(lists,'lists')
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
				// console.log(filePath,'filePath')
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
								this.fileList.push(JSON.parse(res.data).url)
								// console.log(this.fileList,'this.fileList')
								try {
									// const data = JSON.parse(res.data);
									this.fileList.push(JSON.parse(res.data).url)
									// console.log(this.fileList,'fileList')
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
			// 提交表单
			handleSubmit() {
				uni.showLoading({
					title: '提交中...'
				});
				if(_self.commentdetails){
					this.$request.put(comment, this.formData).then((res) => {
						if (res.code == 200) {
							uni.hideLoading();
							uni.showToast({
								title: '评价成功'
							});
							uni.redirectTo({
								url: '/pages/components/success'
							})
						}
					
					}, (err) => {
					
					})
				}else{
					this.$request.post(comment, this.formData).then((res) => {
						if (res.code == 200) {
							uni.hideLoading();
							uni.showToast({
								title: '评价成功'
							});
							uni.redirectTo({
								url: '/pages/components/success'
							})
						}
					
					}, (err) => {
					
					})
				}
				
			},

			// 重置表单
			resetForm() {
				this.formData = {
					score: 0,
					content: ''
				};
				this.fileList = [];
			}
		}
	};
</script>

<style lang="scss">
	@import "@/uview-ui/index.scss";
	
	.teacher-comment-box {
			background: #f8f9fb;
			border-radius: 16rpx;
			padding: 30rpx;
			margin-top: 20rpx;
			position: relative;
			border-left: 6rpx solid #83a4d4;
			// padding-top: 50rpx;
			
			&::before {
				content: "“";
				position: absolute;
				left: 20rpx;
				top: 10rpx;
				font-size: 60rpx;
				color: #83a4d4;
				opacity: 0.3;
			}
			
			&::after {
				content: "”";
				position: absolute;
				right: 20rpx;
				bottom: 10rpx;
				font-size: 60rpx;
				color: #83a4d4;
				opacity: 0.3;
			}
		}
	
		.teacher-comment-text {
			font-size: 28rpx;
			color: #666;
			padding-left: 30rpx;
			padding-right: 30rpx;
			line-height: 1.8;
			white-space: pre-wrap;
		}

	.evaluate-container {
		background: #f8fafc;
		min-height: 100vh;
	}

	.header-bg {
		height: 240rpx;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
	}

	.content-wrapper {
		// padding: 30rpx;
		margin-top: -120rpx;
	}

	.evaluate-card {
		background: #fff;
		border-radius: 24rpx;
		padding: 40rpx;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.06);
	}

	.section {
		margin-bottom: 50rpx;

		&-title {
			display: block;
			font-size: 32rpx;
			color: #2a3c5e;
			font-weight: 600;
			margin-bottom: 30rpx;
			position: relative;
			padding-left: 20rpx;

			&::before {
				content: '';
				position: absolute;
				left: 0;
				top: 50%;
				transform: translateY(-50%);
				width: 6rpx;
				height: 28rpx;
				background: #007aff;
				border-radius: 4rpx;
			}
		}
	}

	.rate-box {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 0;

		.score-text {
			font-size: 36rpx;
			color: #FFC107;
			font-weight: bold;
		}
	}

	.textarea {
		background: #f8f9fb;
		border-radius: 16rpx;
		padding: 20rpx !important;

		::v-deep .u-textarea__field {
			font-size: 28rpx !important;
			color: #333 !important;
		}
	}

	.upload-tips {
		font-size: 24rpx;
		color: #999;
		margin-bottom: 20rpx;
	}

	.upload-box {
		::v-deep .u-upload__wrap {
			gap: 20rpx;
		}

		::v-deep .u-upload__button {
			border: 2rpx dashed #ddd !important;
			border-radius: 16rpx;
			width: 200rpx;
			height: 200rpx;
		}

		::v-deep .u-upload__preview {
			width: 200rpx !important;
			height: 200rpx !important;
			border-radius: 16rpx;
		}
	}

	.submit-btn {
		margin-top: 60rpx;
		height: 90rpx;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%) !important;

		.btn-text {
			font-size: 32rpx;
			letter-spacing: 2rpx;
		}

		&[disabled] {
			opacity: 0.6;
		}
	}
</style>