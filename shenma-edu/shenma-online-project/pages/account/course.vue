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
              <a href="/account/setting"  >个人信息</a>
            </li>
            <li class="cd-sidebar__item  active" >
              <a href="/account/course"  >我的课程</a>
            </li>

            <li class="cd-sidebar__item " >
              <a href="/account/comment"   >我的评论</a>
            </li>
            <li class="cd-sidebar__item  " >
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
            <div class="cd-main__title">我的课程</div>
          </div>
          <div class="cd-main__body">
            <section class="no-data-wrap" v-if="courseList.length==0">
              <em class="icon30 no-data-ico">&nbsp;</em>
              <span class="c-666 fsize14 ml10 vam">没有相关数据...</span>
            </section>
            <!-- /无数据提示 结束-->
            <article class="  comm-course-list">
              <ul class="of">
                <li v-for="(item,index) in courseList" :key="index"  >
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img :src="item.coverImg"  width="100%" >
                      <div class="cc-mask">
                        <a @click.prevent="removeUserCourse(item.id)"  title="移除课程" target="_blank" class="comm-btn c-btn-1">移除课程</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a :href="'/course/'+item.id" :title="item.name" class="course-title fsize14 c-333">{{item.name}}</a>
                    </h3>
                    <!--<section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">{{item.free == 1 ?"付费":"免费"}}</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{item.studyNum}}人学习</i>
                        |
                        <i class="c-999 f-fA">{{item.commentNum}}评论</i>
                      </span>
                    </section>-->
                  </div>
                </li>
              </ul>
            </article>
               <!--分页-->
            <div v-if="coursePage.total > 8">
              <div class="paging">
                <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                <a
                  :class="{undisable: !coursePage.previous}"
                  href="#"
                  title="首页"
                  @click.prevent="gotoPage(1)">首</a>
                <a
                  :class="{undisable: !coursePage.previous}"
                  href="#"
                  title="前一页"
                  @click.prevent="gotoPage(coursePage.current-1)">&lt;</a>
                <a
                  v-for="page in coursePage.pages"
                  :key="page"
                  :class="{current: coursePage.current == page, undisable: coursePage.current == page}"
                  :title="'第'+page+'页'"
                  href="#"
                  @click.prevent="gotoPage(page)">{{ page }}</a>
                <a
                  :class="{undisable:!coursePage.next}"
                  href="#"
                  title="后一页"
                  @click.prevent="gotoPage((coursePage.current+1)> coursePage.pages ? coursePage.pages:(coursePage.current+1))">&gt;</a>
                <a
                  :class="{undisable: !coursePage.next}"
                  href="#"
                  title="末页"
                  @click.prevent="gotoPage(coursePage.pages)">末</a>
                <div class="clear"/>
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
  import Cookies from 'js-cookie'
  import user from '@/api/user'
    export default {
        layout:'default',
        name: "course",
        data(){
          return{
            courseList:[],
            coursePage:{total:0},
            size:8,
            page:1
          }
        },
        mounted() {
              this.getUserCourseByUId()
        },
        methods:{
          //移除课程
          removeUserCourse(courseId){
                 //进行判断
                 this.$confirm('此操作将删除该课程，是否继续','提示',{
                   confirmButtonText:'确定',
                   cancelButtonText:'取消',
                   type:'warning'
                 }).then(()=>{
                    //确认
                     let uId = Cookies.get('uId');
                     if(courseId && uId) {
                       user.removeUserCourse(uId, courseId).then(response => {
                         if (response.data.success) {
                           this.$message({
                             type: 'success',
                             message: '移除成功'
                           })
                           //刷新课程
                           this.getUserCourseByUId()
                         }
                       })
                     }
                 }).catch(()=>{
                   //取消
                   this.$message({
                     type:'info',
                     message:'已取消删除'
                   })
                 })
              },
          //通过用户ID获取用户的所属课程
          getUserCourseByUId(){
                let uId = Cookies.get("uId")
                let token = Cookies.get("token")
                if(uId && token){
                   user.getUserCourseByUId(this.page,this.size,uId).then(response=>{
                      this.courseList = response.data.data.map.courseList
                      this.coursePage = response.data.data.map
                   })
                }else {
                  window.location.href='/login'
                }
          },
          gotoPage(page){
             this.page = page
            this.getUserCourseByUId()
          }
        }

    }
</script>
<style scoped>
  .course-title{
    margin-left: 10px;
  }
  .of{
    padding: 30px;
  }
  .mt10:hover a{
    color: #41B883;
  }
  .cc-mask:hover a{
    cursor: pointer;
    color: #41B883 ;
  }
</style>
