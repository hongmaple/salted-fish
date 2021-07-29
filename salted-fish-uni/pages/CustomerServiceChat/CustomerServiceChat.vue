<template>
	<view class="container">
		<view style="position: fixed;top: 0rpx;text-align: center;z-index: 99;background-color: #00A0EA;width: 100%;">
			<view style="text-align: center;padding-top: 20rpx;font-size: 40rpx;padding-bottom: 20rpx;">{{toUsername}}</view>
		</view>
		<scroll-view scroll-y="true" style="overflow:scroll;padding-bottom: 260rpx;padding-top: 70rpx;">
			<block v-for="(item, index) in messagesList" :key="index">
				<view :class="item.fromUserId==fromUserId?'content-right':'content-left'">
					<view :class="item.fromUserId==fromUserId?'fromAvatarImage-right':'fromAvatarImage-left'">
						<image :src="serverUrl + item.fromAvatarImage" class="more-pic" mode="widthFix"></image>
					</view>
					<view :class="item.fromUserId==fromUserId?'nickname_content_right':'nickname_content_left'">
						<view :class="item.fromUserId==fromUserId?'nickname_right':'nickname_left'">{{item.fromUserName}}</view></br>
						<view :class="item.fromUserId==fromUserId?'text-bs-right':'text-bs-left'" v-html="item.contentText"></view>
					</view>
				</view>
			</block>
		</scroll-view>
		<view class="order-detail-footer">
			<text style="display: none;" v-model="a"></text>
			<textarea v-model="danmuValue" class="uni-input" type="text" placeholder="输入内容" />
			<view class="footer-box">
				<text class="buy-again" @tap="sendMessage()">发送</text>
			</view>
		</view>
	</view>
</template>
<script>
	var config = require('../../utils/config.js');
	var websoket = require('../../utils/websoket.js')
	export default {
		data() {
			return {
				soket: {},
				messagesList: [],
				danmuValue: '',
				toUserId: 'admin1',
				toUsername: '系统客服',
				toAvatarimage: '../../static/images/icon/head04.png',
				user: {},
				fromUserId: 0,
				a: 0,
				serverUrl: config.domain
			}
		},
		onLoad: function(options) {
			var ths = this;
			var user = JSON.parse(uni.getStorageSync('token'));
			var globalData = getApp().globalData;
			var soket = globalData.soket
			if(soket==null) {
				soket = websoket.openSocket(user.id);
			}
			var fromUserMessagesList = uni.getStorageSync(options.toUserId); 
			ths.setData({
				soket: soket,
				messagesList: fromUserMessagesList,
				fromUserId: user.id,
				user: user
			});
			setInterval(function(){
				var fromUserMessagesList = uni.getStorageSync(options.toUserId); 
				ths.setData({
					messagesList: fromUserMessagesList
				});
				ths.setData({
					toUserId: options.toUserId==null? this.toUserId:options.toUserId,
					toUsername: options.toUsername==null? this.toUsername:options.toUsername,
					toAvatarimage: options.toAvatarimage==null? this.toAvatarimage:options.toAvatarimage
				});
			},100);
		},
		methods: {
			sendMessage: function() {
				if (typeof(WebSocket) == "undefined") {
					console.log("您的浏览器不支持WebSocket");
				} else {
					console.log("您的浏览器支持WebSocket");
					var date = new Date();
					var sendMessages = {
						contentText: this.danmuValue,
						fromAvatarImage: this.user.avatarImage,
						fromUserName: this.user.username,
						fromUserId: this.fromUserId,
						sendTime: date,
						toUserId: this.toUserId,
						toUsername: this.toUsername,
						toAvatarImage: this.toAvatarImage
					}
					console.log(sendMessages);
					var messages = [];
					messages.push(sendMessages);
					var list = this.messagesList;
					if (this.messagesList.length>0) {
						Array.prototype.push.apply(list, messages);
					} else {
						list = messages;
					}
					this.setData({
						messagesList: list,
						danmuValue: ''
					});
					this.soket.send(JSON.stringify(sendMessages));
					uni.setStorageSync(this.toUserId,list);
					var messagess = uni.getStorageSync("messagesList_"+this.toUserId);
					var newMessagesList = [];
					var fromMessages = {
						fromAvatarImage: this.toAvatarimage,
						fromUserId: this.toUserId,
						fromUserName: this.toUsername,
						contentText: this.danmuValue,
						sendTime: date
					}
					if(messagess) {
						for (var i=0;i<messagess.length;i++) {
							var fromUser = messagess[i];
							if(fromUser.fromUserId!==this.toUserId) {
								newMessagesList.push(fromMessages);
							}else {
								messagess[i] = fromMessages; 
							}
						}
						Array.prototype.push.apply(messagess,newMessagesList);
					}else {
						newMessagesList.push(fromMessages);
						messagess = newMessagesList
					}
					uni.setStorageSync("messagesList_"+this.toUserId,messagess);
				}
			}
		}
	}
</script>

<style>
	/* 底部栏 */
	.order-detail-footer {
		position: fixed;
		bottom: 0;
		width: 100%;
		max-width: 750rpx;
		background: #fff;
		margin: auto;
		display: -webkit-flex;
		display: -webkit-box;
		display: -moz-box;
		display: -ms-flexbox;
		display: flex;
		padding: 50rpx 0;
		font-size: 26rpx;
		box-shadow: 0 -1px 3px rgba(0, 0, 0, 0.05);
	}

	.uni-input {
		float: left;
		width: 580rpx;
		height: 80rpx;
		font-size: 28rpx;
		border-radius: 10rpx;
		border: 2rpx solid #777;
		margin-left: 22rpx;
		padding-left: 20rpx;
		padding-top: 10rpx;
	}

	.buy-again {
		font-size: 26rpx;
		color: #fff;
		background: #eb2444;
		border-radius: 50rpx;
		float: right;
		margin-left: 22rpx;
		margin-top: 4rpx;
		padding: 10rpx 20rpx;
	}

	.content-left {
		display: block;
		float: left;
		width: 100%;
		margin-top: 20px;
		padding-left: 30rpx;
	}

	.content-right {
		display: block;
		float: right;
		width: 100%;
		margin-top: 20px;
		padding-right: 30rpx;
	}
	.text-bs-left{
			background-color: #fbbd08;
			border-radius: 10rpx;
			border: 2rpx solid #FFFFFF;
			width: auto;
			height: auto;
			padding: 10rpx 20rpx;
			float: left;
			margin-top: 10rpx;
	}
	.text-bs-right{
			background-color: #0081ff;
			border-radius: 10rpx;
			border: 2rpx solid #FFFFFF;
			width: auto;
			height: auto;
			padding: 10rpx 20rpx;
			float: right;
			margin-top: 10rpx;
	}
	.fromAvatarImage-left {
		float: left;
	}
	.fromAvatarImage-right {
		float: right;
	}   
	.nickname_content_left {
		float: left;
		margin-left: 10rpx;
		width: 70%;
	}
	.nickname_content_right {
		float: right;
		margin-right: 10rpx;
		width: 70%;
	}
	.nickname_left {
		float: left;
	}
	.nickname_right {
		float: right;
	}
	.more-pic {
	  max-width: 100rpx;
	  max-height: 100rpx;
	  border-radius: 25rpx;
	  vertical-align: middle;
	}
</style>
