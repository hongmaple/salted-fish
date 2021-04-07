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
        <el-form-item label="花名" prop="flowerName">
          <el-input v-model="form.flowerName"></el-input>
        </el-form-item>
        <el-form-item label="解说" prop="narrate">
          <el-input  type="textarea" :rows="2" v-model="form.narrate"></el-input>
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <el-upload
                class="avatar-uploader"
                action="/api/common/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                v-model="form.image"
                >
                <img alt="图片" v-if="form.image" :src="'api/'+form.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
  name: "SaysCommentaryDialong",
  data() {
    return {
      formdialog: {
        flowerName: [{ required: true, message: "花名不能为空", trigger: "blur" }],
        image: [{ required: true, message: "图片不能为空", trigger: "blur" }],
        narrate: [{ required: true, message: "解说不能为空", trigger: "blur" }]
      }
    };
  },
  props: {
    dialong: Object,
    form: {
        id: '',
        flowerName: '',
        image: '',
        narrate: ''
    },
    id: 0
  },
  methods: {
    addHandle(formdoalog) {
      this.$refs[formdoalog].validate(valid => {
        if (valid) {
          if(this.dialong.option == "add") {
            this.$axios.post("api/SaysCommentary", this.form,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据添加成功"
                });
                (this.dialong.show = false);
                this.$emit("flowerData");
                //清空内容
                this.form = "";
            });
          }else {
            const formData = this.form;
            formData.id = this.id;
             this.$axios.put("api/SaysCommentary", formData,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据修改成功"
                });
                (this.dialong.show = false);
                this.$emit("flowerData");
                //清空内容
                this.form = "";
            });
          }
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },handleAvatarSuccess(res, file) {
            //URL.createObjectURL(file.raw);
            this.form.image = res.fileName;
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
    }
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
