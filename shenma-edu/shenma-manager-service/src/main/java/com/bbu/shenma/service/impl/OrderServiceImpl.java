package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.Order;
import com.bbu.shenma.bean.Teacher;
import com.bbu.shenma.client.UserClient;
import com.bbu.shenma.mapper.OrderMapper;
import com.bbu.shenma.service.CourseService;
import com.bbu.shenma.service.OrderService;
import com.bbu.shenma.util.Result;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserClient userClient;

    @Override
    public void getOrderListByPage(IPage<Order> iPage, Map<String, Object> searchMap) {
         QueryWrapper<Order> queryWrapper = getQueryWrapper(searchMap);
         orderMapper.selectPage(iPage,queryWrapper);
    }

    private QueryWrapper<Order> getQueryWrapper(Map<String, Object> searchMap) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(searchMap != null){
            if(StringUtils.isNotBlank((String)searchMap.get("courseTitle"))){
                    queryWrapper.like("course_title",searchMap.get("courseTitle"));
            }
            if(StringUtils.isNotBlank((String)searchMap.get("username"))){
                queryWrapper.like("username",searchMap.get("username"));
            }
        }
        return queryWrapper;
    }
    //添加用户
    @Override
    public boolean add(Order order) {
        //用户服务通过用户id获取用户信息
        Result result = userClient.getUserById(order.getUserId());

        if(result != null){
            Map<String, Object> data = result.getData();
            order.setUsername((String) data.get("userName"));
        }
        if(order != null){
           int count = orderMapper.insert(order);
            return  count > 0;
        }
        return false;
    }

    @Override
    public boolean modify(Order order) {
        if(order != null){
            int count = orderMapper.updateById(order);
            return  count > 0;
        }
        return false;
    }

    @Override
    public boolean removeById(String id) {
        if(StringUtils.isNotBlank(id)){
            int count = orderMapper.deleteById(id);
            return count > 0;
        }
        return false;
    }
    //前台订单列表查询
    @Override
    public Map<String,Object> getOrderListByUserId(Page<Order> iPage, String userId) {

        Map<String,Object> map = new HashedMap();
        //分页查询
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        queryWrapper.select("id","out_trade_no","state",
                "create_time","total_fee","course_id","course_title");
        orderMapper.selectPage(iPage,queryWrapper);

        List<Order> records = iPage.getRecords();
        long total = iPage.getTotal();
        long size = iPage.getSize();
        long pages = iPage.getPages();
        long current = iPage.getCurrent();
        boolean next = iPage.hasNext();
        boolean previous = iPage.hasPrevious();

        //放入map中
        map.put("orderList",records);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("current",current);
        map.put("next",next);
        map.put("previous",previous);

        return map;
    }
    //通过id获取订单
    @Override
    public Order getOrderById(String id) {
        if (StringUtils.isNotBlank(id)) {
            Order order = orderMapper.selectById(id);
            return order;
        }
        return null;
    }

    @Override
    public Order getOrderByOrderSn(String orderSn) {
        if(StringUtils.isNotBlank(orderSn)){
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("out_trade_no",orderSn);
            Order order = orderMapper.selectOne(queryWrapper);
            return order;
        }
        return null;
    }

    @Override
    public boolean modifyOrderBySn(Order order) {
        //通过outTradeNo查询订单
        Order orderByOrderSn = this.getOrderByOrderSn(order.getOutTradeNo());
        //更新
        int count = 0;
        if(orderByOrderSn != null) {
            orderByOrderSn.setState(order.getState());
            orderByOrderSn.setOpenid(order.getOpenid());
            orderByOrderSn.setNotifyTime(order.getNotifyTime());
            orderByOrderSn.setType(order.getType());
            count = orderMapper.updateById(orderByOrderSn);
            if(count > 0){
                //更新课程购买数
                String courseId = orderByOrderSn.getCourseId();
                Course courseById = courseService.getCourseById(courseId);
                courseById.setBuyNum(courseById.getBuyNum()+1);
                //更新课程
                courseService.modifyCourse(courseById);
            }

        }
        return count > 0;
    }

    @Override
    public List<Order> getOrderListByUIdAndCourseId(String uId, String courseId) {
        if(StringUtils.isNotBlank(uId) && StringUtils.isNotBlank(courseId)){
            //进行查询操作
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",uId);
            queryWrapper.eq("course_id",courseId);
            List<Order> orderList = orderMapper.selectList(queryWrapper);
            return orderList;
        }
        return null;
    }
}
