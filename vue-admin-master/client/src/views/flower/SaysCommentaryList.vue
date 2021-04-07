<template>
  <div class="staff">
    <div class="staff-top">
        <el-form :inline="true">
            <el-form-item class="btnRight">
                <el-button type="primary" size ="small" icon="el-icon-edit-outline" @click='onAddMoney()'>添加</el-button>
            </el-form-item>
        </el-form>
    </div>
      <div class="tables">
           <el-table
            :data="flowerData.list"
            border
            style="width: 100%">
            <el-table-column
                label="id"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="花名"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.flowerName }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="图片"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.image"/>
                </template>
            </el-table-column>
            <el-table-column
                label="解说"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.narrate }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right">
                <template slot-scope="scope">
                    <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                    size="mini"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
      </div>
      <SaysCommentaryDialong :dialong="dialong" :form="form" :id="id" @flowerData="loadCurrentPageflowerList"></SaysCommentaryDialong>
      <div class="page">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="flowerData.pageNum"
          :page-sizes="flowerData.page_sizes"
          :page-size="flowerData.pageSize"
          :layout="flowerData.layout"
          :total="flowerData.total">
        </el-pagination>
	 	  </div>
  </div>
</template>

<script>
// @ is an alias to /src
import SaysCommentaryDialong from "../../components/SaysCommentaryDialong";
export default {
  name: "SaysCommentaryList",
  data() {
    return {
      flowerData: {
        list: [],
        pageNum: 1,
        pageSize: 3,
        pages: 0,
        total: 0,
        page_sizes:[3,6,9,12], //每页显示多少条
				layout:'total, sizes, prev, pager, next, jumper'
      }, //数据存储
      dialong: {
        //弹出框
        show: false,
        title: "",
        option: "edit"
      },
      form: {   //添加和删除需要传递的字段名
        id: '',
        flowerName: '',
        image: '',
        narrate: ''
      },
      id: 0,
    };
  },
  methods: {
    flowerList(formData) {
      this.$axios
        .post("/api/SaysCommentary/list",formData,{headers: {"token": localStorage.getItem("eleToken")}})
        .then(res => {
          this.flowerData = res.data.data;
        }).catch(err => console.log(err));
    },
     loadCurrentPageflowerList() {
	    let pageSize = this.flowerData.pageSize;
        let page = this.flowerData.pageNum;
		const formData = {
            "isAsc": null,
            "orderBy": null,
            "orderByColumn": null,
            "pageNum": page,
            "pageSize": pageSize
        }
        this.flowerList(formData);
    },
    handleEdit(index, row) {
      //编辑
      this.dialong = {
        title: "编辑信息",
        show: true,
        option:"edit"
      }
      this.id = row.id;
      this.form = row;
    },
    handleDelete(index, row) {
      //删除数据
      this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
          this.$axios.delete(`/api/SaysCommentary/${row.id}`,{headers: {"token": localStorage.getItem("eleToken")}})
          .then(res => {
            this.$message({
              type: "success",
              message: "删除成功!"
            });
            this.loadCurrentPageflowerList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    onAddMoney() {
      //添加内容
      this.dialong = {
        title: "添加信息",
        show: true,
        option:"add"
      }
       this.form = {
         cid: '',
         title: "",
         images: null
      };
    },
    handleSizeChange(page_size) {
			this.flowerData.pageNum = 1; //第一页
			this.flowerData.pageSize = page_size; //每页先显示多少数据
	},
	handleCurrentChange(page){
			// 跳转页数
			//获取每行数
			let pageSize = this.flowerData.pageSize;
			const formData = {
             "isAsc": null,
             "orderBy": null,
             "orderByColumn": null,
             "pageNum": page,
             "pageSize": pageSize
            }
            this.flowerList(formData);
   }
  },
  created() {
     const formData = {
                "isAsc": null,
                "orderBy": null,
                "orderByColumn": null,
                "pageNum": 1,
                "pageSize": this.flowerData.pageSize
    }
    this.flowerList(formData);
  },
  components: {
    SaysCommentaryDialong
  }
};
</script>
<style scoped>
.staff {
  margin: 10px;
}
.btnRight {
  float: right;
}
</style>
