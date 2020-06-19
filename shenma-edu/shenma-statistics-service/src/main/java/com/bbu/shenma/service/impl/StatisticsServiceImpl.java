package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bbu.shenma.bean.Statistics;
import com.bbu.shenma.client.UserClient;
import com.bbu.shenma.mapper.StatisticsMapper;
import com.bbu.shenma.service.StatisticsService;
import com.bbu.shenma.util.Result;
import com.netflix.discovery.converters.Auto;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.xml.ws.Action;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private StatisticsMapper statisticsMapper;
    @Autowired
    private UserClient userClient;

    /**
     * 分页
     * @param iPage
     *
     */

    @Override
    public void getStatisticsListByPage(IPage<Statistics> iPage,Map<String,Object> searchMap) {
        QueryWrapper<Statistics> queryWrapper = getQueryWrapper(searchMap);
        statisticsMapper.selectPage(iPage,queryWrapper);
    }

    private QueryWrapper<Statistics> getQueryWrapper(Map<String, Object> searchMap) {
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        if(searchMap != null){
            if(searchMap.get("dateCalculated") != null && !"".equals(searchMap.get("dateCalculated"))){
                queryWrapper.eq("date_calculated",searchMap.get("dateCalculated"));
            }
        }
        return queryWrapper;
    }

    /**
     * 通过日期统计分析对象
     * @param date
     * @return
     */
    @Override
    public Statistics getStatisticsBydateCalculated(String date) {
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        if(date != null) {
            queryWrapper.eq("date_calculated", date);
            //查询
            Statistics statistics = statisticsMapper.selectOne(queryWrapper);
            return  statistics;
        }
        return null;

    }

    /**
     * 添加
     * @param statistics
     * @return
     */
    @Transactional
    @Override
    public boolean addStatistics(Statistics statistics) {
        //进行判断
        boolean flag = validateStatistics(statistics);
        if(flag){
            //查询该日期是否存在
            QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("data_calculated",statistics.getDateCalculated());
            Statistics selectOne = statisticsMapper.selectOne(queryWrapper);
            //判断当前日期是否存在
            if(selectOne == null) {
                int count = statisticsMapper.insert(statistics);
                return count > 0;
            }
        }
        return false;
    }

    private boolean validateStatistics(Statistics statistics) {

        if(StringUtils.isBlank(statistics.getDateCalculated())){
            return false;
        }else if(statistics.getRegisterNum() == null){
            return false;
        }else if(statistics.getLoginNum() == null){
            return false;
        }else if(statistics.getVideoViewNum() == null){
            return  false;
        }else if(statistics.getCourseNum() == null){
            return false;
        }
        return true;
    }

    /**
     * 修改
     * @param statistics
     * @return
     */
    @Override
    public boolean modifyStatistics(Statistics statistics) {
        boolean flag = validateStatistics(statistics);
        if(flag){
            int count = statisticsMapper.updateById(statistics);
            return count > 0;
        }
        return false;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public boolean removeStatisticsByKeyPrimary(String id) {

        if(!StringUtils.isBlank(id)){
            int count = statisticsMapper.deleteById(id);
            return count > 0;
        }
        return false;
    }
    //**********************模拟数据，插入统计表中-------------------------------------------------------
    @Override
    @Transactional
    public boolean insertRegisterNum(String day) {

        //进行判断当前日期是否存在
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",day);
        Statistics selectOne = statisticsMapper.selectOne(queryWrapper);
        //调用用户中心的服务获取注册数量
        Result result = userClient.getRegisterNum(day);
        Integer registerNum = (Integer)result.getData().get("registerNum");
        if(selectOne != null) {
            //更新操作
            selectOne.setRegisterNum(registerNum);
            int count = statisticsMapper.updateById(selectOne);
            return count > 0;
        }else{
            //模拟插入，假数据
            Statistics statistics = new Statistics();
            statistics.setDateCalculated(day);
            statistics.setRegisterNum(registerNum);
            statistics.setLoginNum(0);
            statistics.setVideoViewNum(0);
            statistics.setCourseNum(0);

            //校验
            boolean flag = validateStatistics(statistics);
            if(flag){
                int count = statisticsMapper.insert(statistics);
                return count > 0;
            }
        }
        return  false;
    }

    @Override
    public Map<String, Object> getEchartsData(String type, String begin, String end) {
        //判断
        if(type.equals("") || type == null){
            return  null;
        }else if(begin.equals("") || begin == null){
            return  null;
        }else if(end.equals("") || end == null){
            return  null;
        }
        //获取日期范围内的数据库中的集合
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("date_calculated",begin,end);
        queryWrapper.select("date_calculated",type);
        queryWrapper.orderByAsc("date_calculated");
        List<Statistics> statisticsList = statisticsMapper.selectList(queryWrapper);

        //创建日期数据集合和数据集合
        List<Integer> numberList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        //遍历
        for(int i = 0; i < statisticsList.size(); i++){
            //放入日期集合
            dateList.add(statisticsList.get(i).getDateCalculated());
            //放入数据集合中
            switch (type){
                case "register_num":
                    numberList.add(statisticsList.get(i).getRegisterNum());
                    break;
                case "login_num":
                    numberList.add(statisticsList.get(i).getLoginNum());
                    break;
                case "video_view_num":
                    numberList.add(statisticsList.get(i).getVideoViewNum());
                    break;
                case "course_num":
                    numberList.add(statisticsList.get(i).getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("numberList",numberList);
        return map;
    }
    @Transactional
    @Override
    public void modifyStatisticsByCondition(String condition) {
        //获取当前日期
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        QueryWrapper<Statistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated",date);
        //从数据库中获取对象
        Statistics selectOne = statisticsMapper.selectOne(queryWrapper);
        //判断是否为空，空则添加，不空则修改‘
        if(selectOne == null){
            //添加
            Statistics statistics = new Statistics();
            statistics.setDateCalculated(date);
            switch (condition){
                case "register_num":
                    statistics.setRegisterNum(1);
                    statistics.setLoginNum(0);
                    statistics.setVideoViewNum(0);
                    statistics.setCourseNum(0);
                    break;
                case "login_num":
                    statistics.setRegisterNum(0);
                    statistics.setLoginNum(1);
                    statistics.setVideoViewNum(0);
                    statistics.setCourseNum(0);
                    break;
                case "video_view_num":
                    statistics.setRegisterNum(0);
                    statistics.setLoginNum(0);
                    statistics.setVideoViewNum(1);
                    statistics.setCourseNum(0);
                    break;
                case "course_num":
                    statistics.setRegisterNum(0);
                    statistics.setLoginNum(0);
                    statistics.setVideoViewNum(0);
                    statistics.setCourseNum(1);
                    break;
                default:
                    break;
            }
            boolean flag = this.validateStatistics(statistics);
            if(flag){
                statisticsMapper.insert(statistics);
            }
        }else{
            //判断
            switch (condition){
                case "register_num":
                    selectOne.setRegisterNum(selectOne.getRegisterNum()+1);
                    break;
                case "login_num":
                    selectOne.setLoginNum(selectOne.getLoginNum()+1);
                    break;
                case "video_view_num":
                    selectOne.setVideoViewNum(selectOne.getVideoViewNum()+1);
                    break;
                case "course_num":
                    selectOne.setCourseNum(selectOne.getCourseNum()+1);
                    break;
                default:
                    break;
            }
            //修改
            boolean flag = this.validateStatistics(selectOne);
            if(flag){
                statisticsMapper.updateById(selectOne);
            }
        }
    }

}
