<template>
  <div class="staff">
    <div class="staff-top">
      	<div class="search-box">
          <el-form
            :inline="true"
            ref="search_data"
            :rules="rules"
            :model="search_data"
          >
            <el-form-item label="关键词:">
              <el-input type="text" v-model="search_data.title" placeholder="请输入关键词"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="search" @click='onScreeoutMoney("search_data")'>筛选</el-button>
            </el-form-item>
          </el-form>
        </div>
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
                label="分类"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.categoryName }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="标题"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.title }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="图片"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <img style="width:50px;height=50px;" alt="图片" :src="'api/'+scope.row.images"/>
                </template>
            </el-table-column>
            <el-table-column
                label="价格"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.price }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="更新时间"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.updateTime | moment }}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="创建时间"
                align="center"
                width="180">
                <template slot-scope="scope">
                    <span style="margin-left: 10px">{{ scope.row.createTime | moment }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" fixed="right">
                <template slot-scope="scope">
                    <el-button
                    size="mini"
                    @click="handleEdit(scope.$index, scope.row)">详情</el-button>
                </template>
            </el-table-column>
        </el-table>
      </div>
      <theAgencyDialong :dialong="dialong" :form="form" :parameter="parameter" @flowerData="loadCurrentPageflowerList"></theAgencyDialong>
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
import theAgencyDialong from "../../components/theAgencyDialong";
export default {
  name: "flowerList",
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
     
      },
      parameter: {
        id: 0,
        dialogImageUrls: []
      },
      search_data: {
         title: null
      }
    };
  },
  methods: {
    flowerList(formData) {
      this.$axios
        .post("/api/flower/backstage/list",formData,{headers: {"token": localStorage.getItem("eleToken")}})
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
                "pageSize": pageSize,
                "title": this.search_data.title,
                "type": 1
        }
        this.flowerList(formData);
    },
    handleEdit(index, row) {
      this.parameter.dialogImageUrls = [];
      //编辑
      this.dialong = {
        title: "详细信息",
        show: true,
        option:"edit"
      }
      console.log(row);
      this.parameter.id = row.id;
      if(row.imagesList!=null) {
        let urlArr = row.imagesList.split(",");
        urlArr.forEach((item,value) => {
            if(!item) {
              return urlArr.splice(value,1);
            }
            if(typeof(item)=="undefined") {
                  return urlArr.splice(value,1);
            }
            this.parameter.dialogImageUrls.push({name:value,url:'api'+item})
        });
      }
      this.form = row;
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
                "pageSize": pageSize,
                "title": this.search_data.title,
                "type": 1
        }
        this.flowerList(formData);
		},
    onScreeoutMoney(){
      const formData = {
                "isAsc": null,
                "orderBy": null,
                "orderByColumn": null,
                "pageNum": 1,
                "pageSize": this.flowerData.pageSize,
                "title": this.search_data.title,
                "type": 1
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
                "pageSize": this.flowerData.pageSize,
                "type": 1
    }
    this.flowerList(formData);
  },
  components: {
    theAgencyDialong
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
.search-box {
	margin: 20px 0 0 20px;
}
</style>
