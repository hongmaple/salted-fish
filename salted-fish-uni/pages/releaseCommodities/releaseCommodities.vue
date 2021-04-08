<template>
	<view class="container">
		<!--input列表 -->
		<view class="input-box">
		  <view class="section">
		    <text>标题</text>
		    <input placeholder="请输入标题" type="text" maxlength="11" :value="flower.title" @input="onMobileInput"></input>
		  </view>
		  <view class="section" @tap="changeTypeVisible">
		    <text>类型</text>
		  	<view class="uni-title">{{typeName}}</view>
		  	<picker-view v-if="typeVisible" :indicator-style="indicatorStyle" :value="typevalue" @change="onTypeChange" class="picker-view">
		  				        <picker-view-column>
		  				            <view class="item" v-for="(item,index) in types" :key="index">{{item}}</view>
		  				        </picker-view-column>
		    </picker-view>
		  </view>
		  <view class="section">
		    <text>规格</text>
		    <input placeholder="请输入规格" type="text" maxlength="11" :value="flower.specification" @input="onSpecificationInput"></input>
		  </view>
		  <view class="section" @tap="changeOldNewLevelVisible">
		    <text>成色</text>
		    <view class="uni-title">{{oldNewLevel}}</view>
		    <picker-view v-if="oldNewLevelVisible" :indicator-style="indicatorStyle" :value="oldNewLevelValue" @change="onOldNewLevelChange" class="picker-view">
		    		           <picker-view-column>
		    		               <view class="item" v-for="(item,index) in oldNewLevels" :key="index">{{item}}</view>
		    		           </picker-view-column>
		    </picker-view>
		  </view>
		  <view class="section">
               <block v-for="(item, index) in images" :key="index">
				   <image style="width: 130rpx;height: 130rpx;padding: 1%;" @tap="upload" :src="item?serverUrl+item :'../../static/images/icon/upload.png'"></image>
			   </block>
		  </view> 
		  <view class="section">
		    <text>简介</text>
			 <textarea placeholder="请输入简介" maxlength="600" :value="flower.brief" @input="onBriefInput"></textarea>
		  </view>
		  <view class="section" @tap="changeClassifyVisible">
		    <text>分类</text>
			<view class="uni-title">{{classify}}</view>
			<picker-view v-if="classifyVisible" :indicator-style="indicatorStyle" :value="classifyValue" @change="onClassifyChange" class="picker-view">
					           <picker-view-column>
					               <view class="item" v-for="(item,index) in classifys" :key="index">{{item}}</view>
					           </picker-view-column>
			</picker-view>
		  </view>
		  <view class="section">
		    <text>价格</text>
		    <input placeholder="请输入价格" type="number" :value="flower.price" @input="onPriceInput"></input>
		  </view>
		  <view class="section">
		    <text>手机号码</text>
		    <input placeholder="11位手机号码" type="number" maxlength="11" @input="onMobileInput"></input>
		  </view>
		</view>
		<!-- end input列表 -->
		<!-- 功能按钮 -->
		<view class="btn-box">
		  <view class="keep btn" @tap="onSubmiti">
		    <text>发布</text>
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
				flower: {
					
				},
				images: [
					""
				],
				serverUrl: config.domain,
				classifys: [
			      '日用品',
				  '电子产品',
				  '服饰/鞋子',
				  '其他'
				],
				classify: '日用品',
				classifyValue: [0],
				types: [
					"闲置",
					"寄存"
				],
				typevalue: [0],
				typeName: '闲置',
				typeId: 0,
				oldNewLevel: '全新',
				oldNewLevels: [
					"全新",
					"几乎全新",
					"轻微使用痕迹",
					"明显使用痕迹"
				],
				oldNewLevelValue: [0],
				cid: 0,
				value: [0],
				typeVisible: false,
				oldNewLevelVisible: false,
				classifyVisible: false,
				indicatorStyle: `height: 50px;opacity: 1;`,
				placeholder: '开始输入...'
			}
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			//加载订单数字
			var ths = this; // var status = ths.data.status
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
								var data = JSON.parse(uploadFileRes.data);
								console.log(data.fileName);
								var images = this.images;
								images.push(data.fileName);
								this.setData({
								  images: images
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
			    'flower.contactWay': e.detail.value
			  });
			},
			onPriceInput: function (e) {
				this.setData({
				  'flower.price': e.detail.value
				});
			},
			onSpecificationInput: function (e) {
				this.setData({
				  'flower.specification': e.detail.value
				});
			},
			onTypeChange: function(e) {
				const val = e.detail.value
				this.setData({
				  typeName: this.types[val[0]]
				});
			},
			onOldNewLevelChange: function(e) {
				const val = e.detail.value
				this.setData({
				  oldNewLevel: this.oldNewLevels[val[0]]
				});
			},
			onClassifyChange: function(e) {
				const val = e.detail.value
				this.setData({
				  classify: this.classifys[val[0]]
				});
			},
			changeTypeVisible: function() {
				this.setData({
				  typeVisible: !this.typeVisible,
				  oldNewLevelVisible: !this.oldNewLevelVisible,
				  classifyVisible: !this.classifyVisible
				});
			},
			changeOldNewLevelVisible: function() {
				this.setData({
				  typeVisible: !this.typeVisible,
				  oldNewLevelVisible: !this.oldNewLevelVisible,
				  classifyVisible: !this.classifyVisible
				});
			},
			changeClassifyVisible: function() {
				this.setData({
				  typeVisible: !this.typeVisible,
				  oldNewLevelVisible: !this.oldNewLevelVisible,
				  classifyVisible: !this.classifyVisible
				});
			},
			onBriefInput: function(e) {
				this.setData({
				  'flower.brief': e.detail.value
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
@import "./releaseCommodities.css";
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
    #editor {
        width: 100%;
        height: 300px;
        background-color: #CCCCCC;
    }
</style>
