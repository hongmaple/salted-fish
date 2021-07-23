<template>
<!--pages/order-detail/order-detail.wxml-->

<view class="container">

  <view class="order-detail">
    <view class="delivery-addr" v-if="OrderDetailsVo">
      <view class="user-info">
        <text class="item">{{OrderDetailsVo.receiver}}</text>
        <text class="item">{{OrderDetailsVo.mobile}}</text>
      </view>
      <view class="addr">{{OrderDetailsVo.receiverState}}{{OrderDetailsVo.receiverCity}}{{OrderDetailsVo.receiverDistrict}}{{OrderDetailsVo.receiverAddress}}</view>
    </view>



    <!-- 商品信息 -->
    <view class="prod-item" v-if="OrderDetailsVo.orderDetails">
      <view style="padding: 10rpx 0 10rpx 30rpx;">{{sellerName}}</view>
      <block v-for="(item, index) in OrderDetailsVo.orderDetails" :key="index">
        <view class="item-cont" @tap="toProdPage" :data-prodid="item.skuId">
          <view class="prod-pic">
            <image :src="serverUrl+item.image"></image>
          </view>
          <view class="prod-info">
            <view class="prodname">
              {{item.title}}
            </view>
            <view class="prod-info-cont">
              <text class="number">数量：{{item.num}}</text>
            </view>
            <view class="price-nums clearfix">
              <text class="prodprice"><text class="symbol">￥</text>
              <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text>
              <text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text></text>
            </view>
          </view>
        </view>
      </block>
    </view>

    <!-- 订单信息 -->
    <view class="order-msg">
      <view class="msg-item">
        <view class="item">
          <text class="item-tit">订单编号：</text>
          <text class="item-txt">{{orderNumber}}</text>
          <text class="copy-btn" @tap="copyBtn">复制</text>
        </view>
        <view class="item">
          <text class="item-tit">下单时间：</text>
          <text class="item-txt">{{createTime}}</text>
        </view>
      </view>
      <view class="msg-item">
        <view class="item">
          <text class="item-tit">支付方式：</text>
          <text class="item-txt">微信支付</text>
        </view>
        <view class="item">
          <text class="item-tit">配送方式：</text>
          <text class="item-txt">普通配送</text>
        </view>
        <view class="item">
          <text class="item-tit">订单备注：</text>
          <text class="item-txt">{{remarks}}</text>
        </view>
      </view>
    </view>

    <view class="order-msg">
      <view class="msg-item">
        <view class="item">
          <view class="item-tit">订单总额：</view>
          <view class="item-txt price">
            <text class="symbol">￥</text>
            <text class="big-num">{{wxs.parsePrice(productTotalAmount)[0]}}</text>
            <text class="small-num">.{{wxs.parsePrice(productTotalAmount)[1]}}</text>
          </view>
        </view>
        <view class="item">
          <view class="item-tit">运费：</view>
          <view class="item-txt price">
            <text class="symbol">￥</text>
            <text class="big-num">{{wxs.parsePrice(transfee)[0]}}</text>
            <text class="small-num">.{{wxs.parsePrice(transfee)[1]}}</text>
          </view>
        </view>
        <view class="item payment">
          <view class="item-txt price">
            实付款：
            <text class="symbol">￥</text>
            <text class="big-num">{{wxs.parsePrice(actualTotal)[0]}}</text>
            <text class="small-num">.{{wxs.parsePrice(actualTotal)[1]}}</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 底部栏 -->
    <view class="order-detail-footer">
      <text class="dele-order" v-if="status==4||status==5">删除订单</text>
      <view class="footer-box">
		 <text v-if="status==1" class="buy-again" @tap="cancelOrder" hover-class="none">取消订单</text>
		 <text v-if="status==1" class="buy-again" hover-class="none">再次购买</text>
		 <text v-if="status==1" class="buy-again" @tap="onPayAgain" hover-class="none">付款</text>
		 <text v-if="status==3 || status==5" class="buy-again" @tap="toDeliveryPage" hover-class="none">查看物流</text>
		 <text v-if="status==3" class="buy-again" @tap="onConfirmReceive" hover-class="none">确认收货</text>
         <text class="apply-service" @tap="toCustomerServiceChat" :data-toUserId='sellerId' :data-username='sellerName'>联系客服</text>
      </view>
    </view>

  </view>
</view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
// pages/order-detail/order-detail.js
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");

export default {
  data() {
    return {
      orderItemDtos: [],
      remarks: "",
      actualTotal: 0,
      orderNumber: "",
      createTime: "",
      status: 0,
      productTotalAmount: '',
      transfee: '',
      actualTotal: '',
      prodid: '',
	  OrderDetailsVo: {},
	  serverUrl: config.domain,
	  sellerName: "系统平台",
	  sellerId: "admin1"
    };
  },
  components: {},
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.loadOrderDetail(options.orderNum);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {},

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {},

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {},

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {},

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {},

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},
  methods: {
    //跳转商品详情页
    toProdPage: function (e) {
      var prodid = e.currentTarget.dataset.prodid;
      uni.navigateTo({
        url: '/pages/prod/prod?prodid=' + prodid
      });
    },
	/**
	 * 咨询客服
	 */
	toCustomerServiceChat: function (e) {
	      const toUserId = e.currentTarget.dataset.toUserId
	  	  const username = e.currentTarget.dataset.username
	        uni.navigateTo({
	          url: '/pages/CustomerServiceChat/CustomerServiceChat?toUserId='+toUserId+'&username='+username
	  });
	},

    /**
     * 加载订单数据
     */
    loadOrderDetail: function (orderNum) {
      uni.showLoading(); //加载订单详情

      var params = {
        url: "/order/"+orderNum,
        method: "GET",
		needToken: true,
        callBack: res => {
          this.setData({
            orderNumber: orderNum,
            actualTotal: res.data.actualPay,
            remarks: res.data.remarks,
            createTime: res.data.createTime,
            status: res.data.status,
            productTotalAmount: res.data.totalPay,
            transfee: 10,
			OrderDetailsVo: res.data,
			sellerName: res.data.sellerName,
			sellerId: res.data.backgroundAgentId==0? res.data.sellerId:res.data.backgroundAgentId
          });
          uni.hideLoading();
        }
      };
      http.request(params);
    },
    // 一键复制事件
    copyBtn: function (e) {
      var ths = this;
      uni.setClipboardData({
        //准备复制的数据
        data: ths.orderNumber,
        success: function (res) {
          uni.showToast({
            title: '复制成功'
          });
        }
      });
    },
	onPayAgain: function () {
		uni.showLoading({
		  mask: true
		});
		 var ths = this;
		var params = {
		  url: "/order/"+ths.orderNumber+"/2",
		  method: "PUT",
		  needToken: true,
		  callBack: res => {
				console.log("res",res)
				uni.hideLoading();
				if(res){
					uni.showToast({
						title: "模拟支付成功",
						icon:"none"
					})
					ths.loadOrderDetail(ths.sts, 1);
				}else{
					uni.showToast({
						title: "支付失败！",
						icon:"none"
					})
				}
		  }
		};
		http.request(params);
	},
	/**
	 * 取消订单
	 */
	cancelOrder: function (e) {
	  var ths = this;
	  uni.showModal({
	    title: '',
	    content: '要取消此订单？',
	    confirmColor: "#3e62ad",
	    cancelColor: "#3e62ad",
	    cancelText: '否',
	    confirmText: '是',
	    success(res) {
	      if (res.confirm) {
	        uni.showLoading({
	          mask: true
	        });
	        var params = {
	          url: "/order/"+ths.orderNumber+"/5",
	          method: "PUT",
			   needToken: true,
	          callBack: function (res) {
	            //console.log(res);
	            ths.loadOrderDetail(ths.sts, 1);
	            uni.hideLoading();
	          }
	        };
	        http.request(params);
	      } else if (res.cancel) {//console.log('用户点击取消')
	      }
	    }
	
	  });
	},
	/**
	 * 确认收货
	 */
	onConfirmReceive: function (e) {
	  var ths = this;
	  uni.showModal({
	    title: '',
	    content: '我已收到货？',
	    confirmColor: "#eb2444",
	    success(res) {
	      if (res.confirm) {
	        uni.showLoading({
	          mask: true
	        });
	        var params = {
	          url: "/order/"+ths.orderNumber+"/4",
	          method: "PUT",
			  needToken: true,
	          data: {},
	          callBack: function (res) {
	            //console.log(res);
	            uni.navigateBack({  
	                delta: 1 
	            });  
	            uni.hideLoading();
	          }
	        };
	        http.request(params);
	      } else if (res.cancel) {//console.log('用户点击取消')
	      }
	    }
	
	  });
	},
	/**
	 * 查看物流
	 */
	toDeliveryPage: function (e) {
	  uni.navigateTo({
	    url: '/pages/express-delivery/express-delivery?orderNum=' + e.currentTarget.dataset.ordernum
	  });
	},
  }
};
</script>
<style>
@import "./order-detail.css";
</style>