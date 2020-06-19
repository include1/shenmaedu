import request from '@/utils/request'

export default {
  //分页
  getTeacherListPage(page,size){
    return request({
      url:'/shenma/frontTeacher/getTeacherListPage?page='+page+'&size='+size,
      method:'get'
    })
  },
  //查询讲师详细信息
  getTeacherInfoId(id){
    return request({
      url:'/shenma/frontTeacher/getTeacheInfo?id='+id,
      method:'get'
    })
  }
}
