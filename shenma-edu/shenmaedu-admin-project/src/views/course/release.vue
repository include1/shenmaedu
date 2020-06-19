<template>
  <div class="app-container">
    <el-card>
      <el-header align="middle" style="font-size:1.5em;font-family: 'Arial Black'">发布课程</el-header>
      <!--步骤条-->
      <el-steps :active="active" align-center>
        <el-step title="填写课程信息"></el-step>
        <el-step title="填写课程大纲"></el-step>
        <el-step title="课程发布"></el-step>
      </el-steps>
      <!--显示课程信息-->
      <div class="course-container">
            <div class="image">
              <img :src="courseInfo.coverImg" style="width: 100%;height: 100%;">
            </div>
            <div class="detailInfo">
              <!--课程信息-->
              <p style="font-size: 30px;font-weight: bolder;font-family: 楷体">{{courseInfo.name}}</p>
              <p style="font-size: 16px">所属分类 : <span>{{courseInfo.oneCategory}} - {{courseInfo.twoCategory}}</span>  </p>
              <p style="font-size: 16px">课程讲师 : <span>{{courseInfo.teacherName}}</span></p>
              <p style="margin-top: 50px;font-size: 25px;color: red">￥{{courseInfo.price}}</p>
            </div>
      </div>
      <!--提交按钮-->
      <el-footer>
        <el-button style="margin-right:60px" type="primary" @click="preview">返回上一步
        </el-button>
        <el-button type="primary" @click="release">审核通过
        </el-button>
      </el-footer>
    </el-card>
  </div>
</template>

<script>
  import {getCourseInfo,audit} from '@/api/course'

  export default {
    name: "release",
    data() {
      return {
        active: 3,
        courseId: '',
        courseInfo: {}
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
      }
      this.fetchData()
    },
    methods: {
      //获取详细信息
      fetchData() {
        getCourseInfo(this.courseId).then(response => {
          this.courseInfo = response.data.courseInfo
        })
      },
      //审核按钮
      release() {
        //判断url是否带参数
        audit(this.courseId).then(response =>{
          if(response.success){
              this.$message({
                type:'success',
                message:'审核通过'
              })
            this.$router.push('/course/list')
          }else{
            this.$message({
              type:'error',
              message:'审核不通过'
            })
          }
        })

      },
      //返回上一级按钮
      preview() {
          this.$router.push('/course/chapter/' + this.courseId)
      }
    }
  }

</script>
<style scoped>
  .course-container {
    width:1100px;
    height: 300px;
    margin: 0 auto;
    margin: 0 auto;
    margin-top: 40px;
    background-color: rgba(0,0,0,0.03);
    padding: 20px;
  }
  .image{
    margin:0 auto;
    width: 45%;
    height: 100%;
    float: left;
  }
  .detailInfo{
    float: left;
    margin-left: 20px;
    width: 50%;
    height: 100%;
  }
  .el-footer {
    margin-top: 20px;
    text-align: center;
  }
</style>
