import request from '@/utils/request'

//查询全部列表
export  function getChaptersList() {
    return request({
      url:'/shenma/chapter/getList',
      method:'get'
    })
}
//通过课程id查询章节列表
export  function getChaptersByCourseId(id) {
  return request({
    url:'/shenma/chapter/getChaptersByCourseId?id='+id,
    method:'get'
  })
}
//增加
export  function addCourseChapter(chapter) {
  return request({
    url:'/shenma/chapter/addCourseChapter',
    method:'post',
    data:chapter
  })
}
//修改
export  function modifyCourseChapter(chapter) {
  return request({
    url:'/shenma/chapter/modifyCourseChapter',
    method:'post',
    data:chapter
  })
}
//删除
export  function deleteChapter(id) {
  return request({
    url:'/shenma/chapter/removeCourseChapter?id='+id,
    method:'get'
  })
}
//获取章节信息 getChapterById
export  function getChapterById(id) {
  return request({
    url:'/shenma/chapter/getChapterById?id='+id,
    method:'get'
  })
}


