<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section  class="container" >
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <a href="/course" title class="c-999 fsize14">课程列表</a>
        \
        <span class="c-333 fsize14">{{courseInfo.oneCategory}}</span>
        \
        <span class="c-333 fsize14">{{courseInfo.twoCategory}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img :src="courseInfo.coverImg" :alt="courseInfo.courseName" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseInfo.courseName}}</span>
            </h2>
            <section class="c-attr-jg" v-if="Number(courseInfo.price) > 0">
              <span  class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseInfo.price}}</b>
            </section>
            <section class="c-attr-jg" v-if="Number(courseInfo.price) === 0">
              <b class="c-yellow" style="font-size:24px;">免费</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲：<a  :href="'/teacher/'+courseInfo.teacherId"> {{courseInfo.teacherName}}&nbsp;&nbsp;</a>&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam" >
                <em class="icon18 scIcon"></em>
                <a class="c-fff" title="加入学习" @click.prevent="addUserCourse" v-if="courseState">加入学习</a>
                <span  title="已加入我的课程" style="color: white;"  v-if="!courseState">已加入我的课程</span>
              </span>
            </section>
            <section class="c-attr-mt">
              <a href="#playvideos" title="立即观看"  v-if='courseInfo.free == 0&&userCourseState == 1' class="comm-btn c-btn-3" style="color: white" @click="gochange(1)">立即观看</a>
              <a   title="立即购买" v-if='courseInfo.free == 1&&userCourseState == 1' class="comm-btn c-btn-3"  @click.prevent="purchaseBtn">立即购买</a>
              <a  title="已购买" v-if="userCourseState == 0" class="comm-btn c-btn-3" >已购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box" >
          <ol class="thr-attr-ol clearfix">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseInfo.buyNum}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseInfo.learnTime}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseInfo.viewNum}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a  name="c-i" :class="{'current':current === 1}" title="课程详情" @click.prevent="gochange(1)">课程详情</a>
                  <a  name="c-i" :class="{'current':current === 2}"  title="课程评论" @click.prevent="getCourseCommentInfo(),gochange(2)">课程评论({{total}})</a>
                  <a  name="c-i" :class="{'current':current === 3}"  title="发表评论" @click.prevent="gochange(3)">发表评论</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20"  :class="{'currentPage':Number(current) !== 1}">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <div v-html="courseInfo.description">
                        </div>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul id="playvideos">
                          <!--
                          文件目录遍历章节
                           -->
                          <li class="lh-menu-stair" v-for="(chapter,index) in chapterVideoList" :key="index">
                            <a  :title="chapter.name" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.name}}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <!--已购买-->
                              <li class="lh-menu-second ml30" v-for="(video,index) in chapter.videoList" :key="index">
                                <a :href="'/player/'+video.videoSourseId"
                                   v-if="courseInfo.free==0||userCourseState==0"
                                :title="video.title"
                                target="_blank">
                                  <span class="fr">
                                    <i class="free-icon vam mr10" v-if="courseInfo.free==0">免费</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>
                                <!--未购买-->
                                <a href="javascript:void(0)" v-if="courseInfo.free==1 &&userCourseState==1"
                                   @click="this.$message('该课程需要购买，才能观看')"
                                   :title="video.title"
                                   >
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>
                                <!--面具mask-->
                                <div class="mask" v-if="courseInfo.free==1 && userCourseState==1"></div>
                                <!--btn-->
                                <div class="payBtn" v-if="courseInfo.free==1 && userCourseState==1"><button @click="purchaseBtn">购买</button></div>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
              </article>
              <!--课程评论-->
              <div class="comment" :class="{'currentPage':Number(current) !==2}">
                 <div class="nodata" v-if="commentList.length == 0">
                    <span>暂时没有评论</span>
                 </div>
                <!--评论数据-->
                <div class="comment-content" v-if="commentList.length > 0">
                    <div class="media" v-for="(item,index) in commentList" :key="index">
                      <div class="media-left">
                        <a href="javascript:void(0)"  class="avatar-small" >
                          <img :src="item.headImg">
                        </a>
                        <a href="javascript:void(0)" class="username"><span >{{item.username}}</span></a>
                        <span class="date">{{item.createTime}}</span>
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
              <!--发表评论-->
              <div class="comment" :class="{'currentPage':Number(current) !==3}">
                <div style="font-size: 15px;">发表评论</div>
                <form class="comment-form">
                  <div class="form-group">
                      <textarea class="form-control" name="content"  v-model="courseComment.content"  placeholder="请输入要填写的内容"></textarea>
                  </div>
                  <div class="form-group">
                    <button type="button" :disabled="disabled" class="btn" @click="addCourseComment">发 &nbsp;表</button>
                  </div>
                </form>
              </div>
            </div>
          </section>
        </article>
        <!--讲师-->
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a :href="'/teacher/'+courseInfo.teacherId">
                        <img :src="courseInfo.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" :href="'/teacher/'+courseInfo.teacherId">{{courseInfo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseInfo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>
<script>
import course from "@/api/course"
import user from "../../api/user"
import Cookies from 'js-cookie'
import order from "../../api/order";
export default {
  data(){
    return{
      current:1,
      commentList:[],
      searchMap:{
        courseId:''
      },
      commentPage:{},
      page:1,
      total:0,//评论总数
      courseComment:{
        courseId:'',
        content:'',
        point:0,//评分-----------等待开发
        up:0//点赞---------------等待开发
      },
      userCourse:{},
      courseState:true,//课程状态，是否加入课程
      userCourseState:1,//判断用户是否购买了该课程
      disabled:false
    }
  },created() {
    //获取评论信息
    this.getCourseCommentInfo()
    //判断该课程用户已经加入我的课程中
    this.getUserCourse()
    //判断该用户是否已购买该课程
    this.isPurchase()
  },
  //在页面数据渲染之前调用
  asyncData({ params, error }) {
    return course.getCourseInfoId(params.id)
      .then(response => {
        return {
          courseInfo: response.data.data.courseInfo,
          chapterVideoList: response.data.data.chapterInfo
        }
      })
  },
  methods: {
    //是否购买课程
    isPurchase() {
      let userId = Cookies.get('uId')
      let token = Cookies.get('token')
      let courseId = this.$route.params.id
      if (userId && courseId && token)
        order.getPurchaseState(userId, courseId).then(response => {
           if(response.data.success){
             this.userCourseState = 0
           }
        })
    },
    //购买按钮
    purchaseBtn(){
      let userId = Cookies.get('uId')
      let courseId = this.$route.params.id
      let token = Cookies.get('token')
      if (userId && courseId && token) {
            window.location.href='/order?courseId='+courseId
      }else {
        this.$message({
          type:'info',
          message:'请先登录'
        })
        window.location.href='/login'
      }
    },
    //获取该课程我是否添加了
    getUserCourse() {
      let userId = Cookies.get('uId')
      let token = Cookies.get('token')
      let courseId = this.$route.params.id
      if (userId && courseId && token) {
        this.userCourse.userId = userId
        this.userCourse.courseId = courseId
        user.getUserCourse(this.userCourse).then(response => {
          if (response.data.success) {
            this.courseState = true
          } else {
            this.courseState = false
          }
        })
      }
    },
    //加入我的课程
    addUserCourse() {
      let userId = Cookies.get('uId')
      let courseId = this.$route.params.id
      let token = Cookies.get('token')
      if (userId && courseId && token) {
        this.userCourse.userId = userId
        this.userCourse.courseId = courseId
        this.courseState = false
        course.addUserCourse(this.userCourse).then(response => {
          if (response.data.success) {
            this.$message({
              type: 'success',
              message: '加入我的课程成功'
            })
          }
        })
      }else{
        this.$message({
          type:'info',
          message:'请先登录'
        })
      }
    },
    //获取课程评论信息
    getCourseCommentInfo() {
      let courseId = this.$route.params.id
      if (courseId) {
        this.searchMap.courseId = courseId
        course.getCourseCommentInfo(this.page, 5, this.searchMap).then(response => {
          this.commentList = response.data.data.map.courseCommentList
          this.commentPage = response.data.data.map
          this.total = response.data.data.map.total
        })
      }
    },
    //添加评论
    addCourseComment() {
      let uId = Cookies.get('uId')
      let token = Cookies.get('token')
      if (uId && token) {
        if (this.courseComment.content=='') {
          return this.$message('评论内容不能为空')
        } else {
          //不为空，只能提交一次
          this.disabled = true
          this.courseComment.courseId = this.$route.params.id
          user.addCourseComment(this.courseComment).then(response => {
            if (response.data.success) {
              this.gochange(2)//切换到评论
              this.getCourseCommentInfo()
              this.$message({
                type: 'success',
                message: '评论成功'
              })
            }
            this.courseComment = {content:''}
            this.disabled = false
          })
        }
      }else{
        window.location.href = '/login'
      }
    },
    //换页
    gotoPage(page) {
      this.page = page
      this.getCourseCommentInfo()
    },
    //改变样式
    gochange(id) {
      this.current = id
    }
  }
};
</script>
<style scoped>
  .c-attr-mt a:hover{
     cursor: pointer;
  }

  .payBtn button:hover{
    opacity: 1;
    color: black;
  }
  .payBtn button{
    width: 55px;
    height: 25px;
    color: white;
    background: #41B883;
    opacity: 0.8;
    border: none;
  }
  .payBtn{
    top:3px;
    width: 60px;
    height: 30px;
    position: absolute;
    right:10px;

  }
  .lh-menu-second{
    position: relative;
  }
  .mask a{
    font-size: 16px;
    color: black;
  }
  .mask{
    text-align: right;
    width: 732px;
    height: 32px;
    position: absolute;
    opacity: 0.3;
    top: 0;
  }
  .c-fff:hover{
    cursor: pointer;
  }
  .comm-btn{
    margin-left: 150px;
  }
  .form-group .btn:hover{
      color: white;
      background: green;
  }
  .form-group .btn{
    cursor: pointer;
    width: 60px;
    background: #41B883;
    height: 30px;
    border: none;
    border-radius: 3px;
  }
  .form-group .form-control{
    min-height: 200px;
    max-height: 500px;
    min-width: 100%;
    max-width: 100%;
  }
  .form-group{
    width: 100%;
  }
  .comment-form{
    margin-top: 10px;
  }
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
    color: darkgrey;
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

  .avatar-small img{
    width: 40px;
    height: 40px;
    border-radius: 100%;
  }
  .media{
    width: 100%;
    margin-top: 10px;
    border-bottom: solid 1px darkgray;
  }
  .comment-content{
    margin: 0 auto;
    width: 100%;
  }
   .nodata span{
     line-height: 40px;
   }
  .nodata{
    color: black;
    font-size: 16px;
    text-align: center;
    width: 90%;
    height: 40px;
    margin: 40px auto;
    vertical-align: center;
    line-height: 40px;
  }
  a:hover{
    color: #41B883;
  }
  .c-tab-title a{
    cursor: pointer;
  }
  .currentPage{
    display: none;
  }
  .comment{
    width: 100%;
    margin: 0 auto;
  }
</style>
