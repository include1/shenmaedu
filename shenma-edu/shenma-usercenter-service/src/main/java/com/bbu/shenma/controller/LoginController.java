package com.bbu.shenma.controller;

import com.bbu.shenma.bean.User;
import com.bbu.shenma.service.UserService;
import com.bbu.shenma.util.*;
import com.google.gson.Gson;
import com.netflix.ribbon.proxy.annotation.Http;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

//第三方登录
@RestController
@CrossOrigin
@RequestMapping("thirdParty/api/login")
public class LoginController {
    @Autowired
    private UserService userService;

    //根据token信息jwt的令牌，获取用户信息返回
    @GetMapping("/userInfo")
    public Result getUserInfoToken(String token){
            //调用工具类获取用户信息
        Claims claims = JwtUtils.checkJwt(token);
        String nickName = (String) claims.get("nickName");
        String headImg = (String) claims.get("headImg");
        String id = (String) claims.get("id");
        User user = new User();
        user.setId(id);
        user.setHeadImg(headImg);
        user.setNickname(nickName);
        return Result.ok().data("user",user);
    }
    //回调
    @GetMapping("/callback")
    public String callback(String code,String state){
        //向认证服务器发送请求换取access_token
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";

        //拼接出来请求地址
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
        );

        //发送httpclient请求地址，获取凭证
        String result = null;
        try {
            result = HttpClientUtil.doGet(accessTokenUrl);
        }catch(Exception e) {

        }
        //把返回result使用Gson进行json数据转换
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        //通过转换之后map集合，根据map里面的key获取value值
        String access_token = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //根据openid判断数据库表是否已经存在当前扫描微信用户
        User user = userService.existWxUser(openid);
        if(user == null) {//表没有扫描微信数据，获取用户信息，添加到数据库里面
            //根据凭证和微信id 获取扫描人的信息（用户名称，头像等）
            //拼接获取微信用户信息的地址
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            String userInfoUrl = String.format(baseUserInfoUrl,access_token,openid);

            //发送httpclient请求
            String userInfo = null;
            try {
                userInfo =HttpClientUtil.doGet(userInfoUrl);
            }catch(Exception e) {
            }

            //转换json
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");//微信昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");//微信头像

            //把微信信息添加到数据库里面
            user = new User();
            user.setOpenId(openid);
            user.setNickname(nickname);
            user.setHeadImg(headimgurl);
            //添加用户
            userService.add(user);
        }
        //生成token
        String token = JwtUtils.genJsonWebToken(user);
        return "redirect:http://localhost:6000?token="+token;
    }


    //生成微信登录扫描的二维码
    @GetMapping("/wx")
    public String genQrConnect(HttpSession session){
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //调用controller里面的方法
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        //对回调地址进行url编码
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String state = "shenmaonline";
        //拼接出来最终生成二维码地址//format给占位符填充参数
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        //重定向一个地址
        return "redirect:"+qrcodeUrl;
    }
    //支付宝授权登录
    @GetMapping("/alipay")
    public String alipayConnect(){

        String baseUrl = "https://openauth.alipay.com/oauth2/appToAppAuth.htm" +
                "?app_id=%s" +
               " &application_type=TINYAPP,WEBAPP"+
                "&redirect_uri=%s";
        String returnUrl = AlipayPropertiesUtil.RETURN_URL;
        //对回调地址编码
        try {
           returnUrl = URLEncoder.encode(returnUrl,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String qrcodeUrl = String.format(baseUrl,AlipayPropertiesUtil.APP_ID,returnUrl);
        return "redirect:"+qrcodeUrl;
    }
}
