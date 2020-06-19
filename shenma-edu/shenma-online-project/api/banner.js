import request from '@/utils/request'

//获取轮播图信息
export default {
   getBannerList(page,size){
      return request({
        url:'/shenma/banner/getBannerListByPage?page='+page+'&size='+size,
        method:'post'
      })
   }
}
