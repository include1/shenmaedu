import request from '@/utils/request'

//生成数据
export function generateStatisticsData(day) {
  return request({
    url:'/statistics/service/insertRegisterNum?day='+day,
    method:'get'
  })
}
//获取数据回显到图表上
export  function  showData(type,begin,end) {
  return request({
    url:'/statistics/service/getEchartsData?type='+type+"&begin="+begin+"&end="+end,
    method:'get'
  })
}
//获取统计的数据
export function getStatisticsList(page,size,searchMap) {
    return request({
      url:'/statistics/service/getStatisticsList?page='+page+"&size="+size,
      method:'post',
      data:searchMap
    })
}
