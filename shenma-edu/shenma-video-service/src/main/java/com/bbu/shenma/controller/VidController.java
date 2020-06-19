package com.bbu.shenma.controller;

import com.bbu.shenma.service.video.VidService;
import com.bbu.shenma.util.Result;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/videoservice/vod")
public class VidController {

    @Autowired
    private VidService vidService;

    //视频上传
    @PostMapping("/upload")
    public Result uploadAliyunVideo(@RequestParam(name = "file") MultipartFile file){
        String videoId =  vidService.uploadAliyunVideo(file);
        return Result.ok().data("videoId",videoId);
    }
    //删除小节视频
    @DeleteMapping("/removeAliyunVideo")
    public Result removeAliyunVideo(String aliyunVideoId){
        vidService.removeAliyunVideo(aliyunVideoId);
        return Result.ok();
    }
    //获取视频上传的凭证
    @GetMapping("/getVideoPlayAuth")
    public Result getVideoPlayAuth(String videoId){
      String playAuth =  vidService.getVideoPlayAuth(videoId);
      return Result.ok().data("playAuth",playAuth);
    }
    //删除课程，批量删除阿里云视频
    @DeleteMapping("/removeAliyunVideoMany")
    public Result removeAliyunVideoMany(@RequestParam("aliyunVideoIds")String [] aliyunVideoIds){
        vidService.removeAliyunVideoMany(aliyunVideoIds);
        return Result.ok();
    }

}