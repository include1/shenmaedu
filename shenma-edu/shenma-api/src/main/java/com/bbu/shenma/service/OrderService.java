package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.omg.PortableInterceptor.ObjectReferenceFactory;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public void  getOrderListByPage(IPage<Order> iPage, Map<String,Object> searchMap);

    public boolean add(Order order);

    public boolean modify(Order order);

    public boolean removeById(String id);

    public Map<String,Object> getOrderListByUserId(Page<Order> iPage, String userId);

    Order getOrderById(String id);

    Order getOrderByOrderSn(String orderSn);

    boolean modifyOrderBySn(Order order);

    List<Order> getOrderListByUIdAndCourseId(String uId, String courseId);
}
