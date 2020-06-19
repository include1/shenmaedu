package com.bbu.shenma.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.SysUser;
import com.bbu.shenma.bean.User;
import com.bbu.shenma.form.SysUserForm;
import com.bbu.shenma.service.SysUserService;
import com.bbu.shenma.util.IPAddrUtil;
import com.bbu.shenma.util.MD5;
import com.bbu.shenma.util.Result;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/shenma/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    //分页查询
    @PostMapping("/getPage")
    public Result getPageByConditoin(int page, int size, @RequestParam(value = "searchMap",required = false) Map<String,Object> searchMap){
        Page<SysUser> sysUserPage = new Page<>(page,size);
        sysUserService.getPageByCondition(sysUserPage,searchMap);
        return Result.ok().data("total",sysUserPage.getTotal()).data("records",sysUserPage.getRecords());
    }
    //获取id信息
    @GetMapping("/getSysUserById")
    public Result getSysUserById(String id){
        SysUser sysUser = sysUserService.fingSysUserById(id);
        if(sysUser != null){
            return Result.ok().data("sysUser",sysUser);
        }else{
            return Result.error();
        }
    }

    //添加
    @PostMapping("/addSysUser")
    public Result addSysUser(@RequestBody SysUser sysUser, HttpServletRequest request){
        //获取ip
        sysUser.setIp(IPAddrUtil.getRemoteAddr(request));
        //检验是否为空
        if(!StringUtils.isBlank(sysUser.getUsername())&&!StringUtils.isBlank(sysUser.getPassword())){
            //使用md5算法进行加密操作
            //加盐操作
            sysUser.setPassword(MD5.getNewPwd(sysUser.getPassword()));
            //进行用户名判断是否重名
            SysUser userByName = sysUserService.findSysUserByName(sysUser.getUsername());
            if (userByName == null) {
                boolean flag = sysUserService.add(sysUser);
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
    //修改
    @PostMapping("/modifySysUser")
    public Result modifySysUser(@RequestBody SysUser sysUser,HttpServletRequest request){
        //更新ip地址
        sysUser.setIp(IPAddrUtil.getRemoteAddr(request));
        //更新数据库
        boolean flag = sysUserService.modify(sysUser);
        if(flag){
            return  Result.ok().message("更新成功");
        }else{
            return Result.error().message("更新失败");
        }
    }
    //删除
    @GetMapping("/removeSysUser")
    public Result removeSysUser(String id){
        boolean flag = sysUserService.removeById(id);
        if(flag){
            return  Result.ok().message("删除成功");
        }else{
            return Result.error().message("删除失败");
        }
    }
    //修改密码
    @PostMapping("/updatePwd")
    public Result updatePwd(@RequestBody SysUserForm sysUserForm){

        //首先判断旧密码是否正确
        SysUser sysUserByNameAndPwd = sysUserService.findSysUserByNameAndPwd(sysUserForm.getUsername(), MD5.getNewPwd(sysUserForm.getOldPassword()));
        if(sysUserByNameAndPwd != null){
            //判断新密码和旧密码是否相同
            if(!sysUserByNameAndPwd.getPassword().equals(MD5.getNewPwd(sysUserForm.getPassword()))){
                //判断新密码两次输入是否相同
                if(sysUserForm.getPassword().equals(sysUserForm.getRePassword())){
                    //加盐
                    String newPwd = MD5.getNewPwd(sysUserForm.getPassword());
                    sysUserByNameAndPwd.setPassword(newPwd);
                    //操作数据库
                    sysUserService.modify(sysUserByNameAndPwd);
                    //返回结果
                    return  Result.ok();
                }else{
                    return Result.error().message("新密码和确认密码输入要相同");
                }
            }else {
                return Result.error().message("旧密码与新密码不能相同");
            }
        }else{
            return Result.error().message("旧密码输入错误，重新输入");
        }
    }

}
