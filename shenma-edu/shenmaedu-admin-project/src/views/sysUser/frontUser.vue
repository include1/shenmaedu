<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">用户评论</el-header>
      <!--搜索-->
      <el-form label-width="80px">
        <el-row :gutter="1">
          <el-col :span="6">
            <el-form-item label="用户名">
              <el-input v-model="searchMap.username" placeholder="用户名"></el-input>
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
        :data="userList"
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
          label="用户名"
          width="160"
          prop="username"
        >
        </el-table-column>
        <el-table-column
          align="center"
          label="用户头像"
          width="120"
        >
          <template slot-scope="scope">
            <img :src="scope.row.headImg" style="width: 60px;height: 60px"/>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="个性签名"
          width="150"
          prop="sign"
        >
        </el-table-column>
        <el-table-column
          align="center"
          label="使用状态"
          width="100"
        >
          <template slot-scope="scope">
            <el-switch
              disabled
              v-model="scope.row.disabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value = 0
              :inactive-value = 1>
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="IP"
          width="100"
          prop="ip"
        >
        </el-table-column>
        <el-table-column
          align="center"
          label="创建时间"
          prop="createTime"
        >
        </el-table-column>
        <el-table-column label="操作" align="center" width="220" >
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
        <el-form :model="user" label-width="100px" >
          <!--    <el-input v-model="searchObj.id" type="hidden" autocomplete="off" ></el-input>-->
          <el-form-item label="用户名">
            <el-input  type="text" v-model="user.username" style="width: 300px"></el-input>
          </el-form-item>
          <el-form-item label="用户头像">
            <el-upload
              class="avatar-uploader"
              action="http://localhost:8001/shenma/oss/upload?host=frontUser"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="禁用状态">
            <el-switch
              v-model="user.disabled"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value=0
              :inactive-value=1>
            </el-switch>
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
  import {getUserByPage,getUserById,modifyUser,delUser} from '@/api/frontUser'
  export default {
    name: "comment",
    data() {
      return {
        userList:[],
        page:1,
        size:10,
        total:0,
        user:{},
        searchMap:{},
        dialogFormVisible:false,
        title:'',
        imageUrl:''
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        getUserByPage(this.page,this.size,this.searchMap).then(response=>{
          this.userList = response.data.records
          this.total = response.data.total
        })
      },
      //通过id查询
      searchById(id){
        this.dialogFormVisible = true
        this.title = '修改页面'
        getUserById(id).then(response=>{
           this.user = response.data.user
           this.imageUrl = response.data.user.headImg
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
          delUser(id).then(response=>{
             if(response.success){
                this.$message({
                  type:'success',
                  message:'删除成功'
                })
               this.fetchData()
             }else {
               this.$message({
                 type:'error',
                 message:'删除失败'
               })
             }
          }).catch(error=>{
            this.$message({
              type:'error',
              message:'删除异常'
            })
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
        this.user.headImg = this.imageUrl
        modifyUser(this.user).then(response=>{
            if(response.success){
              this.$message({
                 type:'success',
                 message:'修改成功'
              })
              this.dialogFormVisible=false
              this.fetchData()
            }else {
              this.$message({
                type:'success',
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
      },
      handleAvatarSuccess(res, file){
        this.imageUrl = res.data.imageUrl;//此处要修改
      },
      beforeAvatarUpload(file){
        const isJPG =file.type == 'image/jpeg' || file.type == 'image/png' || file.type == 'image/gif'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.warning('上传头像图片只能是 JPG/PNG/GIF 格式!')
          return isJPG
        }
        if (!isLt2M) {
          this.$message.warning('上传头像图片大小不能超过 2MB!')
          return isLt2M
        }
        this.multfileImg = file
        return isJPG && isLt2M
      }
    }
  }
</script>
<style scoped>
  .avatar-uploader {
    width: 150px;
    height: 150px;
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
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }
  .avatar {
    width: 150px;
    height: 150px;
    display: block;
  }
</style>
