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
              <a href="/account/course"  >我的课程</a>
            </li>

            <li class="cd-sidebar__item  active" >
              <a href="/account/comment"   >我的评论</a>
            </li>
            <li class="cd-sidebar__item  " >
              <a href="/account/order"   >我的订单</a>
            </li>
            <li class="cd-sidebar__item " >
              <a href="/account/password"  >修改密码</a>
            </li>

          </ul>
        </div>
          <!--右边部分-->
          <div class="cd-main">
          <!--个人信息-->
          <div class="cd-main__heading">
            <div class="cd-main__title">我的评论</div>
          </div>
          <div class="cd-main__body">
            <div class="comment">
              <div class="nodata" v-if="commentList.length == 0">
                <span style="color: darkgrey">暂时没有评论</span>
              </div>
              <!--评论数据-->
              <div class="comment-content" v-if="commentList.length > 0">
                <!---->
                <div class="media" v-for="(item,index) in commentList" :key="index">
                  <div class="media-left">
                    <a :href="'/course/'+item.courseId"  class="avatar-small" >
                      <img :src="item.courseImg">
                    </a>
                    <div class="right">
                    <a :href="'/course/'+item.courseId" ><span >课程：{{item.courseName}}</span></a>
                    <a :href="'/teacher/'+item.teacherId" ><span >讲师：{{item.teacherName}}</span></a>
                    <span >{{item.commentTime}}</span>
                    </div>
                  </div>
                  <div class="media-body">
                    <div class="content">{{item.content}}</div>
                  </div>
                </div>
              </div>
              <!--分页插件-->
              <div v-if="commentPage.pages > 1">
                <div class="paging">
                  <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                  <a
                    :class="{undisable: !commentPage.previous}"
                    href="#"
                    title="首页"
                    @click.prevent="gotoPage(1)">首</a>
                  <a
                    :class="{undisable: !commentPage.previous}"
                    href="#"
                    title="前一页"
                    @click.prevent="gotoPage(commentPage.current-1)">&lt;</a>
                  <a
                    v-for="page in commentPage.pages"
                    :key="page"
                    :class="{current: commentPage.current == page, undisable: commentPage.current == page}"
                    :title="'第'+page+'页'"
                    href="#"
                    @click.prevent="gotoPage(page)">{{ page }}</a>
                  <a
                    :class="{undisable:!commentPage.next}"
                    href="#"
                    title="后一页"
                    @click.prevent="gotoPage((commentPage.current+1)> commentPage.pages ? commentPage.pages:(commentPage.current+1))">&gt;</a>
                  <a
                    :class="{undisable: !commentPage.next}"
                    href="#"
                    title="末页"
                    @click.prevent="gotoPage(commentPage.pages)">末</a>
                  <div class="clear"/>
                </div>
              </div>
            </div>
          </div>
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
        name: "comment",
        data(){
          return{
            commentList:[],
            commentPage:{
              page:0
            },
            page:1,
            pageSize:5
          }
        },
        mounted() {
            this.getCommentInfo()
        },
        methods:{
            getCommentInfo(){
              //获取用户id
              let uId = Cookies.get("uId")
              let token = Cookies.get("token")
              if(uId&&token) {
                user.getUserCommentInfo(this.page,this.pageSize,uId).then(response=>{
                   console.log(response.data)
                   this.commentList = response.data.data.commentList
                   this.commentPage = response.data.data
                })
              }else {
                window.location.href='/login'
              }
            },
          gotoPage(page){
              this.page = page
              this.getCommentInfo()
          }
        }
    }
</script>
<style scoped>
  .media-body .content{
    text-indent: 50px;
  }
  .media-body{
    margin-top: 10px;
    color: #919191;
    font-size: 13px;
    margin-bottom: 10px;
  }
  a{
    text-decoration: none;
  }
  .media-left .username{
    top:12px;
    left:60px;
    position: absolute;
  }
  .media-left .date{
    top:12px;
    left:500px;
    position: absolute;
  }
  .media-left{
    font-size: 16px;
    position: relative;
  }
  .media-left .right{
    position: absolute;
    display: block;
    top: 10px;
    left: 60px;
  }
  .media-left .right a{
    width: 240px;
    display: inline-block;
  }

  .avatar-small img{
    width:50px;
    height: 50px;
    margin-top: -10px;
    border-radius: 100%;
  }
  .media{
    width: 100%;
    margin-top: 10px;
    border-bottom: solid 1px darkgray;
  }
  .comment-content{
    margin: 0 auto;
    width: 90%;
    padding: 30px;
    position: relative;
  }
  .nodata span{
    line-height: 40px;
  }
  .nodata{
    font-size: 16px;
    text-align: center;
    width: 90%;
    height: 30px;
    margin: 40px auto;
    vertical-align: center;
    line-height: 30px;
  }
  .c-tab-title a{
    cursor: pointer;
  }
  .comment{
    width: 100%;
    margin: 0 auto;
  }

</style>
