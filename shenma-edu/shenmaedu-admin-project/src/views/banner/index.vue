<template>
    <div class="app-container">
      <el-card>
        <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">轮播图管理</el-header>
        <el-form   label-width="80px" >
          <el-row :gutter="1">
            <el-col :span="6">
              <el-form-item label="标题名:" >
                <el-input v-model="searchObj.title"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="2">
              <el-form-item>
                <el-button type="primary" @click="search"  icon="el-icon-search" style="padding:12px;">查 询</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="3">
              <el-form-item style="margin-left: 20px">
                <el-button type="info" @click="clearForm"  >清 空</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <!--添加按钮-->
        <div  align="right" >
          <el-button type="primary" @click="openAddDialog" icon="el-icon-plus" style="margin-bottom: 20px;margin-right: 40px" >添加按钮</el-button>
          <el-button type="info" @click="showPic" icon="el-icon-view" style="margin-bottom: 20px;margin-right: 40px" >首页轮播图预览</el-button>
        </div>
        <!--表格数据显示-->
        <el-table
            :data="bannerList"
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
              label="图片标题"
              prop="title"
            >
            </el-table-column>

            <el-table-column
              align="center"
              label="图片"
              width="400px"
            >
            <template slot-scope="scope">
                <img :src="scope.row.imgUrl" width="100%" height="200px">
            </template>
            </el-table-column>
            <el-table-column
              align="center"
              label="排序"
              width="100"
              prop="sort"
            >
            </el-table-column>
            <el-table-column
              align="center"
              label="创建时间"
              width="180"
              prop="addTime"
            >
            </el-table-column>
            <el-table-column label="操作" align="center" fixed="right" width="240px">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  icon="el-icon-edit"
                  type="primary"
                  @click="openEditDialog(scope.row.id)">编 辑</el-button>
                <el-button
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row.id)">删 除</el-button>
              </template>
            </el-table-column>
          </el-table>
        <!--分页插件-->
        <el-pagination
          align="right"
          style="margin-top: 10px"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
        </el-card>
      <!--添加或删除的弹框-->
      <el-dialog title="收货地址" :visible.sync="dialogFormVisible" >
        <el-form :model="banner" ref="bannerFormRef"  :rules="bannerRules" label-width="80px">
          <!--图片标题-->
          <el-form-item label="图片标题" prop="title">
            <el-input v-model="banner.title" clearable placeholder="请输入图片名"></el-input>
          </el-form-item>
          <!--轮播图图片-->
            <el-form-item label="图片" required>
              <el-upload
                action="http://localhost:8001/shenma/oss/upload?host=banner"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload">
                <img v-if="imageUrl" :src="imageUrl" class="img">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          <!--排序-->
          <el-form-item label="排序" prop="sort">
            <el-input v-model="banner.sort"  placeholder="请输入排序" type="number" min="0" style="width: 200px"></el-input>
          </el-form-item>
        </el-form>
        <!--footer-->
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import
  {getBannerList, modifyBanner,addBanner,getBannerById,removeBannerById}
  from '@/api/banner'

  export default {
        name: "index",
        data(){
          return{
              page:0,
              size:10,
              total:0,
              id:'',
              imageUrl:'',
              dialogFormVisible:false,
              searchObj:{},
              banner:{},
              bannerList:[],
              bannerRules: {//指定规则
                title:[
                  {required: true,message:'请输入图片标题名',trigger:'blur'}
                ],
                sort:[
                  {required: true,message:'请输入排序',trigger:'blur'}
                ]
              }
          }
        },
    //监控
        watch:{
          $route(to,from){
            console.log('watche $route')
            this.fetchData()
          }
        },
        created() {
          this.fetchData()
        },
        methods:{
          //初始化列表调用
          fetchData(){
            getBannerList(this.page,this.size,this.searchObj).then(response=>{
                if(response.success){
                  //返回结果
                  this.bannerList = response.data.bannerList
                  this.total = response.data.total
                }
            })
          },
          //添加或修改
          saveOrUpdate(){
              //验证表单是否为空
              this.$refs.bannerFormRef.validate(valid =>{
                    if(valid){
                        this.banner.imgUrl = this.imageUrl
                        //有id修改
                        if(this.id){
                          this.banner.id = this.id
                          modifyBanner(this.banner).then(response=>{
                              if(response.success){
                                this.$message({
                                  type:'success',
                                  message:'修改成功'
                                })
                              }else {
                                this.$message({
                                  type:'error',
                                  message:'修改异常'
                                })
                              }
                            this.fetchData()
                          }).catch(error=>{
                              this.$message({
                                  type:'error',
                                  message:'修改异常'
                              })
                          })
                        }else {//添加
                          addBanner(this.banner).then(response=>{
                            if(response.success){
                              this.$message({
                                type:'success',
                                message:'添加成功'
                              })
                            }else {
                              this.$message({
                                type:'error',
                                message:'添加异常'
                              })
                            }
                            this.fetchData()
                          }).catch(error=>{
                            this.$message({
                              type:'error',
                              message:'添加异常'
                            })
                          })
                        }
                        this.dialogFormVisible= false
                    }else {
                      this.$message("信息没有填完整")
                      this.dialogFormVisible = true
                    }
              })
          },
          //打开修改窗口
          openEditDialog(id){
            this.id = id
            getBannerById(id).then(response =>{
              this.banner = response.data.banner
              this.imageUrl = this.banner.imgUrl
            })
            this.dialogFormVisible = true
          },
          //打开添加窗口
          openAddDialog(){
            this.id = ''
            this.banner = {}
            this.imageUrl = ''
            this.dialogFormVisible = true
          },
          //删除通过id
          handleDelete(id){
            //进行判断是否删除
            this.$confirm('此操作会删除选中的一条记录, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              removeBannerById(id).then(response=>{
                if(response.success){
                  this.$message({
                    type: 'success',
                    message: '删除成功!'
                  });
                  this.fetchData()
                }

              }).catch(error=>{
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
          //展示首页轮播图
          showPic(){
            this.$router.push('/banner/show')
          },
          //改变每页显示的页数
          handleCurrentChange(val){
            this.page = val
            this.fetchData()

          },
          //改变显示的行数
          handleSizeChange(val){
              this.size = val
              this.fetchData()
          },
          //查询
          search(){
              this.page = 1
              this.size = 10
              this.fetchData()
          },
          //清空
          clearForm(){
              this.searchObj = {}
          },
          //上传图片使用的方法
          handleAvatarSuccess(res, file) {
            //获取服务器中图片地址
            this.imageUrl = res.data.imageUrl
          },
          beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg';
            const isLt2M = file.size / 1024 / 1024 < 2;

            if (!isJPG) {
              this.$message.error('上传头像图片只能是 JPG 格式!');
            }
            if (!isLt2M) {
              this.$message.error('上传头像图片大小不能超过 2MB!');
            }
            return isJPG && isLt2M;
          }
        }
    }
</script>

<style scoped>
  .avatar-uploader-icon {
    border: 1px dashed skyblue;
    font-size: 28px;
    color: #8c939d;
    width: 400px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .img{
    width: 400px;
    height: 200px;
  }
  .avatar-uploader-icon:hover{
    border: 1px dashed #1482f0;
  }
</style>

