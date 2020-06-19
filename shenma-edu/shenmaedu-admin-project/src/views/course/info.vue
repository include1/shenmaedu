<template>
  <div class="app-container">
    <el-card>
      <el-header align="middle" style="font-size:1.5em;">发布课程</el-header>
      <!--步骤条-->
      <el-steps :active="active" align-center>
        <el-step title="填写课程信息"></el-step>
        <el-step title="填写课程大纲"></el-step>
        <el-step title="课程发布"></el-step>
      </el-steps>
      <!--form表单-->
      <el-form label-width="120px" :model="course" ref="formRef" :rules="rules">
        <el-form-item label="课程名称" prop="name">
          <el-input v-model="course.name"  style="width:600px"  placeholder="请输入课程名"></el-input>
        </el-form-item>
        <el-form-item label="分类名"  required>
          <el-col :span="6">
          <el-form-item>
            <el-select @change="getSubCategory" v-model="course.categoryParentId"  filterable placeholder="请选择"
                       style="width:200px">
              <el-option
                v-for="item in categoryList"
                :key="item.id"
                :label="item.title"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item >
              <el-select v-model="course.categoryId" filterable  placeholder="请选择" style="width:200px">
                <el-option
                  v-for="item in subCategoryList"
                  :key="item.id"
                  :label="item.title"
                  :value="item.id">
                </el-option>
              </el-select>
              </el-form-item>
            </el-col>
          </el-form-item >
            <el-form-item label="讲师名" required>
              <el-select v-model="course.teacherId" filterable placeholder="请选择"  style="width:200px">
                <el-option
                  v-for="item in teacherList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="价格" prop="price">
              <el-input v-model="course.price"   type="number" min="0.0" style="width:200px"></el-input>
            </el-form-item>
          <el-form-item label="课程封面"  required>
            <el-upload
              action="http://localhost:8001/shenma/oss/upload?host=course"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
              <img v-if="imageUrl" :src="imageUrl" class="img">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
            <el-form-item label="课程描述" prop="description">
              <tinymce :height="300" style="width: 900px" v-model="course.description" />
            </el-form-item>
      </el-form>
      <!--提交按钮-->
      <el-button style="margin-top:10px;margin-left: 120px;" align="middle" type="primary" @click="next">保存进入下一步
      </el-button>
    </el-card>
  </div>
</template>

<script>
  import {getAllCategory} from '@/api/category'
  import {getAllTeacher} from '@/api/teacher'
  import {addCourse, getCourseById, updateCourse} from '@/api/course'
  import Tinymce from '@/components/Tinymce'

  export default {
    //声明额外的组件
    components:{Tinymce},
    name: "info",
    data() {
      return {
        active: 1,
        course: {
          price: 0.0
        },
        categoryList: [],
        subCategoryList: [],
        teacherList: [],
        clearable:false,
        imageUrl: 'https://shenmaedu-bucket.oss-cn-beijing.aliyuncs.com/2020/02/12/course/a5067fb1-3664-4d7d-85fc-5b5ed445fe2c.jpg',
        rules: {
          name: [
            {required: true, message: '请输入课程名', trigger: 'blur'}
          ],
          categoryId: [
            {required: true, message: '请选择分类名', trigger: 'change'}
          ],
          teacherId: [
            {required: true, message: '请选择讲师名', trigger: 'change'}
          ],
          price: [
            {required: true, message: '请输入价格', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请输入课程描述', trigger: 'blur'}
          ]
          ,
          categoryParentId:[
            {required:true,  message:'请选择分类名',trigger:'change'}
          ]


        }


      }
    },
    //监控
    watch:{
        $route(to,from){
          this.init()
        }
    },
    created() {
      this.init();
    },
    methods: {
      //初始化页面
      init() {
        //查询分类列表
        getAllCategory().then(response => {
          this.categoryList = response.data.categorys
        })
        //查询讲师列表
        getAllTeacher().then(response => {
          this.teacherList = response.data.teacherList
        })
        //数据回显
        if(this.$route.params&&this.$route.params.id){
          //通过id进行查询
          getCourseById(this.$route.params.id).then(response=>{
              this.course = response.data.course
              this.imageUrl = this.course.coverImg;
              //获取二级分类id信息
              this.getSubCategory(this.course.categoryParentId)
              console.log(this.course)
          })
        }else{
          this.course = {
            price: 0
          }
          this.imageUrl = 'https://shenmaedu-bucket.oss-cn-beijing.aliyuncs.com/2020/02/12/course/a5067fb1-3664-4d7d-85fc-5b5ed445fe2c.jpg'
        }
      },
      next() {
        this.$refs.formRef.validate(valid => {
          if (valid) {
            this.course.coverImg = this.imageUrl
            //判断course是否有id值
            if(this.course.id){
              //修改操作
                this.updateCourse()
            }else {
                //保存操作
                this.addCourse()
            }
          } else {
            this.$message.error('信息未完善，无法提交');
          }
        })
        //进行跳转
      //  this.$router.push('/course/chapter/1')
      },
      //更新课程
      updateCourse(){
        updateCourse(this.course).then(response =>{
            if(response.success){
                this.$message({
                  type:'success',
                  message:'更新成功'
                })
              //页面跳转
              this.$router.push('/course/chapter/'+response.data.courseId)
            }else{
              this.$message({
                type:'error',
                message:'更新失败'
              })
            }
        })
      },
      //添加课程
      addCourse(){
        addCourse(this.course).then(response => {
          if (response.success) {
            this.$message({
              type: 'success',
              message: '填写课程信息完成'
            })
            //进行跳转
            this.$router.push({path: '/course/chapter/' + response.data.courseId})
          }
        })
      },

      getSubCategory(val) {
        for (let i = 0; i < this.categoryList.length; i++) {
          if (this.categoryList[i].id === val) {
            this.subCategoryList = this.categoryList[i].subCategoryList
          }
        }
      },
      handleAvatarSuccess(res, file) {
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
  .el-form {
    margin-top: 80px;
  }
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
