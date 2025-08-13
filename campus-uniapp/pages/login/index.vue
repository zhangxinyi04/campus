<template>
	<view class="container">
		<!-- Logo区域 -->
		<view class="logo-box">
			<image class="logo" src="/static/logo2.jpg" mode="aspectFit"></image>
			
		</view>

		<!-- 标题 -->
		<text class="title">回龙观第二小学</text>

		<!-- 表单区域 -->
		<view class="form-box">
			<!-- 账号输入 -->
			<view class="input-group">
				<!-- <uni-icons type="person" size="24" color="#4a90e2"></uni-icons> -->
				<input class="input" type="number" v-model="name" placeholder="请输入手机号"
					placeholder-style="color:#999" @focus="inputFocus('name')" @blur="inputBlur" />
				<view class="line" :class="{active: currentFocus === 'name'}"></view>
			</view>

			<!-- 密码输入 -->
			<view class="input-group">
				<!-- <uni-icons type="locked" size="24" color="#4a90e2"></uni-icons> -->
				<input class="input" password v-model="password" placeholder="请输入密码" placeholder-style="color:#999"
					@focus="inputFocus('password')" @blur="inputBlur" />
				<view class="line" :class="{active: currentFocus === 'password'}"></view>
			</view>

			<!-- 身份选择 -->
			<view class="type-select">
				<view class="type-item" :class="{active: type === '00'}" @tap="changeType('00')">
					教师
				</view>
				<view class="type-item" :class="{active: type === '01'}" @tap="changeType('01')">
					家长
				</view>
			</view>

			<!-- 登录按钮 -->
			<view class="login-btn" @tap="handleLogin">
				<text class="btn-text">登 录</text>
			</view>

			<!-- 忘记密码 -->
			<view class="forget-pwd" @tap="gotoForget">
				忘记密码？
			</view>
		</view> 

		<!-- 底部装饰 -->
		<view class="decorate-circle"></view>
	</view>
</template>

<script>
	import {
		authlogin
	} from '@/utils/login/login.js'
	var _self;
	export default {
		data() {
			return {
				name: '18600593392',
				password: '123456',
				// name: '13911325656',
				// password: '325656',
				type: '00', // 0-教师 1-家长
				currentFocus: '', // 当前聚焦的输入框
			}
		},
		onLoad(){
			_self = this;
		},
		methods: {
			
			inputFocus(type) {
				this.currentFocus = type
			},
			inputBlur() {
				this.currentFocus = ''
			},
			changeType(type) {
				this.type = type
				// console.log(type)
			},
			handleLogin() {
				// 登录逻辑
				let obj = {
					username:_self.name+'-'+_self.type,
					password:_self.password,
					// type:_self.type,
				}
				this.$request.post(authlogin, obj).then((res) => {
					// console.log(res,'res')
					if(res.code == 200){
						uni.setStorageSync('token', res.token);
						uni.showToast({
							title: '登录成功',
						})
						if(_self.type === '01'){
							uni.redirectTo({
							  url: '/subpackage1/homepage/homepage' // tabBar 页面路径，不能带参数
							});
							
						}else if(_self.type === '00'){
							uni.switchTab({
							  url: '/pages/index/index' // tabBar 页面路径，不能带参数
							});
						}
						
					
					}else{
						uni.showToast({
							title: res.msg,
							icon:'none'
						})
						return
					}
				   
					
				}, (err) => {
				
				})
			
			},
			gotoForget() {
				// 跳转忘记密码
			}
		}
	}
</script>

<style lang="less">
	.container {
		height: 100vh;
		background: linear-gradient(150deg, #f0f9ff 0%, #c2e9fb 100%);
		padding: 60rpx 40rpx;
		position: relative;
		overflow: hidden;
	}

	.logo-box {
		width: 160rpx;
		height: 160rpx;
		margin: 0 auto;
		animation: logoFloat 3s ease-in-out infinite;

		.logo {
			width: 100%;
			height: 100%;
		}
	}

	.title {
		display: block;
		text-align: center;
		font-size: 48rpx;
		color: #2c3e50;
		font-weight: bold;
		margin: 40rpx 0;
		letter-spacing: 4rpx;
	}

	.form-box {
		background: rgba(255, 255, 255, 0.9);
		border-radius: 24rpx;
		padding: 60rpx 40rpx;
		box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.1);
		transform: translateY(0);
		animation: formSlide 0.6s ease-out;
	}

	.input-group {
		margin-bottom: 60rpx;
		position: relative;

		.input {
			height: 80rpx;
			padding-left: 80rpx;
			font-size: 32rpx;
			color: #333;
		}

		.uni-icons {
			position: absolute;
			left: 20rpx;
			top: 28rpx;
			z-index: 2;
		}

		.line {
			position: absolute;
			left: 0;
			bottom: 0;
			width: 0;
			height: 2rpx;
			background: #4a90e2;
			transition: all 0.3s;

			&.active {
				width: 100%;
			}
		}
	}

	.type-select {
		display: flex;
		margin: 40rpx 0 60rpx;

		.type-item {
			flex: 1;
			text-align: center;
			padding: 20rpx;
			margin: 0 20rpx;
			border-radius: 12rpx;
			background: #f5f5f5;
			color: #666;
			transition: all 0.3s;

			&.active {
				background: linear-gradient(90deg, #4a90e2, #6ab0f3);
				color: #fff;
				transform: translateY(-4rpx);
				box-shadow: 0 8rpx 20rpx rgba(74, 144, 226, 0.3);
			}
		}
	}

	.login-btn {
		background: linear-gradient(90deg, #4a90e2, #6ab0f3);
		height: 96rpx;
		border-radius: 48rpx;
		display: flex;
		align-items: center;
		justify-content: center;
		transition: all 0.2s;

		&:active {
			transform: scale(0.98);
		}

		.btn-text {
			color: #fff;
			font-size: 36rpx;
			letter-spacing: 4rpx;
		}
	}

	.forget-pwd {
		text-align: right;
		color: #4a90e2;
		font-size: 28rpx;
		margin-top: 30rpx;
		padding-right: 20rpx;
	}

	.decorate-circle {
		position: absolute;
		right: -200rpx;
		bottom: -200rpx;
		width: 600rpx;
		height: 600rpx;
		background: rgba(255, 255, 255, 0.15);
		border-radius: 50%;
	}

	@keyframes logoFloat {

		0%,
		100% {
			transform: translateY(0);
		}

		50% {
			transform: translateY(-20rpx);
		}
	}

	@keyframes formSlide {
		from {
			transform: translateY(100rpx);
			opacity: 0;
		}

		to {
			transform: translateY(0);
			opacity: 1;
		}
	}
</style>