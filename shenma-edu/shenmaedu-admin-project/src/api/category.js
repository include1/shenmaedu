import request from '@/utils/request'

//获取全部分类
export function getAllCategory() {
  return request({
      url:'/shenma/category/findAllCategory',
      method:'get'
    }
  )
}
//通过id获取分裂
export function getCategoryByParentId(id) {
      return request({
        url:'/shenma/category/getCategoryByParentId?id='+id,
        method:'get'
        }
      )
}
//添加一级分类
export  function addOneCategory(category) {
      return request({
        url:'/shenma/category/addOneCategory',
        method:'post',
        data:category
      })
}
//添加二级分类
export function  addTwoCategory(category) {
      return request({
        url:'/shenma/category/addTwoCategory',
        method:'post',
        data:category
      })
}
//删除
export function deleteCategory(id){
      return request({
        url:'/shenma/category/delete?id='+id,
        method:'delete'
      })
}
