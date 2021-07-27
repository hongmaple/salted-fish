var config = require("./config.js"); //统一的网络请求方法
var util = require("./util.js")
import {
	AppType
} from './constant.js'

function request(params, isGetTonken) {
  // 全局变量
  var globalData = getApp().globalData; // 如果正在进行登陆，就将非登陆请求放在队列中等待登陆完毕后进行调用

  // if (!isGetTonken && globalData.isLanding) {
  //   globalData.requestQueue.push(params);
  //   return;
  // }
	
	if (Object.prototype.toString.call(params.data) == '[object Array]') {
		params.data = JSON.stringify(params.data);
	} else if (Object.prototype.toString.call(params.data) == '[object Number]') {
		params.data = params.data + '';
	}
	var needToken = false
	if (params.needToken) {
		if (!uni.getStorageSync('token')||typeof(uni.getStorageSync('token')) == "undefined") {
			return;
		}
		needToken = params.needToken;
	}
  wx.request({
    // url: config.domain + params.url,
	url: (params.domain ? params.domain : config.domain) + params.url,
    //接口请求地址
    data: params.data,
    header: {
      // 'content-type': params.method == "GET" ? 'application/x-www-form-urlencoded' : 'application/json;charset=utf-8',
      // 'Authorization': params.login ? undefined : uni.getStorageSync('token')
			'token': !needToken ? undefined : encodeURIComponent(uni.getStorageSync('token'))
    },
    method: params.method == undefined ? "POST" : params.method,
    dataType: 'json',
    responseType: params.responseType == undefined ? 'text' : params.responseType,
    success: function (res) {
      if (res.statusCode == 200 || res.statusCode == 201 || res.statusCode == 204) {
        //如果有定义了params.callBack，则调用 params.callBack(res.data)
        if (params.callBack) {
          params.callBack(res.data);
        }
      } else if (res.statusCode == 500) {
        uni.showToast({
          title: res.data.message,
          icon: "none"
        });
      } else if (res.statusCode == 401) {
				uni.removeStorageSync('loginResult');
				uni.removeStorageSync('token');
				// #ifdef H5
				const ua = navigator.userAgent.toLowerCase();
				if (ua.search(/MicroMessenger/i) > -1) uni.setStorageSync('appType', AppType.MP)
				// #endif
				uni.hideLoading();
				if (!params.dontTrunLogin) {
					if (uni.getStorageSync('hadLogin')) {
						uni.showModal({
							title: "提示",
							content: "登录已过期",
							cancelText: "取消",
							confirmText: "确定",
							success: res => {
								if (res.confirm) {
									// 跳转登录页面
									var url = ''
									// #ifdef H5 || MP-WEIXIN
									if (uni.getStorageSync('appType') == AppType.MP || uni.getStorageSync('appType') == AppType.MINI) {
										url = '/pages/login/login'
									} else {
										url = '/pages/accountLogin/accountLogin'
									}
									// #endif
				
									// #ifdef APP-PLUS
									var url = '/pages/accountLogin/accountLogin'
									// #endif
									uni.navigateTo({
										url: url
									})
								} else {
									uni.switchTab({
										url: '/pages/index/index'
									})
								}
							}
						})
					} else {
						uni.showModal({
							title: "提示",
							content: "登录已过期",
							cancelText: "取消",
							confirmText: "确定",
							success: res => {
								if (res.confirm) {
									// 跳转登录页面
									// #ifdef H5	
									uni.navigateTo({
										url: uni.getStorageSync('appType') == AppType.MP ? '/pages/login/login' : '/pages/accountLogin/accountLogin'
									})
									// #endif
													
									// #ifdef MP-WEIXIN
									uni.navigateTo({
										url: '/pages/login/login'
									})
									// #endif
								} else {
									uni.switchTab({
										url: '/pages/index/index'
									})
								}
							}
						})
				// 		// 跳转登录页面
				// 		// #ifdef H5	
				// 		uni.navigateTo({
				// 			url: uni.getStorageSync('appType') == AppType.MP ? '/pages/login/login' : '/pages/accountLogin/accountLogin'
				// 		})
				// 		// #endif
				
				// 		// #ifdef MP-WEIXIN
				// 		uni.navigateTo({
				// 			url: '/pages/login/login'
				// 		})
				// 		// #endif
					}
				}
      } else if (res.statusCode == 400 && !params.errCallBack) {
        uni.hideLoading();
				uni.showToast({
          title: res.data,
          icon: "none"
        });
      } else {
        //如果有定义了params.errCallBack，则调用 params.errCallBack(res.data)
        if (params.errCallBack) {
					
          params.errCallBack(res);
        }
				uni.hideLoading();
      }

      // if (!globalData.isLanding) {
      //   uni.hideLoading();
      // }
    },
    fail: function (err) {
            uni.hideLoading();
			if (err.errMsg == 'request:fail abort') {
				console.log('请求被取消啦~')
				return
			}
			setTimeout(() => {
				uni.showToast({
					// zheli
					title: "服务器出了点小差",
					icon: "none"
				});
			}, 1);
    }
  });
} //通过code获取token,并保存到缓存


var getToken = function () {
	uni.setStorageSync('token', 'bearer' + result.access_token); //把token存入缓存，请求接口数据时要用
}; // 更新用户头像昵称

/**
 * 微信公众号登录
 * @param {Object} fn  登录成功回调
 * @param {String} code  微信授权返回的code, 用于登录
 */
var mpLogin = function (fn, code) {
	// 发送 res.code 到后台换取 openId, sessionKey, unionId
	request({
		login: true,
		url: '/appLogin',
		data: {
			principal: code,
			appType: AppType.MP, // 登录类型
		},
		callBack: result => {
			loginSuccess(result, fn)
		}
	}, true);

};

var getToken = function (fn) {

};

/**
 * 登录成功后执行
 * @param {Object} result  登录成功返回的数据
 * @param {Object} fn		登录成功后的回调
 */
function loginSuccess (result, fn) {
	// 保存登陆信息
	wx.setStorageSync('loginResult', result)
	// 保存成功登录标识,token过期判断
	wx.setStorageSync('hadLogin', true)
	// 没有获取到用户昵称，说明服务器没有保存用户的昵称，也就是用户授权的信息并没有传到服务器
	// if (!result.pic) {
	// 	updateUserInfo();
	// }
	// if (!result.enabled) {
	// 	uni.showModal({
	// 		showCancel: false,
	// 		title: "提示",
	// 		content: "您已被禁用，不能购买，请联系客服",
	// 		cancelText: "取消",
	// 		confirmText: "确定"
	// 	})
	// 	wx.setStorageSync('token', '');
	// } else {
		
	// }
	wx.setStorageSync('token', JSON.stringify(result.data)); //把token存入缓存，请求接口数据时要用
	if (result.data.id) {
		wx.setStorageSync('hadBindUser', true);
		this.getCartCount();
	} else {
		wx.setStorageSync('hadBindUser', false);
	}
	// var globalData = getApp().globalData;
	// globalData.isLanding = false;
	// while (globalData.requestQueue.length) {
	// 	request(globalData.requestQueue.pop());
	// }

	if (fn) {
		fn()
	}
};

function updateUserInfo() {
  uni.getUserInfo({
    success: res => {
      var userInfo = JSON.parse(res.rawData);
      request({
        url: "/p/user/setUserInfo",
        method: "PUT",
        data: {
          avatarUrl: userInfo.avatarUrl,
          nickName: userInfo.nickName
        }
      });
    }
  });
}

function isUserAuthInfo () {
	// 查看是否授权
	wx.getSetting({
		success (res) {
			if (res.authSetting['scope.userInfo']) {
				// 已经授权，可以直接调用 getUserInfo 获取头像昵称
				wx.getUserInfo({
					success: function (res) {
						console.log(res.userInfo);
					}
				});
			}
		}
	});
}

function mpAuthLogin (page, needCode) {
	// 在微信环境打开,请求公众号网页登陆
	var redirectUrl = null;

	if (!page) {
		redirectUrl = window.location.href
	} else {
		var {
			protocol,
			host,
			pathname,
			hash
		} = window.location
		var redirectUrl = `${protocol}//${host}` + page
	}
	var scope = 'snsapi_userinfo'
	window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + config.mpAppId +
		'&redirect_uri=' +
		encodeURIComponent(redirectUrl) + '&response_type=code&scope=' + scope + '&state=' + (needCode ? 'needCode' :
			'unNeedCode') +
		'#wechat_redirect'

}

/**
 * 获取购物车商品数量
 */
function getCartCount () {
	if (!uni.getStorageSync('token')||typeof(uni.getStorageSync('token')) == "undefined") {
		// wx.removeTabBarBadge({
		// 	index: 2
		// });
		util.removeTabBadge()
		return
	}
	var params = {
		url: "/cart/count",
		method: "GET",
		dontTrunLogin: true,
		needToken: true,
		data: {},
		callBack: function (res) {
			if (res > 0) {
				wx.setTabBarBadge({
					index: 3,
					text: res + ""
				});
				var app = getApp().globalData;
				getApp().globalData.totalCartCount = res;
				return res;
			} else {
				wx.removeTabBarBadge({
					index: 3
				});
				var app = getApp().globalData;
				getApp().globalData.totalCartCount = 0;
			}
		}
	};
	request(params);
}

exports.getToken = getToken;
exports.request = request;
exports.getCartCount = getCartCount;
exports.updateUserInfo = updateUserInfo;
exports.mpAuthLogin = mpAuthLogin;
exports.loginSuccess = loginSuccess;
exports.mpLogin = mpLogin;
