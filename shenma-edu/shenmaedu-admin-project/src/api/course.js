import request from '@/utils/request'
import id from "element-ui/src/locale/lang/id";

//获取课程表格
export function findPageByCondition(page,size,searchMap) {
      return request({
        url:'/shenma/course/findPageByCondition?page='+page+'&size='+size,
        method:'post',
        data:searchMap
      })
}
//添加操作
export function addCourse(course) {
  return request({
    url:'/shenma/course/addCourse',
    method:'post',
    data:course
  })
}
//通过id查询
export  function  getCourseById(id) {
    return request({
      url:'/shenma/course/getCourseById?id='+id,
      method:'get'
    })
}
//更新课程
export  function updateCourse(course) {
    return request({
      url:'/shenma/course/updateCourse',
      method:'post',
      data:course
    })
}
//删除操作
export  function deleteCourse(id) {
  return request({
    url:'/shenma/course/deleteCourseById?id='+id,
    method:'get'
  })
}
//获取课程详细信息
export  function getCourseInfo(id){
    return request({
      url:'/shenma/course/getCourseInfo?id='+id,
      method:'get'
    })
}
//审核
export function audit(id) {
  return request({
    url:'/shenma/course/audit?id='+id,
    method:'get'
  })
}
