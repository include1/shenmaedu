package com.bbu.shenma.controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.annotation.LoginAnnotation;
import com.bbu.shenma.bean.User;
import com.bbu.shenma.form.SysUserForm;
import com.bbu.shenma.service.RedisService;
import com.bbu.shenma.service.UserService;
import com.bbu.shenma.util.*;
import com.google.gson.Gson;
import com.netflix.ribbon.proxy.annotation.Http;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//前台用户服务（登录、天剑、修改）
@Controller
@RequestMapping("/userservice/center")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;

    //用户登录使用JWT进行单点登录
    @RequestMapping("/login")
    @ResponseBody
    public Result login(@RequestBody User user){
        //首先进行token判断
        String token = "";
        User userByNameAndPwd = userService.findUserByNameAndPwd(user.getUsername(),MD5.getNewPwd(user.getPassword()));
        System.out.println(userByNameAndPwd);
        if(userByNameAndPwd != null){
             //生成token
             token = JwtUtils.genJsonWebToken(userByNameAndPwd);
             User userRedis = new User();
             userRedis.setId(userByNameAndPwd.getId());
             userRedis.setNickname(userByNameAndPwd.getUsername());
             userRedis.setHeadImg(userByNameAndPwd.getHeadImg());
             //保存到redis中
             redisService.saveToken("shenma:"+token,userRedis);
             return  Result.ok().data("token",token);
        }else{
            token = "fail";
            return Result.error().data("token","fail");
        }
    }
    //用户退出，使用删除redis中token
    @RequestMapping("/logout")
    @LoginAnnotation
    @ResponseBody
    public Result logout(String token){
        //删除redis中的token
        redisService.destoryToken("shenma:"+token);
        //返回字符串
        return Result.ok();
    }
    //去个人中心
    @GetMapping("/goAccount")
    @LoginAnnotation
    @ResponseBody
    public Result goAccount(){
        //判断是已经登录
        return Result.ok();
    }
    //验证前端的token,获取用户信息
    @GetMapping("/getUserInfo")
    @ResponseBody
    public Result getUserInfoByToken(String token, HttpServletRequest request){
        if(StringUtils.isNotBlank(token)) {
            String tokenValue = redisService.getToken("shenma:"+token);
            if (tokenValue != null) {
                Gson gson = new Gson();
                User user = gson.fromJson(tokenValue, User.class);
                return Result.ok().data("user", user);
            }
        }
            return Result.error();
    }
    //获取统计用户注册数量
    @GetMapping("/getRegisterNum")
    @ResponseBody
    public Result getRegisterNum(@RequestParam("day") String day){
       int count =  userService.getRegisterNum(day);
       return Result.ok().data("registerNum",count);
    }
    //查询用户名是否存在
    @GetMapping("/getUserByName")
    @ResponseBody
    public Result getUserByName(String username){
        User userByName = userService.findUserByName(username);
        if(userByName != null){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    //添加用户
    @RequestMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody User user, HttpServletRequest request){
        //获取对应的ip
        String ip = IPAddrUtil.getRemoteAddr(request);
        if(!StringUtils.isBlank(user.getUsername())&&!StringUtils.isBlank(user.getPassword())){
            //使用md5算法进行加密操作
            user.setPassword(MD5.getNewPwd(user.getPassword()));
            //进行用户名判断是否重名
            User userByName = userService.findUserByName(user.getUsername());
            if (userByName == null) {
                user.setIp(ip);
                boolean flag = userService.add(user);
                if (flag) {
                    return Result.ok();
                } else {
                    return Result.error();
                }
            } else {
                return Result.error().message("此用户名已存在");
            }
        }else{
            return Result.error().message("用户名和密码都不能为空");
        }
    }
    //通过id获取用户信息
    @GetMapping("/getUserById")
    @ResponseBody
    public Result getUserById(@RequestParam String uId){
       User user =  userService.getUserById(uId);
        HashMap<String,Object> map = new HashMap<>();
        map.put("id",user.getId());
        map.put("userName",user.getUsername());
        map.put("headImg",user.getHeadImg());
        map.put("createTime",user.getCreateTime());
       return Result.ok().data(map);
    }
    @GetMapping("/getUserInfoById")
    @ResponseBody
    @LoginAnnotation
    public Result getUserInfo(String id){
        User userById = userService.getUserById(id);
        if(userById != null){
            return Result.ok().data("user",userById);
        }else{
            return Result.error();
        }
    }
    //更新前台用户信息
    @PostMapping("/updateUser")
    @ResponseBody
    @LoginAnnotation
    public Result updateUser(@RequestBody User user,HttpServletRequest request){
        String ip = IPAddrUtil.getRemoteAddr(request);
        user.setIp(ip);
        boolean modify = userService.modify(user);
        if(modify){
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    //修改前台用户密码
    @PostMapping("/modifyPwd")
    @ResponseBody
    @LoginAnnotation
    public Result modifyPwd(@RequestBody SysUserForm sysUserForm, HttpServletRequest request){
        String ip = IPAddrUtil.getRemoteAddr(request);
        //通过用户和密码进行查询
        User user = userService.findUserByNameAndPwd(sysUserForm.getUsername(),MD5.getNewPwd(sysUserForm.getOldPassword()));
        if(user != null){

            //判断两次密码是否相等
             if(sysUserForm.getPassword().equals(sysUserForm.getRePassword())){
                  //密码进行加密
                 String newPwd = MD5.getNewPwd(sysUserForm.getRePassword());
                 user.setIp(ip);
                 user.setPassword(newPwd);
                 //更新操作
                 boolean b = userService.modify(user);
                 if(b){
                     return Result.ok();
                 }else {
                     return Result.error();
                 }
             }else{
                 return Result.error();
             }
        }else {
            return Result.error().message("密码错误");
        }
    }
    //后台用户API
    //删除
    @GetMapping("/removeUser")
    @ResponseBody
    public Result removeUser(String id){
        boolean b = userService.removeById(id);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //修改
    @PostMapping("/modifyUser")
    @ResponseBody
    public Result modifyUser(@RequestBody User user) {
        boolean flag = userService.modify(user);
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
    //通过id获取
    @GetMapping("/getFrontUserById")
    @ResponseBody
    public Result getFrontUserById(String id){
        User userById = userService.getUserById(id);
        return Result.ok().data("user",userById);
    }
    //分页查询列表
    @PostMapping("/getUserByPage")
    @ResponseBody
    public Result getUserByPage(int page, int size, @RequestBody(required = false)Map<String,Object> searchMap){
        Page<User> userPage = new Page<>(page,size);
        userService.getPageByCondition(userPage,searchMap);
        return  Result.ok().data("records",userPage.getRecords()).data("total",userPage.getTotal());
    }
}
