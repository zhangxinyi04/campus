
// const config_url = 'http://192.168.4.209:8081/wx/'  //测试
// const img_url = 'http://192.168.4.209:8080'  //用于图片上传 测试
// const config_url =  'http://itcode.cloud.natool.cn/wx/'
import {config_url,imgUrl} from '../utils/baseUrl.js'

const token = uni.getStorageSync('token');
// console.log('token','request token')
const request = (url, data = {}, method = 'GET') => {
	return new Promise((resolve, reject) => {
		uni.request({
			url: config_url + url,
			data: data,
			method: method,
			header: {
				'X-Requested-With': 'XMLHttpRequest',
				"Accept": "application/json",
				"Content-Type": 'application/json',
				'Authorization': 'Bearer ' + uni.getStorageSync('token')
			},
			dataType: 'json',
			success: (res) => {
				// console.log(res)
				const responseData = interceptor(res);
				// console.log(responseData,'11')
				if (responseData.statusCode === 200) {
					resolve(responseData.data);
					// console.log()
					if(responseData.data.code && responseData.data.code === 401){
						// uni.reLaunch({
						//     url:'/pages/login/index'
						// });
						uni.removeStorage({
						    key: 'token', // 这里替换成你需要删除的键名
						    success: function () {
								uni.navigateTo({
									url: "/pages/login/index"
								})	
						    },
						    fail: function () {
						        console.log('删除失败');
						    }
						});
					}else if(responseData.data.code && responseData.data.code == 104){
						uni.showModal({
							title: "提示",
							content:responseData.data.msg,
							confirmText: "确定",
							showCancel: false,
							success: (res) => {
									
							}
						})
						return
					}else if(responseData.data.code && responseData.data.code !== 200){
						uni.showToast({
							icon: 'none',
							duration: 3000,
							title: responseData.data.msg,
						})
					}
				} else {
					throwErr(res)
					reject(res);
				}
			},
			fail: function(err) {
				reject(err)
			}
		})
	});
};
 
// GET 请求封装
const get = (url, data) => {
	return request(url, data, 'GET');
};
 
// POST 请求封装
const post = (url, data) => {
	return request(url, data, 'POST');
};
// PUT 请求封装
const put = (url, data) => {
	return request(url, data, 'PUT');
};
 
// DELETE 请求封装
const del = (url, data) => {
	return request(url, data, 'DELETE');
};
 
// 请求拦截
function interceptor(response) {
	// console.log(response,'responseresponseresponse')
	if (response.statusCode === 401) {
		uni.showToast({
			title: response.statusCode,
			icon: 'none',
			duration: 3000,
			complete: () => {
				uni.reLaunch({
				    url:'/pages/login/index'
				});
			},
		});
		return false;
	}
	return response;
}
 
// 处理错误
function throwErr(res) {
	if (res.code == 500) {
		uni.showToast({
			title: res.msg,
		})
	}
}
 
export {
	get,
	post,
	put,
	del,
	token
}