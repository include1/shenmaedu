import Vue from 'vue'
import Router from 'vue-router'
import store from "../store";
/* Layout */
import Layout from '../views/layout/Layout'
Vue.use(Router)

export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },

  {
    path: '/banner',
    component: Layout,
    redirect: '/banner/list',
    name:'轮播管理',
    meta:{ title: '轮播管理', icon: 'example',requireAuth:true},
    children: [
      {
        path: 'list',
        name: '轮播图管理',
        meta: { title: '轮播图管理', icon: 'example',requireAuth:true},
        component: () => import('@/views/banner/index')
      },
      {
        path: 'show',
        name: '展示轮播图',
        meta: { title: '展示轮播图', icon: 'example',requireAuth:true },
        component: () => import('@/views/banner/show'),
        hidden:true
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    name: '用户管理',
    redirect:'/user/sys',
    meta: { title: '用户管理', icon: 'example',requireAuth:true },
    children: [
      {
        path: 'sys',
        name: '后台用户',
        component: () => import('@/views/sysUser/sysList'),
        meta: { title: '后台用户', icon: 'table',requireAuth:true }
      },
      {
        path: 'comment',
        name: '用户评论',
        component: () => import('@/views/sysUser/comment'),
        meta: { title: '用户评论', icon: 'table',requireAuth:true }
      },
      {
        path: 'order',
        name: '用户订单',
        component: () => import('@/views/sysUser/order'),
        meta: { title: '用户订单', icon: 'table',requireAuth:true }
      },{
        path: 'frontUser',
        name: '前台用户',
        component: () => import('@/views/sysUser/frontUser'),
        meta: { title: '前台用户', icon: 'table' ,requireAuth:true}
      }, {
        path: 'modify',
        name: '修改密码',
        component: () => import('@/views/sysUser/modifyPwd'),
        meta: { title: '修改密码', icon: 'example',requireAuth:true }
      }
    ]
  },
  {
    path: '/teacher',
    component: Layout,
    name: '讲师管理',
    redirect:'/teacher/list',
    meta: { title: '讲师管理', icon: 'user',requireAuth:true },
    children: [
      {
        path: 'list',
        name: '讲师列表',
        component: () => import('@/views/teacher/list'),
        meta: { title: '讲师列表', icon: 'table',requireAuth:true}
      },
      {
        path: 'add',
        name: '添加讲师',
        component: () => import('@/views/teacher/add'),
        meta: { title: '添加讲师', icon: 'tree',requireAuth:true}
      }
    ]
  },
  {
    path: '/category',
    component: Layout,
    redirect: '/category/list',
    name: '分类管理',
    meta: { title: '分类管理', icon: 'example',requireAuth:true },
    children: [
      {
        path: 'list',
        name: '分类列表',
        component: () => import('@/views/category/list'),
        meta: { title: '分类列表', icon: 'table',requireAuth:true }
      },
      {
        path: 'import',
        name: '导入分类',
        component: () => import('@/views/category/import'),
        meta: { title: '导入分类', icon: 'tree',requireAuth:true }
      }
    ]
  },
  {
    path: '/course',
    component: Layout,
    redirect: '/course/list',
    name: '课程管理',
    meta: { title: '课程管理', icon: 'example',requireAuth:true},
    children: [
      {
        path: 'list',
        name: '课程列表',
        component: () => import('@/views/course/list'),
        meta: { title: '课程列表', icon: 'table',requireAuth:true}
      },
      {
        path: 'info',
        name: '发布课程',
        component: () => import('@/views/course/info'),
        meta: { title: '发布课程', icon: 'tree',requireAuth:true}
      },
      {
        path: 'info/:id',
        name: '填写课程信息',
        component: () => import('@/views/course/info'),
        meta: { title: '填写课程信息', noCache:true ,requireAuth:true},
        hidden:true
      },
      {
        path: 'chapter/:id',
        name: '课程章节',
        component: () => import('@/views/course/chapter'),
        meta: { title: '课程章节',  noCache:true ,requireAuth:true},
        hidden:true
      },
      {
        path: 'release/:id',
        name: '提交申请课程',
        component: () => import('@/views/course/release'),
        meta: { title: '提交申请课程', noCache:true,requireAuth:true },
        hidden:true
      }
    ]
  },

  {
    path: '/',
    component: Layout,
    redirect: '/shouye',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'shouye',
      component: () => import('@/views/dashboard/index')
    }
     ]
  },
  // {
  //   path: '/statistics',
  //   component: Layout,
  //   name: '订单管理',
  //   redirect:'/statistics/index',
  //   meta: { title: '订单管理', icon: 'example' },
  //   children: [
  //     {
  //       path: 'index',
  //       name: '订单列表',
  //       component: () => import('@/views/statistics/createData'),
  //       meta: { title: '订单列表', icon: 'table' },
  //     },
  //     {
  //       path: 'index',
  //       name: '订单列表',
  //       component: () => import('@/views/statistics/createData'),
  //       meta: { title: '订单', icon: 'table' }
  //     }
  //   ]
  // },
  {
    path: '/statistics',
    component: Layout,
    name: '统计分析',
    redirect:'/statistics/index',
    meta: { title: '统计分析', icon: 'example',requireAuth:true },
    children: [
      {
        path: 'index',
        name: '生成数据',
        component: () => import('@/views/statistics/createData'),
        meta: { title: '生成数据', icon: 'table',requireAuth:true }
      },
      {
        path: 'echars',
        name: '图表显示',
        component: () => import('@/views/statistics/showEcharts'),
        meta: { title: '图表显示', icon: 'example',requireAuth:true }
      }
    ]
  },
  { path: '*', redirect: '/404', hidden: true }

]


const router = new Router({
  //mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
//验证是否token
router.beforeEach((to,from,next)=>{
  if (to.matched.some(r => r.meta.requireAuth)) {

    if (store.state.user.token) {
        next();
    }
    else {
      next({
        path: '/login',
        query: {redirect: to.fullPath}
      })
    }
  }
  else {
    next();
  }
})
export default router
