import request from '@/utils/request'

//得到全部讲师
export  function  getAllTeacher() {
  return request({
    url:'/shenma/teacher/list',
    method:'get'
  })
}
//分页条件查询的方法

export function getTeacherPageList(page,size,searchObj)
  {
    return request({
      url: '/shenma/teacher/findPageByCondition?page=' + page + "&size=" + size,
      method: 'post',
      data: searchObj
    })
  }
  //通过id查询一条数据
export  function  getTeacherById(id) {
  return request({
    url:'/shenma/teacher/findTeacherById?id='+id,
    method:'get'
  })
}
//删除老师
export  function  deleteTeacher(id) {
  return request({
    url:'/shenma/teacher/logicDel?id='+id,
    method:'delete'
  })
}
//更新老师
export  function  updateTeacher(searchObj) {
  return request({
    url:'/shenma/teacher/modify',
    method:'post',
    data:searchObj
  })
}
//添加老师
export function addTeacher(object) {
  return request({
      url:'/shenma/teacher/add',
      method:'post',
      data:object
  })
}
//批量删除
export function delAllSelection(selectionList) {
  return request({
    url:'/shenma/teacher/delAllSelection',
    method:'post',
    data:selectionList
  })
}
