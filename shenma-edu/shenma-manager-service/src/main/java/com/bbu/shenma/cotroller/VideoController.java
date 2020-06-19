package com.bbu.shenma.cotroller;

import com.bbu.shenma.bean.Video;
import com.bbu.shenma.service.VideoService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/shenma/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    //添加
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody Video video){
        boolean flag = videoService.addVideo(video);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("添加失败");
        }
    }
    //修改
    @PostMapping("/modifyVideo")
    public Result modifyVideo(@RequestBody Video video){
        boolean flag = videoService.modifyVideo(video);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("修改失败");
        }
    }


    //删除
    @GetMapping("/removeVideo")
    public Result removeVideo(String id){
        boolean flag = videoService.removeVideoByPrimaryKey(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("删除失败");
        }
    }
    //通过主键获取视频信息
    @GetMapping("/getVideoById")
    public Result getVideoById(String id){
        Video video = videoService.getVideoById(id);
        return Result.ok().data("video",video);
    }
}
