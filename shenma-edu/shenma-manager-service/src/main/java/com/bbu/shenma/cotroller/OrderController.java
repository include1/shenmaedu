package com.bbu.shenma.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.bean.Order;
import com.bbu.shenma.service.OrderService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shenma/order")
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;
    //进行分页查询
    @PostMapping("/getOrderByPage")
    public Result getOrderByPage(int page, int size, @RequestBody(required = false) Map<String,Object> searchMap){
        Page<Order> orderPage = new Page<>(page,size);
        orderService.getOrderListByPage(orderPage,searchMap);
        return Result.ok().data("records",orderPage.getRecords()).data("total",orderPage.getTotal());
    }
    //添加
    @PostMapping("/addOrder")
    public Result addOrder(@RequestBody Order order){
        boolean b = orderService.add(order);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //修改
    @PostMapping("/modifyOrder")
    public Result modifyOrder(@RequestBody Order order){
        boolean b = orderService.modify(order);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //删除
    @GetMapping("/removeOrder")
    public Result removeOrder(String id){
        boolean b = orderService.removeById(id);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //通过id进行查询
    @GetMapping("/getOrderById")
    public Result getOrderById(String id){
        Order order = orderService.getOrderById(id);
        return Result.ok().data("order",order);
    }
}
