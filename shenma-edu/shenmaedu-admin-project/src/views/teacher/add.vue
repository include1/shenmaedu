<template>
  <div class="app-container">
    <el-card class="box-card">
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">添加讲师</el-header>
      <el-form  label-width="100px" :rules="formRules" ref="formRef" :model="updateForm">
        <!--    <el-input v-model="searchObj.id" type="hidden" autocomplete="off" ></el-input>-->
        <el-form-item label="讲师名:" prop="name">
        <el-input v-model="updateForm.name"   autocomplete="off" ></el-input>
      </el-form-item>

        <el-form-item label="头像:" >
          <el-upload
            class="avatar-uploader"
            action="http://localhost:8001/shenma/oss/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload">
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="等别:" >
          <el-select v-model="updateForm.level" placeholder="请选择">
            <el-option
              v-for="item in levels"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="职业:" >
          <el-input v-model="updateForm.career" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="排序:">
          <el-input v-model="updateForm.sort" autocomplete="off" type="number" :min="0"  style="width: 200px" name="sort"  placeholder="必须填数字"></el-input>
        </el-form-item>
        <el-form-item label="介绍:" >
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入内容"
            v-model="updateForm.intro">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="resetForm">重 置</el-button>
          <el-button type="primary" @click="handleAdd('updateForm')">保 存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
import {addTeacher} from '@/api/teacher'
<script>
  import {addTeacher} from "../../api/teacher";

  export default {
        name: "add",
        data() {
          return {
            formRules:{
                name:[
                  { required: true, message: '请输入讲师,该项不能为空', trigger: 'blur' }
                  ]
            },
            levels:[
              {"label":'初级讲师',
                "value":0
              },
              {"label":'中级讲师',
                "value":1
              },
              {"label":'高级讲师',
                "value":2
              }
            ],
            updateForm:{
              sort:0,
              level:0
            },
            imageUrl:''
          }
        },
        methods:{
          resetForm(){
            this.updateForm={sort: 0};
          },
          handleAdd(){
            this.$refs.formRef.validate(valid => {
              if (valid) {
                this.updateForm.avatar = this.imageUrl
                addTeacher(this.updateForm).then(response => {
                  if (response.code == 20000) {
                    return this.$message({
                      type: 'success',
                      message: '添加成功！'
                    })
                  }
                }).then(response => {
                  this.$router.push('/teacher/list')
                }).catch(error => {
                  this.$message({
                    type: 'error',
                    message: '添加失败！'
                  })
                })
              }
            })
          },
          handleAvatarSuccess(res, file){
            this.imageUrl = res.data.imageUrl;//此处要修改
          },
          beforeAvatarUpload(file){
            const isJPG =file.type == 'image/jpeg' || file.type == 'image/png'
            const isLt2M = file.size / 1024 / 1024 < 2
            if (!isJPG) {
              this.$message.warning('上传头像图片只能是 JPG/PNG 格式!')
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

<style scoped >
  .header{
    font-size: 20px;
    align-content: flex-start;
    height: 50px;
    border-bottom: solid 1px darkgray;
    margin-bottom: 20px;
  }
  .avatar-uploader {
    width: 178px;
    height: 178px;
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
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
