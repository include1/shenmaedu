import request from '@/utils/request'
//获取列表
export  function getList(size) {
    return request({
        url:'/shenma/banner/getList?size='+size,
        method:'get'
    })
}
//通过id查询
export  function getBannerById(id) {
  return request({
    url:'/shenma/banner/getBannerById?id='+id,
    method:'get'
  })
}

//分页查询
export function getBannerList(page,size,searchMap) {
  return request({
      url:'/shenma/banner/getBannerListByPage?page='+page+'&size='+size,
      method:'post',
      data:searchMap
  })
}
//添加操作
export function addBanner(searchMap) {
  return request({
    url:'/shenma/banner/addBanner',
    method:'post',
    data:searchMap
  })
}

//修改操作
export function modifyBanner(banner) {
  return request({
    url:'/shenma/banner/modifyBanner',
    method:'post',
    data:banner
  })
}
//删除操作
export function removeBannerById(id) {
  return request({
    url:'/shenma/banner/removeBannerById?id='+id,
    method:'get'
  })
}
