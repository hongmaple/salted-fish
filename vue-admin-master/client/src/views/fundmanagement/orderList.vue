<template>
 <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="全部" name="0">
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
                        label="订单id"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.orderId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="实际金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.actualPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="总金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.totalPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiver }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货地址"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverState }}{{ scope.row.receiverCity }}{{ scope.row.receiverDistrict }}{{ scope.row.receiverAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="联系方式"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverMobile }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="备注"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.remark}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="付款人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.buyerNick}}</span>
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
                            <el-popover
                                placement="right"
                                width="800"
                                trigger="click">
                                <el-table :data="scope.row.orderDetails">
                                    <el-table-column width="150" label="图片" property="image">
                                        <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.orderDetails[scope.$index].image"/>
                                    </el-table-column>
                                    <el-table-column width="300" property="title" label="商品名"></el-table-column>
                                    <el-table-column width="100" property="num" label="数量"></el-table-column>
                                    <el-table-column width="300" property="price" label="单价"></el-table-column>
                                </el-table>
                                <el-button slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
    </el-tab-pane>
    <el-tab-pane label="待支付" name="1">
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
                        label="订单id"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.orderId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="实际金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.actualPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="总金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.totalPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiver }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货地址"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverState }}{{ scope.row.receiverCity }}{{ scope.row.receiverDistrict }}{{ scope.row.receiverAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="联系方式"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverMobile }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="备注"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.remark}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="付款人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.buyerNick}}</span>
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
                            <el-popover
                                placement="right"
                                width="800"
                                trigger="click">
                                <el-table :data="scope.row.orderDetails">
                                    <el-table-column width="150" label="图片" property="image">
                                        <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.orderDetails[scope.$index].image"/>
                                    </el-table-column>
                                    <el-table-column width="300" property="title" label="商品名"></el-table-column>
                                    <el-table-column width="100" property="num" label="数量"></el-table-column>
                                    <el-table-column width="300" property="price" label="单价"></el-table-column>
                                </el-table>
                                <el-button slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
    </el-tab-pane>
    <el-tab-pane label="待发货" name="2">
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
                        label="订单id"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.orderId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="实际金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.actualPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="总金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.totalPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiver }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货地址"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverState }}{{ scope.row.receiverCity }}{{ scope.row.receiverDistrict }}{{ scope.row.receiverAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="联系方式"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverMobile }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="备注"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.remark}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="付款人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.buyerNick}}</span>
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
                            @click="shipments(scope.row.orderId)">发货</el-button>
                        </template>
                        <template slot-scope="scope">
                            <el-popover
                                placement="right"
                                width="800"
                                trigger="click">
                                <el-table :data="scope.row.orderDetails">
                                    <el-table-column width="150" label="图片" property="image">
                                        <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.orderDetails[scope.$index].image"/>
                                    </el-table-column>
                                    <el-table-column width="300" property="title" label="商品名"></el-table-column>
                                    <el-table-column width="100" property="num" label="数量"></el-table-column>
                                    <el-table-column width="300" property="price" label="单价"></el-table-column>
                                </el-table>
                                <el-button slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
    </el-tab-pane>
    <el-tab-pane label="待收货" name="3">
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
                        label="订单id"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.orderId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="实际金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.actualPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="总金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.totalPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiver }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货地址"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverState }}{{ scope.row.receiverCity }}{{ scope.row.receiverDistrict }}{{ scope.row.receiverAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="联系方式"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverMobile }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="备注"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.remark}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="付款人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.buyerNick}}</span>
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
                            <el-popover
                                placement="right"
                                width="800"
                                trigger="click">
                                <el-table :data="scope.row.orderDetails">
                                    <el-table-column width="150" label="图片" property="image">
                                        <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.orderDetails[scope.$index].image"/>
                                    </el-table-column>
                                    <el-table-column width="300" property="title" label="商品名"></el-table-column>
                                    <el-table-column width="100" property="num" label="数量"></el-table-column>
                                    <el-table-column width="300" property="price" label="单价"></el-table-column>
                                </el-table>
                                <el-button slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
    </el-tab-pane>
    <el-tab-pane label="已取消" name="5">
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
                        label="订单id"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.orderId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="实际金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.actualPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="总金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.totalPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiver }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货地址"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverState }}{{ scope.row.receiverCity }}{{ scope.row.receiverDistrict }}{{ scope.row.receiverAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="联系方式"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverMobile }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="备注"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.remark}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="付款人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.buyerNick}}</span>
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
                            @click="shipments(scope.row.orderId)">发货</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" fixed="right">
                        <template slot-scope="scope">
                            <el-popover
                                placement="right"
                                width="800"
                                trigger="click">
                                <el-table :data="scope.row.orderDetails">
                                    <el-table-column width="150" label="图片" property="image">
                                        <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.orderDetails[scope.$index].image"/>
                                    </el-table-column>
                                    <el-table-column width="300" property="title" label="商品名"></el-table-column>
                                    <el-table-column width="100" property="num" label="数量"></el-table-column>
                                    <el-table-column width="300" property="price" label="单价"></el-table-column>
                                </el-table>
                                <el-button slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
    </el-tab-pane>
    <el-tab-pane label="已完成" name="4">
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
                        label="订单id"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.orderId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="实际金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.actualPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="总金额"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.totalPay }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiver }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="收货地址"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverState }}{{ scope.row.receiverCity }}{{ scope.row.receiverDistrict }}{{ scope.row.receiverAddress }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="联系方式"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.receiverMobile }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="备注"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.remark}}</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        label="付款人"
                        align="center"
                        width="180">
                        <template slot-scope="scope">
                            <span style="margin-left: 10px">{{ scope.row.buyerNick}}</span>
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
                            @click="shipments(scope.row.orderId)">发货</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" fixed="right">
                       <template slot-scope="scope">
                            <el-popover
                                placement="right"
                                width="800"
                                trigger="click">
                                <el-table :data="scope.row.orderDetails">
                                    <el-table-column width="150" label="图片" property="image">
                                        <img style="width:100px;height=80px;" alt="图片" :src="'api/'+scope.row.orderDetails[scope.$index].image"/>
                                    </el-table-column>
                                    <el-table-column width="300" property="title" label="商品名"></el-table-column>
                                    <el-table-column width="100" property="num" label="数量"></el-table-column>
                                    <el-table-column width="300" property="price" label="单价"></el-table-column>
                                </el-table>
                                <el-button slot="reference">查看</el-button>
                            </el-popover>
                        </template>
                    </el-table-column>
                </el-table>
            </div>
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
    </el-tab-pane>
  </el-tabs>
</template>

<script>
// @ is an alias to /src
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
      orderDetails: [
          {
                id: 0,
                image: "",
                num: 0,
                orderId: "",
                price: 0,
                skuId: 0,
                title: "",
          }
      ],
      search_data: {
         title: null,
         status: 0
      },
      activeName: '0'
    };
  },
  methods: {
    flowerList(formData) {
      this.$axios
        .post("/api/order/user/list",formData,{headers: {"token": localStorage.getItem("eleToken")}})
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
                "status": this.search_data.status,
                "orderId": this.search_data.title
        };
        this.flowerList(formData);
    },
    viewDetails(index, row) {
      console.log(row);
      this.orderDetails = row.orderDetails;
    },
    shipments(orderId) {
      this.$axios
        .put("/api/order/"+orderId+"/3",null,{headers: {"token": localStorage.getItem("eleToken")}})
        .then(res => {
            this.loadCurrentPageflowerList();
           this.$message({
              message: '发货成功',
              type: 'success'
           });
        }).catch(err => console.log(err));
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
                    "title": this.search_data.title
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
                "title": this.search_data.title
      }
      this.flowerList(formData);
	},
    handleClick(tab, event) {
        let pageSize = this.flowerData.pageSize;
        let page = this.flowerData.pageNum;
		const formData = {
                "isAsc": null,
                "orderBy": null,
                "orderByColumn": null,
                "pageNum": page,
                "pageSize": pageSize,
                "status": tab.name,
                "orderId": this.search_data.title
        };
        this.search_data.status=tab.name;
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
                "status": this.search_data.status
    }
    this.flowerList(formData);
  },
  components: {

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
