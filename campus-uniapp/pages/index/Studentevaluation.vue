<template>
	<view class="evaluate-container">
		<!-- 顶部渐变背景 -->
		<view class="header-bg"></view>

		<scroll-view scroll-y class="content-wrapper">
			<!-- 评价卡片 -->
			<view class="evaluate-card">
				<!-- 评分区域 -->
				<view class="section">
					<text class="section-title">类型</text>
					<view class="rate-box" @click="showClass = true">
						<u--input v-model="type" disabled disabledColor="#ffffff" placeholder="请选择类型"
							border="none"></u--input>
						<u-icon slot="right" name="arrow-right"></u-icon>
						<!-- <u--input placeholder="请输入分数" border="bottom" clearable v-model="formData.mark"></u--input> -->
					</view>
				</view>
				<!-- 评分区域 -->
				<view class="section">
					<text class="section-title">分数</text>
					<view class="rate-box">
						<u--input placeholder="请输入分数" border="bottom" clearable v-model="formData.mark"></u--input>
					</view>
				</view>

				<!-- 评价内容 -->
				<view class="section">
					<text class="section-title">评价内容</text>
					<u--textarea v-model="formData.content" placeholder="请输入评价内容" height="200"
						maxlength="200" count border="none" class="textarea"></u--textarea>
				</view>

				<!-- 图片上传 -->
				<view class="section">
					<text class="section-title">上传图片</text>
					<!-- <view class="upload-tips">最多可上传3张图片</view> -->
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="10" previewFullImage :deletable="true" class="upload-box"></u-upload>
				</view>

				<!-- 提交按钮 -->
				<u-button type="primary" shape="circle" @click="handleSubmit" :disabled="!formData.mark"
					class="submit-btn">
					<text class="btn-text">提交评价</text>
				</u-button>
			</view>
		</scroll-view>
		
		<u-picker :show="showClass" :columns="classArr" keyName="name" @close='showClass = false'
			@confirm="confirmClass" @cancel="cancelClass"></u-picker>
	</view>
</template>

<script>
	import {
			teacherclassEvaluation,
			teachergetInfo
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
				classArr : [
				    [
				        {
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
				showClass:false,
				type:'',
				formData: {
					type:'',
					mark: '',
					content: '',
					teacherId:uni.getStorageSync('teacherId'),
					// studentId:'',
					// courseId:'',
					deptId:''
				},
				uploadImgUrl: img_url + "/wx/common/upload",
				fileList: [],
				fileList1:[],
				paramsArr:[]
			};
		},
		onLoad(options) {
		  	  _self = this
			  this.paramsArr = JSON.parse(decodeURIComponent(options.params));
			  // _self.formData.studentId = options.studentId
			  // _self.formData.courseId = options.courseId
			  _self.teachergetInfos()
			  console.log(this.paramsArr)
		 },
		methods: {
			teachergetInfos() {
				this.$request.post(teachergetInfo, {}).then((res) => {
					_self.formData.deptId = res.data.deptId
					// console.log(res.data)
				}, (err) => {
			
				})
			},
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
		          url:this.uploadImgUrl,
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
		                reject({ errMsg: '解析响应数据失败' });
		              }
		            } else {
		              reject({ errMsg: '服务器返回状态码非 200' });
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
                this.formData.image = this.fileList.join(',')
				uni.showLoading({
					title: '提交中...'
				});
				
				// console.log(this.paramsArr,this.formData)
				const arr = this.paramsArr.map(item => ({
				    ...item,          // 保留原对象的courseId和studentId
				    ...this.formData       // 合并formData的属性
				}));
				
				console.log(arr)
				
				// var arr = []
				// arr.push(this.formData)
				
				this.$request.post(teacherclassEvaluation, arr).then((res) => {
					if(res.code == 200){
						uni.hideLoading();
				    	uni.showToast({
							title: '评价成功'
						});
						uni.redirectTo({
							url: '/pages/components/success'
						})
					}
					// console.log(res, 'res')
					
					  
				}, (err) => {
					  
				})

				// // 模拟提交
				// setTimeout(() => {
				// 	uni.hideLoading();
				// 	uni.showToast({
				// 		title: '评价成功'
				// 	});
				// 	this.resetForm();
				// }, 1500);
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