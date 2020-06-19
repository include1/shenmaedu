package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.Statistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatisticsService {

    //分页插件获取统计的信息
    public void getStatisticsListByPage(IPage<Statistics> iPage,Map<String,Object> searchMap);
    //通过createDate得到当前数据
    public  Statistics getStatisticsBydateCalculated(String date);
    //添加
    public boolean addStatistics(Statistics statistics);
    //修改
    public boolean modifyStatistics(Statistics statistics);
    //删除
    public  boolean removeStatisticsByKeyPrimary(String id);
    //插入注册数量
    public boolean insertRegisterNum(String day);

    Map<String, Object> getEchartsData(String type, String begin, String end);

    void modifyStatisticsByCondition(String condition);
}
