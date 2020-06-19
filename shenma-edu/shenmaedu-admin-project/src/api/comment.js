import request from '@/utils/request'

export function getCommentPageList(page,size,searchMap) {
      return request({
        url:'/shenma/courseComment/getCourseCommentByPage?page='+page+'&size='+size,
        method:'post',
        data:searchMap
      })
}
//通过id获取评论
export function  getCommentById(id) {
     return request({
       url:'/shenma/courseComment/getCourseCommentById?id='+id,
       method:'get'
     })
}
//修改评论
export function  modifyComment(courseComment) {
  return request({
    url:'/shenma/courseComment/modifyCourseComment',
    method:'post',
    data:courseComment
  })
}
//删除评论
export function  delComment(id) {
  return request({
    url:'/shenma/courseComment/removeCourseComment?id='+id,
    method:'get'
  })
}
