<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <h1 id="logo">
          <a href="http://localhost:6000" title="神马学院">
            <img src="~/assets/img/logo.png" style="margin-top: 5px" alt="神马学院">
          </a>
        </h1>
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a href="/">首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a >课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a >名师</a>
            </router-link>
<!--            <router-link to="/article" tag="li" active-class="current">-->
<!--              <a>文章</a>-->
<!--            </router-link>-->
<!--            <router-link to="/qa" tag="li" active-class="current">-->
<!--              <a>问答</a>-->
<!--            </router-link>-->
          </ul>
          <!-- / nav -->
          <ul class="h-r-login" >
            <li id="no-login" v-if="!loginInfo.id">
              <a href="/login?active=1" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/login?active=2" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <!--<li  v-if="loginInfo.id">
              <a href="#" title="消息" id="headerMsgCountId">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>-->
            <li  v-if="loginInfo.id" class="loginInfo_li">
              <a href="/account/setting" :title="loginInfo.nickname" style="font-size: 14px;color: grey">
                <img
                  :src="loginInfo.headImg"
                  class="vam picImg"
                  style="width: 45px;height:45px;border-radius:100%; "
                >
                <span class="vam disIb" id="userName" >{{loginInfo.nickname}}</span>
              </a>
              <div :class="popWindow?'bottom-triagle':'top-triagle'" @click="change">
              </div>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <!--弹窗-->
          <div :class="{'popWindow-container':popWindow}">
            <ul class="popWindow">
              <li class="top"><i class="triagle_account"></i></li>
              <li class="content">
                <a href="#" title="个人账户" @click.prevent="goAccount">个人账户</a>
              </li>
              <li class="content">
                <a href="#" title="退出登录" @click.prevent="logOut">退出登录</a>
              </li>
            </ul>
          </div>
          <!--搜索-->
          <aside class="h-r-search">
            <form action="/search" method="get">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="keyword" >
                 <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
    <nuxt/>
    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class="b-foot">
                <span style="font-size: 14px;margin-left: 40%">©2020课程版权均归神马学院所有</span>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import user from '@/api/user'
import Cookies from 'js-cookie'
export default {
  data(){
    return{
      token:'',
      loginInfo:{},
      popWindow:true
    }
  },
  created() {
   //获取token信息
    this.token = this.$route.query.token
    if(this.token) {
      this.showInfo()
    }else {
      this.getToken()
    }
  },
  methods:{
     //去个人中心
      goAccount(){
          user.goAccount(this.loginInfo.id).then(response=>{
             if(response.data.success){
               window.location.href='/account/setting'
             }
          })
      },
    //退出登陆
      logOut(){
        let tokenValue = Cookies.get("token")
        user.logout(tokenValue).then(response=>{
          if(response.data.success) {
            //清空Cookie中的东西
            Cookies.remove('token')
            Cookies.remove('uId')
            window.location.href='http://localhost:6000'
          }
        })
      },
    //获取前端传来的token
      getToken(){
        if(Cookies.get("token")) {
          this.token = Cookies.get("token")
          this.showInfo()
        }
      },
    //获取用户信息
      showInfo(){
        user.getInfoByToken(this.token).then(response=>{
          const loginInfo = response.data.data.user
          if(loginInfo){//token未过期
            this.loginInfo = response.data.data.user
            Cookies.set("token",this.token)
            Cookies.set("uId",this.loginInfo.id)
          }else {
              this.$message({
                type:'info',
                message:'登录过期，请重新登录'
              })
              Cookies.remove('token')
              this.$router.push({path:'/login'})
          }
        })
      },
    //是否出现下拉框
    change(){
        this.popWindow = !this.popWindow;
    }
  }
};
</script>
<style scoped>

  .popWindow-container{
    display: none;
  }
   .popWindow{
    position: absolute;
    right:2px;
    top:50px;
    width: 120px;
    z-index: 10000000000000;
  }

  .popWindow a{
    text-decoration: none;
    font-size: 15px;
    color: black;
  }
  .popWindow .top{
    text-align: center;
  }
  .popWindow .content{
    border-bottom: solid 1px grey;
    width: 100%;
    height: 60px;
    line-height: 60px;
    text-align: center;
    background: #fafafa;
  }
  .popWindow .content:hover{
    background: #41B883;
  }
  .loginInfo_li{
    position: absolute;
    right: 60px;
  }
  .triagle_account {
    display: inline-block;
    width: 0;
    height: 0;
    margin-top: 10px;
    vertical-align: middle;
    border-bottom: 8px solid beige;
    border-right: 8px solid transparent ;
    border-left: 8px solid transparent;

  }
  .bottom-triagle {
    display: inline-block;
    width: 0;
    height: 0;
    margin-left: 5px;
    vertical-align: middle;
    border-bottom: 6px solid;
    border-right: 6px solid transparent;
    border-left: 6px solid transparent;
    cursor: pointer;
  }
  .top-triagle {
    display: inline-block;
    width: 0;
    height: 0;
    margin-left: 5px;
    vertical-align: middle;
    border-top: 6px solid;
    border-right: 6px solid transparent;
    border-left: 6px solid transparent;
    cursor: pointer;

  }
  .loginInfo_li:hover a span{color: #6ACC7E}
</style>
