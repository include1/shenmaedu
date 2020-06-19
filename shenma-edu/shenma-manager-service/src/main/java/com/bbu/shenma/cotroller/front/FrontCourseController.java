package com.bbu.shenma.cotroller.front;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.annotation.LoginAnnotation;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.CourseChapter;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.dto.FrontCourseDetailInfoDto;
import com.bbu.shenma.service.CourseChapterService;
import com.bbu.shenma.service.CourseCommentService;
import com.bbu.shenma.service.CourseService;
import com.bbu.shenma.service.UserCourseService;
import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shenma/frontCourse")
@CrossOrigin
public class FrontCourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseChapterService courseChapterService;
    //进行分页查询
    @PostMapping("/getCourseListPage")
    public Result getCourseListPage(int page, int size, @RequestBody(required = false) Map<String,Object> serarchMap){
        //使用分页插件,获取前台课程信息
        Page<Course> coursePage = new Page<>(page,size);
        Map<String,Object> map = courseService.getFrontCourseListPage(coursePage,serarchMap);
        return Result.ok().data(map);
    }

    //通过课程id获取课程详细信息
    //课程信息，包括课程+章节+讲师信息
    @GetMapping("/getCourseInfoByCourseId")
    public Result getCourseInfoByCourseId(String id){
        //获取课程信息+分类信息+讲师信息
          FrontCourseDetailInfoDto frontCourseDetailInfoDto = courseService.getFrontCourseDetailInfo(id);
        //获取章节信息+视频信息;
        final List<CourseChapter> chapterByCourseId = courseChapterService.getChapterByCourseId(id);
        //课程评论信息
        return Result.ok().data("courseInfo",frontCourseDetailInfoDto)
                .data("chapterInfo",chapterByCourseId);
    }

    //通过课程id获取课程信息
    @GetMapping("/getCourseById")
    public Result getCourseById(String id){
          Course course =  courseService.getCourseById(id);
          if(course != null){
              return Result.ok().data("course",course);
          }else {
              return Result.error();
          }
    }
}
