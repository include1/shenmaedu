package com.bbu.shenma.cotroller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.bean.Teacher;
import com.bbu.shenma.service.CourseCommentService;
import com.bbu.shenma.service.TeacherService;
import com.bbu.shenma.service.UserCourseService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/shenma/frontTeacher")
public class FrontTeacherController {
    @Autowired
    private TeacherService teacherService;

    //分页
    @GetMapping("/getTeacherListPage")
    public Result getTeacherListPage(int page,int size){
        //实现分页查询
        Page<Teacher> teacherPage = new Page<>(page,size);
        //调用service实现分页
        Map<String,Object> map = teacherService.getFrontTeacherListPage(teacherPage);

        return Result.ok().data(map);
    }
    //查询讲师详情
    @GetMapping("/getTeacheInfo")
    public Result getTeacherInfoByTeacherId(String id){
        //根据讲师id查询讲师详情，返回对象
        Teacher teacher = teacherService.fingTeacherById(id);
        //根据讲师id查询课程
        List<Course> courseList = teacherService.getCourseListByTeacherId(id);
        //获取课程的学习人数和评论数
        return Result.ok().data("teacher",teacher).data("courseList",courseList);
    }


}
