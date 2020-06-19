package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.dto.CourseInfoDto;
import com.bbu.shenma.dto.FrontCourseDetailInfoDto;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface CourseService {
    public List<Course> getAllCourse();

    public void getPageByCondition(IPage<Course> iPage, Map<String,Object> map);

    public String addCourse(Course course);

    public String modifyCourse(Course course);

    public boolean removeCourse(String id);

    Course getCourseById(String id);

    public CourseInfoDto getCourseInfoDtoById(String id);

    public List<CourseInfoDto> getCourseInfoDtoByPage(int page,int size,Map<String,Object> map);

    boolean updateCourseStatus(String id);

    List<Course> getCourseListByTeacherId(String teacherId);

    Course getCourseByCategoryId(String id);

    Map<String, Object> getFrontCourseListPage(Page<Course> coursePage,Map<String,Object> searchMap);

    FrontCourseDetailInfoDto getFrontCourseDetailInfo(String id);
}
