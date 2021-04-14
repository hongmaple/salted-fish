<template>
<!--pages/basket/basket.wxml-->
<view class="container">
  <view class="prod-list">
   <block v-for="(shopCartItem, shopCartItemIndex) in shopCartItemDiscounts" :key="shopCartItemIndex">
    <view class="section">
    	<image style="width: 130rpx;height: 130rpx;" :src="shopCartItem.user.avatarImage?serverUrl+shopCartItem.user.avatarImage:'../../static/images/icon/head04.png'"></image>
    	<text>{{shopCartItem.user.username}}</text>
    </view>
	<block v-for="(item, index) in shopCartItem.carts" :key="index">
      <view class="prod-block">
       <view class="item">
         <view class="btn">
           <label>
             <checkbox @tap="onSelectedItem" :data-shopCartItemIndex="shopCartItemIndex" :data-index="index" :value="''+item.id" :checked="item.checked" color="#105c3e"></checkbox>
           </label>
         </view>
         <view class="prodinfo">
           <view class="pic">
             <image :src="serverUrl+item.image"></image>
           </view>
           <view class="opt">
             <view class="prod-name">{{item.title}}</view>
             <view class="price-count">
               <view class="price">
                 <text class="symbol">￥</text>
                 <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text>
                 <text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text>
               </view>
               <view class="m-numSelector">
                 <view @tap="onCountMinus" class="minus" :data-shopCartItemIndex="shopCartItemIndex"  :data-index="index"></view>
                 <input type="number" :value="item.num" disabled></input>
                 <view @tap="onCountPlus" class="plus" :data-shopCartItemIndex="shopCartItemIndex" :data-index="index"></view>
               </view>
             </view>
           </view>
         </view>
       </view>
      </view>
    </block>
	<view style="height: 20rpx;"></view>
   </block>
  </view>

  <view class="empty" v-if="shopCartItemDiscounts.length==0">
    <view class="img">
      <image src="/static/images/tabbar/basket.png"></image>
    </view>
    <view class="txt">
      您还没有添加任何商品哦~
    </view>
  </view>

  <!-- 底部按钮 -->
  <view class="cart-footer" v-if="shopCartItemDiscounts.length>0">
    <view class="btn all">
      <label @tap="onSelAll">
        <checkbox :checked="allChecked" color="#f7d731;"></checkbox>全选</label>
    </view>
    <view class="btn del" @tap="onDelBasket">
      <text>删除</text>
    </view>
    <view class="btn total">
      <view class="finally">
        <text>合计:</text>
        <view class="price">
          <text class="symbol">￥</text>
          <text class="big-num">{{wxs.parsePrice(finalMoney)[0]}}</text>
          <text class="small-num">.{{wxs.parsePrice(finalMoney)[1]}}</text>
        </view>
      </view>
      <view class="total-msg" v-if="subtractMoney>0">
        总额:￥{{wxs.toPrice(totalMoney)}} 立减:￥{{wxs.toPrice(subtractMoney)}}
      </view>
    </view>
    <view class="btn settle" @tap="toFirmOrder">
      <text>结算</text>
    </view>
  </view>
  <!-- end 底部按钮 -->

</view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
// pages/basket/basket.js
var http = require("../../utils/http.js"); 
var config = require("../../utils/config.js");
const Big = require("../../utils/big.min.js");

export default {
  data() {
    return {
      // picDomain: config.picDomain,
      shopCartItemDiscounts: [
		  {
			  id: 0,
			  image: "",
			  num: 0,
			  price: 0,
			  skuId: 0,
			  title: "",
			  userId: 0
		  }
	  ],
      finalMoney: 0,
      totalMoney: 0,
      subtractMoney: 0,
      allChecked: true,
	  serverUrl: config.domain,
	  shopCartItem: [],
    };
  },

  components: {},
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {},

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
     //加载购物车
    this.carList();
    http.getCartCount(); //重新计算购物车总数量
  },
  methods: {
	  carList: function() {
		  uni.showLoading();
		  var params = {
		    url: "/cart/list",
		    method: "POST",
			needToken: true,
		    data: {
		  	  "pageNum": 1,
		  	  "pageSize": 10
		    },
		    callBack: res => {
		      if (res.data.total > 0) {
		        // 默认全选
		        var shopCartItemDiscounts = res.data.list;
		        shopCartItemDiscounts.forEach(shopCartItemDiscount => {
					shopCartItemDiscount.carts.forEach(cart => {
						cart.checked = true;
					});
		        });
		        this.setData({
		          shopCartItemDiscounts: shopCartItemDiscounts,
		          allChecked: true
		        });
		      } else {
		        this.setData({
		          shopCartItemDiscounts: []
		        });
		      }
		  
		      this.calTotalPrice(); //计算总价
		  
		      uni.hideLoading();
		    }
		  };
		  http.request(params);
	  },
    /**
     * 去结算
     */
    toFirmOrder: function () {
      var shopCartItemDiscounts = this.shopCartItemDiscounts;
      var basketIds = [];
      shopCartItemDiscounts.forEach(shopCartItems=> {
		  var basketProds = [];
		  var user = shopCartItems.user;
		  shopCartItems.carts.forEach(cart => {
			  if (cart.checked) {
			  	basketProds.push(cart);
			  }
		  });
		  var shopCartItem = {
			  basketProds: basketProds,
			  user: user,
			  isAgent: shopCartItems.isAgent,
			  backgroundAgentId: shopCartItems.backgroundAgentId
		  }
		  basketIds.push(shopCartItem);
      });

      if (!basketIds.length) {
        uni.showToast({
          title: '请选择商品',
          icon: "none"
        });
        return;
      }

      uni.setStorageSync("basketIds", basketIds);
      uni.navigateTo({
        url: '/pages/submit-order/submit-order?orderEntry=0'
      });
    },

    /**
     * 全选
     */
    onSelAll: function () {
      var allChecked = this.allChecked;
      allChecked = !allChecked; //改变状态

      var shopCartItemDiscounts = this.shopCartItemDiscounts;

      for (var i = 0; i < shopCartItemDiscounts.length; i++) {
		  var carts = shopCartItemDiscounts[i].carts;
		  for (var i = 0; i < carts.length; i++) {
			  var cItems = carts[i];
			  cItems.checked = allChecked;
		  }
      }

      this.setData({
        allChecked: allChecked,
        shopCartItemDiscounts: shopCartItemDiscounts
      });
      this.calTotalPrice(); //计算总价
    },

    /**
     * 每一项的选择事件
     */
    onSelectedItem: function (e) {
	  var shopCartItemIndex = e.currentTarget.dataset.shopCartItemIndex;
      var index = e.currentTarget.dataset.index; // 获取data- 传进来的index

      var shopCartItemDiscounts = this.shopCartItemDiscounts; // 获取购物车列表

      var checked = shopCartItemDiscounts[shopCartItemIndex].carts[index].checked; // 获取当前商品的选中状态

      shopCartItemDiscounts[shopCartItemIndex].carts[index].checked = !checked; // 改变状态

      this.setData({
        shopCartItemDiscounts: shopCartItemDiscounts
      });
      this.checkAllSelected(); //检查全选状态

      this.calTotalPrice(); //计算总价
    },

    /**
     * 检查全选状态
     */
    checkAllSelected: function () {
      var allChecked = true;
      var shopCartItemDiscounts = this.shopCartItemDiscounts;
      var flag = false;

      for (var i = 0; i < shopCartItemDiscounts.length; i++) {
		  var carts = shopCartItemDiscounts[i].carts;
		  for (var i = 0; i < carts.length; i++) {
			  var cItems = carts[i];
			  if (!cItems.checked) {
			    allChecked = !allChecked;
			    flag = true;
			    break;
			  }
			   if (flag) {
			     break;
			   }
		  }
      }
      this.setData({
        allChecked: allChecked
      });
    },

    /**
     * 计算购物车总额
     */
    calTotalPrice: function () {
      var shopCartItemDiscounts = this.shopCartItemDiscounts;
	  var shopCartItem = [];
      var finalMoney = 0;
      for (var i = 0; i < shopCartItemDiscounts.length; i++) {
        var cItems = shopCartItemDiscounts[i].carts;
		for (var j = 0; j < cItems.length; j++) {
			var cart = cItems[j]
			if (cart.checked) {
			  shopCartItem.push(cart);
			  finalMoney+= cart.price*cart.num;
			}
		}
       }
      this.setData({
            finalMoney: finalMoney,
            totalMoney: finalMoney,
            subtractMoney: 0,
			shopCartItem: shopCartItem
      });
    },

    /**
     * 减少数量
     */
    onCountMinus: function (e) {
	 var shopCartItemIndex = e.currentTarget.dataset.shopCartItemIndex;
     var index = e.currentTarget.dataset.index;
     var shopCartItemDiscounts = this.shopCartItemDiscounts;
	 var prodCount = shopCartItemDiscounts[shopCartItemIndex].carts[index].num;
      if (prodCount > 1) {
        this.updateCount(shopCartItemDiscounts,shopCartItemIndex,index, prodCount-1);
		return;
      }else {
		  uni.showToast({
		    title: '已经最少了',
		    icon: "none"
		  });
	  }
    },

    /**
     * 增加数量
     */
    onCountPlus: function (e) {
	  var shopCartItemIndex = e.currentTarget.dataset.shopCartItemIndex;
      var index = e.currentTarget.dataset.index;
      var shopCartItemDiscounts = this.shopCartItemDiscounts;
      this.updateCount(shopCartItemDiscounts,shopCartItemIndex, index, shopCartItemDiscounts[shopCartItemIndex].carts[index].num+1);
    },

    /**
     * 改变购物车数量接口
     */
    updateCount: function (shopCartItemDiscounts,shopCartItemIndex, index, prodCount) {
      var ths = this;
      uni.showLoading({
        mask: true
      });
      var params = {
        url: "/cart",
        method: "PUT",
		needToken: true,
        data: {
          num: prodCount,
          id: shopCartItemDiscounts[shopCartItemIndex].carts[index].id
        },
        callBack: function (res) {
          shopCartItemDiscounts[shopCartItemIndex].carts[index].num = prodCount;
          ths.setData({
            shopCartItemDiscounts: shopCartItemDiscounts
          });
          ths.calTotalPrice(); //计算总价

          uni.hideLoading();
          http.getCartCount(); //重新计算购物车总数量
        }
      };
      http.request(params);
    },

    /**
     * 删除购物车商品
     */
    onDelBasket: function () {
      var ths = this;
      var shopCartItemDiscounts = this.shopCartItemDiscounts;
      var basketIds = [];

      for (var i = 0; i < shopCartItemDiscounts.length; i++) {
        var cItems = shopCartItemDiscounts[i].carts;
        for (var j = 0; j < cItems.length; j++) {
        	var cart = cItems[j]
        	if (cart.checked) {
        	  basketIds.push(cart.id);
        	}
        }
      }

      if (basketIds.length == 0) {
        uni.showToast({
          title: '请选择商品',
          icon: "none"
        });
      } else {
        uni.showModal({
          title: '',
          content: '确认要删除选中的商品吗？',
          confirmColor: "#eb2444",
          success(res) {
            if (res.confirm) {
              uni.showLoading({
                mask: true
              });
			  basketIds.forEach(basketId => {
				  var params = {
				    url: "/cart/"+basketId,
				    method: "DELETE",
					needToken: true,
				    callBack: function (res) {
				      uni.hideLoading();
				    }
				  };
				  http.request(params);
			  });
			  ths.carList();
            }
          }

        });
      }
    }
  }
};
</script>
<style>
@import "./basket.css";
</style>