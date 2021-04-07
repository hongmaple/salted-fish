<template>
<!--pages/editAddress/editAddress.wxml-->

<view class="container">
  <!--input列表 -->
  <view class="input-box">
    <view class="section">
      <text>收 货 人</text>
      <input placeholder="姓名" type="text" maxlength="15" :value="consignee" @input="onReceiverInput"></input>
    </view>
    <view class="section">
      <text>手机号码</text>
      <input placeholder="11位手机号码" type="number" maxlength="11" :value="consigneePhone" @input="onMobileInput"></input>
    </view>
    <view class="section">
      <text>省</text>
     <input placeholder="请输入省" type="text" maxlength="10" :value="province" @input="onProvinceInput"></input>
    </view>
	<view class="section">
	  <text>市</text>
	 <input placeholder="请输入市" type="text" maxlength="10" :value="city" @input="onCityInput"></input>  
	</view>
	<view class="section">
	  <text>区</text>
	 <input placeholder="请输入区" type="text" maxlength="10" :value="district" @input="onDistrictInput"></input>
	</view>
    <view class="section">
      <text>详细地址</text>
      <input placeholder="如楼号/单元/门牌号" type="text" :value="detail" @input="onAddrInput"></input>
    </view>
  </view>
  <!-- end input列表 -->
  <!-- 功能按钮 -->
  <view class="btn-box">
    <view class="keep btn" @tap="onSaveAddr">
      <text>保存收货地址</text>
    </view>

    <view class="clear btn" @tap="onDeleteAddr" v-if="addrId!=0">
      <text>删除收货地址</text>
    </view>
  </view>
  <!-- end 功能按钮 -->
</view>
</template>

<script>
// pages/editAddress/editAddress.js
var http = require("../../utils/http.js");
var config = require("../../utils/config.js");
var index = [18, 0, 0];
var t = 0;
var show = false;
var moveY = 200;

export default {
  data() {
    return {
      province: "",
      city: "",
      district: "",
      consignee: "",
      consigneePhone: "",
      detail: "",
      addrId: 0,
      animation: "",
      show: "",
      region: "",
	  isUpdata: false
    };
  },

  components: {},
  props: {},
  onLoad: function (options) {
    if (options.addrId) {
      uni.showLoading();
      var params = {
        url: "/address/" + options.addrId,
        method: "GET",
		needToken: true,
        data: {},
        callBack: res => {
          //console.log(res)
          this.setData({
            province: res.province,
            city: res.city,
            district: res.district,
            receiver: res.receiver,
            consigneePhone: res.consigneePhone,
			consignee: res.consignee,
            detail: res.detail,
            addrId: res.id,
			isUpdata: true
          });
          uni.hideLoading();
        }
      };
      http.request(params);
    }
  },

  /**
  * 生命周期函数--监听页面显示
  */
  onShow: function () {},
  onReady: function () {
    this.animation = uni.createAnimation({
      transformOrigin: "50% 50%",
      duration: 0,
      timingFunction: "ease",
      delay: 0
    });
    this.animation.translateY(200 + 'vh').step();
    this.setData({
      animation: this.animation.export(),
      show: show
    });
  },
  methods: {
    bindRegionChange: function (e) {
      //console.log('picker发送选择改变，携带值为', e.detail.value)
      this.setData({
        region: e.detail.value
      });
    },

    /**
     * 保存地址
     */
    onSaveAddr: function () {
      var ths = this;
      var consignee = ths.consignee;
      var consigneePhone = ths.consigneePhone;
      var detail = ths.detail;

      if (!consignee) {
        uni.showToast({
          title: '请输入收货人姓名',
          icon: "none"
        });
        return;
      }

      if (!consigneePhone) {
        uni.showToast({
          title: '请输入手机号码',
          icon: "none"
        });
        return;
      }

      if (consigneePhone.length != 11) {
        uni.showToast({
          title: '请输入正确的手机号码',
          icon: "none"
        });
        return;
      }

      if (!detail) {
        uni.showToast({
          title: '请输入详细地址',
          icon: "none"
        });
        return;
      }

      uni.showLoading();
      var url = "/address";
      var method = "POST";
       
      if (ths.isUpdata) {
        url = "/address";
        method = "PUT";
      } //添加或修改地址
      var params = {
        url: url,
        method: method,
		needToken: true,
        data: {
          consignee: ths.consignee,
          consigneePhone: ths.consigneePhone,
          detail: ths.detail,
          province: ths.province,
          city: ths.city,
          district: ths.district,
          id: ths.addrId
        },
        callBack: function (res) {
          uni.hideLoading();
          uni.navigateBack({
            delta: 1
          });
        }
      };
      http.request(params);
    },
    onReceiverInput: function (e) {
      this.setData({
        consignee: e.detail.value
      });
    },
    onMobileInput: function (e) {
      this.setData({
        consigneePhone: e.detail.value
      });
    },
	onProvinceInput: function(e) {
		this.setData({
		  province: e.detail.value
		});
	},
	onCityInput: function(e) {
		this.setData({
		  city: e.detail.value
		});
	},
	onDistrictInput: function(e) {
		this.setData({
		  district: e.detail.value
		});
	},
    onAddrInput: function (e) {
      this.setData({
        detail: e.detail.value
      });
    },
    //删除配送地址
    onDeleteAddr: function (e) {
      var ths = this;
      uni.showModal({
        title: '',
        content: '确定要删除此收货地址吗？',
        confirmColor: "#eb2444",
        success(res) {
          if (res.confirm) {
            var addrId = ths.addrId;
            uni.showLoading();
            var params = {
              url: "/address/" + addrId,
              method: "DELETE",
			  needToken: true,
              data: {},
              callBack: function (res) {
                uni.hideLoading();
                uni.navigateBack({
                  delta: 1
                });
              }
            };
            http.request(params);
          } else if (res.cancel) {
            console.log('用户点击取消');
          }
        }

      });
    }
  }
};
</script>
<style>
@import "./editAddress.css";
</style>