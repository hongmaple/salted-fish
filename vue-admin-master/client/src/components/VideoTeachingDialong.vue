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
                 v-model="form.pic"
                >
                <img alt="图片" v-if="form.pic" :src="'api/'+form.pic" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="视频" prop="void">
           <el-upload
                class="avatar-uploader"
                action="/api/common/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccessVoid"
                :before-upload="beforeAvatarUploadVoid"
                 v-model="form.pic"
                >
                 <video width="320" v-if="form.url" height="240" controls="controls">
                        <source :src="'api/'+form.url" type="video/mp4">
                 </video>
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="是否付费" prop="isCharge">
          <el-checkbox v-model="form.isCharge">付费</el-checkbox>
        </el-form-item>
        <el-form-item label="价格" prop="isCharge">
          <el-input v-model="form.price">付费</el-input>
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
  name: "VideoTeachingDialong",
  data() {
    return {
      formdialog: {
        name: [{ required: true, message: "用户名不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "手机号不能为空", trigger: "blur" }]
      }
    };
  },
  props: {
    dialong: Object,
    form: {
         id: 0,
         title: '',
         pic: '',
         url: '',
         isCharge: '',
         price: ''
    },
    id: 0
  },
  methods: {
    addHandle(formdoalog) {
      this.$refs[formdoalog].validate(valid => {
        if (valid) {
          if(this.dialong.option == "add") {
            this.$axios.post("/api/VideoTeaching", this.form).then(res => {
                this.$message({
                  type: "success",
                  message: "数据添加成功"
                });
                (this.dialong.show = false);
                this.$emit("UserData");
                //清空内容
                this.form = "";
            });
          }else {
            const formData = this.form;
            formData.id = this.id;
            this.$axios.put("/api/VideoTeaching", formData,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据修改成功"
                });
                (this.dialong.show = false);
                this.$emit("UserData");
                //清空内容
                this.form = "";
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
            this.form.pic = res.fileName;
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
    handleAvatarSuccessVoid(res, file) {
            //URL.createObjectURL(file.raw);
            this.form.url = res.fileName;
    },
    beforeAvatarUploadVoid(file) {
            const isJPG = file.type === 'video/mp4';
            const isLt100000M = file.size / 1024 / 1024 < 100000;

            if (!isJPG) {
            this.$message.error('上传视频只能是mp4格式!');
            }
            if (!isLt100000M) {
            this.$message.error('上传图片大小不能超过 100000MB!');
            }
            return isJPG && isLt100000M;
    },
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
