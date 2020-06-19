<template>
  <el-container>
    <el-header>欢迎登录 - 神马教育-后台管理系统</el-header>
    <el-main>
      <!--头部-->
      <el-card class="box-card" shadow="always">
        <div class="cross-bar">
          <div class="triangle"></div>
          <div class="cross-bar-bg"></div>
          <div class="title">神马教育-后台管理系统</div>
        </div>
        <!--表单-->
        <el-form   class="loginForm"  ref="loginFormRef" :model="loginForm" :rules="loginRules">
          <el-form-item prop="username" >
             <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
            <el-input
              prefix-icon="el-icon-user"
              v-model="loginForm.username"
              placeholder="用户名"
              name="username"
              type="text"
            />
          </el-form-item>

          <el-form-item  prop="password">
                 <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
            <el-input
              prefix-icon="el-icon-lock"
              :key="pwdType"
              v-model="loginForm.password"
              :type="pwdType"
              placeholder="密码"
              name="password"
            />
            <span class="show-pwd" @click="showPwd">
                      <svg-icon :icon-class="pwdType === 'password' ? 'eye' : 'eye-open'" />
            </span>
          </el-form-item>
          <el-form-item size="small" align="center">
            <el-button :loading="loading" type="primary" style="width: 40%" @click.native.prevent="handleLogin">登 陆</el-button>
            <el-button  type="info" style="width: 40%" @click.native.prevent="resetForm">重 置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
    <!--尾部-->
    <el-footer  class="footer">@ 2020 神马教育 版权所有</el-footer>
  </el-container>
</template>
<script>

  import {Message} from "element-ui";

  export default {
    name: 'Login',
    data() {

      return {
        loginForm: {
          username: 'admin',
          password: '123456'
        },
        loginRules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max:12, message: '长度在 3 到 12 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
          ]
        },
        loading: false,
        pwdType: 'password',
        redirect: undefined
      }
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    methods: {
      showPwd() {
        if (this.pwdType === 'password') {
          this.pwdType = ''
        } else {
          this.pwdType = 'password'
        }
      },
      handleLogin() {
        this.$refs.loginFormRef.validate(valid => {
          if (valid) {
            this.loading = true
            this.$store.dispatch('Login', this.loginForm).then(() => {
              this.loading = false
                Message({
                  message: '登陆成功',
                  type: 'success',
                  duration: 2 * 1000
                })
                this.$router.push({path: this.redirect || '/'})

            }).catch(() => {
              this.loading = false
            })
          } else {
            return false;
          }
        })
      },
      resetForm() {
        this.$refs.loginFormRef.resetFields();
      }
    }
  }
</script>

<style scoped>

  .svg-container{
    width: 30px;
    padding: 1px 4px 6px 10px;
    position: absolute;
    color: darkgray;
    z-index: 2;
    vertical-align: middle;
    display: inline-block;
  }
  .el-main{
    height: 100%;
    display: flex;
    justify-content:center;
    align-items:Center;
  }

  header,footer{
    background: rgba(248, 248, 255, 0.3);
    line-height: 60px;
  }
  .footer{
    text-align: center;
    font-size: 14px;
    position: absolute;
    bottom:0;
    width:100%;
  }
  .el-card{
    width: 350px;
    min-width: 350px;
    max-width: 400px;
    height: 280px;
    margin: 0 auto;
    margin-top:5%;
  }
  .loginForm{
    margin-top: 72px;
    padding: 20px;
  }
  .cross-bar{
    height: 50px;
    width: 380px;
    position: absolute;
    margin-left: -41px;
    padding-left: 1px;
    margin-top: 16px;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 3px;
    font-size: 16px;
    color: darkgray;
    cursor: pointer;
    user-select: none;
  }
  .cross-bar-bg{
    position: absolute;top: 0;bottom: 0;right: 0;left: 0;background: #3c8dbc;z-index: 10;
  }
  .triangle{
    width:0;
    height:0;
    border-width:0 14px 14px;
    border-style:solid;
    border-color:transparent transparent #333;/*透明 透明  灰*/
    margin: 48px auto;
    position: absolute;
    z-index: 11;
    transform:rotate(45deg);
  }
  .title{
    position: absolute;
    z-index: 12;
    padding: 13px 55px;
    color: white;
  }
  .el-header{
    color: #3c8dbc;
  }
</style>
