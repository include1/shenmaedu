<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师列表 开始 -->
    <section class="container">
      <header class="comm-title all-teacher-title">
        <h2 class="fl tac">
          <span class="c-333">全部讲师</span>
        </h2>
        <section class="c-tab-title">
          <a id="subjectAll" title="全部" href="/teacher">全部</a>
        </section>
      </header>
      <section class="c-sort-box unBr">
        <div>
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="teacherInfo.total==0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article v-if="teacherInfo.total>0" class="i-teacher-list">
            <ul class="of">
              <li v-for="(item,index) in teacherInfo.teacherList" :key="index">
                <section class="i-teach-wrap">
                  <div class="i-teach-pic">
                    <a :href="'/teacher/'+item.id" :title="item.name" target="_blank">
                      <img :src="item.avatar"  :alt="item.name">
                    </a>
                  </div>
                  <div class="mt10 hLh30 txtOf tac">
                    <a :href="'/teacher/'+item.id" :title="item.name" target="_blank" class="fsize18 c-666"> {{item.name}} </a>
                  </div>
                  <div class="hLh30 txtOf tac">
                    <span class="fsize14 c-999">{{item.career}}</span>
                  </div>
                  <div class="mt15 i-q-txt">
                    <p class="c-999 f-fA">{{item.intro}}</p>
                  </div>
                </section>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
      <div>
        <div class="paging">
          <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
          <a
            :class="{undisable: !teacherInfo.previous}"
            href="#"
            title="首页"
            @click.prevent="gotoPage(1)">首</a>
          <a
            :class="{undisable: !teacherInfo.previous}"
            href="#"
            title="前一页"
            @click.prevent="gotoPage(teacherInfo.current-1)">&lt;</a>
          <a
            v-for="page in teacherInfo.pages"
            :key="page"
            :class="{current: teacherInfo.current == page, undisable: teacherInfo.current == page}"
            :title="'第'+page+'页'"
            href="#"
            @click.prevent="gotoPage(page)">{{ page }}</a>
          <a
            :class="{undisable:!teacherInfo.next}"
            href="#"
            title="后一页"
            @click.prevent="gotoPage((teacherInfo.current+1)> teacherInfo.pages ? teacherInfo.pages:(teacherInfo.current+1))">&gt;</a>
          <a
            :class="{undisable: !teacherInfo.next}"
            href="#"
            title="末页"
            @click.prevent="gotoPage(teacherInfo.pages)">末</a>
          <div class="clear"/>
        </div>
      </div>
<!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /讲师列表 结束 -->
  </div>
</template>
<script>
import teacher from "@/api/teacher";

export default {
  //asyncData相当于created,在页面加载之前调用
  asyncData({ params, error }) {
    return teacher.getTeacherListPage(1,8)
      .then(response => {
        console.log(response.data.data)
        return{teacherInfo:response.data.data}
      })
  },
  methods: {
    gotoPage(page){
        teacher.getTeacherListPage(page, 8).then(response => {
          this.teacherInfo = response.data.data
        })
    }
  }
}
</script>

