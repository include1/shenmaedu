import request from '@/utils/request'

export default {

  //个人中心通过用户id获取订单列表
  getOrderListByUserId(page,size,uId){
    return request({
      url:'/shenma/frontOrder/getOrderListByUserId?page='+page+'&size='+size+'&uId='+uId,
      method:'get'
    })
  },
  //通过订单id获取订单详情
  getOrderById(id) {
    return request({
      url: '/shenma/frontOrder/getOrderById?id=' + id,
      method: 'get'
    })
  },
  //添加订单
  addOrder(order){
    return request({
      url:'/shenma/frontOrder/addOrder',
      method:'post',
      data:order
    })
  },
  //通过sn查询订单信息
  getOrderInfoByOrderSn(orderSn){
    return request({
      url:'/shenma/frontOrder/getOrderInfoByOrderSn?orderSn='+orderSn,
      method:'get'
    })
  },
  //删除订单
  delOrder(id){
     return request({
        url:'/shenma/frontOrder/delOrder?id='+id,
        method:'get'
     })
  },
  //支付btn
  trade(orderSn,type){
    return request({
      url:'/shenma/frontOrder/trade?orderSn='+orderSn+"&type="+type,
      method:'get'
    })
  },
  //购买课程的状态
  getPurchaseState(uId,courseId) {
    return request({
      url: '/shenma/frontOrder/getPurchaseState?uId=' +uId+'&courseId='+courseId,
      method: 'get'
    })
  }

}
