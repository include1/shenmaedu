<template>

  <div>
    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption"  >
      <div class="swiper-wrapper" >
        <div class="swiper-slide" style="background: #040B1B;" v-for="(banner,index) in bannerList" :key="index">
          <a target="_blank" href="/course/1241325883365523458" :title="banner.title">
            <img
              :src="banner.imgUrl"
              :alt="banner.title">
          </a>
        </div>
      </div>
      <div class="swiper-pagination" slot="pagination"></div>
      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
      <div class="swiper-scrollbar"  slot="scrollbar"></div>
    </div>
    <!-- 幻灯片 结束 -->
    <!--mask面具开始-->
    <div class="mask">
      <div class="left-mask">
        <ul class="category-content">
          <li v-for="(item,index) in categoryList" :key="index">
            <div class="category-title">
              <a>{{item.title}}</a>
            </div>
            <p style="display: inline-block" >
              <a target="_blank" :href="'/course/'+subItem.courseId" v-for="(subItem,index) in item.subCategoryList" :key="index" v-if="index<4">{{subItem.title}}</a>
            </p>
            <ul>
              <li>
                <p class="text-overflow sub-title">
                  <a>{{item.title}}</a>
                </p>
                <p>
                  <a target="_blank" :href="'/course/'+subItem.courseId" v-for="(subItem,index) in item.subCategoryList" :key="index" >{{subItem.title}}</a>
                </p>
              </li>
              <li>
                <p class="text-overflow sub-title">
                  <a>推荐课程</a>
                </p>
                <div class="recommend">
                  <p class="path-recom" v-for="(subItem,index) in item.subCategoryList" :key="index" v-if="index<3">
                    <a :href="'/course/'+subItem.courseId" data-track="syqd-1-1" target="_blank">
                      <span class="cate-tag" style="width: 100px">{{subItem.title}}</span> {{subItem.courseName}}
                    </a>
                  </p>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>

    </div>
    <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门课程</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="(item,index) in frontCourseList" :key="index">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <img :src="item.coverImg" class="img-responsive" >
                      <div class="cc-mask">
                        <a :href="'/course/'+item.id"  title="开始学习" target="_blank" class="comm-btn c-btn-1">开始学习</a>
                      </div>
                    </section>
                    <h3 class="hLh30 txtOf mt10">
                      <a :href="'/course/'+item.id" :title="item.name" class="course-title fsize18 c-333">{{item.name}}</a>
                    </h3>
                    <section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green">
                        <i class="c-fff fsize12 f-fA">{{item.free == 1 ?"付费":"免费"}}</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{item.studyNum}}人学习</i>
                        |
                        <i class="c-999 f-fA">{{item.commentNum}}评论</i>
                      </span>
                    </section>

                  </div>
                </li>

              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/course" title="全部课程" class="comm-btn c-btn-2">全部课程</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">名师大咖</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="(item,index) in teacherList" :key="index">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/teacher/'+item.id" :title="item.name">
                        <img :alt="item.name" :src="item.avatar">
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a :href="'/teacher/'+item.id" :title="item.name" class="fsize18 c-666">{{item.name}}</a>
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{item.carrer}}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">
                        {{item.intro}}
                      </p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/teacher" title="全部讲师" class="comm-btn c-btn-2">全部讲师</a>
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
  </div>
</template>

<script>
  import banner from "../api/banner";
  import teacher from "../api/teacher";
  import category from "../api/category";
  import course from "../api/course";
  export default {
    data() {
      return {
        swiperOption: {
          //配置分页
          pagination: {
            el: '.swiper-pagination'//分页的dom节点
          },
          autoplay:{delay:3000, disableOnInteraction: false},
          loop:true,
          //配置导航
          navigation: {
            nextEl: '.swiper-button-next',//下一页dom节点
            prevEl: '.swiper-button-prev'//前一页dom节点
          }
        },
        bannerList:[],
        teacherList: [],
        categoryList:[],
        frontCourseList:[]
      }
    },
    mounted() {
      //页面渲染之前调用
      //获取轮播图信息
      this.getBannerList()
      //获取分类信息
      this.getCategoryList()
      //获取课程信息
      this.getCourseList()
      //获取讲师信息
      this.getTeacherList()
    },
    methods: {
      //获取轮播图
      getBannerList(){
        banner.getBannerList(1,5).then(response =>{
          this.bannerList = response.data.data.bannerList
        })
      },
      //获取分类信息
      getCategoryList(){
        category.getCategoryInfo().then(response=>{
          this.categoryList = response.data.data.categoryList
        })
      },
      //获取课程信息
      getCourseList(){
        course.getCourseListPage(1,8).then(response=>{
          this.frontCourseList = response.data.data.courseList
        })
      },
      //获取讲师
      getTeacherList() {
        teacher.getTeacherListPage(1, 4).then(response => {
          this.teacherList = response.data.data.teacherList
        })
      }
    }
  }
</script>
<style scoped>
  .mask {
    width: 800px;
    height: 400px;
    position: absolute;
    top: 78px;
    left: 10%;

  }

  .mask li {
    height: 60px;
    width: 100%;
  }

  .category-content {
    position: absolute;
    z-index: 1000001;
    width: 270px;
    background-color: #000000;
    filter: alpha(opacity=70);
    background: rgba(0, 0, 0, 0.5);
    height: 380px;
    color: white;
    top: 10px;
    padding-left: 0;
  }

  .category-content li {
    width: 270px;
    -webkit-transition: all 0.3s ease;
    -moz-transition: all 0.3s ease;
    -o-transition: all 0.3s ease;
    transition: all 0.3s ease;
  }

  .category-content li .category-title {
    margin-bottom: 5px;
    font-size: 16px;
    padding-left: 15px;
    padding-top: 15px;
  }

  .category-content li:hover {
    background-color: white;
  }

  .category-content li:hover a {
    color: black;
  }

  .category-content li:hover ul {
    display: block;
  }

  .category-content li p {
    font-size: 12px;
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    word-wrap: normal;
    padding-left: 15px;
  }

  .category-content li p a {
    color: white;
    margin-left: 5px;
  }

  .category-content li ul {
    width: 500px;
    height: 100%;
    position: absolute;
    left: 270px;
    top: 0px;
    display: none;
    background: white;
    z-index: 100001;

  }

  .category-content li ul li {
    width: 100%;
    height: 100px;
  }

  .category-content li ul li {
    background: white;
    background: rgba(255, 255, 255, 0.95);
  }

  .category-content li ul .text-overflow {
    overflow: hidden;
    margin-top: 30px;
    margin-bottom: 20px;
  }

  .category-content li ul .sub-title a {
    color: green;
    font-size: 16px;
  }

  .category-content li ul p a {
    margin-left: 20px;
    margin-top: 20px;
  }

  .category-content li ul .recommend p {
    display: block;
    height: 30px;
    font-size: 14px;
    margin-top: 10px;
  }

  .category-content li ul .recommend p span {

    display: inline-block;
    width: 80px;
  }

  .mask a {
    text-decoration: none;
    font-family: "Microsoft YaHei", "WenQuanYi Micro Hei", sans-serif;
  }

  .mask a:hover {
    color: green !important;
  }

</style>
