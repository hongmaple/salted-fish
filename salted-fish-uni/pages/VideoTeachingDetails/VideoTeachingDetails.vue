<template>
    <view>
        <view class="uni-padding-wrap uni-common-mt">
            <view>
                <video style="width: 100%; " id="myVideo" :src="serverUrl + item.url"
                    @error="videoErrorCallback" :danmu-list="danmuList" enable-danmu danmu-btn controls></video>
            </view>
			<view class="prod-text-right">
			  <view class="prod-text more">{{item.title}}</view>
			  <view class="prod-price more" v-if="item.isCharge">
			    <text class="symbol">￥</text> <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text><text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text> 
			  </view>
			  <view class="prod-price more" v-else>
			    <text class="symbol">￥</text> <text class="big-num">免费</text> 
			  </view>
			</view>
			<!-- #ifndef MP-ALIPAY -->
			<view class="uni-list">
			    <view class="uni-list-cell">
			        <view class="uni-list-cell-db">
			            <input v-model="danmuValue" class="uni-input" type="text" placeholder="在此处输入弹幕内容" />
						<view class="footer-box" @tap="sendDanmu">
						  发送弹幕
						</view>
			        </view>
			    </view>
			</view>
            <!-- <view class="uni-btn-v">
                <button @click="sendDanmu" class="page-body-button">发送弹幕</button>
            </view> -->
            <!-- #endif -->
        </view>
    </view>
</template>
<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>
<script>
	var config = require("../../utils/config.js");
	export default {
		 data() {
		        return {
		            src: '',
		            danmuList: [{
		                    text: '找工作就上银领人才网',
		                    color: '#ff0000',
		                    time: 1
		                },
		                {
		                    text: 'http://www.yinlinkrc.com/',
		                    color: '#ff00ff',
		                    time: 2
		                },
						{
						    text: '想学编程加qq2496155694',
						    color: '#ff00ff',
						    time: 3
						},
						{
						    text: '鸿枫真优秀',
						    color: '#ff00ff',
						    time: 4
						}
		            ],
		            danmuValue: '',
					item: {
						
					},
					serverUrl: config.domain,
		        }
		    },
		    onReady: function(res) {
		        // #ifndef MP-ALIPAY
		        this.videoContext = uni.createVideoContext('myVideo')
		        // #endif
		    },
			onLoad: function (options) {
				this.setData({
				  item: JSON.parse(options.item)
				});
				console.log(this.item);
			},
		    methods: {
		        sendDanmu: function() {
		            this.videoContext.sendDanmu({
		                text: this.danmuValue,
		                color: this.getRandomColor()
		            });
		            this.danmuValue = '';
		        },
		        videoErrorCallback: function(e) {
		            uni.showModal({
		                content: e.target.errMsg,
		                showCancel: false
		            })
		        },
		        getRandomColor: function() {
		            const rgb = []
		            for (let i = 0; i < 3; ++i) {
		                let color = Math.floor(Math.random() * 256).toString(16)
		                color = color.length == 1 ? '0' + color : color
		                rgb.push(color)
		            }
		            return '#' + rgb.join('')
		        }
		    }
	}
</script>

<style>
.prod-text-right {
  margin-left: 20rpx;
  width: 75%;
}

.prod-text-right .cate-prod-info {
  font-size: 22rpx;
  color: #999;
  margin: 10rpx 0 20rpx 0;
}

.prod-text-right .prod-text.more {
  margin: 0;
  font-size: 28rpx;
  display: -webkit-box;
  word-break: break-all;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  color: #000;
}

.prod-text-right .prod-price.more {
  padding-top: 10rpx;
  font-size: 28rpx;
  color: #eb2444;
  font-family: arial;
}
.uni-input{
	float: left;
	width: 560rpx;
	font-size: 28rpx;
    border-radius: 50rpx;
	border: 2rpx solid #777;
}
.footer-box {
  padding-left: 15rpx;
  width: 140rpx;
  background: #eb2444;
  text-align: center;
  line-height: 40rpx;
  color: #fff;
  float: right;
  border-radius: 50rpx;
}
.search-bar {
  width: 100%;
  position: fixed;
  top: 0;
  left: 0;
  color: #777;
  background: #fff;
  box-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.07);
  z-index: 3;
  padding: 20rpx 0;
}

.search-bar .arrow {
  width: 20rpx;
  height: 20rpx;
  border-bottom: 2rpx solid #777;
  border-left: 2rpx solid #777;
  transform: rotate(45deg);
  position: absolute;
  left: 30rpx;
  top: 41rpx;
}

.search-bar .search-box {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60rpx;
  background: #f7f7f7;
  z-index: 999;
  width: 92%;
  border-radius: 50rpx;
  text-align: center;
  margin: auto;
}

.sear-input {
  font-size: 28rpx;
}

.search-bar .search-hint {
  font-size: 28rpx;
  position: absolute;
  right: 30rpx;
  top: 32rpx;
}
.uni-list {
	padding-top: 10rpx;
}
</style>
