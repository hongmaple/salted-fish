<template>
<!--pages/prod-classify/prod-classify.wxml-->

<view class="container">
  <view class="line-fix"></view>
  <view class="tit-background"></view>
  <view>
    <block v-for="(item, index) in prodList" :key="index">
      <prod :item="item"></prod>
    </block>
  </view>
</view>
</template>

<script>
// pages/prod-classify/prod-classify.js
var http = require("../../utils/http.js");
import prod from "../../components/production/production";

export default {
  data() {
    return {
      sts: 0,
      prodList: [],
      title: "",
      current: 1,
      size: 10,
      pages: 0,
      tagid: null
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
      current: 1,
      pages: 0,
      sts: options.sts,
      title: options.title ? options.title : ""
    });

    if (options.tagid) {
      this.setData({
        tagid: options.tagid
      });
    }

    if (this.sts == 5) {
      uni.setNavigationBarTitle({
        title: '我的收藏商品'
      });
    } else {
      uni.setNavigationBarTitle({
        title: this.title
      });
    }
    this.loadProdData(options);
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
  onReachBottom: function () {
    if (this.current < this.pages) {
      this.setData({
        current: this.current + 1
      });
      this.loadProdData();
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},
  methods: {
    /**
     * 加载商品数据
     */
    loadProdData: function (options) {
      let sts = this.sts;

      if (sts == 0) {
        // 分组标签商品列表
        this.getTagProd();
      } else if (sts == 1) {
        // 新品推荐
         this.getTagProd();
      } else if (sts == 2) {
        // 限时特惠
        this.getTagProd();
      } else if (sts == 3) {
        // 每日疯抢
        this.getTagProd();
      } else if (sts == 4) {
        this.getTagProd();
      } else if (sts == 5) {
        // 收藏商品列表
        this.getCollectionProd();
      }
    },
    getActProd: function (url) {
      var ths = this;
      uni.showLoading();
      var params = {
        url: url,
        method: "GET",
        data: {
          current: ths.current,
          size: ths.size
        },
        callBack: function (res) {
          let list = [];

          if (res.current == 1) {
            list = res.records;
          } else {
            list = ths.prodList;
            list = list.concat(res.records);
          }

          ths.setData({
            prodList: list,
            pages: res.pages
          });
          uni.hideLoading();
        }
      };
      http.request(params);
    },

    /**
     * 获取我的收藏商品
     */
    getCollectionProd: function () {
      var ths = this;
      uni.showLoading();
      var params = {
        url: "/flower/favorite/list",
        method: "POST",
		needToken: true,
        data: {
           pageNum: ths.current,
           pageSize: ths.size
        },
        callBack: function (res) {
          let list = [];

          if (res.current == 1) {
            list = res.data.list;
          } else {
            list = ths.prodList;
            list = list.concat(res.data.list);
          }

          ths.setData({
            prodList: list,
            pages: res.pages
          });
          uni.hideLoading();
        }
      };
      http.request(params);
    },

    /**
     * 获取标签列表
     */
    getTagProd: function (id) {
      var ths = this;
      uni.showLoading();
      var param = {
        url: "/flower/list",
        method: "POST",
        data: {
          cid: ths.tagid,
		  isAsc: null,
		  orderBy: null,
		  orderByColumn: null,
		  pageNum: ths.current,
		  pageSize: ths.size
        },
        callBack: res => {
          let list = [];
          console.log(res);
          if (res.current == 1) {
            list = res.data.list;
          } else {
            list = ths.prodList.concat(res.data.list);
          }

          ths.setData({
            prodList: list,
            pages: res.data.pages
          });
          uni.hideLoading();
        }
      };
      http.request(param);
    }
  }
};
</script>
<style>
@import "./prod-classify.css";
</style>