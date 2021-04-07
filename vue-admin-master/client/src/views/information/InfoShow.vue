<template>
  <div class="infoshow">
    <div class="users">
        <el-row>
            <el-col :span="8">
                <div class="box-card">
                    <div class="el-card mgb20 is-hover-shadow" style="height: 252px">
                        <div class="user-info">
                            <el-upload
                                class="avatar-uploader"
                                action="/api/common/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :before-upload="beforeAvatarUpload">
                                <img v-if="user.avatarImage" :src="'api/'+user.avatarImage" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                            <div class="user-info-cont">
                                <span class="user-name">{{user.username}}</span>
                                <span>管理员</span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 详细信息 -->
                <div class="box-card" style="margin-top: 20px">
                    <div class="el-card is-hover-shadow" style="height: 252px">
                        <div class="el-card__header">
                            <span>语言描述</span>
                        </div>
                        <div class="el-card__body">
                            <div class="card-body-list">
                                <div class="title">手机号:</div>
                                <!-- <div class="progress"><el-progress :percentage="70"></el-progress></div> -->
                                <div class="progress"><span class="user-name">{{user.phone}}</span></div>
                            </div>
                            <div class="card-body-list">
                                <div class="title">注册时间:</div>
                                <div class="progress"><span style="margin-left: 10px">{{ user.createTime | moment }}</span></div>
                            </div>
                            <div class="card-body-list">
                                 <UserDialong :dialong="dialong" :form="form" :id="id" @UserData="userInfo"></UserDialong>
                                 <el-button size="mini" @click="handleEdit()">编辑信息</el-button>
                            </div>
                        </div>
                    </div>
                </div>
            </el-col>
            <el-col :span="16">
                <!-- <el-row :gutter="20">
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-1">
                                <i class="el-icon-view grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">1234</div>
                                    <div>用户访问量</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-2 grid-con-1">
                                <i class="el-icon-message grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">321</div>
                                    <div>系统消息</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                    <el-col :span="8">
                        <el-card shadow="hover" :body-style="{padding: '0px'}">
                            <div class="grid-content grid-con-3">
                                <i class="el-icon-goods grid-con-icon"></i>
                                <div class="grid-cont-right">
                                    <div class="grid-num">5000</div>
                                    <div>数量</div>
                                </div>
                            </div>
                        </el-card>
                    </el-col>
                </el-row> -->
                <!-- 2019年 -->
                <!-- <el-card class="box-card">
                  <div slot="header" class="clearfix">
                    <span>2019年目标</span>
                  </div>
                  <el-table
                    :data="todoList"
                  >
                    <el-table-column width="40">
                        <template slot-scope="scope">
                            <el-checkbox v-model="scope.row.status"></el-checkbox>
                        </template>
                    </el-table-column>
                    <el-table-column>
                        <template slot-scope="scope">
                            <div class="todo-item" :class="{'todo-item-del': scope.row.status}">{{scope.row.title}}</div>
                        </template>
                    </el-table-column> 
                  </el-table>
                </el-card> -->
                <!-- 日历 -->
                <!-- <div class="box-card" style="margin-top: 20px">
                    <div class="el-card is-hover-shadow" style="height: 400px">
                        <div class="el-card__header">
                            <span>时钟</span>
                        </div>
                        <div class="el-card__body">
                            <Clock/>
                        </div>
                    </div>
                </div> -->
            </el-col>
        </el-row>
    </div>
  </div>
</template>

<script>
import Clock from './Clock'
import UserDialong from "../../components/UserDialong";
// @ is an alias to /src
export default {
  name: 'infoshow',
    data(){
        return {
           user: {},
            dialong: {
                //弹出框
                show: false,
                title: "",
                option: "edit"
            },
            form: {   //添加和删除需要传递的字段名
                password: "",
                phone: "",
                username: "",
                id: 0
            },
            id: 0
        }
    },
    methods:{
        userInfo() {
			this.$axios
				.post("/api/background/user/info",null,{headers: {"token": localStorage.getItem("eleToken")}})
				.then(res => {
					    this.user = res.data.data; 
				}).catch(err => console.log(err));
		},
        updataUser() {
            this.$axios.put("/api/background/user", this.user,{headers: {"token": localStorage.getItem("eleToken")}}).then(res => {
                this.$message({
                  type: "success",
                  message: "数据修改成功"
                });
            });
        },
        handleAvatarSuccess(res, file) {
            //URL.createObjectURL(file.raw);
            this.user.avatarImage = res.fileName;
            this.updataUser();
        },
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg' || file.type === 'image/jpg' || file.type === 'image/png' ||  file.type === 'image/svg' ||  file.type === 'image/gif';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
            this.$message.error('上传头像图片只能是 jpeg，JPG，png，svg，gif 格式!');
            }
            if (!isLt2M) {
            this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
        },
         handleEdit() {
            //编辑
            this.dialong = {
                title: "编辑信息",
                show: true,
                option:"edit"
            }
            this.id = this.user.id;
            this.form = {
                password: this.user.password,
                phone: this.user.phone,
                username: this.user.username,
                id: this.user.id
        }
    },
    },
    computed:{
        // users() {
        //   return this.$store.getters.user;
        // }
    },
    created() {
        this.userInfo();
   },
    components: {
        Clock,
        UserDialong
    }
}
</script>
<style scoped>
    .infoshow {
        width: 100%;
        height: 100%;
        box-sizing: border-box;
        float: right;
        background: #eeeeee;
    }
    .row-bg {
        width: 100%;
        height: 100%;
    }
    .users{
        margin: 20px;
    }
    .user-info {
        display: flex;
        align-items: center;
        padding: 20px;
        border-bottom: 2px solid #ccc;
        margin-bottom: 20px;
    }
    .avatar{
        width: 120px;
        height: 120px;
        border-radius: 50%;
    }
    .el-card{
        margin-bottom: 20px;
    }
    .user-info-cont {
        flex: 1;
        padding-left: 50px;
    }
    .user-info-cont span {
        display: block;
    }
    .user-info-cont span:first-child{
        color: #222;
        font-size: 22px;
    }
    .user-info-cont span:last-child{
        color: #999;
        font-size: 14px;
        padding-top: 10px;
    }
    .user-info-list {
        font-size: 14px;
        line-height: 25px;
        color: #999;
        padding-left: 10px;
    }
    .address {
        margin-left: 70px;
    }
    .card-body-list {
        width: 100%;
        height: 100%;
        height: 30px;
    }
    .card-body-list .title {
        width: 20%;
        float: left
    }
    .card-body-list .progress{
        width: 80%;
        float: right;
    }
    .grid-content  {
        display: flex;
        align-items: center;
        height: 100px;
    }
    .grid-con-icon {
        font-size: 50px;
        width: 100px;
        height: 100px;
        text-align: center;
        line-height: 100px;
        color: #fff;
    }
    .grid-con-1 .grid-con-icon {
        background: #2d8cf0;
    }
    .grid-con-2 .grid-con-icon{
        background: #64d572;
    }
    .grid-con-3 .grid-con-icon {
        background: #f25e43;
    }
    .grid-cont-right {
        flex: 1;
        text-align: center;
        font-size: 14px;
        color: #999;
    }
    .grid-num {
        font-size: 30px;
        font-weight: bold;
    }
    .grid-con-1 .grid-num{
        color: #2d8cf0;
    }
    .grid-con-3 .grid-num{
        color: #f25e43;
    }
    .el-col-16 {
        width: 65%;
        margin-left: 1%;
    }
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
