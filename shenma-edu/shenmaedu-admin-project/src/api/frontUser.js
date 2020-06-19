import request from '@/utils/request'

export function getUserByPage(page,size,searchMap) {
  return request({
    url:'/userservice/center/getUserByPage?page='+page+'&size='+size,
    method:'post',
    data:searchMap
  })
}

//通过id获取前台用户
export function  getUserById(id) {
  return request({
    url:'/userservice/center/getFrontUserById?id='+id,
    method:'get'
  })
}
//修改前台用户
export function  modifyUser(user) {
  return request({
    url:'/userservice/center/modifyUser',
    method:'post',
    data:user
  })
}
//删除前台用户
export function  delUser(id) {
  return request({
    url:'/userservice/center/removeUser?id='+id,
    method:'get'
  })
}
