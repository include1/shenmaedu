package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.Teacher;
import com.bbu.shenma.client.StatisticsClient;
import com.bbu.shenma.dto.CourseInfoDto;
import com.bbu.shenma.dto.FrontCourseDetailInfoDto;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.mapper.CourseMapper;
import com.bbu.shenma.service.*;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseChapterService courseChapterService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private StatisticsClient statisticsClient;
    @Autowired
    private UserCourseService userCourseService;
    @Autowired
    private CourseCommentService courseCommentService;

    @Override
    public List<Course> getAllCourse() {
        return courseMapper.selectList(null);
    }

    @Override
    public void getPageByCondition(IPage<Course> iPage, Map<String, Object> map) {
        //过滤查询条件
        QueryWrapper<Course> queryWrapper = getQueryWrapper(map);
        //调用分页插件
        courseMapper.selectPage(iPage, queryWrapper);
    }

    private QueryWrapper<Course> getQueryWrapper(Map<String, Object> map) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        if (map != null) {
            if (map.get("name") != null && !"".equals(map.get("name"))) {
                courseQueryWrapper.like("name", map.get("name"));
            }
            if (map.get("teacherId") != null && !"".equals(map.get("teacherId"))) {
                courseQueryWrapper.eq("teacher_id", map.get("teacherId"));
            }
            if (map.get("categoryId") != null && !"".equals(map.get("categoryId"))) {
                courseQueryWrapper.eq("category_id", map.get("categoryId"));
            }
            if (map.get("categoryParentId") != null && !"".equals(map.get("categoryParentId"))) {
                courseQueryWrapper.eq("category_parent_id", map.get("categoryParentId"));
            }
        }
        return courseQueryWrapper;
    }

    /**
     * bean对象的属性判断
     *
     * @param course
     * @return
     */
    private boolean filedValidation(Course course) {
        boolean flag = true;
        if (StringUtils.isBlank(course.getName())) {
            flag = false;
        } else if (StringUtils.isBlank(course.getTeacherId())) {
            flag = false;
        } else if (StringUtils.isBlank(course.getCategoryId())) {
            flag = false;
        } else if (course.getPrice() == null) {
            flag = false;
        }
        return flag;
    }

    @Override
    public String addCourse(Course course) {
        if (filedValidation(course)) {
            try {
                int result = courseMapper.insert(course);
                if (result > 0) {
                    //更新添加课程的数量,统计服务要开启
                    statisticsClient.modifyStatisticsByCondition("course_num");
                    return course.getId();
                }
            } catch (Exception e) {
                throw new EduException(40000, "添加异常");
            }
        }
        return null;

    }

    @Override
    public String modifyCourse(Course course) {
        if (filedValidation(course) && StringUtils.isNotBlank(course.getId())) {
            try {
                int result = courseMapper.updateById(course);
                if (result > 0) {
                    return course.getId();
                }
            } catch (Exception e) {
                throw new EduException(40000, "更新异常");
            }
        }
        return null;
    }

    @Transactional
    @Override
    public boolean removeCourse(String id) {
        int result = 0;
        try {
            //首先删除子章节
            videoService.removeVideoByCourseId(id);
            //删除章节
            courseChapterService.removeCourseChapterByCourseId(id);
            //删除课程
            result = courseMapper.deleteById(id);

        } catch (Exception e) {
            e.printStackTrace();
            throw new EduException(40000, "删除失败");
        }

        return result > 0;
    }

    @Override
    public Course getCourseById(String id) {
        if (StringUtils.isNotBlank(id)) {
            Course course = courseMapper.selectById(id);
            return course;
        }
        return null;
    }

    @Override
    public CourseInfoDto getCourseInfoDtoById(String id) {
        if (!StringUtils.isBlank(id)) {
            CourseInfoDto courseInfoDtoById = courseMapper.getCourseInfoDtoById(id);
            return courseInfoDtoById;
        }
        return null;
    }

    @Override
    public List<CourseInfoDto> getCourseInfoDtoByPage(int page, int size, Map<String, Object> map) {
        page = (page - 1) * size;
        List<CourseInfoDto> courseInfoDtoByPage = courseMapper.getCourseInfoDtoByPage(page, size, map);
        return courseInfoDtoByPage;
    }

    //更新审核状态
    @Override
    public boolean updateCourseStatus(String id) {
        //通过id查询该课程
        Course course = courseMapper.selectById(id);
        if (course != null) {
            //更新审核状态
            course.setStatus(1);
            int result = courseMapper.updateById(course);
            return result > 0;
        }
        return false;
    }

    /**
     * 通过讲师id获取课程
     *
     * @return
     */
    @Override
    public List<Course> getCourseListByTeacherId(String teacherId) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", teacherId);
        queryWrapper.orderByDesc("update_time");
        //查询审核通过的课程
        queryWrapper.eq("status", 1);
        List<Course> courseList = courseMapper.selectList(queryWrapper);
        return courseList;
    }

    //根据二级分类id查询课程
    @Override
    public Course getCourseByCategoryId(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", id);
        //查询课程信息
        List<Course> courseList = courseMapper.selectList(queryWrapper);
        if(courseList.size() > 0){
            return courseList.get(0);
        }
        return null;
    }
//遍历records

    @Override
    public Map<String, Object> getFrontCourseListPage(Page<Course> coursePage,Map<String,Object> searchMap) {
        Map<String,Object> map = new HashMap<>();
        //获取查询添加
        QueryWrapper<Course> queryWrapper = this.getFrontQueryWrapper(searchMap);
        courseMapper.selectPage(coursePage, queryWrapper);
        //从teacherPage分页数据获取出来，放到map中
        if(coursePage == null){
            return null;
        }
        //防止空指针错误
        List<Course> records = coursePage.getRecords();
        for(Course course : records){
            //调用服务
            int studyNum = userCourseService.getCountByCourseId(course.getId());
            int commentNum = courseCommentService.getCountByCourseId(course.getId());
            course.setStudyNum(studyNum);
            course.setCommentNum(commentNum);
        }
        long total = coursePage.getTotal();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        long current = coursePage.getCurrent();
        boolean next = coursePage.hasNext();
        boolean previous = coursePage.hasPrevious();
        //放入map中
        map.put("courseList",records);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("current",current);
        map.put("next",next);
        map.put("previous",previous);
        return map;
    }

    private QueryWrapper<Course> getFrontQueryWrapper(Map<String, Object> searchMap) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        //必须是审核通过的
        queryWrapper.eq("status","1");
        if(searchMap != null){
            //通过课程名查询课程
            if(searchMap.get("courseName") != null &&!"".equals(searchMap.get("courseName"))){
              queryWrapper.like("name",searchMap.get("courseName"));
            }
            //按价格升序
            if(searchMap.get("ascPrice") != null && "ascPrice".equals(searchMap.get("ascPrice"))){
                queryWrapper.orderByAsc("price");
            }
            //按价格降序
            if(searchMap.get("descPrice") != null && "descPrice".equals(searchMap.get("descPrice"))){
                queryWrapper.orderByDesc("price");
            }
            //按日期降序,获取最新课程
            if(searchMap.get("updateTime") != null && "descTime".equals(searchMap.get("updateTime"))){
                queryWrapper.orderByDesc("update_time");
            }
            //通过类别Id过滤
            if(searchMap.get("categoryParentId") != null && !"".equals(searchMap.get("categoryParentId"))){
                queryWrapper.eq("category_parent_id",searchMap.get("categoryParentId"));
            }
        }
        return queryWrapper;
    }

    //获取前台课程详情
    @Override
    public FrontCourseDetailInfoDto getFrontCourseDetailInfo(String id) {
        FrontCourseDetailInfoDto frontCourseDetailInfoDto =  courseMapper.selectFrontCourseDetailInfo(id);
        //更新课程的浏览数
        if(frontCourseDetailInfoDto != null){
            Course courseById = this.getCourseById(id);
            courseById.setViewNum(courseById.getViewNum()+1);
            this.modifyCourse(courseById);
        }
        return frontCourseDetailInfoDto;
    }

}
