<template>
    <div class="app-container">
      <el-card>
        <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">课程列表</el-header>
        <!--搜索-->
        <el-form   label-width="80px"  :model="searchObj">
          <el-row :gutter="2">
            <el-col :span="6">
              <el-form-item label="课程名:" >
                <el-input v-model="searchObj.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="讲师名:">
                <el-select v-model="searchObj.teacherId" filterable clearable placeholder="请选择">
                  <el-option
                    v-for="item in teacherList"
                    :key="item.id"

                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="类别:">
                <el-select v-model="searchObj.categoryParentId"@change="getTwoCategory" filterable clearable placeholder="请选择">
                  <el-option
                    v-for="item in categoryList"
                    :key="item.id"
                    :label="item.title"
                    :value="item.id"
                    >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label-width="0"  align="right">
                <el-select v-model="searchObj.categoryId" filterable clearable placeholder="请选择">
                  <el-option
                    v-for="item in subCategoryList"
                    :key="item.id"
                    :label="item.title"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item >
                <el-button type="primary" @click="search"  icon="el-icon-search" style="padding:12px;">查 询</el-button>
              </el-form-item>
            </el-col>
            <el-col :span="4">
              <el-form-item label-width="0">
                <el-button type="info" @click="clearForm"  >清 空</el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <!--表格-->
        <el-table
          :data="courseList"
          border
          style="width: 100%">
          <!--编号-->
          <el-table-column
            align="center"
            label="编号"
            width="80">
            <template slot-scope="scope">
              {{ scope.$index+1}}
            </template>
          </el-table-column>
          <!--课程名-->
          <el-table-column
            align="center"
            label="课程名"
            width="120"
            prop="name">

          </el-table-column>
          <!--课程封面-->
          <el-table-column
            align="center"
            label="课程封面"
           >
            <template slot-scope="scope">
              <img :src="scope.row.coverImg" style="width: 70px;height: 70px">
            </template>
          </el-table-column>
          <!--是否免费-->
          <el-table-column
            align="center"
            label="是否免费"
            width="80">
            <template slot-scope="scope">
              <el-switch
                disabled
                v-model="scope.row.free == 0 ? false:true"
                inactive-color="#13ce66"
                active-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <!--价格-->
          <el-table-column
            align="center"
            label="价格"
            width="85">
            <template slot-scope="scope">
              ￥{{ scope.row.price }}
            </template>
          </el-table-column>
          <!--购买数量-->
          <el-table-column
            align="center"
            label="购买数量"
            width="90"
            prop="buyNum">
          </el-table-column>
          <!--总时长-->
          <el-table-column
            align="center"
            label="总时长"
            prop="learnTime"
            width="80"
          ></el-table-column>
          <!--创建日期-->
          <el-table-column
            align="center"
            label="创建日期"
            width="190">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <!--是否审核-->
          <el-table-column
            align="center"
            label="审核状态"
            width="80">
            <template slot-scope="scope">
              <el-switch
                disabled
                v-model="scope.row.status == 0 ? false:true"
                active-color="#13ce66"
                inactive-color="#ff4949">
              </el-switch>
            </template>
          </el-table-column>
          <!--操作-->
          <el-table-column label="操作"  align="center" fixed="right" width="190px">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="handleEdit(scope.row.id)"
                icon="el-icon-edit"
              >编辑</el-button>
              <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row.id)"
                icon="el-icon-delete">删除</el-button>
            </template>
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
      <!--弹窗-->
      <el-dialog title="修改课程" :visible.sync="dialogCourseFormVisible">
        <el-form :model="course" label-width="80px" :rules="courseRules" ref="formRef">
          <!--课程名-->
          <el-form-item label="课程名" prop="name">
            <el-input v-model="course.name" ></el-input>
          </el-form-item>
          <!--课程封面-->
          <el-form-item label="课程封面" >
            <el-upload
              action="http://localhost:8001/shenma/oss/upload?host=course"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="img">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <!--价格-->
          <el-form-item label="价格" >
            <el-input v-model="course.price" type="number" min="0"></el-input>
          </el-form-item>
          <!--是否免费-->
          <el-form-item label="免费">
            <el-switch
              v-model="course.free"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="0"
              :inactive-value="1"
             >
            </el-switch>
          </el-form-item>
          <!--审核状态-->
          <el-form-item label="审核状态">
            <el-switch
              v-model="course.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
              :active-value="1"
              :inactive-value="0">
            </el-switch>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogCourseFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import {deleteCourse, findPageByCondition, getCourseById, updateCourse} from '@/api/course'
  import {getAllCategory} from '@/api/category'
  import {getAllTeacher} from '@/api/teacher'

  export default {
        name: "list",
        data(){
          return{
            courseList:[],
            page:1,
            size:10,
            total:0,
            course:{},
            dialogCourseFormVisible:false,
            imageUrl:'',
            searchObj:{},
            teacherList:[],
            categoryList:[],
            subCategoryList:[],
            courseRules:{
              name:[
                {required:true,message:'请填写课程名',trigger:'blur'}
              ]
            }
          }
        },
        created() {
          //初始化页面
          this.fetchData()
          this.getCategory()
          this.getTeacher()
        },
        methods:{
          //初始化表格
          fetchData(){
            findPageByCondition(this.page,this.size,this.searchObj).then(response =>{
                this.courseList = response.data.courseList
                this.total = response.data.total
            })
          },
          //搜索
          search(){
            this.page = 1
            this.size = 10
            this.fetchData()
          },
          //清空表单
          clearForm(){
           this.searchObj = {}
          },
          //获取老师
          getTeacher(){
            //查询讲师列表
            getAllTeacher().then(response => {
              this.teacherList = response.data.teacherList
            })
          },
          //获取分类
          getCategory() {
            //查询分类列表
            getAllCategory().then(response => {
              this.categoryList = response.data.categorys
            })
          },
          //获取二级分类
          getTwoCategory(val){
            for (let i = 0; i < this.categoryList.length; i++) {
              if (this.categoryList[i].id === val) {
                this.subCategoryList = this.categoryList[i].subCategoryList
              }
            }
          },
          //更新
          save() {
            this.$refs.formRef.validate(valid => {
              if(valid) {
                this.course.coverImg = this.imageUrl
                updateCourse(this.course).then(response => {
                  if (response.success) {
                    this.$message({
                      type: 'success',
                      message: '更新成功!'
                    })
                    this.fetchData()
                  }
                }).catch(error => {
                  this.$message({
                    type: 'error',
                    message: '更新失败!'
                  })
                })
                this.dialogCourseFormVisible = false
              }else {
                this.$message({
                  type: 'error',
                  message: '请填写信息'
                })
              }
            })
          },
          //编辑
          handleEdit(id){
              this.course.id = id
              //通过id查询
              getCourseById(id).then(response =>{
                  this.course = response.data.course
                  this.imageUrl = this.course.coverImg
              })
              this.dialogCourseFormVisible = true
          },
          //删除
          handleDelete(id){
            //进行判断是否删除
            this.$confirm('此操作会删除选中的一条记录, 是否继续?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              deleteCourse(id).then(response=>{
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
                })
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消删除'
              })
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
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
  }
  .img{
    width: 200px;
    height: 200px;
  }
  .avatar-uploader-icon:hover{
    border: 1px dashed #1482f0;
  }
</style>
