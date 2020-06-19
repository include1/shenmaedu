<template>
  <div class="main">
    <div class="container">
      <!--步骤条-->
      <el-steps :active="1"  finish-status="finish" :space="600" align-center >
        <el-step title="订单确认" ></el-step>
        <el-step title="订单支付"></el-step>
        <el-step title="订单完成"></el-step>
      </el-steps>
      <!--商品信息-->
      <div class="items">
        <div class="items-left">
          <img :src="course.coverImg">
        </div>
        <div class="items-right">
          <div class="items-right-info">
            <div class="items-right-card">
              <div class="items-right-card-title">商品名称</div>
              <div class="items-right-card-content"><a :href="'/course/'+course.id">{{course.name}}</a></div>
            </div>
            <div class="items-right-card">
              <div class="items-right-card-title">商品价格</div>
              <div class="items-right-card-content" style="color:red;">￥{{course.price}}</div>
            </div>
          </div>
        </div>
      </div>
      <!--商品价格-->
       <div class="price">
         <div class="price-info">
           <div class="price-card">
               <div class="price-title">商品价格</div>
               <div class="price-content" style="font-size: 12px">￥{{course.price}}</div>
           </div>
         </div>
       </div>
        <!--应付价格-->
        <div class="price">
          <div class="price-info">
            <div class="price-card">
              <div class="price-title">应付</div>
              <div class="price-content" style="font-size: 16px;color: red;">￥{{course.price}}</div>
            </div>
          </div>
          </div>
     <el-button style="margin-top: 12px;" @click="orderBtn" class="btn">订单确认</el-button>
    </div>
  </div>
</template>

<script>
  import 'assets/css/order.css'
  import course from '@/api/course'
  import Cookies from 'js-cookie'
  import  order from  '@/api/order'
    export default {
      layout:'default',
        name: "index",
        data(){
         return{
             course:{},
             order:{
               courseId:'',
               userId:'',
               courseTitle:'',
               courseImg:''
             }
         }
        },
       created() {
        //获取课程信息
         this.getCourseInfoId()
       },
       methods:{
        //获取购买课程的详细信息
         getCourseInfoId(){
           let id = this.$route.query.courseId
           if(id) {
             course.getCourseById(id).then(response => {
                this.course = response.data.data.course
             })
           }
         },
        //跳转到支付页面
         orderBtn(){
            let courseId = this.course.id
            let uId = Cookies.get('uId')
            if(courseId  && uId){
              this.order.courseId = courseId
              this.order.userId = uId
              this.order.courseTitle = this.course.name
              this.order.courseImg = this.course.coverImg
              this.order.totalFee = this.course.price
              //添加订单
              order.addOrder(this.order).then(response=>{
                   //直接转发
                   if(response.data.success) {
                     window.location.href = 'http://localhost:6000/order/payment?sn='+response.data.data.sn
                   }
              })
            }else{
              window.location.href='/login'
            }
         }
       }
    }
</script>
<style scoped>
  a{text-decoration: none}
  .items-right-card-content:hover a{
     color: #41B883;
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
