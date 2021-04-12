<template>
  <div class="nofind">
    <el-dialog
      :title="dialong.title"
      type="primary"
      size="small"
      :close-on-press-escape="false"
      :modal-append-to-body="false"
      :close-on-click-modal="false"
      :visible.sync="dialong.show"
    >
      <el-form :model="form" ref="formdoalog" label-width="80px">
        <el-form-item label="分类名" prop="cid">
          <h6 v-text="form.categoryName"></h6>
        </el-form-item>
         <el-form-item label="标题" prop="title">
          <h6 v-text="form.title"></h6>
        </el-form-item>
        <el-form-item label="封面" prop="images">
           <img alt="图片" v-if="form.images" :src="'api/'+form.images" class="avatar">
        </el-form-item>
        <el-form-item label="图片列表" prop="imagesList">
          <div v-for="(item,i) in parameter.dialogImageUrls" :key="i">
              <img width="100%" :src="item.url" alt="">
          </div>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <h6 v-text="form.price"></h6>
        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <h6 v-text="form.specification"></h6>
        </el-form-item>
        <el-form-item label="新旧程度" prop="oldNewLevel">
          <h6 v-text="form.oldNewLevel"></h6>
        </el-form-item>
        <el-form-item label="简介" prop="oldNewLevel">
          <h6 v-text="form.brief"></h6>
        </el-form-item>
        <el-form-item label="联系方式" prop="oldNewLevel">
          <h6 v-text="form.contactWay"></h6>
        </el-form-item>
        <el-form-item label="寄放日期" prop="oldNewLevel">
           <span style="margin-left: 10px">{{ form.depositingTime | moment }}</span>
        </el-form-item>
        <el-form-item label="发布时间" prop="oldNewLevel">
           <span style="margin-left: 10px">{{ form.createTime | moment }}</span>
        </el-form-item>
        <el-form-item label="代理状态" prop="auditStatus">
           <span style="margin-left: 10px" v-text="form.auditStatus==0?'待入库':(form.auditStatus==1?'已入库':(form.auditStatus==2?'不通过':(form.auditStatus==3?'已售出':(form.auditStatus==4?'捐赠':'邮寄回家'))))"></span>
        </el-form-item>
        <el-form-item label="状态" prop="saleable">
           <span style="margin-left: 10px" v-text="form.saleable?'上架':'下架'"></span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialong.show = false">关闭</el-button>
        <el-button v-if="form.auditStatus==0||form.auditStatus==2" type="primary" @click="addHandle('1')">通过</el-button>
        <el-button v-if="form.auditStatus==0||form.auditStatus==1" type="primary" @click="addHandle('2')">不通过</el-button>
        <el-button v-if="form.auditStatus==1&&form.saleable" type="primary" @click="saleableHandle('false')">下架</el-button>
        <el-button v-if="form.auditStatus==1&&!form.saleable" type="primary" @click="saleableHandle('true')">上架</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: "theAgencyDialong",
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false
    };
  },
  props: {
    dialong: Object,
    form: {},
    parameter: {
        id: 0,
        dialogImageUrls: []
    },
    value: ''
  },
  methods: {
    addHandle(auditStatus) {
             this.$axios.put(`api/flower/agency/${this.form.id}/${auditStatus}`, null,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据修改成功"
                });
                (this.dialong.show = false);
                this.$emit("flowerData");
                //清空内容
                this.form = null;
                this.parameter.dialogImageUrls = [];
            });
    },
    saleableHandle(saleable) {
             this.$axios.put(`api/flower/agency/saleable/${this.form.id}/${saleable}`, null,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据修改成功"
                });
                (this.dialong.show = false);
                this.$emit("flowerData");
                //清空内容
                this.form = null;
                this.parameter.dialogImageUrls = [];
            });
    }
  },
  created() {
      
  }
};
</script>
<style scoped>
 .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
