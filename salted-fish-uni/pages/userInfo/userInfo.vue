<template>
	<view class="container">
		<!--input列表 -->
		<view class="input-box">
		  <view class="section">
		    <text>头像</text>
		    <image style="width: 130rpx;height: 130rpx;padding-left: 55%;" @tap="upload" :src="userInfo.avatarImage?serverUrl+userInfo.avatarImage:'../../static/images/icon/head04.png'"></image>
		  </view>
		  <view class="section">
		    <text>手机号码</text>
		    <input placeholder="11位手机号码" type="number" maxlength="11" :value="userInfo.phone" @input="onMobileInput"></input>
		  </view>
		  <view class="section">
		    <text>昵称</text>
		   <input placeholder="请输入昵称" type="text" maxlength="10" :value="userInfo.username" @input="onUsernameInput"></input>
		  </view>
		  <view class="section">
		    <text>密码</text>
		   <input placeholder="请输入密码" type="password" maxlength="10" :value="userInfo.password" @input="onPasswordInput"></input>
		  </view>
		  <view class="section">
		    <text>学号</text>
		   <input placeholder="请输入学号" type="number" maxlength="12" :value="userInfo.studentNumber" @input="onStudentNumberInput"></input>
		  </view>
		  <view class="section" @tap="changeVisible">
		    <text>年级</text>
			<view class="uni-title">{{userInfo.grade}}</view>
		  </view>
		</view>
		<picker-view v-if="visible" :indicator-style="indicatorStyle" :value="value" @change="onGradeInput" class="picker-view">
		           <picker-view-column>
		               <view class="item" v-for="(item,index) in grades" :key="index">{{item}}</view>
		           </picker-view-column>
		</picker-view>
		<!-- end input列表 -->
		<!-- 功能按钮 -->
		<view class="btn-box">
		  <view class="keep btn" @tap="onSubmiti">
		    <text>保存</text>
		  </view>
		</view>
		<!-- end 功能按钮 -->
	</view>
</template>

<script>
	var http = require("../../utils/http.js");
	var config = require("../../utils/config.js");
	var index = [18, 0, 0];
	var t = 0;
	var show = false;
	var moveY = 200;
	export default {
		data() {
			return {
				userInfo: {
					
				},
				serverUrl: config.domain,
				grades: [
			      '大一',
				  '大二',
				  '大三',
				  '大四'
				],
				value: [0],
				visible: false,
				indicatorStyle: `height: 50px;`
			}
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			//加载订单数字
			var ths = this; // var status = ths.data.status
			ths.setData({
				userInfo: uni.getStorageSync("loginResult").data
			});
		},
		methods: {
			upload: function() {
				uni.chooseImage({
				    success: (chooseImageRes) => {
				        const tempFilePaths = chooseImageRes.tempFilePaths;
				        const uploadTask = uni.uploadFile({
				            url: config.domain+"/common/upload", //仅为示例，非真实的接口地址
				            filePath: tempFilePaths[0],
				            name: 'file',
				            formData: {
				                
				            },
				            success: (uploadFileRes) => {
								var userInfo = this.userInfo;
								var data = JSON.parse(uploadFileRes.data);
								userInfo.avatarImage = data.fileName;
								this.setData({
								  userInfo: userInfo
								});
				            }
				        });
				
				        uploadTask.onProgressUpdate((res) => {
				            console.log('上传进度' + res.progress);
				            console.log('已经上传的数据长度' + res.totalBytesSent);
				            console.log('预期需要上传的数据总长度' + res.totalBytesExpectedToSend);
				
				            // 测试条件，取消上传任务。
				            // if (res.progress > 50) {
				            //     uploadTask.abort();
				            // }
				        });
				    }
				});
		    },
			onMobileInput: function (e) {
			  this.setData({
			    'userInfo.phone': e.detail.value
			  });
			},
			onUsernameInput: function (e) {
				this.setData({
				  'userInfo.username': e.detail.value
				});
			},
			onPasswordInput: function (e) {
				this.setData({
				  'userInfo.password': e.detail.value
				});
			},
			onStudentNumberInput: function(e) {
				this.setData({
				  'userInfo.studentNumber': e.detail.value
				});
			},
			onGradeInput: function(e) {
				const val = e.detail.value
				console.log(val);
				console.log(this.grades[val[0]]);
				this.setData({
				  'userInfo.grade': this.grades[val[0]]
				});
			},
			changeVisible: function() {
				this.setData({
				  visible: !this.visible
				});
			},
			onSubmiti: function () {
				var params = {
				  url: "/user",
				  method: "PUT",
				  needToken: true,
				  data: {
					id: this.userInfo.id,
				    username: this.userInfo.username,
				    password: this.userInfo.password,
				    phone: this.userInfo.phone,
				    avatarImage: this.userInfo.avatarImage,
					studentNumber: this.userInfo.studentNumber,
					grade: this.userInfo.grade
				  },
				  callBack: function (res) {
				    uni.hideLoading();
				    uni.showToast({
				    	title: "修改成功",
				    	icon: 'none',
				    });
					var params = {
					  url: "/user/info",
					  method: "post",
					  needToken: true,
					  data: {
					  },
					  callBack: function (res) {
						var token = res.data;
						console.log(res.data);
					    uni.setStorageSync("loginResult",res);
						// console.log(token);
						// uni.setStorageSync('token',token);
					  }
					};
					http.request(params);
				  }
				};
				http.request(params);
			}
	    }
	}
</script>

<style>
@import "./userInfo.css";
 .picker-view {
        width: 750rpx;
        height: 600rpx;
        margin-top: 20rpx;
}
.item {
        height: 50px;
        align-items: center;
        justify-content: center;
        text-align: center;
}
</style>
