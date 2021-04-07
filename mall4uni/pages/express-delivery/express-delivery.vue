<template>
<!--pages/express-delivery/express-delivery.wxml-->
<!-- 物流信息 -->
<view class="container">
  <view class="wrapper">
    <view class="deliveryInfo" style="background:url(http://jiales.gz-yami.com/delivery-bg.png) center center no-repeat #fff;">
      <view class="icon-express" style="background:url(http://jiales.gz-yami.com/delivery-car.png) no-repeat;background-size:100% 100%;">
      </view>
      <view class="infoWarp">
        <view class="companyname">
          <text class="key">物流公司：</text>
          <text class="value">上海银领网络科技有限公司</text>
        </view>
        <view class="expno">
          <text class="key">运单编号：</text>
          <text class="value">2873784364556412332543</text>
        </view>
      </view>
    </view>
    <view class="deliveryDetail">
      <block>
        <view class="'detailItem lastest')">
          <view class="dot">
            <image src="/static/images/icon/delive-dot.png"></image>
             <image src="/static/images/icon/dot.png"></image>
          </view>
          <view class="detail">
            <view class="desc">已被签收</view>
            <view class="time">2021/3/25 12:00:00</view>
          </view>
        </view>
		<view class="'detailItem')">
		  <view class="dot">
		    <image src="/static/images/icon/delive-dot.png"></image>
		     <image src="/static/images/icon/dot.png"></image>
		  </view>
		  <view class="detail">
		    <view class="desc">货已到达由岳麓区派送点</view>
		    <view class="time">2021/3/25 09:00:00</view>
		  </view>
		</view>
		<view class="'detailItem')">
		  <view class="dot">
		    <image src="/static/images/icon/delive-dot.png"></image>
		     <image src="/static/images/icon/dot.png"></image>
		  </view>
		  <view class="detail">
		    <view class="desc">货已到达由湖南长沙转运中心</view>
		    <view class="time">2021/3/24 18:00:00</view>
		  </view>
		</view>
		<view class="'detailItem')">
		  <view class="dot">
		    <image src="/static/images/icon/delive-dot.png"></image>
		     <image src="/static/images/icon/dot.png"></image>
		  </view>
		  <view class="detail">
		    <view class="desc">货已到达由广东清远转运中心</view>
		    <view class="time">2021/3/24 13:00:00</view>
		  </view>
		</view>
        <view class="'detailItem')">
          <view class="dot">
            <image src="/static/images/icon/delive-dot.png"></image>
             <image src="/static/images/icon/dot.png"></image>
          </view>
          <view class="detail">
            <view class="desc">货已由广东广州发出</view>
            <view class="time">2021/3/24 09:00:00</view>
          </view>
        </view>
      </block>
    </view>
  </view>
</view>
</template>

<script>
// pages/express-delivery/express-delivery.js
var http = require("../../utils/http.js");

export default {
  data() {
    return {
      companyName: "",
      dvyFlowId: "",
      dvyData: []
    };
  },
  components: {},
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var ths = this;
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
  methods: {}
};
</script>
<style>
@import "./express-delivery.css";
</style>