import request from '@/utils/request'
export function getOrderPageList(page,size,searchMap) {
  return request({
    url:'/shenma/order/getOrderByPage?page='+page+'&size='+size,
    method:'post',
    data:searchMap
  })
}

//通过id获取订单
export function  getOrderById(id) {
  return request({
    url:'/shenma/order/getOrderById?id='+id,
    method:'get'
  })
}
//修改订单
export function  modifyOrder(order) {
  return request({
    url:'/shenma/order/modifyOrder',
    method:'post',
    data:order
  })
}
//删除订单
export function  delOrder(id) {
  return request({
    url:'/shenma/order/removeOrder?id='+id,
    method:'get'
  })
}
