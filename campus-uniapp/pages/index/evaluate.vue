<template>
	<view class="container">

		<!-- 添加选项按钮 -->
		<view class="add-btn-wrapper" v-if="!dialogMode">
			<view @click="openDialog('add')" class="add-btn">
				<text class="add-icon">+</text>
				<text class="add-text">添加选项</text>
			</view>
		</view>


		<view v-if="dialogMode">
			<view class="header-bg"></view>
			<scroll-view scroll-y class="form-wrapper">
				<!-- 表单卡片 -->
				<view class="form-card">
					<u--form labelPosition="left" :model="formData" :rules="rules" ref="uForm">
						<!-- 基础信息 -->
						<view class="form-section">
							<!-- <text class="section-title">基本信息</text> -->
							<u-form-item label="类型" borderBottom>
								<u-radio-group v-model="radiovalue1" placement="row" @change="groupChange">
									<u-radio :customStyle="{marginRight: '8px'}" label="表扬" name="表扬">
									</u-radio>
									<u-radio :customStyle="{marginRight: '8px'}" label="待改进" name="待改进">
									</u-radio>
								</u-radio-group>
							</u-form-item>

							<u-form-item label="标题" borderBottom>
								<u--textarea v-model="formData.title" placeholder="请输入标题"></u--textarea>
							</u-form-item>

							<u-form-item label="图标" borderBottom>
								<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1"
									multiple :maxCount="1"></u-upload>
							</u-form-item>
							<u-form-item label="分数" borderBottom>
								<u-number-box v-model="formData.mark" v-if="radiovalue1 == '表扬'"
									:min='0'></u-number-box>
								<u-number-box v-model="formData.mark" v-if="radiovalue1 == '待改进'"
									:max='0'></u-number-box>
							</u-form-item>

						</view>
					</u--form>
				</view>

				<!-- 提交按钮 -->
				<button class="submit-btn" @click="submitForm">确定</button>
				<button class="submit-btn" @click="dialogMode = false">取消</button>
			</scroll-view>


		</view>


		<view v-if="!dialogMode">
			<!-- 选项列表 -->
			<view class="option-list">
				<!-- 表扬榜 -->
				<view class="list-section">
					<view class="section-title">
						<text style="margin-right: 12rpx;">👍</text>
						<!-- <image src="https://picsum.photos/200/200?random=1" class="title-icon" mode="aspectFit"></image> -->
						<text class="title-text">表扬榜</text>
					</view>

					<!-- 表扬榜列表项 -->
					<view class="option-item" v-for="(item, index) in praiseList" :key="index">
						<view class="item-content">
							<image :src="configUrl+item.image" class="item-image" mode="aspectFit"></image>
							<text class="item-name">{{ item.title }}</text>
							<view class="count-wrapper" @click="incrementCount(item, 'praise')">
								<text class="count-text">次数: {{ item.counts }}</text>
								<text class="count-icon">+</text>
							</view>
						</view>
						<view class="item-actions">
							<view @click="Textreview(item, 'Textreview')" class="action-btn">
								<text class="action-text">文字点评</text>
							</view>
							<view @click="openDialog('edit', item)" class="action-btn">
								<text class="action-text">编辑选项</text>
							</view>
							<view class="action-btn delete-btn" @click="deleteOption(item, 'praise')">
								<text class="action-text">删除</text>
							</view>
						</view>
					</view>
				</view>

				<!-- 待改进 -->
				<view class="list-section">
					<view class="section-title">
						<text style="margin-right: 12rpx;">💡</text>
						<!-- <image src="https://picsum.photos/200/200?random=2" class="title-icon" mode="aspectFit"></image> -->
						<text class="title-text">待改进</text>
					</view>

					<!-- 待改进列表项 -->
					<view class="option-item" v-for="(item, index) in improveList" :key="index">
						<view class="item-content">
							<image :src="configUrl+item.image" class="item-image" mode="aspectFit"></image>
							<text class="item-name">{{ item.title }}</text>
							<view class="count-wrapper" @click="incrementCount(item, 'improve')">
								<text class="count-text">次数: {{ item.counts }}</text>
								<text class="count-icon">+</text>
							</view>
						</view>
						<view class="item-actions">
							<view @click="Textreview(item, 'Textreview')" class="action-btn">
								<text class="action-text">文字点评</text>
							</view>
							<view @click="openDialog('edit', item)" class="action-btn">
								<text class="action-text">编辑选项</text>
							</view>
							<view class="action-btn delete-btn" @click="deleteOption(item, 'improve')">
								<text class="action-text">删除</text>
							</view>
						</view>
					</view>
				</view>
			</view>
		</view>


	</view>
</template>

<script>
	var _self;
	import {
		classEvaluationlist,
		classEvaluationselectStudentBonusCountByStudentId,
		classEvaluationclassEvaluatio,
		classEvaluationDel
	} from '@/utils/index.js'
	import {
		img_url
	} from '@/utils/baseUrl.js'
	export default {

		onLoad(options) {
			_self = this
			this.obj = JSON.parse(decodeURIComponent(options.params));
			this.classEvaluationselectStudentBonusCountByStudentId0()
			this.classEvaluationselectStudentBonusCountByStudentId1()
		},
		data() {
			return {
				uploadImgUrl: img_url + "/wx/common/upload",
				uploadOfficeUrl: img_url + "/wx/common/upload",
				radiolist1: [],
				// u-radio-group的v-model绑定的值如果设置为某个radio的name，就会被默认选中
				radiovalue1: '表扬',
				rules: [],
				formData: {
					type: 0
				},
				configUrl: img_url,
				obj: [],
				queryParams: {
					pageNum: 1,
					pageSize: 100,
					title: null,
					image: null,
					type: null,
					mark: null
				},
				praiseList: [],
				improveList: [],
				selectStudentArr0: [],
				selectStudentArr1: [],
				dialogMode: false,
				value: 0,
				fileList1: [],
				fileList: []
			}
		},
		methods: {
			groupChange(n) {
				this.formData.type = n == '表扬' ? '0' : '1'
			},
			submitForm() {
				if (this.formData.bonusTypeId != null) {
					this.formData.image = null
					if(this.fileList1[0].url){
						this.formData.image = this.fileList1[0].url
						this.formData.image = this.formData.image.startsWith(this.configUrl) ?  this.formData.image.replace(this.configUrl, '') : this.formData.image;
					}else{
						this.formData.image = this.fileList[0]
					}
					// console.log(this.formData.image,'this.formData.image')
					// return
					
					this.$request.put(classEvaluationDel, this.formData).then((res) => {
						uni.showToast({
							title: '修改成功',
							icon: 'none',
							duration: 800
						})
						this.dialogMode = false
						this.classEvaluationselectStudentBonusCountByStudentId0()
						this.classEvaluationselectStudentBonusCountByStudentId1()
					}, (err) => {

					})
				} else {
					this.formData.image = _self.fileList.join(',')
					this.$request.post(classEvaluationDel, this.formData).then((res) => {
						uni.showToast({
							title: '新增成功',
							icon: 'none',
							duration: 800
						})
						this.dialogMode = false
						this.classEvaluationselectStudentBonusCountByStudentId0()
						this.classEvaluationselectStudentBonusCountByStudentId1()
					}, (err) => {

					})

				}
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
			openDialog(mode, item = null) {
				this.dialogMode = true
				this.formData = {}
				this.formData.type = 0
			    this.fileList1 = []
				// console.log(item, '')
				if (item != null) {
					this.formData = item
					this.radiovalue1 = this.formData.type === '0' ? '表扬' : '待改进'
					if (item.image) { // 确保图片路径存在
					      this.fileList1.push({
					        url: this.configUrl + item.image, // 使用 configUrl 作为图片基础路径（而非 uploadImgUrl，后者是上传接口地址）
					        // 可选：添加其他属性如 name、type 等，根据需求
					      });
					    }
				}
				// console.log(this.formData,'11')
			},
			Textreview(item) {
				var dataArr = this.obj
				var dataObj = {}
				for (var i = 0; i < dataArr.length; i++) {
					dataArr[i].mark = item.mark
					dataArr[i].type = item.type
					dataArr[i].title = item.title
					dataArr[i].bonusTypeId = item.bonusTypeId
				}
				var Arr = encodeURIComponent(JSON.stringify(dataArr));
				uni.navigateTo({
					url: `/pages/index/Textreview?params=${Arr}`
				});

			},
			classEvaluationselectStudentBonusCountByStudentId0() {
				this.$request.post(classEvaluationselectStudentBonusCountByStudentId, {
					studentId: this.obj[0].studentId,
					type: 0
				}).then((res) => {
					this.selectStudentArr0 = res.data
					_self.classEvaluationlist0()
				}, (err) => {

				})
			},
			classEvaluationselectStudentBonusCountByStudentId1() {
				this.$request.post(classEvaluationselectStudentBonusCountByStudentId, {
					studentId: this.obj[0].studentId,
					type: 1
				}).then((res) => {
					this.selectStudentArr1 = res.data
					_self.classEvaluationlist1()
				}, (err) => {

				})
			},
			classEvaluationlist0() {
				this.queryParams.type = 0
				this.$request.post(classEvaluationlist, this.queryParams).then((res) => {
					_self.praiseList = res.data
					const countMap = new Map();
					_self.selectStudentArr0.forEach(item => {
						countMap.set(item.bonusTypeId, item.counts);
					});
					_self.praiseList = _self.praiseList.map(item => ({
						...item,
						counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
					}));
				}, (err) => {

				})
			},
			classEvaluationlist1() {
				this.queryParams.type = 1
				this.$request.post(classEvaluationlist, this.queryParams).then((res) => {
					_self.improveList = res.data
					const countMap = new Map();
					_self.selectStudentArr1.forEach(item => {
						countMap.set(item.bonusTypeId, item.counts);
					});
					_self.improveList = _self.improveList.map(item => ({
						...item,
						counts: countMap.has(item.bonusTypeId) ? countMap.get(item.bonusTypeId) : 0
					}));
				}, (err) => {

				})
			},

			// 增加次数
			incrementCount(item, type) {
				var dataArr = this.obj
				var dataObj = {}
				dataObj.mark = item.mark
				dataObj.type = item.type
				dataObj.bonusTypeId = item.bonusTypeId
				for (var i = 0; i < dataArr.length; i++) {
					dataArr[i].mark = item.mark
					dataArr[i].type = item.type
					dataArr[i].bonusTypeId = item.bonusTypeId
				}
				this.$request.post(classEvaluationclassEvaluatio, dataArr).then((res) => {
					uni.showToast({
						title: item.type === '0' ? `${item.title} +${item.mark}` :
							`${item.title} ${item.mark}`,
						icon: 'none',
						duration: 800
					})
					if (this.obj.length == 1) {
						this.classEvaluationselectStudentBonusCountByStudentId0()
						this.classEvaluationselectStudentBonusCountByStudentId1()
					}
				}, (err) => {

				})
			},

			// 删除选项
			deleteOption(item, type) {
				const bonusTypeIds = item.bonusTypeId
				// console.log(item,'1')
				uni.showModal({
					title: '提示',
					content: '您确定要删除该选项？',
					success: function(res) {
						if (res.confirm) {
							_self.$request.del(`${classEvaluationDel}/${bonusTypeIds}`, {}).then((res) => {
								uni.showToast({
									title: '删除成功！',
									icon: 'none',
									duration: 800
								})
								_self.classEvaluationselectStudentBonusCountByStudentId0()
								_self.classEvaluationselectStudentBonusCountByStudentId1()
							}, (err) => {

							})
							console.log('用户点击确定');
						} else if (res.cancel) {
							// console.log('用户点击取消');
						}
					}
				});
			},

			// 获取学生信息
			teachergetStudentInfo() {
				this.$request.post(teachergetStudentInfo, {}).then((res) => {
					console.log(res, 'res')
				}, (err) => {

				})
			}
		}
	}
</script>

<style lang="scss">
	@import "@/uview-ui/index.scss";

	.header-bg {
		height: 200rpx;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
	}

	.form-wrapper {
		// padding: 30rpx;
		margin-top: -100rpx;
	}

	.form-section {
		margin-bottom: 40rpx;

		.section-title {
			display: block;
			font-size: 32rpx;
			color: #2a3c5e;
			font-weight: 600;
			// margin-bottom: 30rpx;
			padding-left: 20rpx;
			border-left: 6rpx solid #007aff;
		}
	}

	.form-wrapper {

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

	/* 全局样式 */
	.container {
		background-color: #f8f8f8;
		min-height: 100vh;
	}

	/* 导航栏样式 */
	.nav-bar {
		height: 100rpx;
		background-color: #409EFF;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 2rpx 10rpx rgba(0, 0, 0, 0.1);
	}

	.nav-title {
		color: #FFFFFF;
		font-size: 36rpx;
		font-weight: bold;
	}

	/* 添加按钮样式 */
	.add-btn-wrapper {
		padding: 30rpx 20rpx;
	}

	.add-btn {
		height: 80rpx;
		background-color: #409EFF;
		border-radius: 40rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		background: linear-gradient(135deg, #83a4d4 0%, #b6fbff 100%);
	}

	.add-icon {
		color: #FFFFFF;
		font-size: 40rpx;
		margin-right: 10rpx;
	}

	.add-text {
		color: #FFFFFF;
		font-size: 28rpx;
	}

	/* 列表样式 */
	.option-list {
		padding: 0 20rpx 30rpx;
	}

	.list-section {
		margin-bottom: 40rpx;
		background-color: #FFFFFF;
		border-radius: 16rpx;
		box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.05);
	}

	.section-title {
		height: 100rpx;
		display: flex;
		align-items: center;
		padding: 0 30rpx;
		border-bottom: 2rpx solid #f5f5f5;
	}

	.title-icon {
		width: 40rpx;
		height: 40rpx;
		margin-right: 15rpx;
	}

	.title-text {
		font-size: 32rpx;
		font-weight: 500;
		color: #333333;
	}

	/* 列表项样式 */
	.option-item {
		padding: 25rpx 30rpx;
		border-bottom: 2rpx solid #f5f5f5;
	}

	.item-content {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
		justify-content: space-between;
	}

	.item-image {
		width: 60rpx;
		height: 60rpx;
		border-radius: 10rpx;
		margin-right: 20rpx;
	}

	.item-name {
		font-size: 30rpx;
		color: #333333;
		flex: 1;
	}

	.count-wrapper {
		display: flex;
		align-items: center;
		background-color: #f5f7fa;
		padding: 8rpx 16rpx;
		border-radius: 20rpx;
	}

	.count-text {
		font-size: 26rpx;
		color: #606266;
		margin-right: 6rpx;
	}

	.count-icon {
		font-size: 22rpx;
		color: #409EFF;
	}

	.item-actions {
		display: flex;
		justify-content: flex-end;
	}

	.action-btn {
		padding: 10rpx 20rpx;
		margin-left: 15rpx;
		border-radius: 6rpx;
		font-size: 24rpx;
	}

	.action-text {
		color: #409EFF;
	}

	.delete-btn .action-text {
		color: #F56C6C;
	}
</style>