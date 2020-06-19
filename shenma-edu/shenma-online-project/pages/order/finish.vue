<template>
  <div class="main">
    <div class="container">
      <el-steps :active="3"  finish-status="success" :space="600" align-center style="margin-top:20px;">
        <el-step title="订单确认"></el-step>
        <el-step title="订单支付"></el-step>
        <el-step title="订单完成"></el-step>
      </el-steps>
      <!--信息-->
      <div class="success">支付成功!!!<span style="font-size: 15px;color: #41B883;margin-left: 30px">{{content}}</span></div>
    </div>
  </div>
</template>

<script>
  import 'assets/css/order.css'
  export default {
    layout:'default',
    name: "finish",
    data(){
      return{
          total:5,
          content:''
      }
    },
    mounted() {
      this.interval()
    },
    methods:{
        interval(){
          window.setInterval(() => {
            this.content = '倒数'+this.total + 's后，跳转到首页'
            this.total--
            if(this.total == 0) {
              window.location.href='/'
            }
          },1000)
        }
    }
  }
</script>
<style scoped>
  .success{
    font-size: 30px;
    width: 75%;
    margin: 0 auto;
    margin-top: 50px;
  }
  .btn:hover{
    background: #43bc60;
    opacity: 1;
  }
  .btn{
    background: #43bc60;
    border-color: #43bc60;
    opacity: 0.8;
    color: white;
    margin-left: 560px;
  }
  .container{
    width: 900px;
    padding: 20px;
    min-height: 500px;
    margin:0 auto;
    background: white;
    border-radius: 3px;
  }
  .main{
    padding: 20px 0;
    width: 100%;
    background: rgb(250, 250, 250);
  }
</style>
