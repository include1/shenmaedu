<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">系统用户</el-header>
      <!--表格-->
      <el-table
        :data="sysUserList"
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
          width="100"
          prop="username"
        >
        </el-table-column>
        <el-table-column
          align="center"
          label="密码"
          width="80"
        >
          <template slot-scope="scope">
          ********
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="用户头像"
        >
          <template slot-scope="scope">
             <img :src="scope.row.headImg" style="width: 60px;height: 60px"/>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="IP地址"
          prop="ip"
        >
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
              @click="searchById(scope.row.id),dialogFormVisible=true">编 辑</el-button>
            <el-button
              size="mini"
              type="danger"
              icon="el-icon-delete"
              @click="handleDelete(scope.row.id)">删 除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
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
      <el-form :model="sysUser" label-width="100px" >
        <!--    <el-input v-model="searchObj.id" type="hidden" autocomplete="off" ></el-input>-->
        <el-form-item label="用户名:" prop="username">
          <el-input v-model="sysUser.username" disabled  style="width: 400px"></el-input>
        </el-form-item>
        <el-form-item label="头像:" >
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8001/shenma/oss/upload?host=sysUser"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <!--操作-->
        <el-form-item>
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="sumitBtn">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>

</template>

<script>
  import {addSysUser, getSysUserById, getSysUserList, modifySysUser, removeSysUser} from "@/api/sysUser";
    export default {
        name: "sysList",
        data(){
          return{
              page:1,
              size:10,
              total:0,
              sysUser:{},
              sysUserList:[],
              searchObj:{},
              imageUrl:'',
              dialogFormVisible:false,
              title:''
          }
        },
        created() {
          this.fetchData()
        },
        methods:{
          fetchData(){
            getSysUserList(this.page,this.size,this.searchObj).then(response=>{
              console.log(response)
              this.sysUserList = response.data.records
              this.total = response.data.total
            })
          },
          //通过id查询
          searchById(id){
            this.dialogFormVisible = true
            this.title = '修改页面'
            getSysUserById(id).then(response=>{
              this.sysUser = response.data.sysUser
              this.imageUrl = this.sysUser.headImg
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
                removeSysUser(id).then(response => {

                  if (response.success) {
                    this.$message({
                      type: 'success',
                      message: '删除成功!'
                    });
                    this.fetchData()
                  }

                }).catch(error => {
                  this.$message({
                    type: 'info',
                    message: '删除失败'
                  });
                })
              }).catch(() => {
                this.$message({
                  type: 'info',
                  message: '已取消删除'
                });
              });
            },
          //提交按钮
          sumitBtn(){
            this.sysUser.headImg = this.imageUrl
            modifySysUser(this.sysUser).then(response=>{
                if(response.success){
                    this.$message({
                      type:'success',
                      message:'修改成功'
                    })
                  this.dialogFormVisible = false
                  this.fetchData()

                }else{
                  this.$message({
                    type:'error',
                    message:'修改失败'
                  })
                }
            })
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
          //删除图片的回调函数
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
