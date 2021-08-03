<template>
	<view class="container">
		<!-- 头部搜索区 -->
		  <view class="search-bar">
		    <view class="search-box" @tap="toSearchPage">
		      <image src="/static/images/icon/search.png" class="search-img"></image>
		      <text class="sear-input">搜索</text>
		    </view>
		  </view>
		<!-- 滚动内容区 -->
		  <view class="main">
		    <!-- 右侧内容start -->
		    <scroll-view scroll-y="true" class="rightcontent">
		    <!-- <block wx:for='{{ productList}}' wx:key=''> -->
		      <view class="cont-item">
		        <block v-for="(item, index) in messagesList" :key="index">
		          <view class="show-item" @tap="toCustomerServiceChat" :data-touserid='item.fromUserId' :data-tousername='item.fromUserName' :data-fromavatarimage='item.fromAvatarImage'>
		            <view class="more-prod-pic">
		              <image :src="item.fromAvatarImage?serverUrl + item.fromAvatarImage:'../../static/images/icon/head04.png'" class="more-pic" mode="widthFix"></image>
		            </view>
		            <view class="prod-text-right">
		              <view class="prod-text more">{{item.fromUserName}}</view>
		              <view class="cate-prod-info">{{item.contentText}}</view>
		              <view class="prod-price more">
		                <text class="big-num">{{item.sendTime}}</text>
		              </view>
		            </view>
		          </view>
		         
		        </block>
		      </view>
		     <!-- </block> -->
		    </scroll-view>
		    <!-- 右侧内容end -->
		    <!-- </block> -->
		  </view>
	</view>
</template>
<script src="../../utils/config.js"></script>
<script>
	var config = require("../../utils/config.js");
	export default {
		data() {
			return {
				messagesList: [],
				serverUrl: config.domain,
				user: {}
			}
		},
		onLoad() {
			var user = JSON.parse(uni.getStorageSync('token'));
			var ths = this;
			ths.setData({
				user: user
			});
			setInterval(function() {
				const  messagesList = uni.getStorageSync("messagesList_"+user.id);
				ths.setData({
				  messagesList: messagesList
				});
			},100)
		},
		methods: {
			/**
			 * 咨询客服
			 */
			toCustomerServiceChat: function (e) {
			      const toUserId = e.currentTarget.dataset.touserid;
			  	  const toUsername = e.currentTarget.dataset.tousername;
				  const toAvatarimage = e.currentTarget.dataset.fromavatarimage;
			        uni.navigateTo({
			          url: '/pages/CustomerServiceChat/CustomerServiceChat?toUserId='+toUserId+'&toUsername='+toUsername+"&toAvatarimage="+toAvatarimage
			  });
			}
		}
	}
</script>

<style>
@import "./messageList.css";
</style>
