package com.bbu.shenma.interceptor;

import com.bbu.shenma.annotation.LoginAnnotation;
import com.bbu.shenma.util.HttpClientUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截代码

        //判断被拦截的请求的访问的方法的注解
        LoginAnnotation methodAnnotation = null;
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            methodAnnotation =  hm.getMethodAnnotation(LoginAnnotation.class);
        }
        //不加此注解不需要拦截，加了此注解必须拦截
        if(methodAnnotation == null){
            return  true;
        }else {
                //通过验证中心进行验证
                String token = null;
                        if(request.getHeader("token") != null){
                            token  = request.getHeader("token");
                        }else {
                            Cookie[] cookies = request.getCookies();
                            if(cookies != null && cookies.length > 0) {
                                for (Cookie cookie : cookies) {
                                    if (cookie.getName().equals("token")) {
                                        token = cookie.getValue();
                                    }
                                }
                            }else {
                                return false;
                            }
                        }
                //验证token
                System.out.println(token);
                String userInfo = null;
                if(token != null) {
                    userInfo = HttpClientUtil.doGet("http://localhost:8004/userservice/center/getUserInfo?token=" + token);
                    HashMap hashMap = new Gson().fromJson(userInfo, HashMap.class);
                    if((boolean)hashMap.get("success")){
                          return true;
                    }
                }
                    //重定向到登录界面,跨域问题暂时不能解决------------------------------------------------------------------------------
                return false;
            }
    }
}
