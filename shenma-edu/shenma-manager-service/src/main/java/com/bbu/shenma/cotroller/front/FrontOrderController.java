package com.bbu.shenma.cotroller.front;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.annotation.LoginAnnotation;
import com.bbu.shenma.bean.Order;
import com.bbu.shenma.config.AlipayConfig;
import com.bbu.shenma.service.OrderService;
import com.bbu.shenma.util.Result;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shenma/frontOrder")
@CrossOrigin
public class FrontOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private AlipayClient alipayClient;

    //通过用户id获取订单列表
    @GetMapping("/getOrderListByUserId")
    @LoginAnnotation
    @ResponseBody
    public Result getOredrListByUserId(int page,int size,String uId){
        Page<Order> orderPage = new Page<>(page,size);
        Map<String,Object> map = orderService.getOrderListByUserId(orderPage,uId);
        return Result.ok().data(map);
    }
    //通过id获取订单详情
    @GetMapping("/getOrderById")
    @LoginAnnotation
    @ResponseBody
    public Result getOrderById(String id){
        Order order = orderService.getOrderById(id);
        return Result.ok().data("order",order);
    }
    //添加订单
    @PostMapping("/addOrder")
    @LoginAnnotation
    @ResponseBody
    public Result addOrder(@RequestBody Order order){
        //生成一个唯一的订单号
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDDhhmmss");
        String sn = format.format(new Date()) + System.currentTimeMillis();
        order.setOutTradeNo(sn);
        //添加订单信息
        boolean flag = orderService.add(order);
        if(flag){
            return Result.ok().data("sn",sn);
        }else {
            return Result.error();
        }
    }
    //通过OrderSn获取相应的订单信息
    @GetMapping("/getOrderInfoByOrderSn")
    @LoginAnnotation
    @ResponseBody
    public Result getOrderInfoByOrderSn(String orderSn){
        Order order = orderService.getOrderByOrderSn(orderSn);
        if(order != null){
            return Result.ok().data("order",order);
        }else {
            return Result.error();
        }
    }
    //通过id删除订单信息
    @GetMapping("/delOrder")
    @LoginAnnotation
    @ResponseBody
    public Result delOrder(String id){
        boolean flag = orderService.removeById(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    //订单支付，跳转到第三方支付系统
    @PostMapping("/trade")
    @ResponseBody
    @LoginAnnotation
    public String trade(String orderSn,String type){
        //通过orderSn获取对应的价格
        Order orderByOrderSn = orderService.getOrderByOrderSn(orderSn);
        String form = null;
        //支付宝支付
        if(type.equals("alipay") && orderByOrderSn != null){
            //获取总价格
            Double totalFee = orderByOrderSn.getTotalFee();
            //生成阿里云的支付页面
            AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
            //回调地址
            alipayRequest.setReturnUrl(AlipayConfig.return_payment_url);
            alipayRequest.setNotifyUrl(AlipayConfig.notify_payment_url);

            //封装参数请求支付接口
            Map<String,Object> map = new HashedMap();
            map.put("out_trade_no",orderSn);
            map.put("product_code","FAST_INSTANT_TRADE_PAY");
            map.put("total_amount",orderByOrderSn.getTotalFee());
            map.put("subject",orderByOrderSn.getCourseTitle());
            String param = new Gson().toJson(map);
            alipayRequest.setBizContent(param);

            try {
                form = alipayClient.pageExecute(alipayRequest).getBody();
            }catch (AlipayApiException e){
                e.printStackTrace();
            }
        }
        System.out.println(form);
        return form;
    }
    //系统同步回调地址
    @GetMapping("/alipay/callback")
    @LoginAnnotation
    public String callback(HttpServletRequest request){
        //获取签名
        String outTradeNo = request.getParameter("out_trade_no");
        String sellerId = request.getParameter("seller_id");
        String sign = request.getParameter("sign");
        //暂时模拟验证
        if (StringUtils.isNotBlank(sign)) {
            //更新订单信息
            Order order = new Order();
            order.setOutTradeNo(outTradeNo);
            order.setOpenid(sellerId);
            order.setType("alipay");
            order.setState(1);
            order.setNotifyTime(new Date());
            orderService.modifyOrderBySn(order);
        }
        return "redirect:http://localhost:6000/order/finish";
    }
    //获取该课程是否被购买
    @GetMapping("/getPurchaseState")
    @LoginAnnotation
    @ResponseBody
    public Result getPurchaseState(String uId,String courseId){
        //获取课程的结果
        List<Order> orderList = orderService.getOrderListByUIdAndCourseId(uId,courseId);
        //遍历判断
        for(Order order : orderList){
            if(order.getState() == 1){
                return Result.ok();
            }
        }
        return Result.error();
    }
}
