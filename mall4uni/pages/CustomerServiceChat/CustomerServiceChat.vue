<template>
	<view class="container">
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
	export default {
		data() {
			return {
				socket: {},
				messagesList: [],
				danmuValue: '',
				a: ''
			}
		},
		onLoad: function(options) {
			var ths = this;
			var user = JSON.parse(uni.getStorageSync('token'));
			ths.openSocket(user.id);
			var i = 0;
			setInterval(function(){
				i++;
				ths.setData({
					a: i
				});
			},100);
		},
		methods: {
			openSocket: function(id) {
				if (typeof(WebSocket) == "undefined") {
					console.log("您的浏览器不支持WebSocket");
				} else {
					console.log("您的浏览器支持WebSocket");
					//实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
					//等同于socket = new WebSocket("ws://localhost:8888/xxxx/im/25");
					//var socketUrl="${request.contextPath}/im/"+$("#userId").val();
					var socketUrl = "http://localhost:9001/ws/" + id;
					socketUrl = socketUrl.replace("https", "ws").replace("http", "ws");
					var socket = new WebSocket(socketUrl);
					var ths = this;
					this.setData({
						socket: socket
					});
					//打开事件
					socket.onopen = function() {
						console.log("websocket已打开");
						//socket.send("这是来自客户端的消息" + location.href + new Date());
					};
					//获得消息事件
					socket.onmessage = function(msg) {
						//发现消息进入    开始处理前端触发逻辑
						var message = JSON.parse(msg.data);
						if ("连接成功" === message) {
							return;
						}
						var messagesList = [];
						messagesList.push(message);
						var list = ths.messagesList;
						if (ths.messagesList) {
							Array.prototype.push.apply(list, messagesList);
						} else {
							list = messagesList;
						}
					    ths.setData({
					    	messagesList: list
					    });
					};
					//关闭事件
					socket.onclose = function() {
						console.log("websocket已关闭");
					};
					//发生了错误事件
					socket.onerror = function() {
						uni.showToast({
							title: "连接发生了错误！",
							icon: "none"
						})
					}
				}
			},
			sendMessage: function() {
				if (typeof(WebSocket) == "undefined") {
					console.log("您的浏览器不支持WebSocket");
				} else {
					console.log("您的浏览器支持WebSocket");
					var sendMessages = {
						contentText:this.danmuValue,
						fromUserId: 0,
						sendTime: new Date(),
						toUserId: "admin1"
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
