package com.bbu.shenma.cotroller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.dto.CourseInfoDto;
import com.bbu.shenma.service.CourseService;
import com.bbu.shenma.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shenma/course")
@CrossOrigin
public class CourseController {
    @Autowired
    private CourseService courseService;
    //进行条件分页查询
    @PostMapping("/findPageByCondition")
    public Result findPageByCondition(int page, int size,@RequestBody Map<String,Object> searchMap){
        //使用分页插件
        Page<Course> coursePage = new Page<>(page,size);
        courseService.getPageByCondition(coursePage,searchMap);
        return Result.ok().data("total",coursePage.getTotal()).data("courseList",coursePage.getRecords());
    }
    //添加操作
    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody Course course){
        System.out.println(course);
        String str = courseService.addCourse(course);
        if(!StringUtils.isBlank(str)){
            return Result.ok().data("courseId",str);
        }else{
            return Result.error().message("添加失败");
        }
    }
    //通过课程id进行查询操作
    @GetMapping("/getCourseById")
    public Result getCourseById(String id){
        Course course = courseService.getCourseById(id);
        if(course != null){
            return  Result.ok().data("course",course);
        }else {
            return Result.error().message("查询失败");
        }
    }
    //删除操作
    @GetMapping("/deleteCourseById")
    public Result deleteCourseById(String id){
        boolean b = courseService.removeCourse(id);
        if(b){
            return Result.ok();
        }else {
            return Result.error();
        }
    }
    //更新操作
    @PostMapping("/updateCourse")
    public Result updateCourse(@RequestBody Course course) {
        String str = courseService.modifyCourse(course);
        if (!StringUtils.isBlank(str)) {
            return Result.ok().data("courseId", str);
        } else {
            return Result.error().message("更新失败");
        }
    }
        //获取课程详细信息
        @GetMapping("/getCourseInfo")
    public Result getCourseInfo(String id){
            CourseInfoDto courseInfoDtoById = courseService.getCourseInfoDtoById(id);
            if(courseInfoDtoById != null){
                return  Result.ok().data("courseInfo",courseInfoDtoById);
            }else{
                return Result.error();
            }
        }
        //获取课程详细信息进行分页
    @PostMapping("/getCourseInfoDtoByPage")
    public Result getCourseInfoDtoByPage(int page, int size, @RequestBody(required = false) Map<String, Object> map){
        List<CourseInfoDto> courseInfoDtoByPage = courseService.getCourseInfoDtoByPage(page, size, map);
        if(courseInfoDtoByPage.size() > 0){
            return Result.ok().data("courseInfoList",courseInfoDtoByPage);
        }else {
            return Result.error();
        }
    }
    //修改审核状态
    @GetMapping("/audit")
    public Result audit(String id){
        boolean flag =  courseService.updateCourseStatus(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error();
        }
    }

}
