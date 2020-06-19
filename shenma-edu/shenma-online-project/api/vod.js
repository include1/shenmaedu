import request from '@/utils/request'

export default {
  //获取视频凭证
  getPlayVideoAuth(videoId){
    return request({
      url:'/videoservice/vod/getVideoPlayAuth?videoId='+videoId,
      method:'get'
    })
  }
}
