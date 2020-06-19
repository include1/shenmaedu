<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">讲师介绍</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 讲师基本信息 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img :src="teacher.avatar">
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{teacher.name}}&nbsp;
                <span v-if="teacher.level==0">初级讲师</span>
                <span v-if="teacher.level==1">中级讲师</span>
                <span v-if="teacher.level==2">高级讲师</span>
                </span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg">职业：{{teacher.career}}</span>
            </section>
            <section class="t-infor-txt">
              个人介绍：
              <p
                class="mt20"
              >
               {{teacher.intro}}
              </p>
            </section>
            <div class="clear"></div>
          </div>
        </section>
        <div class="clear"></div>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">主讲课程</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>
          <!-- /无数据提示 开始  courseList -->
          <section class="no-data-wrap" v-if="courseList.length==0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list">
            <ul class="of">
              <li v-for="(item,index) in courseList" :key="index">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.coverImg" class="img-responsive" >
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id"  title="开始学习"  class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.name"  class="course-title fsize18 c-333">{{item.name}}</a>
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
        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>
<script>
import teacher from '@/api/teacher'
export default {
  data(){
    return{
      teacher:{},
      courseList:[],
      teacherId:'',
    }
  },
  created() {
    if(this.$route.params && this.$route.params.id){
        this.teacherId = this.$route.params.id
    }
    teacher.getTeacherInfoId(this.teacherId).then(response => {
        this.teacher = response.data.data.teacher
        this.courseList = response.data.data.courseList
    })
  }
}
</script>
