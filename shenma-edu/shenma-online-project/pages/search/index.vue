<template>
  <div class="main">
    <div class="course-banner">
      <div class="container">
        <div class="title">
          <span>搜索</span>
          SEARCH
        </div>
      </div>
    </div>
    <!--全部课程-->
    <div class="course-content">
      <div class="title">
        共找到了
        <span style="color: red">&nbsp;{{courseList.length}}&nbsp;</span>
        门
        <span style="color: red">&nbsp; "{{searchMap.courseName}}" &nbsp;</span>
        相关课程</div>
      <div>
        <!-- 无数据 -->
        <section class="no-data-wrap" v-if="courseList.length==0">
          <em class="icon30 no-data-ico">&nbsp;</em>
          <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
        </section>
        <!-- 有数据-->
        <section class="container" v-if="courseList.length > 0">
        <article class="comm-course-list">
          <ul class="of" id="bna">
            <li v-for="(item,index) in courseList" :key="index">
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
        </article>
        </section>
      </div>
    </div>
    <!-- 公共分页 开始 -->
    <div v-if="searchPage.pages > 1">
      <div class="paging">
        <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
        <a
          :class="{undisable: !searchPage.previous}"
          href="#"
          title="首页"
          @click.prevent="gotoPage(1)">首</a>
        <a
          :class="{undisable: !searchPage.previous}"
          href="#"
          title="前一页"
          @click.prevent="gotoPage(searchPage.current-1)">&lt;</a>
        <a
          v-for="page in searchPage.pages"
          :key="page"
          :class="{current: searchPage.current == page, undisable: searchPage.current == page}"
          :title="'第'+page+'页'"
          href="#"
          @click.prevent="gotoPage(page)">{{ page }}</a>
        <a
          :class="{undisable:!searchPage.next}"
          href="#"
          title="后一页"
          @click.prevent="gotoPage((searchPage.current+1)> searchPage.pages ? searchPage.pages:(searchPage.current+1))">&gt;</a>
        <a
          :class="{undisable: !searchPage.next}"
          href="#"
          title="末页"
          @click.prevent="gotoPage(searchPage.pages)">末</a>
        <div class="clear"/>
      </div>
    </div>
  </div>
</template>

<script>
  import course from "../../api/course";
    export default {
        name: "index.vue",
         data(){
          return{
            searchMap:{courseName:''},
            courseList:[],
            searchPage:{}
          }
         },
         created() {
           if(this.$route.query.keyword){
               this.searchMap.courseName = this.$route.query.keyword
               this.getCourseList()
           }
         },
         methods:{
             getCourseList(){
                course.getCourseListPage(1,8,this.searchMap).then(response=>{
                    console.log(response.data.data.map)
                    this.searchPage = response.data.data
                    this.courseList = response.data.data.courseList
                })
             }
          }
    }
</script>

<style scoped>
  .course-content .title{
    width: 100%;
    height: 20px;
    margin-left: 30px;
    color: #616161;
    padding: 10px;
    font-size: 15px;
    font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB", "Microsoft YaHei", "微软雅黑", Arial, sans-serif;
  }
  .course-content{
    width: 90%;
    margin: 0 auto;
    margin-top: 40px;
  }
  .container .title{
    font-size: 20px;
    line-height: 80px;
    color: rgba(0,0,0,.56);
    font-family: "Microsoft YaHei","WenQuanYi Micro Hei",sans-serif;
  }
  .course-banner .container{
      width: 80%;
      height: 75px;
      margin: 0 auto;
  }
  .course-banner{
    width: 100%;
    height: 80px;
    background: rgb(250, 250, 250);
  }
.main{
  width: 100%;
  margin:0 auto;
  height: 800px;
  position: relative;
}
</style>
