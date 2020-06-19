package com.bbu.shenma.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;
import com.bbu.shenma.client.StatisticsClient;
import com.bbu.shenma.config.AliyunPropertyConfig;
import com.bbu.shenma.service.video.VidService;
import com.bbu.shenma.util.AliyunVodSDKUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VidServiceImpl implements VidService {
    @Autowired
    private StatisticsClient statisticsClient;

    //上传aliyun视频
    @Override
    public String uploadAliyunVideo(MultipartFile file) {
        try {
            //上传文件名
            String fileName = file.getOriginalFilename();
            //文件标题
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            UploadStreamRequest request = new UploadStreamRequest(AliyunPropertyConfig.KEY_ID, AliyunPropertyConfig.KEY_SECRET, title, fileName, file.getInputStream());

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                System.out.println("成功"+response.getVideoId());
                videoId =  response.getVideoId();
            } else {
                videoId =  response.getVideoId();
            }
            return videoId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }
    //删除阿里云视频
    @Override
    public void removeAliyunVideo(String aliyunVideoId) {

        try {
            //创建对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(AliyunPropertyConfig.KEY_ID, AliyunPropertyConfig.KEY_SECRET);
            //创建请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置传入的视频id
            request.setVideoIds(aliyunVideoId);
            //创建响应对象
            //返回结果
            DeleteVideoResponse response = client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //批量删除阿里云视频
    @Override
    public void removeAliyunVideoMany(String[] aliyunVideoIds) {
        try {
            //创建对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(AliyunPropertyConfig.KEY_ID, AliyunPropertyConfig.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            //拼接字符串
            String join = StringUtils.join(aliyunVideoIds, ",");
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(join);
            DeleteVideoResponse acsResponse = client.getAcsResponse(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取播放凭证
     * @return
     */
    @Override
    public String getVideoPlayAuth(String videoId) {
        String palyAuth = "";
        if(StringUtils.isNotBlank(videoId)) {
            try {
                //获取阿里云点播对象
                DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(AliyunPropertyConfig.KEY_ID, AliyunPropertyConfig.KEY_SECRET);
                //请求
                GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
                request.setVideoId(videoId);
                request.setAuthInfoTimeout(1000L);
                //响应
                GetVideoPlayAuthResponse response = client.getAcsResponse(request);
                palyAuth = response.getPlayAuth();

                //进行判断，如何获取播放凭证，就代表可以播放
                if (StringUtils.isNotBlank(palyAuth)) {
                    //调用statistics的服务接口,更新video_view_num
                    statisticsClient.modifyStatisticsByCondition("video_view_num");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return palyAuth;
    }


}
