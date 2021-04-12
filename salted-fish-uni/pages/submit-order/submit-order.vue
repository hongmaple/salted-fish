<template>
<view>
<!--pages/submit-order/submit-order.wxml-->
<view class="container">
  <view class="submit-order">
    <!-- 收货地址 -->
    <view class="delivery-addr " @tap="toAddrListPage">
      <view class="addr-bg " v-if="!userAddr">
        <view class="add-addr">
          <view class="plus-sign-img">
            <image src="/static/images/icon/plus-sign.png"></image>
          </view>
          <text>新增收货地址</text>
        </view>
        <view class="arrow empty"></view>
      </view>
      <view class="addr-bg whole" v-if="userAddr">
        <view class="addr-icon">
          <image src="/static/images/icon/addr.png"></image>
        </view>
        <view class="user-info">
          <text class="item">{{userAddr.consignee}}</text>
          <text class="item">{{userAddr.consigneePhone}}</text>
        </view>
        <view class="addr">{{userAddr.province}}{{userAddr.city}}{{userAddr.district}}{{userAddr.detail}}</view>
        <view class="arrow"></view>
      </view>
    </view>

    <!-- 商品详情 -->
	<block v-for="(orderItem, index0) in orderItems" :key="index0">
	 <h3>{{orderItem.user.username}}</h3>
		<view class="prod-item">
		  <block v-for="(item, index) in orderItem.prods" :key="index">
			<view class="item-cont" @tap="toOrderDetailPage" :data-ordernum="item.num">
			  <view class="prod-pic">
				<image :src="serverUrl+item.image"></image>
			  </view>
			  <view class="prod-info">
				<view class="prodname">
				  {{item.title}}
				</view>
				<view class="price-nums">
				  <text class="prodprice"><text class="symbol">￥</text>
				  <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text>
				  <text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text></text>
				  <text class="prodcount">x{{item.num}}</text>
				</view>
			  </view>
			</view>
		  </block>
		  <view class="total-num">
			<text class="prodcount">共{{totalCount}}件商品</text>
			<view class="prodprice">合计：
			  <text class="symbol">￥</text>
			  <text class="big-num">{{wxs.parsePrice(total)[0]}}</text>
			  <text class="small-num">.{{wxs.parsePrice(total)[1]}}</text>
			</view>
		  </view>
		</view>
		
		<!-- 订单详情 -->
		<view class="order-msg">
		  <view class="msg-item">
		    <view class="item">
		      <text>买家留言：</text>
		      <input placeholder="给卖家留言" @input="onDistrictInput"></input>
		    </view>
		  </view>
		</view>
		
		<view class="order-msg">
		  <view class="msg-item">
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
		        小计：
		        <text class="symbol">￥</text>
		        <text class="big-num">{{wxs.parsePrice(actualTotal)[0]}}</text>
		        <text class="small-num">.{{wxs.parsePrice(actualTotal)[1]}}</text>
		      </view>
		    </view>
		  </view>
		</view>
    </block>	
  </view>


  <!-- 底部栏 -->
  <view class="submit-order-footer">
    <view class="sub-order">
      <view class="item-txt">
        合计：
        <view class="price">
          <text class="symbol">￥</text>
          <text class="big-num">{{wxs.parsePrice(actualTotal)[0]}}</text>
          <text class="small-num">.{{wxs.parsePrice(actualTotal)[1]}}</text>
        </view>
      </view>
    </view>
    <view class="footer-box" @tap="toPay">
      提交订单
    </view>
  </view>
</view>
</view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
// pages/submit-order/submit-order.js
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");

export default {
  data() {
    return {
      // 订单入口 0购物车 1立即购买
      orderEntry: "0",
      userAddr: null,
      orderItems: [
		
	  ],
	  total: 0,
      actualTotal: 0,
      price: 0,
      totalCount: 0,
      transfee: 10,
      reduceAmount: 0,
      remark: "",
	  serverUrl: config.domain
    };
  },

  components: {
  },
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      orderEntry: options.orderEntry
    });
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.loadOrderData();
  },

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
    //加载订单数据
    loadOrderData: function () {
      var addrId = 0;
      if (this.userAddr != null) {
        addrId = this.userAddr.addrId;
      }
      uni.showLoading({
        mask: true
      });
	  if(this.orderEntry === "1") {
		  var item =  JSON.parse(uni.getStorageSync("orderItem"));
		  var params = {
		  		  url: `/user/${item.sellerId}`,
		  		  method: "GET",
		  		  callBack:  res => {
		  			var user = res.data;
					var orderItems = [];
					var prods = [];
					prods[0] = item;
					orderItems[0] = {
						prods: prods,
						user: user
					};
					this.setData({
					  orderItems: orderItems
					});
		  		  }
		  };
		  http.request(params);
	  }else {
		  var basketList = uni.getStorageSync("basketIds");
		  var orderItems = [];
		  var orderItemMaps = {};
		  for(var i=0;i<basketList.length;i++) {
			  for(var j=i+1;j<basketList.length;j++) {
				  if(basketList[i].sellerId===basketList[j].sellerId) {
					  var items = [];
					  items.push(basketList[i]);
					  items.push(basketList[j]);
					  var key  =  basketList[i].sellerId;
					  orderItemMaps[key]=items;
				  }else {
					  var items1 = [];
					  var items2 = [];
					  items1.push(basketList[i]);
					  items2.push(basketList[j]);
					  var key1  =  basketList[i].sellerId;
					  var key2  =  basketList[j].sellerId;
					  orderItemMaps[key1]=items1;
					  orderItemMaps[key2]=items2;
				  }
			  }
		  }
		  for(var key in orderItemMaps) {
			  var params = {
			  		  url: `/user/${key}`,
			  		  method: "GET",
			  		  callBack:  res => {
			  			var user = res.data;
						var item = {
							prods: orderItemMaps[key],
							user: user
						};
						orderItems.push(item);
			  		  }
			  };
			  http.request(params);
		  }
		   this.setData({
		    	orderItems: orderItems
		   });
		   console.log(this.orderItems);
	  }
	  var total = 0;
	  var totalCount = 0;
	  this.orderItems.forEach(item=> {
		  total += item.prodCount*item.price;
		  totalCount += item.prodCount;
		  this.setData({
		    "totalCount": totalCount,
			"total": total,
			"actualTotal": total+this.transfee
		  });
	  });
	  uni.hideLoading();
    },
    /**
     * 提交订单
     */
    toPay: function () {
      if (!this.userAddr) {
        uni.showToast({
          title: '请选择地址',
          icon: "none"
        });
        return;
      }

      this.submitOrder();
    },
    submitOrder: function () {
      uni.showLoading({
        mask: true
      });
	  var orderDetails = [];
	  this.orderItems.forEach(item=> {
			   var orderDetail = {
				   id: 0,
				   orderId: 0,
				   skuId: item.skuId,
				   num: item.prodCount,
				   title: item.title,
				   price: item.price,
				   image: item.pic
			   }
			   orderDetails.push(orderDetail);
	  });
	  var data = {
		  totalPay: this.actualTotal,
		  actualPay: this.actualTotal,
		  receiver: this.userAddr.consignee,
		  receiverMobile: this.userAddr.consigneePhone,
		  receiverState: this.userAddr.province,
		  receiverCity: this.userAddr.city,
		  receiverDistrict: this.userAddr.district,
		  receiverAddress: this.userAddr.detail,
		  remark: this.remark,
		  orderDetails: orderDetails
	  }
	  var ths = this;
      var params = {
        url: "/order",
        method: "POST",
		needToken: true,
        data: data,
		callBack: function(res) {
			uni.hideLoading();
			ths.simulationPay(res.data);
		}
      };
      http.request(params);
    },	
	//模拟支付，直接提交成功
	simulationPay: function(orderNumbers){
		    console.log(orderNumbers);
			uni.showLoading({
			  mask: true
			});
			var params = {
			  url: "/order/"+orderNumbers+"/2",
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
						setTimeout(() => {
							uni.navigateTo({
							  url: '/pages/pay-result/pay-result?sts=1&orderNumbers=' + orderNumbers
							});
						},1200)
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
     * 去地址页面
     */
    toAddrListPage: function () {
      uni.navigateTo({
        url: '/pages/delivery-address/delivery-address?order=0'
      });
    },
	onDistrictInput: function(e) {
		this.setData({
		  remark: e.detail.value
		});
	}
  }
};
</script>
<style>
@import "./submit-order.css";
</style>