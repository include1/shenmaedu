package com.bbu.shenma.controller;

import com.bbu.shenma.bean.User;
import com.bbu.shenma.service.RedisService;
import com.bbu.shenma.service.UserService;
import com.bbu.shenma.util.*;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
//第三方新浪登录
@Controller
@RequestMapping("/xinlang")
@CrossOrigin
public class XinLangLoginController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;

    //调用新浪登录接口
    @GetMapping("/connect")
    public String xinglangConnect(){
        //拼接url
        String baseUrl = "https://api.weibo.com/oauth2/authorize" +
                "?client_id=%s" +
                "&response_type=code" +
                "&redirect_uri=%s";
        //获取回调地址
        String redirectUrl = XinLangPropertiesUtil.XL_OPEN_REDIRECT_URL;
        //url进行编码
        try {
            redirectUrl =  URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //引导新浪登录页面
        String queryUrl = String.format(baseUrl,XinLangPropertiesUtil.XL_OPEN_APP_ID,redirectUrl);
        return "redirect:"+queryUrl;
    }

    //回调地址
    @GetMapping("/vlogin")
    public String callback(String code, HttpServletRequest request,HttpServletResponse response){
        System.out.println(code);
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weibo.com/oauth2/access_token?";
        //拼接出来请求地址
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("client_id",XinLangPropertiesUtil.XL_OPEN_APP_ID);
        paramMap.put("client_secret",XinLangPropertiesUtil.XL_OPEN_APP_SECRET);
        paramMap.put("grant_type","authorization_code");
        paramMap.put("redirect_uri",XinLangPropertiesUtil.XL_OPEN_REDIRECT_URL);
        paramMap.put("code",code);

        //发送httpclient post请求地址，获取凭证
        String result = null;
        try {
            result = HttpClientUtil.doPost(baseAccessTokenUrl,paramMap);
        }catch(Exception e) {
            e.printStackTrace();
        }
        //把返回result使用Gson进行json数据转换
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        //获取access_token 、uid
        if(map != null) {
            String accessToken = (String) map.get("access_token");
            String uid = (String) map.get("uid");
            //通过uid查询用户信息
            User user = userService.existWxUser(uid);
            //如何为null保存到数据库
            String userInfo = "";
            if (user == null) {
                //获取用户信息
                try {
                    userInfo = HttpClientUtil.doGet("https://api.weibo.com/2/users/show.json?access_token=" + accessToken + "&uid=" + uid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HashMap userMap = gson.fromJson(userInfo, HashMap.class);

                //用户信息保存得到数据库中
                String screenName = (String) userMap.get("screen_name");
                String profileImage = (String) userMap.get("profile_image_url");
                String sex = (String) userMap.get("gender");
                String city = (String) userMap.get("location");

                user = new User();
                user.setUsername(screenName);
                user.setNickname(screenName);
                user.setOpenId(uid);
                user.setHeadImg(profileImage);
                user.setCity(city);
                user.setSex(sex.equals("m") ? "1" : "0");
                userService.add(user);

            }
            //生成一个token
            String token = JwtUtils.genJsonWebToken(user);
            //保存到redis中
            redisService.saveToken("shenma:" + token, user);
            //带回前台页面，并保存到cookie中
            return "redirect:http://localhost:6000?token=" + token;
        }else{
            return "redirect:http://localhost:6000/login";
        }
    }

}
