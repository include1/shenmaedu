import request from '@/utils/request'

//增加
export  function addVideo(video) {
  return request({
    url:'/shenma/video/addVideo',
    method:'post',
    data:video
  })
}
//修改
export  function modifyVideo(video) {
  return request({
    url:'/shenma/video/modifyVideo',
    method:'post',
    data:video
  })
}
//删除
export  function removeVideo(id) {
  return request({
    url:'/shenma/video/removeVideo?id='+id,
    method:'get'
  })
}
//通过主键获取视频信息
export  function getVideoById(id) {
  return request({
    url:'/shenma/video/getVideoById?id='+id,
    method:'get'
  })
}
//阿里云视频操作
export  function  removeAliyunVideo(videoId) {
    return request({
      url:'/videoservice/vod/removeAliyunVideo?aliyunVideoId='+videoId,
      method:'delete'
    })
}
