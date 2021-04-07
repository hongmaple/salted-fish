<template>
<!-- 商品详情 -->
<view class="container">
  <!-- 轮播图 -->
  <swiper :indicator-dots="indicatorDots" :autoplay="autoplay" :indicator-color="indicatorColor" :interval="interval" :duration="duration" :indicator-active-color="indicatorActiveColor">
    <block v-for="(item, index) in imgs" :key="index">
      <swiper-item>
        <image :src="serverUrl+item"></image>
      </swiper-item>
    </block>
  </swiper>
  <!-- end  轮播图 -->
  <!-- 商品信息 -->
  <!-- <block wx:for="{{imgs}}" wx:key=''> -->
  <view class="prod-info">
    <view class="tit-wrap">
      <view class="prod-tit">{{prodName}}</view>
      <view class="col" @tap="addOrCannelCollection">
        <image v-if="!isCollection" src="/static/images/icon/prod-col.png"></image>
        <image v-if="isCollection" src="/static/images/icon/prod-col-red.png"></image>
        收藏
      </view>
    </view>
    <view class="sales-p">{{brief}}</view>
    <view class="prod-price">
      <text class="price">￥<text class="price-num">{{wxs.parsePrice(price)[0]}}</text>.{{wxs.parsePrice(price)[1]}}</text>
      <text class="sales"></text>
    </view>
  </view>
  <!-- 已选规格 -->
  <view class="sku" @tap="showSku">
    <view class="sku-tit">已选</view>
    <view class="sku-con">{{prodNum}}件</view>
    <view class="more">...</view>
  </view>
  <!-- 评价 -->
  <view class="cmt-wrap" style="display:none;">
    <view class="cmt-tit" @tap="showComment">
      <view class="cmt-t">
        评价
        <text class="cmt-good">好评{{prodCommData.positiveRating}}%</text>
      </view>
      <view class="cmt-count">
        共{{prodCommData.number}}条
        <text class="cmt-more"></text>
      </view>
    </view>
    <view class="cmt-cont">
      <view class="cmt-tag" @tap="showComment">
        <text>全部({{prodCommData.number}})</text>
        <text>好评({{prodCommData.praiseNumber}})</text>
        <text>中评({{prodCommData.secondaryNumber}})</text>
        <text>差评({{prodCommData.negativeNumber}})</text>
        <text>有图({{prodCommData.picNumber}})</text>
      </view>
      <view class="cmt-items">
        <view v-for="(item, index) in littleCommPage" :key="index" class="cmt-item">
          <view class="cmt-user">
            <text class="date">{{item.recTime}}</text>
            <view class="cmt-user-info">
              <image class="user-img" :src="item.pic"></image>
              <view class="nickname">{{item.nickName}}</view>
              <!-- <van-rate readonly :value="item.score" @change="onChange" color="#f44"></van-rate> -->
            </view>
          </view>
          <view class="cmt-cnt">{{item.content}}</view>
          <scroll-view class="cmt-attr" scroll-x="true" v-if="item.pics.length">
            <image v-for="(commPic, index2) in item.pics" :key="index2" :src="commPic"></image>
          </scroll-view>
        </view>
      </view>
      <view class="cmt-more-v" v-if="prodCommPage.records.length > 2">
        <text @tap="showComment">查看全部评价</text>
      </view>
    </view>
  </view>
  <!-- 商品详情 -->
  <view class="prod-detail">
    <view>
      <rich-text :nodes="content"></rich-text>
      <!-- <image src="//img14.360buyimg.com/cms/jfs/t1/25195/1/9487/388554/5c7f80a5E8b8f8f0c/46818404849d6ec6.jpg!q70.dpg" mode="widthFix"></image> -->
    </view>
  </view>
  <!-- end 商品详情 -->
  <!-- 底部按钮 -->
  <view class="cart-footer">
    <view class="btn icon" @tap="toHomePage">
      <image src="/static/images/tabbar/homepage.png"></image>
      首页
    </view>
    <view class="btn icon" @tap="toCartPage">
      <image src="/static/images/tabbar/basket.png"></image>
      购物车
      <view class="badge badge-1" v-if="totalCartNum>0">{{totalCartNum}}</view>
    </view>
    <view class="btn cart" @tap="addToCart">
      <text>加入购物车</text>
    </view>
    <view class="btn buy" @tap="buyNow">
      <text>立即购买</text>
    </view>
  </view>


  <!-- 规格弹窗 -->
  <view class="pup-sku" v-if="skuShow">
    <view class="pup-sku-main">
      <view class="pup-sku-header">
        <image class="pup-sku-img" :src="serverUrl+pic"></image>
        <view class="pup-sku-price">
          ￥
          <text class="pup-sku-price-int">{{wxs.parsePrice(price)[0]}}</text> .{{wxs.parsePrice(price)[1]}}
        </view>
        <view class="pup-sku-prop">
          <text>已选</text>{{prodNum}}件
        </view>
        <view class="close" @tap="closePopup"></view>
      </view>
      <view class="pup-sku-body">
        <view class="pup-sku-count">
          <view class="num-wrap">
            <view class="minus" @tap="onCountMinus">
              <text class="row"></text>
            </view>
            <view class="text-wrap">
              <input type="number" :value="prodNum" disabled></input>
            </view>
            <view class="plus" @tap="onCountPlus">
              <text class="row"></text>
              <text class="col"></text>
            </view>
          </view>
          <view class="count-name">数量</view>
        </view>
      </view>
      <view class="pup-sku-footer">
        <view class="btn cart" @tap="addCartRequest">加入购物车</view>
        <view class="btn buy" @tap="buyNow">立即购买</view>
      </view>
    </view>
  </view>
  <!-- 评价弹窗 -->
  <view class="cmt-popup" v-if="commentShow">
    <view class="cmt-tit">
      <view class="cmt-t">
        商品评价
        <text class="cmt-good">好评度{{prodCommData.positiveRating}}%</text>
      </view>
      <text class="close" @tap="closePopup"></text>
    </view>
    <view class="cmt-cont">
      <view class="cmt-tag">
        <text @tap="getProdCommPage" data-evaluate="-1" :class="evaluate==-1?'selected':''">全部({{prodCommData.number}})</text>
        <text @tap="getProdCommPage" data-evaluate="0" :class="evaluate==0?'selected':''">好评({{prodCommData.praiseNumber}})</text>
        <text @tap="getProdCommPage" data-evaluate="1" :class="evaluate==1?'selected':''">中评({{prodCommData.secondaryNumber}})</text>
        <text @tap="getProdCommPage" data-evaluate="2" :class="evaluate==2?'selected':''">差评({{prodCommData.negativeNumber}})</text>
        <text @tap="getProdCommPage" data-evaluate="3" :class="evaluate==3?'selected':''">有图({{prodCommData.picNumber}})</text>
      </view>
      <view class="cmt-items">
        <view v-for="(item, index) in prodCommPage.records" :key="index" class="cmt-item">
          <view class="cmt-user">
            <text class="date">{{item.recTime}}</text>
            <view class="cmt-user-info">
              <image class="user-img" :src="item.pic"></image>
              <view class="nickname">{{item.nickName}}</view>
              <!-- <van-rate readonly :value="item.score" @change="onChange" color="#f44"></van-rate> -->
            </view>
          </view>
          <view class="cmt-cnt">{{item.content}}</view>
          <scroll-view class="cmt-attr" scroll-x="true" v-if="item.pics.length">
            <image v-for="(commPic, index2) in item.pics" :key="index2" :src="commPic"></image>
          </scroll-view>
          <view class="cmt-reply" v-if="item.replyContent">
            <text class="reply-tit">店铺回复：</text> {{item.replyContent}}
          </view>
        </view>
      </view>
      <view class="load-more" v-if="prodCommPage.pages > prodCommPage.current">
        <text @tap="getMoreCommPage">点击加载更多</text>
      </view>
    </view>
  </view>
</view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
// pages/prod/prod.js
const app = getApp();
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");
var util = require("../../utils/util.js");
// import vanRate from "../../vant/rate/index";

export default {
  data() {
    return {
      // picDomain: config.picDomain,
      indicatorDots: true,
      indicatorColor: '#f2f2f2',
      indicatorActiveColor: '#eb2444',
      autoplay: true,
      interval: 3000,
      duration: 1000,
      prodNum: 0,
      totalCartNum: 0,
      pic: "",
      imgs: '',
      prodName: '',
      price: 0,
      content: '',
      prodId: 0,
      brief: '',
      skuId: 0,
      popupShow: false,
      // 是否获取过用户领取过的优惠券id
      loadCouponIds: false,
      skuShow: false,
      commentShow: false,
      skuList: [],
      skuGroup: {},
      defaultSku: undefined,
      selectedProp: [],
      selectedPropObj: {},
      propKeys: [],
      allProperties: [],
      prodCommData: {},
      prodCommPage: {
        current: 0,
        pages: 0,
        records: []
      },
      littleCommPage: [],
      evaluate: -1,
      isCollection: false,
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
      prodId: options.prodid
    }); 
	// 加载商品信息

    this.getProdInfo(); 
	this.getCartInfo();
	// 加载评论数据

    //this.getProdCommData(); // 加载评论项

    this.getCollection();
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {},

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      totalCartNum: app.globalData.totalCartCount
    });
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

  /**
   * 分享设置
   */
  onShareAppMessage: function (res) {
    return {
      title: this.prodName,
      path: '/pages/prod/prod?prodid=' + this.prodid
    };
  },
  methods: {
    /**
     * 添加或者取消收藏商品 
     */
    addOrCannelCollection() {
      uni.showLoading();
	  var favoriteId =this.prodId;
      var params = {
        url: "/flower/favorite",
		needToken: true,
        method: "POST",
        data: {
         id: 0,
         createTime: null,
         favoriteId: favoriteId
        },
        callBack: res => {
          this.setData({
            isCollection: !this.isCollection
          });
          uni.hideLoading();
        }
      };
      http.request(params);
    },
    // 获取商品信息
    getProdInfo() {
      uni.showLoading();
	  console.log(this.prodId);
      var params = {
        url: `/flower/prodInfo/${this.prodId}`,
        method: "GET",
        callBack: res => {
          var imgStrs = res.imagesList;
		  var imgs;
		  if(imgStrs!=null) {
			  imgs = imgStrs.split(",");
		  }
          //var content = util.formatHtml(res.content);
          this.setData({
            imgs: imgs,
            content: "花语："+res.flowerLanguage,
            price: res.price,
            prodName: res.title,
            prodId: res.id,
            brief: "适宜人群："+res.appropriateCrowd,
            // skuId: res.skuId
            skuList: null,
            pic: res.images
          }); 
          uni.hideLoading();
        }
      };
      http.request(params);
    },
    getCartInfo() {
		uni.showLoading();
		var params = {
		  url: `/cart/bySkuId/${this.prodId}`,
		  method: "GET",
		  callBack: res => {
			  if(res) {
				  this.setData({
				    prodNum: res.num,
				  }); 
			  }
		    uni.hideLoading();
		  }
		};
		http.request(params);
	},
	getCollection() {
		var params = {
		  url: `/flower/favorite/${this.prodId}`,
		  method: "GET",
		  needToken: true,
		  callBack: res => {
			  if(res) {
				  this.setData({
				    isCollection: res.data,
				  }); 
			  }
		    uni.hideLoading();
		  }
		};
		http.request(params);
	},
    getProdCommData() {
      http.request({
        url: "/prodComm/prodCommData",
        method: "GET",
        data: {
          prodId: this.prodId
        },
        callBack: res => {
          this.setData({
            prodCommData: res
          });
        }
      });
    },

    // 获取部分评论
    getLittleProdComm() {
      if (this.prodCommPage.records.length) {
        return;
      }

      //this.getProdCommPage();
    },

    getMoreCommPage(e) {
      //this.getProdCommPage();
    },

    // 获取分页获取评论
    getProdCommPage(e) {
      if (e) {
        if (e.currentTarget.dataset.evaluate === this.evaluate) {
          return;
        }

        this.setData({
          prodCommPage: {
            current: 0,
            pages: 0,
            records: []
          },
          evaluate: e.currentTarget.dataset.evaluate
        });
      }

      http.request({
        url: "/prodComm/prodCommPageByProd",
        method: "GET",
        data: {
          prodId: this.prodId,
          size: 10,
          current: this.prodCommPage.current + 1,
          evaluate: this.evaluate
        },
        callBack: res => {
          res.records.forEach(item => {
            if (item.pics) {
              item.pics = item.pics.split(',');
            }
          });
          let records = this.prodCommPage.records;
          records = records.concat(res.records);
          this.setData({
            prodCommPage: {
              current: res.current,
              pages: res.pages,
              records: records
            }
          }); // 如果商品详情中没有评论的数据，截取两条到商品详情页商品详情

          if (!this.littleCommPage.length) {
            this.setData({
              littleCommPage: records.slice(0, 2)
            });
          }
        }
      });
    },
    /**
     * 跳转到首页
     */
    toHomePage: function () {
      uni.switchTab({
        url: '/pages/index/index'
      });
    },

    /**
     * 跳转到购物车
     */
    toCartPage: function () {
      uni.switchTab({
        url: '/pages/basket/basket'
      });
    },

    /**
     * 加入购物车
     */
    addToCart: function () {
		this.setData({
		  skuShow: true
		});
    },
	addCartRequest: function() {
		uni.showLoading({
		  mask: true
		});
		var params = {
		  url: "/cart",
		  method: "POST",
		  needToken: true,
		  data: {
		    skuId: this.prodId,
		    title: this.prodName,
		    image: this.pic,
		    price: this.price,
			num: this.prodNum
		  },
		  callBack: res => {
			var totalCartNum = http.getCartCount();
		    this.setData({
		      totalCartNum: totalCartNum
		    });
		    uni.hideLoading();
		    uni.showToast({
		      title: "加入购物车成功",
		      icon: "none"
		    });
			this.setData({
			  skuShow: false
			});
		  }
		};
		http.request(params);
	},

    /**
     * 立即购买
     */
    buyNow: function () {
		var item = {
			skuId: this.prodId,
			title: this.prodName,
			price: this.price,
			prodCount: 1,
			pic: this.pic
      };
      uni.setStorageSync("orderItem", JSON.stringify(item));
      uni.navigateTo({
        url: '/pages/submit-order/submit-order?orderEntry=1'
      });
    },

    /**
     * 减数量
     */
    onCountMinus: function () {
      var prodNum = this.prodNum;

      if (prodNum > 1) {
        this.setData({
          prodNum: prodNum - 1
        });
      }
    },

    /**
     * 加数量
     */
    onCountPlus: function () {
      var prodNum = this.prodNum;

      if (prodNum < 1000) {
        this.setData({
          prodNum: prodNum + 1
        });
      }
    },
    showComment: function () {
      this.setData({
        commentShow: true
      });
    },
    closePopup: function () {
      this.setData({
        popupShow: false,
        skuShow: false,
        commentShow: false
      });
    }
  }
};
</script>
<style>
@import "./prod.css";
</style>