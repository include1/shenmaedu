import request from '@/utils/request'

export default {
  //分页获取课程列表
  getCourseListPage(page,size,searchMap){
    return request({
        url:'/shenma/frontCourse/getCourseListPage?page='+page+'&size='+size,
        method:'post',
        data:searchMap
    })
  },
  //通过课程id获取信息
  getCourseInfoId(id) {
    return request({
      url: '/shenma/frontCourse/getCourseInfoByCourseId?id='+id,
      method: 'get'
    })
  },
  //获取课程评论信息
  getCourseCommentInfo(page,size,searchMap){
     return request({
       url:'/shenma/frontCourseComment/findCourseCommentByPage?page='+page+"&size="+size,
       method:'post',
       data:searchMap
     })
  },
  //添加用户课程
  addUserCourse(userCourse){
    return request({
      url:'/shenma/frontUserCourse/addUserCourse',
      method:'post',
      data:userCourse
    })
  },
  //通过id获取课程信息
  getCourseById(id){
    return request({
      url:'/shenma/frontCourse/getCourseById?id='+id,
      method:'get'
    })
  },


}
