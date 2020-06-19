package com.bbu.shenma.client;

import com.bbu.shenma.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("video-service") //该注解是发现其他服务
@Component //防止其他位置注入client时，idea报错
public interface VidClient {

    //定义调用的映射路径，调用的方法
    @DeleteMapping("/videoservice/vod/removeAliyunVideo")
    public Result removeAliyunVideo(String aliyunVideoId);
    //批量删除阿里云视频
    @DeleteMapping("/videoservice/vod/removeAliyunVideoMany")
    public Result removeAliyunVideoMany(@RequestParam("aliyunVideoIds")String [] aliyunVideoIds);
}
