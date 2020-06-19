<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">用户评论</el-header>
      <!--搜索-->
      <el-form label-width="80px">
        <el-row :gutter="1">
          <el-col :span="6">
            <el-form-item label="评论用户">
              <el-input v-model="searchMap.username"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item>
              <el-button type="primary" @click="search" icon="el-icon-search" style="padding:12px;">查 询</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item>
              <el-button type="info" @click="clearForm">清 空</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <!--表格-->
      <el-table
        :data="commentList"
        border
        fit
        highlight-current-row
        element-loading-text="数据加载中"
        empty-text="暂无数据显示"
        style="width: 100%">
        <el-table-column
          align="center"
          label="序号"
          width="80"
        >
          <template slot-scope="scope">
            {{ scope.$index +1}}
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="评论用户"
          width="100"
          prop="username"
        >
        </el-table-column>
        <el-table-column
          align="center"
          label="评论内容"
          prop="content"
        >
        </el-table-column>
        <el-table-column
          align="center"
          label="用户头像"
          width="100"
        >
          <template slot-scope="scope">
            <img :src="scope.row.headImg" style="width: 60px;height: 60px"/>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="创建时间"
          width="180"
          prop="createTime"
        >
        </el-table-column>
        <el-table-column label="操作" align="center" width="200px">
          <template slot-scope="scope">
            <el-button
              size="mini"
              icon="el-icon-edit"
              type="primary"
              @click="searchById(scope.row.id),dialogFormVisible=true">编 辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="handleDelete(scope.row.id)">删 除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    <!--分页插件-->
    <el-pagination
      align="right"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="page"
      :page-sizes="[10, 20, 30, 40]"
      :page-size="size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
      <!--添加和修改弹窗-->
      <el-dialog :title="title" :visible.sync="dialogFormVisible" width="650px">
        <el-form :model="comment" label-width="100px" >
          <!--    <el-input v-model="searchObj.id" type="hidden" autocomplete="off" ></el-input>-->
          <el-input v-model="comment.id" type="hidden" style="width: 400px"></el-input>
          <el-form-item label="用户名:" prop="username">
            <el-input  disabled v-model="comment.username" type="text" style="width: 400px"></el-input>
          </el-form-item>
          <el-form-item label="评论内容" prop="content">
            <el-input min="6" v-model="comment.content" type="textarea" style="width: 400px"></el-input>
          </el-form-item>
          <!--操作-->
          <el-form-item>
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitBtn">确 定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
  import {getCommentPageList,getCommentById,modifyComment,delComment} from '@/api/comment'
  export default {
    name: "comment",
    data() {
      return {
        commentList:[],
        page:1,
        size:10,
        total:0,
        comment:{},
        searchMap:{},
        dialogFormVisible:false,
        title:''
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        getCommentPageList(this.page,this.size,this.searchMap).then(response=>{
            this.commentList = response.data.records
            this.total = response.data.total
        })
      },
      //通过id查询
      searchById(id){
        this.dialogFormVisible = true
        this.title = '修改页面'
        getCommentById(id).then(response=>{
           this.comment = response.data.courseComment
        })
      },
      //删除操作
      handleDelete(id) {
        //进行判断是否删除
        this.$confirm('此操作会删除选中的一条记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          delComment(id).then(response=>{
            if(response.success){
              this.$message({
                type:'success',
                message:'删除成功'
              })
              this.dialogFormVisible = false
              this.fetchData()
            }else {
              this.$message({
                type: 'error',
                message: '删除失败'
              })
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });
      },
      //提交按钮
      submitBtn(){
        modifyComment(this.comment).then(response=>{
          if(response.success){
             this.$message({
               type:'success',
               message:'修改成功'
             })
            this.dialogFormVisible = false
            this.fetchData()
          }else {
            this.$message({
              type:'error',
              message:'修改失败'
            })
          }
        })
      },
      //查询模块
      clearForm() {
        this.searchMap = {};
      },
      search() {
        this.page = 1
        this.size = 10
        this.fetchData();
      },
      //分页插件中的方法
      handleSizeChange(val){
        this.size = val
        this.fetchData();
      },
      handleCurrentChange(val){
        this.page = val
        this.fetchData();
      }
    }
  }
</script>

<style scoped>

</style>
