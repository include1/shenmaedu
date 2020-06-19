package com.bbu.shenma.cotroller;

import com.bbu.shenma.bean.SysUser;
import com.bbu.shenma.bean.User;
import com.bbu.shenma.service.SysUserService;
import com.bbu.shenma.service.UserService;
import com.bbu.shenma.util.HmacMD5Util;
import com.bbu.shenma.util.MD5;
import com.bbu.shenma.util.Result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/shenma/user")
@CrossOrigin
public class LoginController {
    @Autowired
    private SysUserService sysUserService;
    private HttpSession session;

    @PostMapping("/login")
    public Result login(@RequestBody SysUser sysUser, HttpSession session){
        Map<String,Object> map = new HashMap<>();
        //判断用户对象是否为空  //{"code":20000,"data":{"token":"admin"}}
        if(sysUser != null){

            sysUser.setPassword(MD5.getNewPwd(sysUser.getPassword()));
            SysUser userByNameAndPwd = sysUserService.findSysUserByNameAndPwd(sysUser.getUsername(), sysUser.getPassword());
            if(userByNameAndPwd != null){
                this.session = session;
                //生成一个token
                String token = UUID.randomUUID().toString();
                session.setAttribute("sysUser",userByNameAndPwd);
                session.setAttribute("token",token);
                return Result.ok().data("token",token);
            }
        }
        return Result.error().message("用户名或密码错误");
    }
    @GetMapping("/info")
    public Result info(String token)
    {//"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}

        if(!StringUtils.isBlank(token)){
            if(session != null) {
                String sessionToken = (String) session.getAttribute("token");
                if (token.equals(sessionToken)) {
                    SysUser sysUser = (SysUser) session.getAttribute("sysUser");
                    return Result.ok().data("roles", "admin").data("name", sysUser.getUsername()).data("avatar", sysUser.getHeadImg());
                }
            }
            return  Result.error().message("token过期").code(50014);
        }
        return Result.error().message("token不合法").code(50008);
    }
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request)
    {
        session.invalidate();
        return  Result.ok();
    }
}
