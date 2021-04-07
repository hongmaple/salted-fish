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
      <el-form :model="form" ref="formdoalog" :rules="formdialog" label-width="80px">
        <el-form-item label="分类名" prop="cid">
          <el-select v-model="form.cid" placeholder="请选择">
                <el-option
                v-for="item in parameter.options"
                :key="item.id"
                :label="item.name"
                :value="item.id">
                </el-option>
          </el-select>
        </el-form-item>
         <el-form-item label="标题" prop="title">
          <el-input v-model="form.title"></el-input>
        </el-form-item>
        <el-form-item label="封面" prop="images">
           <el-upload
                class="avatar-uploader"
                action="/api/common/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                v-model="form.images"
                >
                <img alt="图片" v-if="form.images" :src="'api/'+form.images" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="图片列表" prop="imagesList">
            <el-upload
              action="/api/common/upload"
              list-type="picture-card"
              :file-list="parameter.dialogImageUrls"
              :on-preview="handlePictureCardPreview"
              :before-upload="beforeAvatarUpload"
              :on-success="handleAvatarSuccessList"
              :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="'api'+dialogImageUrl" alt="">
            </el-dialog>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price"></el-input>
        </el-form-item>
        <el-form-item label="花语" prop="flowerLanguage">
          <el-input v-model="form.flowerLanguage"></el-input>
        </el-form-item>
        <el-form-item label="适宜人群" prop="appropriateCrowd">
          <el-input v-model="form.appropriateCrowd"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialong.show = false">取 消</el-button>
        <el-button type="primary" @click="addHandle('formdoalog')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
export default {
  name: "FlowerDialong",
  data() {
    return {
      formdialog: {
        cid: [{ required: true, message: "分类名不能为空", trigger: "blur" }],
        title: [{ required: true, message: "标题不能为空", trigger: "blur" }],
        images: [{ required: true, message: "图片不能为空", trigger: "blur" }],
        price: [{ required: true, message: "价格不能为空", trigger: "blur" }],
        flowerLanguage: [{ required: true, message: "花语不能为空", trigger: "blur" }],
        appropriateCrowd: [{ required: true, message: "适宜人群不能为空", trigger: "blur" }]
      },
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false
    };
  },
  props: {
    dialong: Object,
    form: {
        id: 0,
        cid: 0,
        title: 0,
        images: '',
        imagesList: '',
        price: '',
        flowerLanguage: '',
        appropriateCrowd: '',
        isDeleted: 0,
        createTime: '',
        updateTime: '',
    },
    parameter: {
        options: [{
                id: null,
                name: null
        }],
        id: 0,
        dialogImageUrls: []
    },
    value: ''
  },
  methods: {
    addHandle(formdoalog) {
      this.$refs[formdoalog].validate(valid => {
        if (valid) {
          if(this.dialong.option == "add") {
            const formData = this.form;
            let imagesList=formData.images+",";
            let urlArr = this.parameter.dialogImageUrls;
            urlArr.forEach((item,value) => {
              if(!item) {
                 return urlArr.splice(value,1);
              }
              if(typeof(item)=="undefined") {
                return urlArr.splice(value,1);
              }
              item.url+=",";
              imagesList+=item.url.replace("api","");
            });
            formData.imagesList=imagesList;
            this.$axios.post("api/flower", formData,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据添加成功"
                });
                (this.dialong.show = false);
                this.$emit("flowerData");
                //清空内容
                this.form = null;
                this.parameter.dialogImageUrls = [];
            });
          }else {
            const formData = this.form;
            formData.id = this.parameter.id;
            let imagesList="";
            let urlArr = this.parameter.dialogImageUrls;
            urlArr.forEach((item,value) => {
              if(!item) {
                 return urlArr.splice(value,1);
              }
              if(typeof(item)=="undefined") {
                return urlArr.splice(value,1);
              }
              item.url+=",";
              imagesList+=item.url.replace("api","");
            });
            formData.imagesList=imagesList;
             this.$axios.put("api/flower", formData,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
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
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    handleAvatarSuccess(res, file) {
            //URL.createObjectURL(file.raw);
            this.form.images = res.fileName;
    },
    beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png' ||  file.type === 'image/svg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
            this.$message.error('上传头像图片只能是 jpeg，JPG，png，svg格式!');
            }
            if (!isLt2M) {
            this.$message.error('上传图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
    },
    handleAvatarSuccessList(res,file) {
        this.dialogImageUrl = res.fileName;
        let urlArr = this.parameter.dialogImageUrls;
        urlArr.push({name: urlArr.length+1,url: "api"+res.fileName});
        this.parameter.dialogImageUrls=urlArr;
        console.log(this.parameter.dialogImageUrls)
    },
    handleRemove(file) {
      console.log(file.url);
      let urlArr = this.parameter.dialogImageUrls;
      urlArr.forEach((item,index,arr) => {
        if(item.url === file.url){
            arr.splice(index,1)
        }
      });
      this.parameter.dialogImageUrls = urlArr;
      console.log(this.parameter.dialogImageUrls)
    },
    handlePictureCardPreview(file) {
        console.log(file.url);
        this.dialogVisible = true;
    },
    handleDownload(file) {
        console.log(file);
    },
    categoryList() {
      this.$axios
        .get("/api/category",{headers: {"token": localStorage.getItem("eleToken")}})
        .then(res => {
          this.options = res.data.data;
        }).catch(err => console.log(err));
    }
  },
  created() {
      this.categoryList();
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
