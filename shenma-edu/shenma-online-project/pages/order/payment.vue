<template>
  <div class="main">
    <div class="container">
      <!--步骤条-->
      <el-steps :active="2"  finish-status="finish" :space="500" align-center style="margin-top:20px;">
        <el-step title="订单确认"></el-step>
        <el-step title="订单支付"></el-step>
        <el-step title="订单完成"></el-step>
      </el-steps>
      <!--商品信息-->
      <div class="items">
        <div class="items-left">
          <img :src="order.courseImg">
        </div>
        <div class="items-right">
          <div class="items-right-info">
            <div class="items-right-card">
              <div class="items-right-card-title">订单编号</div>
              <div class="items-right-card-content">{{order.outTradeNo}}</div>
            </div>
            <div class="items-right-card">
              <div class="items-right-card-title">商品名称</div>
              <div class="items-right-card-content">{{order.courseTitle}}</div>
            </div>
            <div class="items-right-card">
              <div class="items-right-card-title">商品价格</div>
              <div class="items-right-card-content" style="color:red;">￥{{order.totalFee}}</div>
            </div>
          </div>
        </div>
      </div>
      <form action="http://localhost:9000/shenma/frontOrder/trade" method="post">
      <!--选择支付方式-->
      <div class="payment">
        <div class="payment-title">
          <span>支付方式</span>
        </div>
        <div class="payment-list">
          <div class="alipay" :class="{active:active==1}" title="支付宝" @click="change(1)">
              <img src="../../assets/img/paylogo.png" />
          </div>
          <input type="hidden" name="orderSn" v-model="orderSn"/>
          <input type="hidden" name="type" v-model="type"/>
<!--          <div class="alipay" :class="{active:active==2}" title="微信" @click="change(2)">-->
<!--            <img src="../../assets/img/paylogo.png" >-->
<!--          </div>-->
        </div>
      </div>
      <!--商品价格-->
      <div class="price">
        <div class="price-info">
          <div class="price-card">
            <div class="price-title">商品价格</div>
            <div class="price-content" style="font-size: 12px">￥{{order.totalFee}}</div>
          </div>
        </div>
      </div>
      <!--应付价格-->
        <div class="price">
          <div class="price-info">
            <div class="price-card">
              <div class="price-title">应付</div>
              <div class="price-content" style="font-size: 16px;color: red;">￥{{order.totalFee}}</div>
            </div>
          </div>
        </div>
        <button style="margin-top: 12px;" type="submit" class="btn">订单支付</button>
      </form>
    </div>
  </div>

</template>

<script>
  import 'assets/css/order.css'
  import order from "../../api/order";
  export default {
    layout:'default',
    name: "payment",
    data(){
      return{
          orderSn:'',
          order:{},
          type:'alipay',
          active: 1
      }
    },
    created() {
      //获取订单号查询 订单信息
      this.orderSn = this.$route.query.sn
      this.getOrderInfo()
  },
    methods:{
      //转变
      change(id){
        if(id === 1){//支付宝
           this.type='alipay'
           this.active=1
        }else{//微信
          this.type='weixin'
          this.active=2
        }
      },
      //获取订单信息
      getOrderInfo(){
        order.getOrderInfoByOrderSn(this.orderSn).then(response=>{
             console.log(response)
             this.order = response.data.data.order
        })
      },
      //支付按钮
      // paymentBtn(){
      //   order.trade(this.order.outTradeNo,this.type).then(response=>{
      //       this.alipayHtml = response.data.data.form
      //   })
      // }
    }
  }
</script>
<style scoped>
   .active{
      border-color: #86e89e;
   }
  .alipay img{
    width: 85%;
    height: 30px;
  }
   .payment-list .alipay:hover{
     cursor: pointer;
   }
  .payment-list .alipay{
    text-align: center;
    vertical-align: center;
    width: 140px;
    height: 30px;
    border:2px #41B883 solid;
    float: left;
  }
  .payment-list{
    margin-top: 20px;
  }
  .payment-title{
    font-size: 12px;
  }
  .payment{
    width: 700px;
    height: 80px;
    margin: 0 auto;
    margin-top: 20px;
  }
  .items-right-info{
    padding-top: 30px;
  }
  .btn:hover{
    background: #43bc60;
    opacity: 1;
  }
  .btn{
    display: inline-block;
    white-space: nowrap;
    cursor: pointer;
    border: 1px solid #43bc60;
    text-align: center;
    box-sizing: border-box;
    transition: .1s;
    padding: 10px 20px;
    font-size: 14px;
    border-radius: 4px;
    background: #43bc60;
    opacity: 0.8;
    color: white;
    margin-left: 700px;
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
