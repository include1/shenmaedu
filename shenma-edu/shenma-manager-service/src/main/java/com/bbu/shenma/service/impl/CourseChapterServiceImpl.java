package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbu.shenma.bean.CourseChapter;
import com.bbu.shenma.bean.Video;
import com.bbu.shenma.mapper.CourseChapterMapper;
import com.bbu.shenma.service.CourseChapterService;
import com.bbu.shenma.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.SimpleTriggerContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseChapterServiceImpl implements CourseChapterService {
    @Autowired
    private CourseChapterMapper courseChapterMapper;
    @Autowired
    private VideoService videoService;

    @Override
    public List<CourseChapter> getAllCourseChapter() {
        //查询章节
        List<CourseChapter> courseChapters = courseChapterMapper.selectList(null);
        if (courseChapters.size() > 0) {
            for (int i = 0; i < courseChapters.size(); i++) {
                CourseChapter courseChapter = courseChapters.get(i);
                //通过章节id查询子章节
                List<Video> videoList = videoService.getVideoByChapterId(courseChapter.getId());
                //加入章节实体类中
                courseChapter.setVideoList(videoList);
            }
        }

        return courseChapters;
    }

    @Override
    public boolean addCourseChapter(CourseChapter courseChapter) {
        boolean flag = validate(courseChapter);
        if (flag) {
            int result = courseChapterMapper.insert(courseChapter);
            return result > 0;
        }
        return false;
    }
    //验证CourseChapter对象中的字段不能为空
    private boolean validate(CourseChapter courseChapter) {
        if(courseChapter != null) {
            if (StringUtils.isBlank(courseChapter.getCourseId()) || "".equals(courseChapter.getCourseId())) {
                return false;
            } else if (StringUtils.isBlank(courseChapter.getName()) || "".equals(courseChapter.getName())) {
                return false;
            } else if (courseChapter.getSort() == null) {
                return false;
            } else if (courseChapter.getFree() == null) {
                return false;
            }
            return true;
        }else{
            return false;
        }
    }
    //更新
    @Override
    public boolean modifyCourseChapter(CourseChapter courseChapter) {
        boolean flag = validate(courseChapter);
        if (flag && !StringUtils.isBlank(courseChapter.getId())) {
            int result = courseChapterMapper.updateById(courseChapter);
            return result > 0;
        }
        return false;
    }
    //删除
    @Override
    public boolean removeCourseChapterPrimaryKey(String id) {
        //判断是否有子章节
        List<Video> videoList = videoService.getVideoByChapterId(id);
        if(videoList.size() == 0){
            //删除章节操作
            int result = courseChapterMapper.deleteById(id);
            return result > 0;
        }
        return false;
    }

    /**
     * 通过课程id删除章节
     * @param courseId
     * @return
     */
    @Override
    public boolean removeCourseChapterByCourseId(String courseId) {
        int result = 0;
        QueryWrapper<CourseChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        //首先查询判断是否有章节，有则删除，没有则不执行
        List<CourseChapter> courseChapterList = courseChapterMapper.selectList(queryWrapper);
        if(courseChapterList.size() != 0) {
           result = courseChapterMapper.delete(queryWrapper);
        }
        return result > 0;
    }
    //通过主键查询课程信息
    @Override
    public CourseChapter getChapterById(String id) {
        if(!StringUtils.isBlank(id)){
            CourseChapter courseChapter = courseChapterMapper.selectById(id);
            return courseChapter;
        }
        return null;
    }

    @Override
    public List<CourseChapter> getChapterByCourseId(String courseId) {
        if(!StringUtils.isBlank(courseId)){
            QueryWrapper<CourseChapter> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id",courseId);
            List<CourseChapter> courseChapters = courseChapterMapper.selectList(queryWrapper);
            if (courseChapters.size() > 0) {
                for (int i = 0; i < courseChapters.size(); i++) {
                    CourseChapter courseChapter = courseChapters.get(i);
                    //通过章节id查询子章节
                    List<Video> videoList = videoService.getVideoByChapterId(courseChapter.getId());
                    //加入章节实体类中
                    courseChapter.setVideoList(videoList);
                }
            }
            return courseChapters;
        }
        return null;
    }
}
