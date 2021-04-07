<template>
  <div class="nofind">
    <el-dialog
      :title="dialong.title"
      type="primary"
      size="small"
      :close-on-press-escape="false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :visible.sync="dialong.show"
    >
    <div style="width=700px;hight: 600px;overflow:scroll;">
      <div v-for="(item, index) in messagesList" :key="index">
          <div :class="item.fromUserId==0?'content-right':'content-left'">
            <div style="width: 300px;">
              <div :class="item.fromUserId==0?'text-bs-right':'text-bs-left'" v-html="item.contentText"></div>
            </div>
          </div>
      </div>
    </div>
      <div slot="footer" class="dialog-footer">
          <p style="display: none;" v-text="a"></p>
          <el-input type="textarea" v-model="danmuValue"></el-input>
          <el-button type="primary" @click="sendMessage('formdoalog')">发送</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: "liaotian",
  data() {
    return {
        socket: {},
	   	  messagesList: [],
		    danmuValue: '',
        a: 0
    };
  },
  props: {
    dialong: Object,
    id: 0
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
            this.socket = socket;
            var ths = this;
			//打开事件
            socket.onopen = function() {
                console.log("websocket已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            socket.onmessage = function(msg) {
                        //发现消息进入    开始处理前端触发逻辑
                        console.log(msg);
                        var message = JSON.parse(msg.data);
                        if ("连接成功" === message) {
                            return;
                        }
                        var messages = [];
                        messages.push(message);
                        var list = ths.messagesList;
                        if (typeof(ths.messagesList) != "undefined"&&ths.messagesList.length>0) {
                            Array.prototype.push.apply(list, messages);
                        } else {
                            list = messages;
                        }
                        ths.messagesList=list;
                        console.log(ths.messagesList);
            };
			//关闭事件
			socket.onclose = function() {
				console.log("websocket已关闭");
			};
			//发生了错误事件
			socket.onerror = function() {
         Message.error("连接发生了错误");
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
                        toUserId: this.id
                }
				var messages = [];
				messages.push(sendMessages);
                var list = this.messagesList;
                if (typeof(this.messagesList) != "undefined"&&this.messagesList.length>0) {
                      Array.prototype.push.apply(list, messages);
                } else {
                      list = messages;
                }
                this.messagesList=list;
                console.log(this.messagesList)
                this.danmuValue='';
			    this.socket.send(JSON.stringify(sendMessages));
			}
		}
  },
  created() {
      var user = JSON.parse(localStorage.getItem("eleToken"));
      this.openSocket("admin"+user.id);
      var i = 0;
			setInterval(function(){
        console.log(i);
				i++;
			  this.a=i;
			},100);
  }
};
</script>
<style scoped>
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
      display: block;
	}
	.text-bs-right{
			background-color: #0081ff;
			border-radius: 10rpx;
			border: 2rpx solid #FFFFFF;
			width: auto;
			height: auto;
			padding: 10rpx 20rpx;
      display: block;
	}
</style>
