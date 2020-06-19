package com.bbu.shenma.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Statistics;
import com.bbu.shenma.service.StatisticsService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/statistics/service")
@CrossOrigin
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;

    @GetMapping("/insertRegisterNum")
    public Result insertRegisterNum(String day){
        boolean b = statisticsService.insertRegisterNum(day);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //分页展示统计数据
    @PostMapping("/getStatisticsList")
    public Result getStatisticsList(int page,int size ,@RequestBody(required = false)Map<String,Object> searchMap){
        Page<Statistics> statisticsPage = new Page<>(page,size);
        statisticsService.getStatisticsListByPage(statisticsPage,searchMap);
        return Result.ok().data("total",statisticsPage.getTotal()).data("records",statisticsPage.getRecords());
    }

    //查询数量集合和日期集合
    @GetMapping("/getEchartsData")
    public Result getEchartsData(String type,String begin,String end){
       Map<String,Object> map =  statisticsService.getEchartsData(type,begin,end);
       return Result.ok().data("map",map);
    }

    //添加操作
    @PostMapping("/addStatistics")
    public Result addStatistics(@RequestBody Statistics statistics){
        boolean flag = statisticsService.addStatistics(statistics);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //修改操作
    @PostMapping("/modifyStatistics")
    public Result modifyStatistics(@RequestBody Statistics statistics){
        //调用更新
        boolean flag = statisticsService.modifyStatistics(statistics);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //通过条件更新对象
    @PostMapping("/modifyStatisticsByCondition")
    public Result modifyStatisticsByCondition(@RequestParam("condition")String condition){
        statisticsService.modifyStatisticsByCondition(condition);
        return Result.ok();
    }

}
