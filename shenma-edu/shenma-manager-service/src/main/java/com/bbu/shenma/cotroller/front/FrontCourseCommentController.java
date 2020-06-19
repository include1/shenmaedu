package com.bbu.shenma.cotroller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.annotation.LoginAnnotation;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.service.CourseCommentService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/shenma/frontCourseComment")
@CrossOrigin
public class FrontCourseCommentController {
    @Autowired
    private CourseCommentService courseCommentService;

       //通过条件查询该课程所有的评论进行分页
   @PostMapping("/findCourseCommentByPage")
    public Result findCourseCommentByPage(int page, int size,@RequestBody(required = false)Map<String,Object> searchMap){
           //使用分页插件
         Page<CourseComment> commentPage = new Page<>(page,size);
         Map<String,Object> map = courseCommentService.findFrontCourseCommentList(commentPage,searchMap);
         return Result.ok().data("map",map);
    }
    //添加评论，要获取用户id+课程id
    @PostMapping("/addCourseComment")
    @LoginAnnotation
    public Result addCourseComment(@RequestBody CourseComment courseComment, HttpServletRequest request){
        //获取用户id
        String  uId= request.getHeader("uId");
        boolean flag = courseCommentService.addFrontCourseComment(courseComment,uId);
        if(flag) {
            return Result.ok();
        }else{
            return Result.error();
        }
    }
    /*/shenma/frontUserCourse/getUserCommentInfo?uId*/
    @GetMapping("/getUserCommentInfo")
    @LoginAnnotation
    public Result getUserCommentInfo(int page,int pageSize,String uId){
        Map<String,Object> map = courseCommentService.getUserCommentInfo(page,pageSize,uId);
        return Result.ok().data(map);
    }
}
