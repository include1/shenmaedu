<template>
  <div>

    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="//g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" />
    <!-- 阿里云视频播放器脚本 -->
    <script type="text/javascript" src="//g.alicdn.com/de/prismplayer/2.8.0/aliplayer-min.js"></script>
    <!--组件-->
    <script type="text/javascript" charset="utf-8" src="https://player.alicdn.com/aliplayer/presentation/js/aliplayercomponents.min.js"/>

    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player"/>
  </div>
</template>

<script>
import vod from '@/api/vod'
export default {

  layout: 'default',
  asyncData({ params, error }) {
    return vod.getPlayVideoAuth(params.vid).then(response => {
      return {
        vid: params.vid,
        playAuth: response.data.data.playAuth
      }
    })
  },

  /**
 * 页面渲染完成时：此时js脚本已加载，Aliplayer已定义，可以使用
 * 如果在created生命周期函数中使用，Aliplayer is not defined错误
 */
  mounted() {


    /* eslint-disable no-undef */
    const player = new Aliplayer({
      id: 'J_prismPlayer',
      vid: this.vid, // 视频id
      playauth: this.playAuth, // 播放凭证
      encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
      width: '100%',
      height: '470px',

      // 以下可选设置
      cover: 'https://shenmaedu-bucket.oss-cn-beijing.aliyuncs.com/2020/02/15/banner/ef28b0b1-83b3-4bc3-92cc-daac40be5fcf.jpg', // 封面
      qualitySort: 'asc', // 清晰度排序
      mediaType: 'video', // 返回音频还是视频
      autoplay: false, // 自动播放
      isLive: false, // 直播
      rePlay: false, // 循环播放
      preload: true,
      controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
      useH5Prism: true, // 播放器类型：html5

      components: [
      // 跑马灯组件
        {
          name: 'BulletScreenComponent',
          type: AliPlayerComponent.BulletScreenComponent,
          /** 跑马灯组件三个参数 text, style, bulletPosition
       * text: 跑马灯文字内容
       * style: 跑马灯样式
       * bulletPosition: 跑马灯位置, 可选的值为 'top' (顶部), 'bottom' (底部), 'random' (随机), 不传值默认为 'random'
       */
          args: ['Hello，欢迎使用阿里播放器', { fontSize: '16px', color: 'grey',opacity:0.1}, 'random']
        }
      ],

      /* h5截图按钮 */
      'extraInfo': {
        'crossOrigin': 'anonymous'
      },
      'skinLayout': [
        { 'name': 'bigPlayButton', 'align': 'blabs', 'x': 30, 'y': 80 },
        { 'name': 'H5Loading', 'align': 'cc' },
        { 'name': 'errorDisplay', 'align': 'tlabs', 'x': 0, 'y': 0 },
        { 'name': 'infoDisplay' },
        { 'name': 'tooltip', 'align': 'blabs', 'x': 0, 'y': 56 },
        { 'name': 'thumbnail' },
        {
          'name': 'controlBar', 'align': 'blabs', 'x': 0, 'y': 0,
          'children': [
            { 'name': 'progress', 'align': 'blabs', 'x': 0, 'y': 44 },
            { 'name': 'playButton', 'align': 'tl', 'x': 15, 'y': 12 },
            { 'name': 'timeDisplay', 'align': 'tl', 'x': 10, 'y': 7 },
            { 'name': 'fullScreenButton', 'align': 'tr', 'x': 10, 'y': 12 },
            { 'name': 'subtitle', 'align': 'tr', 'x': 15, 'y': 12 },
            { 'name': 'setting', 'align': 'tr', 'x': 15, 'y': 12 },
            { 'name': 'volume', 'align': 'tr', 'x': 5, 'y': 10 },
            { 'name': 'snapshot', 'align': 'tr', 'x': 10, 'y': 12 }
          ]
        }
      ]
    }, function(player) {
      console.log('播放器创建成功')
    })

    /* h5截图按钮, 截图成功回调 */
    player.on('snapshoted', function(data) {
      var pictureData = data.paramData.base64
      var downloadElement = document.createElement('a')
      downloadElement.setAttribute('href', pictureData)
      var fileName = 'Aliplayer' + Date.now() + '.png'
      downloadElement.setAttribute('download', fileName)
      downloadElement.click()
      pictureData = null
    })
  }
}
</script>
<style scoped>
  .prism-player{
    top: 30px;
    height:600px;
   border: solid 1px skyblue;
  }
</style>
