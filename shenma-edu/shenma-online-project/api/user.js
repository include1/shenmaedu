import request from '@/utils/request'

export default {
  //获取用户信息
  getInfoByToken(token){
    return request({
      url:'/userservice/center/getUserInfo?token='+token,
      method:'get'
    })
  },
  //用户登录判断
  login(user){
     return request({
       url:'/userservice/center/login',
       method:'post',
       data:user
     })
  },
  //用户退出
  logout(tokenValue){
    return request({
      url:'/userservice/center/logout?token='+tokenValue,
      method:'get'
    })
  },
  //通过用户名查询
  getUserByName(username){
    return request({
      url:'/userservice/center/getUserByName?username='+username,
      method:'get'
    })
  },
  //用户添加
  addUser(user){
    return request({
      url:'/userservice/center/addUser',
      method:'post',
      data:user
    })
  },
  //更新用户
  updateUser(user){
    return request({
      url: '/userservice/center/updateUser',
      method: 'post',
      data: user
    })
  },
  //跳转的个人中心
  goAccount(id){
      return request({
        url:'/userservice/center/goAccount',
        method:'get'
      })
  },
  //用户发表评论
  addCourseComment(courseComment){
    return request({
      url:'/shenma/frontCourseComment/addCourseComment',
      method:'post',
      data:courseComment
    })
  },
  //通过用户id获取用户信息
  getUserInfoById(id){
    return request({
      url:'/userservice/center/getUserInfoById?id='+id,
      method:'get'
    })
  },
  //获取个人中心中的用户课程
  getUserCourseByUId(page,size,uId){
    return request({
      url:'/shenma/frontUserCourse/getUserCourseByUId?page='+page+'&size='+size+'&uId='+uId,
      method:'get'
    })
  },
  //获取用户评论课程的信息
  getUserCommentInfo(page,pageSize,uId){
    return request({
      url:'/shenma/frontCourseComment/getUserCommentInfo?page='+page+'&pageSize='+pageSize+'&uId='+uId,
      method:'get'
      }
    )
  },
  //修改个人的密码
  modifyPwd(sysUserForm){
      return request({
        url:'/userservice/center/modifyPwd',
        method:'post',
        data:sysUserForm
      })
  },
  //通过用户id和课程id获取课程
  getUserCourse(userCourse){
    return  request({
      url:'/shenma/frontUserCourse/getUserCourse',
      method:'post',
      data:userCourse
    })
  },
  //移除用户课程
  removeUserCourse(uId,courseId) {
    return request({
      url:'/shenma/frontUserCourse/removeUserCourse?uId='+uId+'&courseId='+courseId,
      method:'get'
    })
  }

}
