<template>
<view class="container">
  <!-- 头部菜单 -->
  <view class="order-tit">
    <text @tap="onStsTap" data-sts='-1' data-tap="1"  :class="tap==1?'on':''">全部</text>
    <text @tap="onStsTap" data-sts="0" data-tap="2" :class="tap==2?'on':''">待审核</text>
    <text @tap="onStsTap" data-sts="1" data-tap="3" :class="tap==3?'on':''">通过</text>
    <text @tap="onStsTap" data-sts="2" data-tap="4" :class="tap==4?'on':''">不通过</text>
	<text @tap="onStsTap" data-sts="3" data-tap="5" :class="tap==5?'on':''">下架</text>
	<text @tap="onConsignTap" data-tap="6" :class="tap==6?'on':''">寄存</text>
  </view>
  <!-- end 头部菜单 -->
  <view class="main">
    <view class="empty" v-if="commodity.length==0">
      还没有任何宝贝，快去发布吧
    </view>
    <!-- 订单列表 -->
    <block v-for="(item, index) in commodity" :key="index">
      <view class="prod-item">
        <view class="order-num">
          <text>宝贝编号：{{item.id}}</text>
          <view class="order-state">
            <text :class="'order-sts  ' + (item.auditStatus==1?'gray':'') + '  ' + ((item.auditStatus==0||item.auditStatus==2||item.auditStatus==3)?'red':'')">{{item.auditStatus==0?'待审核':(item.auditStatus==1?'通过':(item.auditStatus==3?'不通过':''))}}</text>
            <text style="margin-left: 10rpx;" v-if="item.type==1" :class="'order-sts  ' + (item.inventoryStatus==0?'red':'') + '  ' + ((item.inventoryStatus==1||item.inventoryStatus==2||item.inventoryStatus==3||item.inventoryStatus==4)?'gray':'')">{{item.inventoryStatus==0?'待入库':(item.inventoryStatus==1?'已入库':(item.inventoryStatus==2?'已售出':(item.inventoryStatus==3?'捐赠':(item.inventoryStatus==4?'回家':''))))}}</text>
            <view class="clear-btn" v-if="item.auditStatus==3 || item.auditStatus==2 || item.auditStatus==0">
              <image src="/static/images/icon/clear-his.png" class="clear-list-btn" @tap="delOrderList" :data-ordernum="item.id"></image>
            </view>
          </view>
        </view>

        <view>
              <view class="item-cont" @tap="toProdPage" :data-prodid="item.id">
                <view class="prod-pic">
                  <image :src="serverUrl+item.images"></image>
                </view>
                <view class="prod-info">
                  <view class="prodname">
                    {{item.title}}
                  </view>
                  <view class="price-nums">
                    <text class="prodprice"><text class="symbol">￥</text>
                    <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text>
                    <text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text></text>
                  </view>
                </view>
              </view>
        </view>

        <view class="total-num">
          <text class="prodcount">{{item.createTime}}</text>
        </view>
        <!-- end 商品列表 -->
        <view class="prod-foot">
          <view class="btn">
           <text v-if="item.auditStatus==2" class="button" @tap="cancelOrder" :data-ordernum="item.orderId" hover-class="none">下架</text>
            <!-- <text class="button warn" @tap :data-ordernum="item.orderId" hover-class="none">再次购买</text> -->
            <text v-if="item.auditStatus==3" class="button warn" @tap="normalPay" :data-ordernum="item.orderId" hover-class="none">上架</text>
            <text v-if="item.type==1&&item.auditStatus==2&&item.inventoryStatus==1" class="button warn" @tap="onConfirmReceive" :data-ordernum="item.orderId" hover-class="none">捐赠</text>
			<text v-if="item.type==1&&item.auditStatus==2&&item.inventoryStatus==1" class="button warn" @tap="onConfirmReceive" :data-ordernum="item.orderId" hover-class="none">邮寄回家</text>
          </view>
        </view>
      </view>



    </block>


  </view>
</view>
<!-- end 订单列表 -->
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");

export default {
  data() {
    return {
      commodity: [],
      current: 1,
      pages: 0,
      sts: 0,
	  serverUrl: '',
	  type: 0,
	  tap: 1
    };
  },

  components: {},
  props: {},

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (options.sts) {
      this.setData({
        sts: options.sts,
		serverUrl: config.domain
      });
      this.loadOrderData(options.sts,this.type, 1);
    } else {
      this.loadOrderData(null,this.type, 1);
    }
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
      this.loadOrderData(this.sts,this.type, this.current + 1);
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {},
  methods: {
    /**
     * 加载订单数据
     */
    loadOrderData: function (sts ,type , current) {
      var ths = this;
      uni.showLoading(); //加载订单列表

      var params = {
        url: "/flower/MyBabyList",
        method: "POST",
		needToken: true,
        data: {
          pageNum: current,
          pageSize: 10,
          auditStatus: sts?sts:null,
		  type: type
        },
        callBack: function (res) {
          //console.log(res);
          var list = [];
          if (res.data.pageNum == 1) {
            list = res.data.list;
          } else {
            list = ths.commodity;
            Array.prototype.push.apply(list, res.data.list);
          }
          ths.setData({
            commodity: list,
            pages: res.data.pages,
            current: res.data.pageNum
          });
          uni.hideLoading();
        }
      };
      http.request(params);
    },

    /**
     * 状态点击事件
     */
    onStsTap: function (e) {
      var sts = e.currentTarget.dataset.sts;
      this.setData({
        sts: sts==-1? null:sts,
		tap: e.currentTarget.dataset.tap
      });
      this.loadOrderData(this.sts,0, 1);
    },
	
	/**
	 * 状态点击事件
	 */
	onConsignTap: function (e) {
	  this.sts = e.currentTarget.dataset.sts==-1? null:e.currentTarget.dataset.sts;
	  this.setData({
		  tap: e.currentTarget.dataset.tap
	  });
	  this.loadOrderData(null,1, 1);
	},
    //删除已完成||已取消的订单
    delOrderList: function (e) {
      var ths = this;
      uni.showModal({
        title: '',
        content: '确定要删除此宝贝吗？',
        confirmColor: "#eb2444",

        success(res) {
          if (res.confirm) {
            var ordernum = e.currentTarget.dataset.ordernum;
            uni.showLoading();
            var params = {
              url: "/order/" + ordernum,
              method: "DELETE",
			  needToken: true,
              callBack: function (res) {
                ths.loadOrderData(ths.sts,this.type, 1);
                uni.hideLoading();
              }
            };
			uni.hideLoading();
            //http.request(params);
          } else if (res.cancel) {
            console.log('用户点击取消');
          }
        }

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
@import "./MyReleased.css";
</style>