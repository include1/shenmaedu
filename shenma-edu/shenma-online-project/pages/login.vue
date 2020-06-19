<template>
  <div class="main">
    <div class="login_container">
    <div class="title">
      <a :class="{'active':active == 2}" @click.prevent="loginPage">登录</a>
      <a :class="{'active':active == 1}" @click.prevent="registerPage">注册</a>
    </div>
    <div class="sign-up-container">
      <!--登录-->
      <form  action="#" :class="{'current-page':active === 1}">
        <div class="input-prepend restyle">
          <div class="error">{{error}}</div>
          <label>用户名：</label>
          <input type="text" placeholder="用户名" v-model="username">
          <span class="username">{{usernameError}}</span>
        </div>
        <div class="input-prepend">
          <label>密码：</label>
          <input type="password" placeholder="密码" v-model="password">
          <span class="passowrd">{{passwordError}}</span>
        </div>
        <div class="btn">
          <input type="submit" class="sign-in-button" :disabled="disabled" value="登录" @click.prevent="loginBtn" onClick="return false">
        </div>
      </form>
      <!--注册-->
      <form  :class="{'current-page':active === 2}" >
        <div class="input-prepend restyle">
          <div class="error">{{registerError}}</div>
          <label>用户名：</label>
          <input type="text" placeholder="用户名" v-model="registerUser.username" @blur="validateUserName">
          <span class="username">{{usernameError}}</span>
        </div>
        <div class="input-prepend">
          <label>密码：</label>
          <input type="password" placeholder="密码" v-model="registerUser.password" @blur="validatePwd" >
          <span class="passowrd">{{passwordError}}</span>
        </div>
        <div class="btn">
          <input type="submit" class="sign-in-button" :disabled="disabled" value="注册" onClick="return false" @click.prevent="registerBtn">
        </div>
      </form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>—————  社交帐号登录  —————</h6>
        <ul>
          <li ><a id="weixin" class="weixin" target="_blank" href="http://localhost:8004/xinlang/connect"><img src="../assets/xinlang.png" style="width: 27px;height: 27px;margin-left:150px;margin-top: 10px"/></a></li>
        </ul>
      </div>
    </div>
    </div>
  </div>
</template>
<script>
  import user from '@/api/user'
export default {
  layout: 'default',
  data(){
    return{
      active:1,
      username:'',
      password:'',
      user:{},
      usernameError:'',
      passwordError:'',
      error:'',
      registerUser:{
        username:'',
        password:''
      },
      registerError:'',
      disabled:false
    }
  },
  created() {
    //判断时登录还是注册
    if(this.$route.query.active == 2){
      this.registerPage()
    }else{
      this.loginPage()
    }
  },
  methods:{
    //登录
    async loginBtn(){
      //校验用户名和密码

      if(this.username !== ""&&this.password !== "" ) {
        this.disabled = true
        this.usernameError= ''
        this.passwordError=''
        this.user = {username:this.username,password:this.password}
        user.login(this.user).then(response => {
          let info = response.data
            if(info.success){
              this.$message({
                type:'success',
                message:'登录成功'
              })
              window.location.href='/?token='+info.data.token
            }else {
              this.error="用户名或密码错误，请重新登录！"
            }
            //禁用按钮解除
            this.disabled = false
        })
      }else {
        if(this.username == ''){
          this.usernameError = '用户名不能为空!!!'
        }else {
          this.usernameError == ''
        }
        if(this.password == ''){
          this.passwordError='密码不能为空!!!'
        }else {
          this.passwordError == ''
        }
        return false;
      }
    },
    registerBtn(){
      //校验用户名和密码
      if(this.registerUser.username !== ""&&this.registerUser.password !== "" ) {
        this.usernameError= ''
        this.passwordError=''
        //禁止按钮
        this.disabled = true
        user.addUser(this.registerUser).then(response => {
          if(response.data.success){
            this.$message({
              type:'info',
              message:'注册成功'
            })
            window.location.href='/login'
          }else {
              this.$message("注册失败，请稍后再试")
          }
          this.disabled = false
        })
      }else {
        if(this.registerUser.username == ''){
          this.usernameError = '用户名不能为空!!!'
        }else {
          this.usernameError == ''
        }
        if(this.registerUser.password == ''){
          this.passwordError='密码不能为空!!!'
        }else {
          this.passwordError == ''
        }
        return false;
      }
    },
    validateUserName(){
      if(this.registerUser.username !== '') {
        user.getUserByName(this.registerUser.username).then(response => {
          if (response.data.success) {
            this.registerError = '用户名已存在,请重新输入!!!'
          }else{
            this.registerError=''
          }
          this.usernameError = ''
        })
      }else {
        this.usernameError = '用户名不能为空'
      }

    },
    validatePwd(){
      if(this.registerUser.password != ''){
        this.passwordError=''
      }else{
        this.passwordError='密码不能为空'
      }
    },
    loginPage(){
      this.active = 2
    },
    registerPage(){
      this.active = 1
    }

  }
}
</script>
<style scoped>
   .error{
     font-size:12px;
     width: 200px;
     text-align: left;
     left: 120px;
     top:20px;
     color: red;
     position: absolute;
   }
  .current-page{
    display: none;
  }
  .more-sign{
    margin-top: 10px;
  }
  .more-sign h6{
    text-align: center;
    margin-left: 10px;
  }
  .sign-up-container .btn{
    margin-top: 60px;
    text-align: center;
    height: 50px;
  }
  .sign-up-container .btn input:hover{
    background:#10c46e;
    opacity: 0.9;
  }
  .sign-up-container .btn input{
    cursor: pointer;
    width: 240px;
    height: 40px;
    background:#10c46e;
    border: solid 1px white;
    border-radius: 3px;
    font-size: 18px;
    color: white;
    letter-spacing:5px;
    opacity: 0.7;
  }
  .sign-up-container .input-prepend label{
    display: inline-block;
    width: 65px;
    font-size: 14px;
  }
  .sign-up-container .input-prepend input:hover{
    border: solid 1px green;
  }
  .sign-up-container .input-prepend input{
    background: ghostwhite;
    border: solid 1px darkgrey;
    width: 180px;
    height: 30px;
    margin-left: -10px;
    border-radius: 6px;
    text-indent:3px;
    margin-top: 40px;
  }
  .sign-up-container .input-prepend{

    height: 40px;
    text-align: center;
    margin-top:10px ;
  }
  .sign-up-container .input-prepend span{
    position: absolute;
    width: 100px;
    color: red;
    letter-spacing: 0;
  }
  .sign-up-container .input-prepend .username{
    top:72px;
    left: 115px;
  }
  .sign-up-container .input-prepend .passowrd{
    top:122px;
    left: 110px;
  }
  .sign-up-container{
    height: 300px;
    position: relative;
  }
   .main{
     display: block;
     background: #e8e6e6;
     margin: 0 auto;
     height: 500px;
     position: relative;
   }
   .login_container{
     align: center;
     width: 362px;
     height: 360px;
     position: absolute;
     margin-top: 40px;
     margin-left: 38%;
     background: white;
   }
  .title{
      height: 60px;
  }
  .title a{
    line-height: 60px;
    display: block;
    float: left;
    width: 50%;
    text-align: center;
    font-size: 16px;
    color: #616161;
  }
  a{
    text-decoration: none;
    cursor: pointer;
  }
  .active{
    background:#10c46e;
    opacity: 0.7;
    color: black;
  }
  a:hover{
    color: gainsboro;
  }
  .active:hover{
    background:#10c46e;
    opacity: 0.9;
    color: black;
  }
</style>
