<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container" style="height: 800px">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl >
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a href="#" title="全部" @click.prevent="allCourse">全部</a>
                </li>
                <li v-for="(item,index) in categoryList" :key="index">
                  <a  href="#" :title="item.title" @click.prevent="filterPage(item.id,index)" :class='{"current-color":isActive == index}'>{{item.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>

          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <!--右边-->
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">{{coursePage.current}}</i>/
              <i class="c-666 f-fM">{{coursePage.pages}}</i>
            </span>
          </section>
          <!--左边-->
          <section class="fl">
            <ol class="js-tap clearfix">
              <li  :class="{'current bg-orange':newBool}">
                <a title="最新" href="#" @click.prevent="getNewCourse">最新</a>
              </li>
              <li :class="{'current bg-orange':priceBool}">
                <a title="价格" href="#" @click.prevent="getCourseByPrice">价格&nbsp;
                  <span>{{arrowhead}}</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="coursePage.total==0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article v-if="coursePage.total > 0" class="comm-course-list">
            <ul class="of" id="bna">
              <li v-for="(item,index) in courseList" :key="index">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.coverImg" class="img-responsive" :alt="item.name">
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.name" class="course-title fsize18 c-333">{{item.name}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" >
                      <i class="c-fff fsize12 f-fA">
                        {{Number(item.price) === 0?'免费':'付费'}}
                      </i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{item.studyNum}}学习</i>
                      |
                      <i class="c-999 f-fA">{{item.commentNum}}评论</i>
                    </span>
                  </section>
                </div>
              </li>

            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div v-if="coursePage.pages > 0">
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
        <!-- /公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import course from '@/api/course'
import category from '@/api/category'
export default {
  layout:'default',
  data(){
    return{
      categoryList:[], //分类集合
      searchMap:{descPrice:'descPrice'},
      coursePage:{},
      courseList:[],
      newBool:false,
      priceBool:true,
      arrowhead:'↓',   //箭头 ↑
      isActive:-1,     //判断当前课程分类
      categoryId:''    //记录分类的当前id
    }
  },
  created() {
    //获取分类
    this.getTwoCategoryList()
    //获取课程
    this.getCourseListPage()
  },
  methods: {
    //获取分类
      getCourseListPage(){
            course.getCourseListPage(1,8,this.searchMap).then(response => {
                this.coursePage = response.data.data
                this.courseList= response.data.data.courseList
                this.searchMap = {descPrice:'descPrice'}
              })
    },
    //获取分类
    getTwoCategoryList(){
      category.getTwoCategoryList().then(response =>{
         this.categoryList = response.data.data.allCategory
      })
    },
    //跳转
    gotoPage(page){
       //判断searchMap里的内容
        if(this.categoryId !== ''){
             //判断当前选中的是否价格
          if(this.priceBool){
            //判断降序还是升序
            if ('↓' == this.arrowhead) {
              this.searchMap = {categoryParentId:this.categoryId,descPrice: 'descPrice'}
            } else {
              this.searchMap = {categoryParentId:this.categoryId,ascPrice: 'ascPrice'}
            }
          }
            //判断当前选中的是否是最新
          if(this.newBool){
            this.searchMap = {categoryParentId:this.categoryId,updateTime:'descTime'}
          }
        }else{
          //判断当前选中的是否价格
          if(this.priceBool){
            //判断降序还是升序
            if ('↓' == this.arrowhead) {
              this.searchMap = {descPrice: 'descPrice'}
            } else {
              this.searchMap = {ascPrice: 'ascPrice'}
            }
          }
          //判断当前选中的是否是最新
          if(this.newBool){
            this.searchMap = {updateTime:'descTime'}
          }
        }
        course.getCourseListPage(page, 8,this.searchMap).then(response => {
            this.coursePage = response.data.data
            this.courseList= response.data.data.courseList
            this.searchMap = {descPrice:'descPrice'}
        })
    },
    //过滤课程
    filterPage(id,index){
      this.isActive = index
      this.categoryId = id
      //默认当时在价格上
      this.priceBool = true
      this.newBool = false
      this.arrowhead = '↓'//默认降序
      this.searchMap = {categoryParentId:id}
      this.getCourseListPage()
    },
    //全部
    allCourse(){
      this.isActive = -1
      this.priceBool = true //默认出现价格升序排列
      this.arrowhead = '↓'
      this.newBool = false // 最新清空
      this.categoryId = ''//清空id
      this.getCourseListPage()
    },
    //获取最新的课程
    getNewCourse(){
      this.newBool = true
      this.priceBool = false
      //判断是否选中了分类信息
      if(this.categoryId !== ''){
        this.searchMap = {categoryParentId:this.categoryId,updateTime:'descTime'}
      }else{
        this.searchMap = {updateTime:'descTime'}
      }
      this.getCourseListPage()
    },
    //通过价格排序
    getCourseByPrice(){
       //过滤颜色选择
       this.priceBool = true
       this.newBool = false
        //判断是否选中分类信息
        if(this.categoryId !== ''){
          if ('↓' == this.arrowhead) {
            this.searchMap = {categoryParentId:this.categoryId,ascPrice: 'ascPrice'}
            this.arrowhead = '↑'
          } else {
            this.searchMap = {categoryParentId:this.categoryId,descPrice: 'descPrice'}
            this.arrowhead = '↓'
          }
        }else{
          if ('↓' == this.arrowhead) {
            this.searchMap = {ascPrice: 'ascPrice'}
            this.arrowhead = '↑'
          } else {
            this.searchMap = {descPrice: 'descPrice'}
            this.arrowhead = '↓'
          }
        }
      this.getCourseListPage()
    }
}

};
</script>

