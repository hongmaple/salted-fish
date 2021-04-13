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
	<block v-for="(orderItem, orderItemIndex) in orderItems" :key="orderItemIndex">
		<view class="prod-item">
		  <view class="section">
		    <image style="width: 130rpx;height: 130rpx;" :src="orderItem.user.avatarImage?serverUrl+orderItem.user.avatarImage:'../../static/images/icon/head04.png'"></image>
		  		<text>{{orderItem.user.username}}</text>
		  </view>
		  <block v-for="(item, index) in orderItem.prods" :key="index">
			<view class="item-cont" @tap="toProdPage" :data-prodid="item.skuId">
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
		</view>
		
		<!-- 订单详情 -->
		<view class="order-msg">
		  <view class="msg-item">
		    <view class="item">
		      <text>买家留言：</text>
		      <input placeholder="给卖家留言" :value="orderItem.remark" :data-orderItemIndex="orderItemIndex" @input="onDistrictInput"></input>
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
			  <text class="prodcount">共{{orderItem.subtotalCount}}件商品</text>
		      <view class="item-txt price">
		        小计：
		        <text class="symbol">￥</text>
		        <text class="big-num">{{wxs.parsePrice(orderItem.actualPay)[0]}}</text>
		        <text class="small-num">{{wxs.parsePrice(orderItem.actualPay)[1]}}</text>
		      </view>
		    </view>
		  </view>
		</view>
    </block>	
<!-- 	<view class="total-num">
				<text class="prodcount">共{{totalCount}}件商品</text>
				<view class="prodprice">合计：
				  <text class="symbol">￥</text>
				  <text class="big-num">{{wxs.parsePrice(total)[0]}}</text>
				  <text class="small-num">.{{wxs.parsePrice(total)[1]}}</text>
				</view>
	</view> -->
	<view style="height: 120rpx;"></view>
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
	  var ths = this;
      if (ths.userAddr != null) {
        addrId = ths.userAddr.addrId;
      }
      uni.showLoading({
        mask: true
      });
	  var total = 0;
	  var totalCount = 0;
	  var orderItems = [];
	  if(ths.orderEntry === "1") {
		  var item =  JSON.parse(uni.getStorageSync("orderItem"));
		  var params = {
		  		  url: `/user/${item.sellerId}`,
		  		  method: "GET",
		  		  callBack:  res => {
		  			var user = res.data;
					var prods = [];
					prods[0] = item;
					total += item.num*item.price;
					totalCount += item.num;
					orderItems[0] = {
						prods: prods,
						user: user,
						totalPay: total,
						actualPay: total+ths.transfee,
						subtotalCount: totalCount,
						remark: '',
						backgroundAgentId: item.backgroundAgentId
					};
					console.log(orderItems);
					ths.setData({
					  orderItems: orderItems,
					  "totalCount": totalCount,
					  "total": total,
					  "actualTotal": total+ths.transfee
					});
		  		  }
		  };
		  http.request(params);
	  }else {
		  var basketList = uni.getStorageSync("basketIds");
		  console.log(basketList);
		  // var orderItemMaps = {};
		  // for(var i=0;i<basketList.length;i++) {
			 //  for(var j=i+1;j<basketList.length;j++) {
				//   if(basketList[i].sellerId===basketList[j].sellerId) {
				// 	  var key  =  basketList[i].sellerId;
				// 	  var items = orderItemMaps[key];
				// 	  items.push(basketList[i]);
				// 	  items.push(basketList[j]);
				// 	  orderItemMaps[key] = items;
				// 	  break;
				//   }else {
				// 	  var key1  =  basketList[i].sellerId;
				// 	  var key2  =  basketList[j].sellerId;
				// 	  var items1 = orderItemMaps[key1];
				// 	  var items2 = orderItemMaps[key2];
				// 	  if(items1 instanceof Array) {
				// 		  items1.push(basketList[i]);
				// 	  }else {
				// 		  items1=[];
				// 		  items1.push(basketList[i]);
				// 	  }
				// 	  if(items2 instanceof Array) {
				// 	  	  items2.push(basketList[j]);
				// 	  }else {
				// 	  	  items2=[];
				// 		  items2.push(basketList[j]);
				// 	  }
				// 	  orderItemMaps[key1]=items1;
				// 	  orderItemMaps[key2]=items2;
				// 	  break;
				//   }
			 //  }
		  // }
		  // for(var key in orderItemMaps) {
			 //  var totalPay = 0;
			 //  var actualPay = 0;
			 //  var subtotalCount = 0;
			 //  var hash=[];
			 //  for (var i = 0; i < orderItemMaps[key].length; i++) {
			 //     if(hash.indexOf(orderItemMaps[key][i])==-1){
			 //      hash.push(orderItemMaps[key][i]);
			 //     }
			 //  }
			 //  hash.forEach(function(item,index) {
			 //  				  totalCount += item.num;
			 //  				  totalPay += item.num*item.price;
				// 			  subtotalCount +=item.num;
			 //  });
			 //  actualPay += totalPay+ths.transfee;
			 //  total += actualPay;
			 //  var orderItem = hash;
			 //  var item = {
			 //  	prods: orderItem,
			 //  	totalPay: totalPay,
			 //  	actualPay: actualPay,
			 //  	subtotalCount: subtotalCount
			 //  };
			 //  var params = {
			 //  		  url: `/user/${key}`,
			 //  		  method: "GET",
			 //  		  callBack:  res => {
			 //  			var user = res.data;
				// 		item['user'] = user;
			 //  		  }
			 //  };
			 //  http.request(params);
			 //  setTimeout(function () {
			 //     orderItems.push(item);
			 //  }, 500);
		  // }
		  basketList.forEach(basket => {
			    var totalPay = 0;
			    var actualPay = 0;
			    var subtotalCount = 0;
			    basket.basketProds.forEach(function(item,index) {
			    				  totalCount += item.num;
			    				  totalPay += item.num*item.price;
			  				      subtotalCount +=item.num;
			    });
			    actualPay += totalPay+ths.transfee;
			    total += actualPay;
			    var orderItem = basket.basketProds;
			    var item = {
			    	prods: orderItem,
			    	totalPay: totalPay,
			    	actualPay: actualPay,
			    	subtotalCount: subtotalCount,
			  	    user: basket.user,
					remark: '',
					backgroundAgentId: basket.backgroundAgentId
			    };
			  orderItems.push(item);
		  });
		   ths.setData({
		    	orderItems: orderItems,
				"totalCount": totalCount,
				"total": total,
				"actualTotal": total
		   });
	  }
	  uni.hideLoading();
    },
	unique1: function(arr){
		console.log(arr)
	  var hash=[];
	  for (var i = 0; i < arr.length; i++) {
	     if(hash.indexOf(arr[i])==-1){
	      hash.push(arr[i]);
	     }
	  }
	  console.log(hash)
	  return hash;
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
	  var orderItems = this.orderItems;
	  var orderBodys = [];
	  orderItems.forEach(orderItem => {
		  var orderDetails = [];
		  orderItem.prods.forEach(item => {
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
		 var orderBody = {
		  		  totalPay: orderItem.totalPay,
		  		  actualPay: orderItem.actualPay,
		  		  receiver: this.userAddr.consignee,
		  		  receiverMobile: this.userAddr.consigneePhone,
		  		  receiverState: this.userAddr.province,
		  		  receiverCity: this.userAddr.city,
		  		  receiverDistrict: this.userAddr.district,
		  		  receiverAddress: this.userAddr.detail,
		  		  remark: orderItem.remark,
		  		  orderDetails: orderDetails,
				  sellerId: orderItem.user.id,
				  backgroundAgentId: orderItem.backgroundAgentId
		  }
		  orderBodys.push(orderBody)
	  });
	  var ths = this;
	  var data = {
		  orderBodies: orderBodys,
		  total: this.total,
		  actualTotal: this.actualTotal
	  }
	  console.log(data);
      var params = {
        url: "/order",
        method: "POST",
		needToken: true,
        data: data,
		callBack: function(res) {
			uni.hideLoading();
			res.data.forEach(orderNumbers => {
				ths.simulationPay(orderNumbers);
				uni.showToast({
					title: "模拟支付成功",
					icon:"none"
				})
				setTimeout(() => {
					uni.navigateTo({
					  url: '/pages/pay-result/pay-result?sts=1&orderNumbers=' + orderNumbers
					});
				},1200)
			});
		}
      };
      //http.request(params);
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

					}else{
						uni.showToast({
							title: "支付失败！",
							icon:"none"
						})
						return;
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
		var orderItemIndex = e.currentTarget.dataset.prodid
		var orderItems = this.orderItems;
		orderItems.remark=e.detail.value;
		this.setData({
		  orderItems: orderItems
		});
	},
	//跳转商品详情页
    toProdPage: function (e) {
      var prodid = e.currentTarget.dataset.prodid;
      uni.navigateTo({
        url: '/pages/prod/prod?prodid=' + prodid
      });
    },
  }
};
</script>
<style>
@import "./submit-order.css";
</style>