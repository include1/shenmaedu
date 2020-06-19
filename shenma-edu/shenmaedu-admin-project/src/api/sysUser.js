import request from '@/utils/request'

//获取用户的列表
export  function getSysUserList(page,size,searchObject) {
    return request({
      url:'/shenma/sysUser/getPage?page='+page+"&size="+size,
      method:'post',
      data:searchObject
    })
}

//通过id获取用户信息
export function getSysUserById(id) {
  return request({
    url:'/shenma/sysUser/getSysUserById?id='+id,
    method:'get'
  })
}

//添加
export function addSysUser(sysUser) {
  return request({
    url:'/shenma/sysUser/addSysUser',
    method:'post',
    data:sysUser
  })
}
//更新
export function modifySysUser(sysUser) {
  return request({
    url:'/shenma/sysUser/modifySysUser',
    method:'post',
    data:sysUser
  })
}
//删除
export function removeSysUser(id) {
  return request({
    url:'/shenma/sysUser/removeSysUser?id='+id,
    method:'get'
  })
}
//修改用户的密码
export function  updatePwd(sysUserForm) {
  return request({
    url:'/shenma/sysUser/updatePwd',
    method:'post',
    data:sysUserForm
  })
}
