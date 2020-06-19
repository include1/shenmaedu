<template>
  <!--个人账号-->
  <div class="main">
    <div class="container">
        <!--左边部分-->
        <div class="cd-sidebar">
          <ul class="cd-sidebar__list">
            <li class="cd-sidebar__heading">
              个人中心
            </li>
            <li class="cd-sidebar__item active" >
              <a href="/account/setting"  >个人信息</a>
            </li>
            <li class="cd-sidebar__item " >
              <a href="/account/course"  >我的课程</a>
            </li>

            <li class="cd-sidebar__item " >
              <a href="/account/comment"   >我的评论</a>
            </li>
            <li class="cd-sidebar__item  " >
              <a href="/account/order"   >我的订单</a>
            </li>
            <li class="cd-sidebar__item "  >
              <a href="/account/password"  >修改密码</a>
            </li>

          </ul>
        </div>
        <!--右边部分-->
        <div class="cd-main">
          <!--个人信息-->
          <div class="cd-main__heading">
            <div class="cd-main__title">个人信息</div>
          </div>
          <div class="cd-main__body">
            <!--使用框架模板-->
            <el-form label-width="100px"  :class="{'active-form':bool}">
              <!--头像-->
              <el-form-item  label="头像：" >
                <el-upload
                  class="avatar-uploader"
                  action="http://localhost:8001/shenma/oss/upload?host=frontUser"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload">
                  <img v-if="imageUrl" :src="imageUrl" class="avatar">
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <span style="color:grey;font-size: 12px;">只能上传jpg/png/gif文件，且不超过2M</span>
              </el-form-item>
              <!--用户名-->
              <el-form-item label="用户名：">
                    <span>{{user.username}}</span>
              </el-form-item>
              <!--昵称-->
              <el-form-item label="昵称：">
                <el-input type="text" v-model="user.nickname" />
              </el-form-item>
              <!--年龄-->
              <el-form-item label="年龄：">
                <el-input type="number" min="0" v-model="user.age"></el-input>
              </el-form-item>
              <!--性别-->
              <el-form-item label="性别：">
                <el-radio v-model="user.sex" label=1>男</el-radio>
                <el-radio v-model="user.sex" label=0>女</el-radio>
              </el-form-item>
              <!--所在城市-->
              <el-form-item label="所在城市：">
                <el-input type="text" v-model="user.city"></el-input>
              </el-form-item>
              <!--手机号-->
              <el-form-item label="手机号：">
                <el-input type="text" v-model="user.phone"></el-input>
              </el-form-item>
              <!--自我介绍-->
              <el-form-item label="自我介绍：">
                <el-input type="textarea"  maxRows="6" v-model="user.sign" style="width: 600px;" resize="none"></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="save">保存</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
    </div>
  </div>
</template>
<script>
  import "~/assets/css/account.css"
  import user from '@/api/user'
  import Cookies from 'js-cookie'
  export default {
    layout:'default',
    data() {
      return {
        imageUrl: '',
        bool:true,
        user:{
          headImg:'',
          username:'',
          nickname:'',
          sex:"1",
          age: 0,
          sign:'',
          phone:'',
          city:''
        }
      };
    },
    mounted() {
      //验证是否登录
          this.getUserInfo()
    },
    methods: {
      getUserInfo(){
         //通过id获取信息
        let id = Cookies.get('uId')
        let token = Cookies.get('token')
        if(id&&token) {
          user.getUserInfoById(id).then(response => {
            if(response.data.success) {
              this.user = response.data.data.user
              this.imageUrl = response.data.data.user.headImg
              this.bool = false
            }
          })
        }else {
          window.location.href='/login'
        }
      },
      save(){
        //更新用户内容
        this.user.headImg = this.imageUrl
        user.updateUser(this.user).then(response=>{
            if(response.data.success){
              this.$message({
                type:'success',
                message:'保存成功'
              })
            }
        })

      },
      handleAvatarSuccess(res, file) {
        this.imageUrl = res.data.imageUrl
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
  .active-form{
    display: none;
  }
  .el-input{
    width: 240px;
  }
  .el-form-item{
    margin-left: 50px;
  }
  .avatar-uploader el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader-icon:hover {
    border: 1px dashed #409EFF;
  }
  .avatar-uploader-icon {
    border: 1px gray dashed;
    font-size: 28px;
    color: #8c939d;
    width: 100px;
    height: 100px;
    line-height: 100px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
</style>

