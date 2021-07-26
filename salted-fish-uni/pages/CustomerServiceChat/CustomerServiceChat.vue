<template>
	<view class="container">
		<view style="text-align: center;padding-top: 40rpx;font-size: 40rpx;">{{toUsername}}</view>
		<scroll-view scroll-y="true" style="overflow:scroll;">
			<block v-for="(item, index) in messagesList" :key="index">
				<view :class="item.fromUserId==0?'content-right':'content-left'">
					<view style="width: 300rpx;">
						<view :class="item.fromUserId==0?'text-bs-right':'text-bs-left'" v-html="item.contentText"></view>
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
	export default {
		data() {
			return {
				socket: {},
				messagesList: [],
				danmuValue: '',
				a: '',
				toUserId: 'admin1',
				toUsername: '系统客服'
			}
		},
		onLoad: function(options) {
			console.log(options)
			var ths = this;
			var user = JSON.parse(uni.getStorageSync('token'));
			var socket = uni.getStorageSync('socket');
			ths.setData({
				socket: socket
			});
			setInterval(function(){
				ths.setData({
					toUserId: options.toUserId==null? this.toUserId:options.toUserId,
					toUsername: options.username==null? this.toUsername:options.username,
				});
			},100);
		},
		methods: {
			sendMessage: function() {
				if (typeof(WebSocket) == "undefined") {
					console.log("您的浏览器不支持WebSocket");
				} else {
					console.log("您的浏览器支持WebSocket");
					var sendMessages = {
						contentText:this.danmuValue,
						fromUserId: 0,
						sendTime: new Date(),
						toUserId: this.toUserId
					}
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
					this.socket.send(JSON.stringify(sendMessages));
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
		padding: 22rpx 0;
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
		padding-right: 100rpx;
		width: 100%;
		margin-top: 20px;
		padding-right: 440rpx;
		padding-left: 15rpx;
	}

	.content-right {
		display: block;
		float: left;
		padding-left: 100rpx;
		padding: 10rpx 20rpx;
		width: 100%;
		margin-top: 20px;
		padding-left: 440rpx;
	}
	.text-bs-left{
			background-color: #fbbd08;
			border-radius: 10rpx;
			border: 2rpx solid #FFFFFF;
			width: auto;
			height: auto;
			padding: 10rpx 20rpx;
	}
	.text-bs-right{
			background-color: #0081ff;
			border-radius: 10rpx;
			border: 2rpx solid #FFFFFF;
			width: auto;
			height: auto;
			padding: 10rpx 20rpx;
	}
</style>
