import request from '@/utils/request'
export default {
  //查询分类信息和推荐课程
   getCategoryInfo(){
      return request({
        url:'/shenma/frontCategory/getCategoryInfo',
        method:'get'
        })
  },
  //获取课程分二级分类集合
  getTwoCategoryList(){
     return request({
       url:'/shenma/frontCategory/getCategoryList',
       method:'get'
     })
  }
}
