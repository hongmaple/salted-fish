<template>
<!--index.wxml-->
<view class="container">
  <view class="bg-sear">
    <view class="scrolltop">
      <view class="section" @tap="toSearchPage">
        <image src="/static/images/icon/search.png" class="search-img"></image>
        <text class="placeholder">搜索</text>
      </view>
    </view>
  </view>

  <view class="content">
    <!-- swiper -->
    <swiper :autoplay="autoplay" :indicator-color="indicatorColor" :interval="interval" :duration="duration" :indicator-active-color="indicatorActiveColor + ' '" circular="true" class="pic-swiper" indicator-dots previous-margin="20rpx" next-margin="20rpx">
      <block v-for="(item, index) in indexImgs" :key="index">
        <swiper-item class="banner-item">
          <view class="img-box">
            <image :src="item.imgUrl" :data-prodid="item.relation" @tap="toProdPage" class="banner"></image>
          </view>
        </swiper-item>
      </block>
    </swiper>
    <!-- end swiper -->

    <view class="cat-item">
      <view class="item" @tap="toClassifyPage" data-sts="1" data-title="日用品">
        <image src="/static/images/icon/newProd.png"></image>
        <text>日用品</text>
      </view>
      <view class="item" @tap="toClassifyPage" data-sts="2" data-title="电子产品">
        <image src="/static/images/icon/timePrice.png"></image>
        <text>电子产品</text>
      </view>
      <view class="item" @tap="toClassifyPage" data-sts="3" data-title="服饰/鞋子">
        <image src="/static/images/icon/neweveryday.png"></image>
        <text>服饰/鞋子</text>
      </view>
      <view class="item" @tap="toClassifyPage" data-sts="4" data-title="其他">
        <image src="/static/images/icon/newprods.png"></image>
        <text>其他</text>
      </view>
    </view>

    <!-- 消息播放 -->
    <view class="message-play" @tap="onNewsPage">
      <image src="/static/images/icon/horn.png" class="hornpng"></image>
      <swiper vertical="true" autoplay="true" duration="1000" class="swiper-cont">
      <!--  <block v-for="(item, index) in news" :key="index">
          <swiper-item class="items">{{item.title}}</swiper-item>
        </block> -->
		<swiper-item class="items"><a href="//www.yinlinkrc.com">银领人才网专注于技能人才招聘</a></swiper-item>
		<swiper-item class="items">樱花盛开，买花的好季节</swiper-item>
		<swiper-item class="items">春季买花优惠多多，快来抢购</swiper-item>
		<swiper-item class="items"><a href="//www.yinlinkrc.com">银领人才网</a></swiper-item>
      </swiper>
      <text class="arrow"></text>
    </view>

  </view>
	
	<view class="updata" v-if="updata">
		<block v-for="(item, index) in taglist" :key="index">
		  <view class="up-to-date" v-if="item.style==2">
		    <view class="title">
		      <text>{{item.title}}</text>
		      <view class="more-prod-cont" @tap="toClassifyPage" data-sts="0" :data-id="item.id" :data-title="item.title">
		        <text class="more">查看更多</text>
		        <!-- <text class='arrow'></text> -->
		      </view>
		    </view>
		    <view class="item-cont">
		      <block v-for="(prod, index2) in item.prods" :key="index2">
		        <view class="prod-item" @tap="toProdPage" :data-prodid="prod.id">
		          <view>
		            <view class="imagecont">
		              <image :src="serverUrl+prod.images" class="prodimg"></image>
		            </view>
		            <view class="prod-text">{{prod.title}}</view>
		            <view class="price">
		              <text class="symbol">￥</text>
		              <text class="big-num">{{wxs.parsePrice(prod.price)[0]}}</text>
		              <text class="small-num">.{{wxs.parsePrice(prod.price)[1]}}</text>
		            </view>
		          </view>
		        </view>
		      </block>
		
		    </view>
		  </view>
		
		  <!-- 商城热卖 -->
		  <view class="hot-sale" v-if="item.style==1">
		    <view class="title">
		      <text>{{item.title}}</text>
		      <view class="more-prod-cont" @tap="toClassifyPage" data-sts="0" :data-id="item.id" :data-title="item.title">
		        <text class="more">更多</text>
		        <text class="arrow"></text>
		      </view>
		    </view>
		    <view class="hotsale-item-cont">
		      <block v-for="(prod, index2) in item.prods" :key="index2">
		        <view class="prod-items" @tap="toProdPage" :data-prodid="prod.id">
		          <view class="hot-imagecont">
		            <image :src="serverUrl+prod.images" class="hotsaleimg"></image>
		          </view>
		          <view class="hot-text">
		            <view class="hotprod-text">{{prod.title}}</view>
		            <view class="prod-info">{{prod.flowerLanguage}}</view>
		            <view class="prod-text-info">
		              <view class="price">
		                <text class="symbol">￥</text>
		                <text class="big-num">{{wxs.parsePrice(prod.price)[0]}}</text>
		                <text class="small-num">.{{wxs.parsePrice(prod.price)[1]}}</text>
		              </view>
		              <!-- <view class='singal-price'>
		                <text>￥</text>
		                <text>{{prod.oriPrice}}</text>
		              </view> -->
		              <image src="/static/images/tabbar/basket-sel.png" class="basket-img"></image>
		            </view>
		          </view>
		        </view>
		      </block>
		    </view>
		  </view>
		
		  <!-- 新品推荐 -->
		  <view class="more-prod" v-if="item.style==0">
		    <view class="title">{{item.title}}</view>
		    <view class="prod-show">
		      <block v-for="(prod, index2) in item.prods" :key="index2">
		        <view class="show-item" @tap="toProdPage" :data-prodid="prod.id">
		          <view class="more-prod-pic">
		            <image :src="serverUrl+prod.images" class="more-pic"></image>
		          </view>
		          <view class="prod-text-right">
		            <view class="prod-text more">{{prod.title}}</view>
		            <view class="prod-info">{{prod.flowerLanguage}}</view>
		            <view class="b-cart">
		              <view class="price">
		                <text class="symbol">￥</text>
		                <text class="big-num">{{wxs.parsePrice(prod.price)[0]}}</text>
		                <text class="small-num">.{{wxs.parsePrice(prod.price)[1]}}</text>
		              </view>
		              <!-- <view class='go-to-buy'>立即购买</view> -->
		              <image src="/static/images/tabbar/basket-sel.png" class="basket-img"></image>
		            </view>
		          </view>
		        </view>
		      </block>
		    </view>
		  </view>
		</block>
	</view>
  
</view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
//index.js
//获取应用实例
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");
const app = getApp();

export default {
  data() {
    return {
      indicatorDots: true,
      indicatorColor: '#d1e5fb',
      indicatorActiveColor: '#1b7dec',
      autoplay: true,
      interval: 2000,
      duration: 1000,
      indexImgs: [],
      seq: 0,
      news: [],
      taglist: [],
      sts: 0,
      scrollTop: "",
	  current: 0,
	  updata: true,
	  serverUrl: config.domain
    };
  },

  components: {},
  props: {},
  onLoad: function () {
    this.getAllData();
  },
  onShow: function () {
		//#ifdef MP-WEIXIN
    uni.getSetting({
      success(res) {
        if (!res.authSetting['scope.userInfo']) {
          uni.navigateTo({
            url: '/pages/login/login'
          });
        }
      }
    });
		//#endif
  },
  onPullDownRefresh: function () {
    // wx.showNavigationBarLoading() //在标题栏中显示加载
    //模拟加载
    var ths = this;
    setTimeout(function () {
      ths.getAllData(); // wx.hideNavigationBarLoading() //完成停止加载

      uni.stopPullDownRefresh(); //停止下拉刷新
    }, 100);
  },
  methods: {
    //事件处理函数
    bindViewTap: function () {
      uni.navigateTo({
        url: '../logs/logs'
      });
    },
    // 页面滚动到指定位置指定元素固定在顶部
    onPageScroll: function (e) {
      //监听页面滚动
      this.setData({
        scrollTop: e.scrollTop
      });
    },
    toProdPage: function (e) {
      var prodid = e.currentTarget.dataset.prodid;

      if (prodid) {
        uni.navigateTo({
          url: '/pages/prod/prod?prodid=' + prodid
        });
      }
    },
	//优惠券
    toCouponCenter: function () {
      uni.showToast({
        icon: "none",
        title: '该功能未开发，敬请期待'
      });
    },
    // 跳转搜索页
    toSearchPage: function () {
      uni.navigateTo({
        url: '/pages/search-page/search-page'
      });
    },
    //跳转商品活动页面
    toClassifyPage: function (e) {
	  var title = e.currentTarget.dataset.title;
      var url = '/pages/prod-classify/prod-classify?sts=' + e.currentTarget.dataset.sts+"&title="+title;
      var id = e.currentTarget.dataset.id;
      
      if (id) {
        url += "&tagid=" + id + "&title=" + title;
      }

      uni.navigateTo({
        url: url
      });
    },
    //跳转公告列表页面
    onNewsPage: function () {
      // uni.navigateTo({
      //   url: '/pages/recent-news/recent-news'
      // });
    },

    getAllData() {
      http.getCartCount(); //重新计算购物车总数量

      this.getIndexImgs();
      this.getNoticeList();
      this.getTag();
    },

    //加载轮播图
    getIndexImgs() {
      //加载轮播图
      // var params = {
      //   url: "/indexImgs",
      //   method: "GET",
      //   data: {},
      //   callBack: res => {
      //     this.setData({
      //       indexImgs: res,
      //       seq: res
      //     });
      //   }
      // };
      //http.request(params);
	  this.setData({
	    indexImgs: [
			{
				imgUrl: "/static/images/index/banner/21_birthday_banner_m.jpg",
				relation: 1
			},
			{
				imgUrl: "/static/images/index/banner/flower.png",
				relation: 2
			},
			{
				imgUrl: "/static/images/index/banner/hot.png",
				relation: 3
			}
		],
	    seq: [
			{
				imgUrl: "/static/images/index/banner/21_birthday_banner_m.jpg",
				relation: 4
			},
			{
				imgUrl: "/static/images/index/banner/flower.png",
				relation: 5
			},
			{
				imgUrl: "/static/images/index/banner/hot.png",
				relation: 6
			}
		]
	  });
    },

    getNoticeList() {
      // 加载公告
      var params = {
        url: "/shop/notice/topNoticeList",
        method: "GET",
        data: {},
        callBack: res => {
          this.setData({
            news: res
          });
        }
      };
      //http.request(params);
    },

    // 加载商品标题分组列表
    getTag() {
      var params = {
        url: "/flower/prodTagList",
        method: "GET",
        data: {},
        callBack: res => {
          this.setData({
            taglist: res
          });
        }
      };
      http.request(params);
    },

    // getTagProd(id, index) {
    //   var param = {
    //     url: "/prod/prodListByTagId",
    //     method: "GET",
    //     data: {
    //       tagId: id,
    //       size: 6
    //     },
    //     callBack: res => {
		  // this.updata = false
		  // this.updata = true
    //       var taglist = this.taglist;
    //       taglist[index].prods = res.records;
    //       this.setData({
    //         taglist: taglist
    //       });
    //     }
    //   };
    //   http.request(param);
    // },

    /**
     * 跳转至商品详情
     */
    showProdInfo: function (e) {
      let relation = e.currentTarget.dataset.relation;

      if (relation) {
        uni.navigateTo({
          url: 'pages/prod/prod'
        });
      }
    }
  }
};
</script>
<style>
@import "./index.css";
</style>