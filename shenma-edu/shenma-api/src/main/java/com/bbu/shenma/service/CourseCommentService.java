package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.CourseComment;

import java.util.List;
import java.util.Map;

public interface CourseCommentService {
    //获取分页集合
    public void getCourseCommentListPage(IPage<CourseComment> iPage, Map<String,Object> searchMap);
    //通过课程id获取数量
    public int getCountByCourseId(String courseId);

    public boolean addCourseComment(CourseComment courseComment);

    public boolean modifyCourseComment(CourseComment courseComment);

    public boolean removeCourseCommentById(String id);

    Map<String, Object> findFrontCourseCommentList(Page<CourseComment> commentPage, Map<String, Object> searchMap);

    boolean addFrontCourseComment(CourseComment courseComment, String uId);

    Map<String, Object> getUserCommentInfo(int page,int pageSize,String uId);

    CourseComment getCourseCommentById(String id);
}
