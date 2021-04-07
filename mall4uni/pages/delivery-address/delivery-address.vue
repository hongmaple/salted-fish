<template>
<!--pages/delivery-address/delivery-address.wxml-->
<view class="container">
  <view class="main">
    <view class="empty" v-if="addressList.length==0">
      <view class="img">
        <image src="http://jiales.gz-yami.com/addr.png"></image>
      </view>
      <view class="txt">您还没有收货地址</view>
    </view>
    <radio-group class="radio-group" @change="radioChange">
      <block v-for="(item, index) in addressList" :key="index">
        <view class="address">
          <view class="personal" @tap="selAddrToOrder" :data-item="item">
            <view class="info-tit">
              <text class="name">{{item.consignee}}</text>
              <text class="tel">{{item.consigneePhone}}</text>
              <image src="/static/images/icon/revise.png" @tap.stop="toEditAddress" :data-addrid="item.id"></image>
            </view>
            <view class="addr">

              <text class="addr-get"> {{item.province}}{{item.city}}{{item.district}}{{item.detail}}</text>
            </view>
          </view>
        </view>
      </block>
    </radio-group>
  </view>
  <view class="footer" @tap="onAddAddr">
    <text>新增收货地址</text>
  </view>
</view>
</template>

<script>
// pages/delivery-address/delivery-address.js
var http = require("../../utils/http.js"); // var config = require("../../utils/config.js");

export default {
  data() {
    return {
      defaultSize: 'mini',
      disabled: false,
      plain: true,
      loading: false,
      addressList: [],
      addAddress: '',
      order: -1,
      item: "",
      selAddress: ""
    };
  },

  components: {},
  props: {},
  onLoad: function (option) {
    if (option.order) {
      this.setData({
        order: option.order
      });
    }
  },
  //加载地址列表
  onShow: function () {
    var ths = this;
    uni.showLoading();
    var params = {
      url: "/address",
      method: "GET",
	  needToken: true,
      data: {},
      callBack: function (res) {
        //console.log(res)
        ths.setData({
          addressList: res.data
        });
        uni.hideLoading();
      }
    };
    http.request(params);
  },
  methods: {
    //新增收货地址
    onAddAddr: function (e) {
      uni.navigateTo({
        url: '/pages/editAddress/editAddress'
      });
    },
    // 修改地址 
    toEditAddress: function (e) {
      var addrId = e.currentTarget.dataset.addrid;
      uni.navigateTo({
        url: '/pages/editAddress/editAddress?addrId=' + addrId
      });
    },

    /**
     * 选择地址 跳转回提交订单页
     */
    selAddrToOrder: function (e) {
      if (this.order == 0) {
        var pages = getCurrentPages(); //当前页面

        var prevPage = pages[pages.length - 2]; //上一页面
        console.log(prevPage);
        prevPage.setData({
          //直接给上移页面赋值
          userAddr: e.currentTarget.dataset.item,
          selAddress: 'yes'
        });
        uni.navigateBack({
          //返回
          delta: 1
        });
      }
    }
  }
};
</script>
<style>
@import "./delivery-address.css";
</style>