package com.bbu.shenma.cotroller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.bbu.shenma.bean.CourseChapter;
import com.bbu.shenma.service.CourseChapterService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shenma/chapter")
@CrossOrigin
public class CourseChapterController {
    @Autowired
    private CourseChapterService courseChapterService;
    //获取所有章节信息+视频信息
    @GetMapping("/getList")
    public Result getList(){
        List<CourseChapter> courseChapterList = courseChapterService.getAllCourseChapter();
         return Result.ok().data("chapterList",courseChapterList);
    }
    //获取某个课程已经添加的课程章节
    @GetMapping("/getChaptersByCourseId")
    public Result getChaptersByCourseId(String id){
       List<CourseChapter> courseChapterList = courseChapterService.getChapterByCourseId(id);
        return Result.ok().data("chapterList",courseChapterList);
    }

    //增加课程章节
    @PostMapping("/addCourseChapter")
    public Result addCourseChapter(@RequestBody CourseChapter courseChapter){
        boolean flag = courseChapterService.addCourseChapter(courseChapter);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("添加失败");
        }
    }
    //通过id获取章节信息
    @GetMapping("/getChapterById")
    public Result getChapterById(String id){
        CourseChapter courseChapter = courseChapterService.getChapterById(id);
        return Result.ok().data("chapter",courseChapter);
    }
    //修改课程章节
    @PostMapping("/modifyCourseChapter")
    public Result modifyCourseChapter(@RequestBody CourseChapter courseChapter){
        boolean flag = courseChapterService.modifyCourseChapter(courseChapter);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("修改失败");
        }
    }
    //删除课程章节
    @GetMapping("/removeCourseChapter")
    public Result removeCourseChapter(String id){
        boolean flag = courseChapterService.removeCourseChapterPrimaryKey(id);
        if(flag){
            return Result.ok();
        }else{
            return Result.error().message("删除失败! 存在二级分类");
        }
    }

}
