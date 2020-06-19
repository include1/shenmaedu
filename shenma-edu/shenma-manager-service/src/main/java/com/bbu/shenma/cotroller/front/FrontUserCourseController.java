package com.bbu.shenma.cotroller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.annotation.LoginAnnotation;
import com.bbu.shenma.bean.UserCourse;
import com.bbu.shenma.service.UserCourseService;
import com.bbu.shenma.util.Result;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/shenma/frontUserCourse")
@CrossOrigin
public class FrontUserCourseController {
    @Autowired
    private UserCourseService userCourseService;

    @GetMapping("/getUserCourseByUId")
    @LoginAnnotation
    public Result getUserCourseByUId(int page,int size,String uId){
        //通过id获取课程信息
        Page<UserCourse> userCoursePage = new Page<>(page,size);
        //获取map集合
        Map<String,Object> map = userCourseService.getUserCourseByUId(userCoursePage,uId);
        return  Result.ok().data("map",map);
    }
    //添加用户课程
    @PostMapping("/addUserCourse")
    @LoginAnnotation
    public Result addUserCourse(@RequestBody UserCourse userCourse){
       boolean flag = userCourseService.addUserCourse(userCourse);
       if(flag){
           return Result.ok();
       }else{
           return Result.error();
       }
    }
    //判断一下用户课程是否已经加入学习
    @PostMapping("/getUserCourse")
    @LoginAnnotation
    public Result getUserCourse(@RequestBody UserCourse userCourse){
        //查询用户课程，（userId+courseId）
        UserCourse userCourse1 =userCourseService.getUserCourseByUserIdAndCourseId(userCourse);
        if(userCourse1 == null){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //移除用户课程
    @GetMapping("/removeUserCourse")
    @LoginAnnotation
    public Result removeUserCourse(String uId,String courseId){
        boolean flag = userCourseService.removeUserCourse(uId,courseId);
        if(flag){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

}
