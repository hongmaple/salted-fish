<template>
	<view class="container">
		<!--input列表 -->
		<view class="input-box">
		  <view class="section">
		    <text>标题</text>
		    <input placeholder="请输入标题" type="text" maxlength="11" :value="flower.title" @input="onTitleInput"></input>
		  </view>
		  <view class="section" @tap="changeTypeVisible">
		    <text>类型</text>
		  	<view class="uni-title">{{typeName}}</view>
		  	<picker-view v-if="typeVisible" :indicator-style="indicatorStyle" :value="typevalue" @change="onTypeChange" class="picker-view">
		  				        <picker-view-column>
		  				            <view class="item" v-for="(item,index) in types" :key="index">{{item.name}}</view>
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
		  <view class="section" @tap="changeClassifyVisible">
		    <text>分类</text>
			<view class="uni-title">{{classify}}</view>
			<picker-view v-if="classifyVisible" :indicator-style="indicatorStyle" :value="classifyValue" @change="onClassifyChange" class="picker-view">
					           <picker-view-column>
					               <view class="item" v-for="(item,index) in classifys" :key="index">{{item.name}}</view>
					           </picker-view-column>
			</picker-view>
		  </view>
		  <view class="section">
		    <text>价格</text>
		    <input placeholder="请输入价格" type="number" :value="flower.price" @input="onPriceInput"></input>
		  </view>
		  <view class="section" v-if="flower.type==1">
		    <text>手机号码</text>
		    <input placeholder="11位手机号码" type="number" maxlength="11" :value="flower.contactWay"  @input="onMobileInput"></input>
		  </view>
		  <view class="section" @tap="changeDepositingVisible" v-if="flower.type==1">
		    <text>寄存时间</text>
            <view class="uni-title">{{flower.depositingTime}}</view>
			<picker-view v-if="depositingTimeVisible" :indicator-style="indicatorStyle" :value="value" @change="bindDepositingTimeChange" class="picker-view">
			             <picker-view-column>
			                 <view class="item" v-for="(item,index) in years" :key="index">{{item}}年</view>
			             </picker-view-column>
			             <picker-view-column>
			                 <view class="item" v-for="(item,index) in months" :key="index">{{item}}月</view>
			             </picker-view-column>
			             <picker-view-column>
			                 <view class="item" v-for="(item,index) in days" :key="index">{{item}}日</view>
			             </picker-view-column>
			 </picker-view>
		  </view>
		  <view class="section">
		  	<textarea placeholder="请输入简介" maxlength="600" :value="flower.brief" @input="onBriefInput"></textarea>
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
			var currentTime = '';
			const date = new Date()
			            const years = []
			            const year = date.getFullYear()
			            const months = []
			            var month = date.getMonth() + 1;
			            const days = []
			            var day = '';
						if(date.getDate()<10) {
							day='0' + date.getDate();
						}else {
							day=date.getDate();
						}
						if(month<10) {
							month='0' + month;
						}
						currentTime = year+"-"+month+"-"+day;
			            for (let i = 1990; i <= date.getFullYear(); i++) {
			                years.push(i)
			            }
			            for (let i = 1; i <= 12; i++) {
							if(i<10) {
								var month = '0'+i;
								months.push(month)
							}else {
								months.push(i)
							}
			            }
			            for (let i = 1; i <= 31; i++) {
							if(i<10) {
								var day = '0'+i;
								days.push(day);
							}else {
								days.push(i)
							}
			            }
			return {
				flower: {
					depositingTime: currentTime,
					price: 0
				},
				images: [
					""
				],
				serverUrl: config.domain,
				classifys: [
			     
				],
				classify: '',
				classifyValue: [0],
				types: [
					{
					name: "闲置",
					id: 0	
					},
					{
					name: "寄存",
					id: 1	
					}
				],
				typevalue: [0],
				typeName: '闲置',
				oldNewLevel: '全新',
				oldNewLevels: [
					"全新",
					"几乎全新",
					"轻微使用痕迹",
					"明显使用痕迹"
				],
				oldNewLevelValue: [0],
				value: [0],
				typeVisible: false,
				oldNewLevelVisible: false,
				classifyVisible: false,
				depositingTimeVisible: false,
				indicatorStyle: `height: 50px;opacity: 1;`,
				placeholder: '开始输入...',
				years,
				months,
				days,
				value: [9999, month - 1, day - 1],
			}
		},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			//加载订单数字
			var ths = this; 
			var params = {
			  url: "/category",
			  method: "GET",
			  needToken: true,
			  callBack: function (res) {
			    uni.hideLoading();
				ths.setData({
				  classifys: res.data,
				  classify: res.data[0].name,
				  "flower.cid": res.data[0].id
				});
			  }
			};
			http.request(params);
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
			onTitleInput: function (e) {
				this.setData({
				  'flower.title': e.detail.value
				});
			},
			onTypeChange: function(e) {
				const val = e.detail.value
				this.setData({
				  typeName: this.types[val[0]].name,
				  'flower.type': this.types[val[0]].id
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
				  classify: this.classifys[val[0]].name,
				  "flower.cid": this.classifys[val[0]].id
				});
			},
			changeTypeVisible: function() {
				this.setData({
				  typeVisible: !this.typeVisible,
				  oldNewLevelVisible: false,
				  classifyVisible: false,
				  depositingTimeVisible: false
				});
			},
			changeOldNewLevelVisible: function() {
				this.setData({
				  typeVisible: false,
				  oldNewLevelVisible: !this.oldNewLevelVisible,
				  classifyVisible: false,
				  depositingTimeVisible: false
				});
			},
			changeClassifyVisible: function() {
				this.setData({
				  typeVisible: false,
				  oldNewLevelVisible: false,
				  classifyVisible: !this.classifyVisible,
				  depositingTimeVisible: false
				});
			},
			changeDepositingVisible: function() {
				this.setData({
				  typeVisible: false,
				  oldNewLevelVisible: false,
				  classifyVisible: false,
				  depositingTimeVisible: !this.depositingTimeVisible
				});
			},
			onBriefInput: function(e) {
				this.setData({
				  'flower.brief': e.detail.value
				});
			},
			bindDepositingTimeChange: function(e) {
				const val = e.detail.value
				var year = this.years[val[0]]
				var month = this.months[val[1]]
				var day = this.days[val[2]]
				this.flower.depositingTime = year+"-"+month+"-"+day;
			},
			onSubmiti: function () {
				var images = this.images;
				var imagesList = images.join(',');
				console.log(imagesList);
				if(!this.flower.title||typeof(this.flower.title) == "undefined") {
					uni.showToast({
						title: "标题不能为空",
						icon: 'none',
					});
					return;
				}
				if(!this.flower.specification||typeof(this.flower.specification) == "undefined") {
					uni.showToast({
						title: "规格不能为空",
						icon: 'none',
					});
					return;
				}
				if(!imagesList||typeof(imagesList) == "undefined") {
					uni.showToast({
						title: "图片不能为空",
						icon: 'none',
					});
					return;
				}
				if(!this.flower.price||typeof(this.flower.price) == "undefined") {
					uni.showToast({
						title: "价格不能为空",
						icon: 'none',
					});
					return;
				}
				if(this.flower.price==0) {
					uni.showToast({
						title: "价格需大于0",
						icon: 'none',
					});
					return;
				}
				
				if(this.flower.type==1) {
					if(!this.flower.contactWay||typeof(this.flower.contactWay) == "undefined") {
						uni.showToast({
							title: "联系方式不能为空",
							icon: 'none',
						});
						return;
					}
				}
				
				var params = {
				  url: "/flower",
				  method: "POST",
				  needToken: true,
				  data: {
					    cid: this.flower.cid,
					    title: this.flower.title,
					    images: this.images[1],
						imagesList: imagesList,
			            price: this.flower.price,
					    specification: this.flower.specification,
					    oldNewLevel: this.oldNewLevel,
					    brief: this.flower.brief,
                        type: this.flower.type? this.flower.type:0,
	                    contactWay: this.flower.contactWay,
					    depositingTime: this.flower.depositingTime
				  },
				  callBack: function (res) {
				    uni.hideLoading();
				    uni.showToast({
				    	title: "发布成功",
				    	icon: 'none',
				    });
					uni.navigateBack({
						delta: 1
					});
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
