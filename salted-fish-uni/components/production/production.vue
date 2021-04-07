<template>
<view class="prod-items" @tap="toProdPage" :data-prodid="item.id">
      <view class="hot-imagecont">
        <image :src="serverUrl+item.images" class="hotsaleimg"></image>
      </view>
      <view class="hot-text">
        <view class="hotprod-text">{{item.title}}</view>
        <view class="prod-text-info">
          <view class="price">
            <text class="symbol">￥</text>
            <text class="big-num">{{wxs.parsePrice(item.price)[0]}}</text>
            <text class="small-num">.{{wxs.parsePrice(item.price)[1]}}</text>
          </view>
        </view>
      </view>
    </view>
</template>

<script module="wxs" lang="wxs" src="../../wxs/number.wxs"></script>

<script>
var config = require("../../utils/config.js");
export default {
  data() {
    return {
		serverUrl: config.domain
	};
  },
  components: {},
  props: {
    item: Object,
    sts: Number
  },
  methods: {
    toProdPage: function (e) {
      var prodid = e.currentTarget.dataset.prodid;
	  console.log(prodid);
      uni.navigateTo({
        url: '/pages/prod/prod?prodid=' + prodid
      });
    }
  }
};
</script>
<style>
@import "./production.css";
</style>