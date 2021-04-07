<template>
<!--pages/search-prod-show/search-prod-show.wxml-->
<view class="container">

  <!-- 搜索框 -->
  <view class="fixed-box">
    <view class="search-bar">
      <view class="search-box">
        <input class="sear-input" :value="prodName" @input="getSearchContent" confirm-type="search" @confirm="toSearchConfirm"></input>
        <image src="/static/images/icon/search.png" class="search-img"></image>
      </view>
      <view class="search-list-img" @tap="changeShowType">
        <image src="/static/images/icon/search-col.png"></image>
      </view>
    </view>
  </view>

  <!-- 商品列表 -->
  <view class="prod-list">
    <!-- 纵向列表 -->
    <view class="cont-item">
      <block v-for="(item, index) in searchProdList" :key="index">
        <view class="show-item" @tap="toProdPage" :data-prodid="item.id">
          <view class="more-prod-pic">
            <image :src="serverUrl+item.images" class="more-pic"></image>
          </view>
          <view class="prod-text-right">
            <view class="prod-text more">{{item.title}}</view>
			<view class="prod-text more">适宜人群：{{item.appropriateCrowd}}</view>
            <view class="prod-price more">
              <text class="symbol">￥</text>
              <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text>
              <text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text>
            </view>
          </view>
        </view>
      </block>
    </view>
  </view>

</view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
// pages/search-prod-show/search-prod-show.js
var http = require("../../utils/http.js");
import prod from "../../components/production/production";
var config = require("../../utils/config.js");

export default {
  data() {
    return {
      sts: 0,
      showType: 2,
      searchProdList: [],
      prodName: "",
	  serverUrl: config.domain
    };
  },

  components: {
    prod
  },
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      prodName: options.prodName
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
    this.toLoadData();
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
    changeShowType: function () {
      var showType = this.showType;

      if (showType == 1) {
        showType = 2;
      } else {
        showType = 1;
      }

      this.setData({
        showType: showType
      });
    },
    //输入商品获取数据
    getSearchContent: function (e) {
      this.setData({
        prodName: e.detail.value
      });
    },
    //请求商品接口
    toLoadData: function () {
      var ths = this; //热门搜索

      var params = {
		url: "/flower/list",
		method: "POST",
		data: {
		  title: this.prodName,
		  pageNum: 1,
		  pageSize: 10
		},
        callBack: function (res) {
          ths.setData({
            searchProdList: res.data.list
          });
        }
      };
      http.request(params);
    },
    //当前搜索页二次搜索商品
    toSearchConfirm: function () {
      this.toLoadData();
    },

    /**
    * 状态点击事件
    */
    onStsTap: function (e) {
      var sts = e.currentTarget.dataset.sts;
      this.setData({
        sts: sts
      });
      this.toLoadData();
    },
    toProdPage: function (e) {
      var prodid = e.currentTarget.dataset.prodid;
      uni.navigateTo({
        url: '/pages/prod/prod?prodid=' + prodid
      });
    }
  }
};
</script>
<style>
@import "./search-prod-show.css";
</style>