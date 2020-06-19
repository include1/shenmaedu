package com.bbu.shenma.cotroller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Banner;
import com.bbu.shenma.service.BannerService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shenma/banner")
@CrossOrigin
public class BannerController {

    @Autowired
    private BannerService bannerService;
    //获取首页轮播图列表
    @GetMapping("/getList")
    public Result getList(int size){
       List<Banner> bannerList =  bannerService.getList(size);
       return Result.ok().data("bannerList",bannerList);
    }
    @PostMapping("/getBannerListByPage")
    public Result getBannerListByPage(int page,int size,@RequestBody(required = false) Map<String,Object> searchMap){
        //分页插件
        Page<Banner> bannerPage = new Page<>(page,size);
        //查询结果
        bannerService.getBannerListByPage(bannerPage,searchMap);
        //返回结果
        return Result.ok().data("bannerList",bannerPage.getRecords()).data("total",bannerPage.getTotal());
    }
    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody Banner banner){
        boolean flag = bannerService.addBanner(banner);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @PostMapping("/modifyBanner")
    public Result modifyBanner(@RequestBody Banner banner){
        boolean flag = bannerService.modifyBanner(banner);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    @GetMapping("/removeBannerById")
    public Result removeBannerById(String id){
        boolean flag = bannerService.removeBannerById(id);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //通过id查询banner
    @GetMapping("/getBannerById")
    public Result getBannerById(String id){
        Banner banner = bannerService.getBannerById(id);
        if(banner != null) {
            return Result.ok().data("banner", banner);
        }else{
            return Result.error();
        }
    }
}
