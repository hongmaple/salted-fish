<template>
<!-- 商品详情 -->
<view class="container">
  <view class="input-box">
	  <view class="section">
	    <image style="width: 130rpx;height: 130rpx;" :src="user.avatarImage?serverUrl+user.avatarImage:'../../static/images/icon/head04.png'"></image>
		<text>{{user.username}}</text>
	  </view>
  </view>
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
    <view class="sales-p">{{specification}}</view>
    <view class="prod-price">
      <text class="price">￥<text class="price-num">{{wxs.parsePrice(price)[0]}}</text>.{{wxs.parsePrice(price)[1]}}</text>
      <text class="sales"></text>
    </view>
  </view>
  <view class="sku" @tap="showSku">
    <view class="sku-tit">成色</view>
    <view class="sku-con">{{oldNewLevel}}</view>
    <view class="more">...</view>
  </view>
  <view class="sku" @tap="showSku">
    <view class="sku-tit">运费</view>
    <view class="sku-con">10元</view>
    <view class="more">...</view>
  </view>
  <!-- 已选规格 -->
  <view class="sku" @tap="showSku">
    <view class="sku-tit">已选</view>
    <view class="sku-con">{{prodNum}}件</view>
    <view class="more">...</view>
  </view>
  <!-- 商品详情 -->
  <view class="prod-detail">
    <view>
      <rich-text :nodes="brief"></rich-text>
      <!-- <image src="//img14.360buyimg.com/cms/jfs/t1/25195/1/9487/388554/5c7f80a5E8b8f8f0c/46818404849d6ec6.jpg!q70.dpg" mode="widthFix"></image> -->
    </view>
  </view>
  <!-- end 商品详情 -->
  <!-- 留言 -->
  <view class="cmt-wrap">
    <view class="cmt-tit" @tap="showComment">
      <view class="cmt-t">
        留言
      </view>
      <view class="cmt-count">
        共{{LeaveNumber}}条
        <text class="cmt-more"></text>
      </view>
    </view>
    <view class="cmt-cont">
      <view class="cmt-items">
        <view v-for="(item, index) in littleCommPage" :key="index" class="cmt-item">
          <view class="cmt-user">
            <text class="date">{{item.createdTime}}</text>
            <view class="cmt-user-info">
              <image class="user-img" :src="item.commentatorAvatarImage?serverUrl+item.commentatorAvatarImage:'../../static/images/icon/head04.png'"></image>
              <view class="nickname">{{item.commentator}}</view>
              <!-- <van-rate readonly :value="item.score" @change="onChange" color="#f44"></van-rate> -->
            </view>
          </view>
          <view class="cmt-cnt" @tap="openLeaveMessage" :data-id="item.id" :data-commentator="item.commentator" :data-toUserType="item.creatorType" :data-toUserId="item.creatorId">{{item.content}}</view>
<!--          <scroll-view class="cmt-attr" scroll-x="true" v-if="item.pics.length">
            <image v-for="(commPic, index2) in item.pics" :key="index2" :src="commPic"></image>
          </scroll-view> -->
        </view>
      </view>
      <view class="cmt-more-v" v-if="LeaveNumber > 2">
        <text @tap="showComment">查看全部留言</text>
      </view>
	  <view class="cmt-more-v">
	    <text @tap="openLeaveMessage" data-toUserName="" data-index="-1" data-id="0" data-toUserType="0" data-toUserId="0">留言</text>
	  </view>
    </view>
  </view>
  <!-- 底部按钮 -->
  <view class="cart-footer">
    <view class="btn icon" @tap="toHomePage">
      <image src="/static/images/tabbar/homepage.png"></image>
      首页
    </view>
	<view class="btn icon">
	      <image src="/static/images/tabbar/kefu.png"></image>
	      <text class="apply-service" @tap="toCustomerServiceChat" :data-touserid='user.id' :data-tousername='user.username' :data-fromavatarimage='user.avatarImage'>私聊</text>
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
  <!-- 留言弹窗 -->
  <view class="cmt-popup" v-if="commentShow">
    <view class="cmt-tit">
      <view class="cmt-t">
        留言
      </view>
	  <view class="cmt-count" style="margin-right: 100rpx;">
	    共{{LeaveNumber}}条
	  </view>
      <text class="close" @tap="closePopup"></text>
    </view>
    <view class="cmt-cont">
      <view class="cmt-items">
        <view v-for="(item, index) in prodCommPage.records" :key="index" class="cmt-item">
          <view class="cmt-user">
            <text class="date">{{item.createdTime}}</text>
            <view class="cmt-user-info">
              <image class="user-img" :src="item.commentatorAvatarImage?serverUrl+item.commentatorAvatarImage:'../../static/images/icon/head04.png'"></image>
              <view class="nickname">{{item.commentator}}</view>
              <!-- <van-rate readonly :value="item.score" @change="onChange" color="#f44"></van-rate> -->
            </view>
          </view>
          <view class="cmt-cnt" @tap="openLeaveMessage" :data-toUserName="item.commentator" :data-index="index" :data-id="item.id" :data-commentator="item.commentator" :data-toUserType="item.creatorType" :data-toUserId="item.creatorId">{{item.content}}</view>
<!--          <scroll-view class="cmt-attr" scroll-x="true" v-if="item.pics.length">
            <image v-for="(commPic, index2) in item.pics" :key="index2" :src="commPic"></image>
          </scroll-view> -->
        <!--  <view class="cmt-reply" v-if="item.replyContent">
            <text class="reply-tit">店铺回复：</text> {{item.replyContent}}
          </view> -->
		  <view v-for="(subItem, subIndex) in item.subEvaluationRowVos.list" :key="subIndex" class="cmt-reply">
		    <!-- <text class="reply-tit">店铺回复：</text> 的说法是是否的说法都是发 -->
			<view class="cmt-user" style="padding-left: 50rpx;">
			  <text class="date">{{subItem.createdTime}}</text>
			  <view class="cmt-user-info">
			    <image class="user-img" :src="subItem.commentatorAvatarImage?serverUrl+subItem.commentatorAvatarImage:'../../static/images/icon/head04.png'"></image>
			    <view class="nickname">{{subItem.commentator}}</view>
			    <!-- <van-rate readonly :value="item.score" @change="onChange" color="#f44"></van-rate> -->
			  </view>
			</view>
			<view class="cmt-cnt" @tap="openLeaveMessage" :data-toUserName="subItem.commentator" :data-index="index" style="padding-left: 50rpx;" :data-id="item.id" :data-commentator="subItem.commentator" :data-toUserType="subItem.creatorType" :data-toUserId="subItem.creatorId">回复{{subItem.toUserName}}: {{subItem.content}}</view>
		  </view>
		  <view class="load-more" v-if="item.subEvaluationRowVos.pages > item.subEvaluationRowVos.pageNum">
		    <text @tap="getSubMoreCommPage" :data-pageNum="item.subEvaluationRowVos.pageNum" :data-parentId="item.id" :data-index="index">点击加载更多</text>
		  </view>
        </view>
      </view>
      <view class="load-more" v-if="prodCommPage.pages > prodCommPage.pageNum">
        <text @tap="getMoreCommPage">点击加载更多</text>
      </view>
    </view>
  </view>
  <!-- 评价输入框-->
  <view class="cmt-input-popup" v-if="showLeave">
	  <view class="cmt-input-tit">
	    <text class="close" @tap="closeInputPopup"></text>
	  </view>
	  <view>
		<textarea style="height: 260rpx;width: 100%;padding-top: 30rpx;" :placeholder="leavePla" type="text" maxlength="600" :value="evaluation.content" @input="onLeaveMessageInput"></textarea>
		<!-- 功能按钮 -->
		<view class="btn-box">
		  <view class="keep btn" @tap="onSendLeave">
		    <text>发送</text>
		  </view>
		</view>
		<!-- end 功能按钮 -->
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
      brief: '',
      prodId: 0,
      specification: '',
      skuId: 0,
	  oldNewLevel: '',
      popupShow: false,
      // 是否获取过用户领取过的优惠券id
      loadCouponIds: false,
      skuShow: false,
      commentShow: false,
      skuGroup: {},
      defaultSku: undefined,
      selectedProp: [],
      selectedPropObj: {},
      propKeys: [],
      allProperties: [],
      prodCommData: {
		  number: 0
	  },
      prodCommPage: {
        current: 0,
        pages: 0,
        records: []
      },
      littleCommPage: [],
      isCollection: false,
	  serverUrl: config.domain,
	  user: {},
	  sellerId: 0,
	  sellerType: 0, //卖家类型，0客户，1系统代理人
	  backgroundAgentId: 0,
	  showLeave: false,
	  evaluation: {},
	  LeaveNumber: 0,
	  leavePla: '评论千万条,友善第一条',
	  leaveIndex: -1,
	  loginResult: {},
	  toUserName: ''
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
	  loginResult: uni.getStorageSync("loginResult"),
      prodId: options.prodid
    }); 
	// 加载商品信息

    this.getProdInfo(); 
	this.getCartInfo();
	// 加载评论数据

    this.getProdCommData(); // 加载评论项
	this.getLittleProdComm();

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
	   * @param {Object} e
	   * 私聊
	 */
	toCustomerServiceChat: function (e) {
	      const toUserId = e.currentTarget.dataset.touserid;
	  	  const toUsername = e.currentTarget.dataset.tousername;
		  const toAvatarimage = e.currentTarget.dataset.fromavatarimage;
	        uni.navigateTo({
	          url: '/pages/CustomerServiceChat/CustomerServiceChat?toUserId='+toUserId+'&toUsername='+toUsername+"&toAvatarimage="+toAvatarimage
	  });
	},
    /**
     * 添加或者取消收藏商品 
     */
    addOrCannelCollection() {
      uni.showLoading();
	  var favoriteId =this.prodId;
	  var params;
	  if(!this.isCollection) {
		  params = {
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
	  }else {
		 params = {
		   url: `/flower/favorite/${favoriteId}`,
		   needToken: true,
		   method: "delete",
		   callBack: res => {
		     this.setData({
		       isCollection: !this.isCollection
		     });
		     uni.hideLoading();
		   }
		 }; 
	  }
      http.request(params);
    },
    // 获取商品信息
    getProdInfo() {
      uni.showLoading();
      var params = {
        url: `/flower/prodInfo/${this.prodId}`,
        method: "GET",
        callBack: res => {
          var imgStrs = res.imagesList;
		  var imgs;
		  if(imgStrs!=null) {
			  var array = imgStrs.split(",");
			  imgs = this.trimSpace(array);
		  }
          //var content = util.formatHtml(res.content);
		  var sellerType = 0;
		  var sellerId=res.createId
		  var toUserId = sellerId;
		  console.log(res.backgroundAgentId);
		  if(res.backgroundAgentId>0) {
			  sellerType=1;
			  sellerId=res.backgroundAgentId
			  toUserId='admin'+res.backgroundAgentId
		  }
          this.setData({
            imgs: imgs,
            brief: res.brief,
            price: res.price,
            prodName: res.title,
            prodId: res.id,
            specification: "规格："+res.specification,
            pic: res.images,
			oldNewLevel: res.oldNewLevel,
			backgroundAgentId: res.backgroundAgentId,
			sellerId: sellerId,
			sellerType: sellerType
          }); 
		  var url = `/user/${sellerId}`
		  if(sellerType==1) {
			  url = `/background/user/${sellerId}`
		  }
		  var params = {
		    url: url,
		    method: "GET",
		    callBack: res2 => {
			  var user = {
				  id: toUserId,
				  username: res2.data.username
			  }
		      this.setData({
		          user: user
		      }); 
		  		  
		      uni.hideLoading();
		    }
		  };
		  http.request(params);
          uni.hideLoading();
        }
      };
      http.request(params);
    },
	trimSpace(array) {  
	     for(var i = 0 ;i<array.length;i++)  
	     {  
	         if(array[i] == "" || array[i] == null || typeof(array[i]) == "undefined")  
	         {  
	                  array.splice(i,1);  
	                  i= i-1;  
	         }  
	     }  
	     return array;  
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
        url: `/evaluation/count/${this.prodId}`,
        method: "GET",
        data: {
        },
        callBack: res => {
          this.setData({
            LeaveNumber: res
          });
        }
      });
    },

    // 获取部分评论
    getLittleProdComm() {
      var query = {
      	pageNum: 1,
      	pageSize: 5,
      	flowerId: this.prodId
      }
      this.getProdCommPage(query);
    },
	getSubMoreCommPage(e) {
		var index = e.currentTarget.dataset.index;
		var parentId = e.currentTarget.dataset.parentId;
		var pageNum = e.currentTarget.dataset.pageNum+1;
		console.log(pageNum)
		var query = {
			pageNum: pageNum,
			pageSize: 3,
			flowerId: this.prodId,
			parentId: parentId
		}
		http.request({
		  url: "/evaluation/sub/list",
		  method: "POST",
		  data: query,
		  callBack: res => {
			  console.log(res);
		    let records = this.prodCommPage.records;
			let subEvaluationRowVos =  records[index].subEvaluationRowVos;
			let subList = subEvaluationRowVos.list
			subList = subList.concat(res.list);
			subEvaluationRowVos.list = subList;
			subEvaluationRowVos.pages = res.pages;
			subEvaluationRowVos.pageNum = res.pageNum;
			records[index].subEvaluationRowVos = subEvaluationRowVos;
			console.log(records[index]);
		    this.setData({
		      prodCommPage: {
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
    getMoreCommPage(e) {
		var pageNum = this.prodCommPage.current+1;
		var query = {
			pageNum: pageNum,
			pageSize: 5,
			flowerId: this.prodId
		}
		this.getProdCommPage(query);
    },

    // 获取分页获取一级评论
    getProdCommPage(query) {
      http.request({
        url: "/evaluation/list",
        method: "POST",
        data: query,
        callBack: res => {
          let records = this.prodCommPage.records;
          records = records.concat(res.list);
          this.setData({
            prodCommPage: {
              current: res.pageNum,
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
		if(this.prodNum==0) {
			uni.showToast({
			  title: "至少选择一件",
			  icon: "none"
			});
			return;
		}
		var params = {
		  url: "/cart",
		  method: "POST",
		  needToken: true,
		  data: {
		    skuId: this.prodId,
		    title: this.prodName,
		    image: this.pic,
		    price: this.price,
			num: this.prodNum,
	        sellerId: this.sellerId,
		    backgroundAgentId: this.backgroundAgentId
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
			num: 1,
			image: this.pic,
			sellerId: this.sellerId,
			backgroundAgentId: this.backgroundAgentId
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
    },
	closeInputPopup: function () {
      this.setData({
        showLeave: false,
      });
    },
	openLeaveMessage: function (e) {
		this.setData({
		  evaluation: {}
		});
		let id = e.currentTarget.dataset.id;
		let toUserType = e.currentTarget.dataset.toUserType;
		let toUserId = e.currentTarget.dataset.toUserId;
		let commentator = e.currentTarget.dataset.commentator;
		let index = e.currentTarget.dataset.index;
		let leavePla = commentator?'回复 '+commentator:'评论千万条,友善第一条';
		let toUserName = e.currentTarget.dataset.toUserName;
		this.setData({
		  'evaluation.parentId': id,
		  'evaluation.flowerId': this.prodId,
		  'evaluation.toUserType': toUserType,
		  'evaluation.toUserId': toUserId,
		  leavePla: leavePla,
		  leaveIndex: index,
		  toUserName: toUserName
		});
		this.showLeave=true;
	},
	onLeaveMessageInput: function(e) {
		this.setData({
		  'evaluation.content': e.detail.value
		});
	},
	onSendLeave: function(e) {
		console.log(this.evaluation)
		var evaluation = this.evaluation;
		var params = {
		  url: "/evaluation",
		  method: "POST",
		  needToken: true,
		  data: evaluation,
		  callBack: res => {
		    uni.hideLoading();
		    uni.showToast({
		      title: "发送成功",
		      icon: "none"
		    });
			let index = this.leaveIndex;
			let records = this.prodCommPage.records;
			let user = this.loginResult.data;
			console.log(user);
			let avatarImage = user.avatarImage;
			let username = user.username;
			let toUserName = this.toUserName;
			var evaluationRowVo = evaluation;
			evaluationRowVo.commentator = username;
			evaluationRowVo.commentatorAvatarImage = avatarImage;
			evaluationRowVo.toUserName = toUserName;
			var evaluationRowVos = [];
			evaluationRowVos[0] = evaluationRowVo;
			if(index==-1) {
				records = records.concat(evaluationRowVos);
			}else{
				let subEvaluationRowVos =  records[index].subEvaluationRowVos;
				let subList = subEvaluationRowVos.list
				subList = subList.concat(evaluationRowVos);
				subEvaluationRowVos.list = subList;
				records[index].subEvaluationRowVos = subEvaluationRowVos;
			}
			this.setData({
			  showLeave: false,
			  prodCommPage: {
			    records: records
			  }
			});
		  }
		};
		http.request(params);
	}
  }
};
</script>
<style>
@import "./prod.css";
</style>