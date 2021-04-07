<template>
<!--pages/category/category.wxml-->

<view class="container">
<!-- 头部搜索区 -->
  <view class="search-bar">
    <view class="search-box">
      <input placeholder="输入关键字搜索" class="sear-input" confirm-type="search" @confirm="toSearchProdPage" @input="getSearchContent" :value="flowerName"></input>
      <image src="/static/images/icon/search.png" class="search-img"></image>
    </view>
  </view>
<!-- 滚动内容区 -->
  <view class="main">
    <!-- 右侧内容start -->
    <scroll-view scroll-y="true" class="rightcontent">
    <!-- <block wx:for='{{ productList}}' wx:key=''> -->
      <view class="cont-item">
        <block v-for="(item, index) in productList" :key="index">
          <view class="show-item" @tap="toProdPage" :data-item="item">
            <view class="more-prod-pic">
			  <image :src="serverUrl + item.pic" class="more-pic" mode="widthFix"></image>
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

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
// pages/category/category.js
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");

export default {
  data() {
    return {
      selIndex: 0,
      productList: [],
	  serverUrl: config.domain,
	  flowerName: ''
    };
  },
  components: {},
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
   this.getProdList();
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
    toSearchProdPage: function () {
      if (this.flowerName.trim()) {
         this.getProdList();
      }
    },
	//输入商品名获取数据 || 绑定输入值
	getSearchContent: function (e) {
	  this.setData({
	    flowerName: e.detail.value
	  }); // this.data.prodName=e.detail.value
	},
    getProdList: function(){
      //加载分类列表
      var params = {
        url: "/VideoTeaching/list",
        method: "POST",
        data: {
          title: this.flowerName,
		  pageNum: 1,
		  pageSize: 10
        },
        callBack: res => {
          this.setData({
            productList: res.data.list
          });
        }
      };
      http.request(params);
    },
	//跳转详情页
	toProdPage: function (e) {
	  var item = e.currentTarget.dataset.item;
	  uni.navigateTo({
	    url: '/pages/VideoTeachingDetails/VideoTeachingDetails?item=' + JSON.stringify(item)
	  });
	}
  }
};
</script>
<style>
@import "./VideoTeaching.css";
</style>