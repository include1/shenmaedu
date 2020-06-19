package com.bbu.shenma.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.service.CourseCommentService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shenma/courseComment")
@CrossOrigin
public class CourseCommentController {
    @Autowired
    private CourseCommentService courseCommentService;
    //分页查询
    @PostMapping("/getCourseCommentByPage")
    public Result getCourseCommentByPage(int page, int size, @RequestBody(required = false) Map<String,Object> searchMap){
        Page<CourseComment> courseCommentPage = new Page<>(page,size);
        courseCommentService.getCourseCommentListPage(courseCommentPage,searchMap);
        return Result.ok().data("records",courseCommentPage.getRecords()).data("total",courseCommentPage.getTotal());
     }
    //添加
    @PostMapping("/addCourseComment")
    public Result addCourseComment(@RequestBody CourseComment courseComment){
        boolean b = courseCommentService.addCourseComment(courseComment);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //修改
    @PostMapping("/modifyCourseComment")
    public Result modifyCourseComment(@RequestBody CourseComment courseComment){
        boolean b = courseCommentService.modifyCourseComment(courseComment);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //删除
    @GetMapping("/removeCourseComment")
    public Result removeCourseComment(String id){
        boolean b = courseCommentService.removeCourseCommentById(id);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }

    //通过id进行查询
    @GetMapping("/getCourseCommentById")
    public Result getCourseCommentById(String id){
        CourseComment courseComment = courseCommentService.getCourseCommentById(id);
        return Result.ok().data("courseComment",courseComment);
    }

}
