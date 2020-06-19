<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">数据显示</el-header>
      <!--操作按钮-->
      <el-date-picker
        v-model="day"
        type="date"
        placeholder="选择日期"
        value-format="yyyy-MM-dd">
      </el-date-picker>
      <el-button type="primary" @click="generateData">生成数据</el-button>
      <!--搜索-->
      <div class="search-container">
      <el-form :model="searchObj" ref="searchObjRef" :rules="rules">
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item prop="dateCalculated">
              <el-date-picker
                v-model="searchObj.dateCalculated"
                type="date"
                placeholder="选择日期"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="5">
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
          </el-form-item>
        </el-col>
          <el-col :span="5">
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="reload">刷新</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      </div>
      <!--表格-->
      <el-table
        border
        :stripe="true"
        :data="tableData"
        style="width: 100%;margin-top:30px">

        <!--编号-->
        <el-table-column
          label="编号"
          width="100px"
        >
          <template slot-scope="scope">
            {{scope.$index+1}}
          </template>
        </el-table-column>
        <!--统计日期-->
        <el-table-column
          label="统计日期"
          sortable
          prop="dateCalculated">
        </el-table-column>

        <!--当日注册数-->
        <el-table-column
          label="当日注册数"
          sortable
          prop="registerNum">
        </el-table-column>
        <!--当日登录数-->
        <el-table-column
          label="当日登录数"
          sortable
          prop="loginNum">
        </el-table-column>
        <!--当日视频播放数-->
        <el-table-column
          label="当日视频播放数"
          sortable
          prop="videoViewNum">
        </el-table-column>
        <!--当日添加课程数-->
        <el-table-column
          label="当日添加课程数"
          sortable
          prop="courseNum">
        </el-table-column>
        <!--创建日期-->
        <el-table-column
          label="创建日期"
          prop="createTime">
        </el-table-column>
      </el-table>

      <!--分页插件-->
      <el-pagination
        align="center"
        style="margin-top: 20px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
  import {generateStatisticsData, getStatisticsList} from '@/api/statistics'

  export default {
    name: "index",
    data() {
      return {
        page: 1,
        size: 10,
        total: 0,
        day: '',
        tableData: [],
        searchObj: {dateCalculated: ''},
        rules: {
          dateCalculated: [
            {required: true, message: '请输入日期', trigger: 'blur'},
          ]
        }
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      //初始化数据
      fetchData() {
        getStatisticsList(this.page, this.size, this.searchObj).then(response => {
          //回显数据
          this.tableData = response.data.records
          this.total = response.data.total
        })
      },
      //生成数据
      generateData() {
        generateStatisticsData(this.day).then(response => {
          if (response.success) {
            this.$message({
              type: 'success',
              message: '生成数据成功'
            })
          }
        })
      },
      //改变每页显示的数量
      handleSizeChange(val) {
        this.size = val
        this.fetchData()
      },
      //改变当前的页数
      handleCurrentChange(val) {
        this.page = val
        this.fetchData()
      },
      //搜索
      search() {
        this.$refs.searchObjRef.validate((valid) => {
            if(valid){
                this.page = 0
                this.fetchData();
            }else {
              this.$message({
                type:'error',
                message:'请输入查询日期'
              })
            }
        })
      },
      reload(){
        //清空数据，重置表单
        this.day = ''
        this.searchObj = {}
        this.$refs['searchObjRef'].resetFields()
        this.fetchData()
      }
    }
  }
</script>

<style scoped>
  .search-container{
    position: absolute;
    right:50px;
    top:90px;
  }
</style>
