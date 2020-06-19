<template>
  <!--个人账号-->
  <div class="main">
    <div class="container">
      <div class="cd-content">
        <!--左边部分-->
        <div class="cd-sidebar">
          <ul class="cd-sidebar__list">
            <li class="cd-sidebar__heading">
              个人中心
            </li>
            <li class="cd-sidebar__item " >
              <a href="/account/setting" >个人信息</a>
            </li>
            <li class="cd-sidebar__item " >
              <a href="/account/course" >我的课程</a>
            </li>

            <li class="cd-sidebar__item " >
              <a href="/account/comment"   >我的评论</a>
            </li>
            <li class="cd-sidebar__item active " >
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
            <div class="cd-main__title">我的订单</div>
          </div>
          <div class="cd-main__body">
            <el-table
              :header-cell-style="{background:'#f5f5f5',color:'black'}"
              :data="orderList"
              style="width: 100%;">
              <el-table-column
                prop="outTradeNo"
                label="订单号"
                width="260">
              </el-table-column>
              <el-table-column
                label="商品名称"
                width="200">
                <template slot-scope="scope">
                  <a class="goods-name" :href="'/course/'+scope.row.courseId">{{scope.row.courseTitle}}</a>
                </template>
              </el-table-column>
              <el-table-column
                label="订单价格"
                width="90">
                <template slot-scope="scope">
                  ￥{{scope.row.totalFee}}
                </template>
              </el-table-column>
              <el-table-column
                label="订单状态"
                width="90">
                <template slot-scope="scope">
                  <span style="color:#41B883;" v-if="scope.row.state == 0" >未支付</span>
                  <span style="color:#41B883;" v-if="scope.row.state == 1" >已支付</span>
                  <span style="color:#41B883;" v-if="scope.row.state == 2" >交易关闭</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="createTime"
                label="创建时间"
                width="180">
              </el-table-column>
              <el-table-column
                label="操作"
                width="160px"
                fixed="right"
               >
                <template slot-scope="scope">
                  <el-button class="detail" v-if="scope.row.state == 0" @click="delOrder(scope.row.id)" type="text">取消订单</el-button>
                  <el-button class="detail" @click="handleClick(scope.row.id),dialogVisible=true" type="text">查看详情</el-button>
                </template>
              </el-table-column>
            </el-table>
            <!--分页-->
            <div >
              <div class="paging" v-if="orderPage.pages > 1">
                <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                <a
                  :class="{undisable: !orderPage.previous}"
                  href="#"
                  title="首页"
                  @click.prevent="gotoPage(1)">首</a>
                <a
                  :class="{undisable: !orderPage.previous}"
                  href="#"
                  title="前一页"
                  @click.prevent="gotoPage(orderPage.current-1)">&lt;</a>
                <a
                  v-for="page in orderPage.pages"
                  :key="page"
                  :class="{current: orderPage.current == page, undisable: orderPage.current == page}"
                  :title="'第'+page+'页'"
                  href="#"
                  @click.prevent="gotoPage(page)">{{ page }}</a>
                <a
                  :class="{undisable:!orderPage.next}"
                  href="#"
                  title="后一页"
                  @click.prevent="gotoPage((orderPage.current+1)> orderPage.pages ? orderPage.pages:(orderPage.current+1))">&gt;</a>
                <a
                  :class="{undisable: !orderPage.next}"
                  href="#"
                  title="末页"
                  @click.prevent="gotoPage(orderPage.pages)">末</a>
                <div class="clear"/>
              </div>
            </div>
            <!--弹窗-->
            <el-dialog
              title="订单详情"
              width="500px"
              :visible.sync="dialogVisible"
              >
              <!--放入table-->
              <table class="table">
                <tbody>
                <tr class="table-active"><td>订单号</td><td>{{order.outTradeNo}}</td></tr>
                <tr>
                  <td>订单状态</td>
                  <td v-if="order.state == 0">未支付</td>
                  <td v-if="order.state == 1">已支付</td>
                  <td v-if="order.state == 2">关闭交易</td>
                </tr>
                <tr  class="table-active"><td>商品名称</td><td>{{order.courseTitle}}</td></tr>
                <tr><td>商品价格</td><td>￥{{order.totalFee}}</td></tr>
                <tr  class="table-active"><td>创建时间</td><td>{{order.createTime}}</td></tr>
                <tr><td>关闭时间</td><td>{{order.updateTime == null ? '-':order.updateTime}}</td></tr>
                </tbody>
              </table>
              <span slot="footer" class="dialog-footer">
                  <el-button type="primary" @click="dialogVisible = false">关闭</el-button>
              </span>
            </el-dialog>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import "~/assets/css/account.css"
  import Cookies from 'js-cookie'
  import order from  '@/api/order'

    export default {
       layout:'default',
        name: "order",
        data(){
          return{
            orderList:[],
            orderPage:{},
            order:{},
            page:1,
            size:8,
            dialogVisible:false
          }
        },
        mounted() {
              this.getOrderList()

        },
        methods:{
         //删除订单
          delOrder(id){
            console.log('执行了')
              order.delOrder(id).then(response=>{
                console.log(response)
                  if(response.data.success){
                        this.getOrderList()
                  }
              })
          },
         //获取订单列表
          getOrderList() {
            let uId = Cookies.get('uId');
            let token = Cookies.get("token")
            if (uId && token) {
                 order.getOrderListByUserId(this.page, this.size,uId).then(response => {
                      this.orderList = response.data.data.orderList
                      this.orderPage = response.data.data
                 })
            }else {
              window.location.href='/login'
            }
          },
          handleClick(id){
            console.log("查看详情"+id)
            this.getOrderById(id)
          },
          gotoPage(page){
            this.page = page
            this.getOrderList()
          },
          //通过id获取订单详情
          getOrderById(id){
            order.getOrderById(id).then(resonse =>{
               this.order = resonse.data.data.order
            })
          }
        }

    }
</script>

<style scoped>
  .table-active{
    background: #f5f5f5;
  }
  .table{
    text-align: center;
    width: 100%;
  }
  .table tr{
    line-height: 40px;
    height: 40px;
  }
  .detail:hover{
    color: #41B883;
  }
  .goods-name{
     color: cornflowerblue;
  }
  .goods-name:hover{
    color: #41B883;
  }
  .el-table{
    padding: 10px;
  }
  .header-table{
    background: red;
  }
</style>
