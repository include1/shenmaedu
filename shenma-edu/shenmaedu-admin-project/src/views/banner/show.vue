<template>
  <div class="app-container">
    <el-card>
      <el-header style="font-size: 16px;margin-left:-25px;margin-top: -5px">首页轮播图展示</el-header>
      <!--返回上一级按钮-->
      <el-button type="primary" @click="reback">返回上一级</el-button>
      <!--显示首页轮播图-->
      <div style="margin-top: 30px;margin-left: 10%">
        <el-carousel indicator-position="outside" style="width: 90%;height:370px">
          <el-carousel-item v-for="(banner,index) in bannerList" :key="index" >
            <img :src="banner.imgUrl" style="width: 100%;height:100%">
          </el-carousel-item>
        </el-carousel>
      </div>
    </el-card>
  </div>
</template>

<script>
  import {getList} from '@/api/banner'

    export default {
        name: "show",
        data(){
          return{
            bannerList:[]
          }
        },
        created() {
          this.fethData()
        },
        methods:{
          //初始化调用的数据
          fethData(){
              getList(5).then(response =>{
                this.bannerList = response.data.bannerList
              })
          },
          //返回上一级按钮
          reback(){
              this.$router.push('/banner/list')
          }
        }
    }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
</style>
