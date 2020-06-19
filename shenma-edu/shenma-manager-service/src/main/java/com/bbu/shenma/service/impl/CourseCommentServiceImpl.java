package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.CourseComment;
import com.bbu.shenma.bean.User;
import com.bbu.shenma.client.UserClient;
import com.bbu.shenma.dto.UserCommentDetailDto;
import com.bbu.shenma.mapper.CourseCommentMapper;
import com.bbu.shenma.service.CourseCommentService;
import com.bbu.shenma.util.MyPageHelper;
import com.bbu.shenma.util.Result;
import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseCommentServiceImpl implements CourseCommentService {
    @Autowired
    private CourseCommentMapper courseCommentMapper;
    @Autowired
    private UserClient userClient;

    /**
     * 后台分页查询
     *
     * @param iPage
     * @param searchMap
     */
    @Override
    public void getCourseCommentListPage(IPage<CourseComment> iPage, Map<String, Object> searchMap) {

        QueryWrapper<CourseComment> queryWrapper = getQueryWrapper(searchMap);
        courseCommentMapper.selectPage(iPage, queryWrapper);
    }

    //判断
    private QueryWrapper<CourseComment> getQueryWrapper(Map<String, Object> searchMap) {
        QueryWrapper<CourseComment> queryWrapper = new QueryWrapper<>();
        if (searchMap != null) {
            if (StringUtils.isNotBlank((String) searchMap.get("content"))) {
                queryWrapper.like("content", (String) searchMap.get("content"));
            }
            if (StringUtils.isNotBlank((String) searchMap.get("courseId"))) {
                queryWrapper.eq("course_id", (String) searchMap.get("courseId"));
            }
            if (StringUtils.isNotBlank((String) searchMap.get("userId"))) {
                queryWrapper.eq("user_id", (String) searchMap.get("userId"));
            }
            if (StringUtils.isNotBlank((String) searchMap.get("username"))) {
                queryWrapper.like("username", (String) searchMap.get("username"));
            }
            if (searchMap.get("DescUp") != null && searchMap.get("DescUp").equals("DescUp")) {
                queryWrapper.orderByDesc("up");
            }
            //通过时间排序
            if (searchMap.get("DescTime") != null && searchMap.get("DescTime").equals("DescTime")) {
                queryWrapper.orderByDesc("create_time");
            }
            if (searchMap.get("AscTime") != null && searchMap.get("AscTime").equals("AscTime")) {
                queryWrapper.orderByAsc("create_time");
            }
        }
        return queryWrapper;
    }

    /**
     * 获取该课程的评论数
     *
     * @param courseId
     * @return
     */
    @Override
    public int getCountByCourseId(String courseId) {
        QueryWrapper<CourseComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = courseCommentMapper.selectCount(queryWrapper);
        return count;
    }

    /**
     * 添加
     *
     * @param courseComment
     * @return
     */
    @Override
    public boolean addCourseComment(CourseComment courseComment) {
        boolean flag = validateCourseComment(courseComment);
        if (flag) {
            courseCommentMapper.insert(courseComment);
        }
        return false;
    }

    private boolean validateCourseComment(CourseComment courseComment) {
        if (courseComment != null) {
            if (courseComment.getCourseId() == null || courseComment.getCourseId().equals("")) {
                return false;
            } else if (courseComment.getUserId() == null || courseComment.getUserId().equals("")) {
                return false;
            } else if (courseComment.getContent() == null || courseComment.getContent().equals("")) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * 更新
     *
     * @param courseComment
     * @return
     */
    @Override
    public boolean modifyCourseComment(CourseComment courseComment) {
        boolean flag = validateCourseComment(courseComment);
        if (flag) {
            int count = courseCommentMapper.updateById(courseComment);
            return count > 0;
        }
        return false;
    }

    //删除课程评论
    @Override
    public boolean removeCourseCommentById(String id) {
        if (StringUtils.isNotBlank(id)) {
            int count = courseCommentMapper.deleteById(id);
            return count > 0;
        }
        return false;
    }

    /**
     * 获取前台课程评论的集合
     *
     * @param commentPage
     * @param searchMap
     * @return
     */
    @Override
    public Map<String, Object> findFrontCourseCommentList(Page<CourseComment> commentPage, Map<String, Object> searchMap) {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<CourseComment> queryWrapper = this.getQueryWrapper(searchMap);
        courseCommentMapper.selectPage(commentPage, queryWrapper);
        //获取分页数据,并封装到map集合中
        List<CourseComment> records = commentPage.getRecords();
        long total = commentPage.getTotal();
        long current = commentPage.getCurrent();
        long size = commentPage.getSize();
        long pages = commentPage.getPages();
        boolean next = commentPage.hasNext();
        boolean previous = commentPage.hasPrevious();
        //放入map中
        map.put("courseCommentList", records);
        map.put("total", total);
        map.put("size", size);
        map.put("pages", pages);
        map.put("current", current);
        map.put("next", next);
        map.put("previous", previous);
        return map;
    }

    /**
     * 添加前台评论信息
     *
     * @param courseComment
     * @param uId
     * @return
     */
    @Override
    public boolean addFrontCourseComment(CourseComment courseComment, String uId) {
        //通过用户id获取用户信息
        Result result = userClient.getUserById(uId);
        Map<String, Object> data = result.getData();
        courseComment.setUserId((String) data.get("id"));
        courseComment.setUsername((String) data.get("userName"));
        courseComment.setHeadImg((String) data.get("headImg"));
        System.out.println(courseComment.getUsername());
        boolean b = this.validateCourseComment(courseComment);
        //进行添加操作
        if (b) {
            int insert = courseCommentMapper.insert(courseComment);
            return insert > 0;
        }
        return false;
    }

    // 获取前台用户的评论的详细信息
    @Override
    public Map<String, Object> getUserCommentInfo(int page,int pageSize,String uId) {
        Map<String,Object> map = new HashedMap();
        List<UserCommentDetailDto> list = courseCommentMapper.selectUserCommentInfo(uId);
        //自定义分页工具类
        MyPageHelper<UserCommentDetailDto> pageHelper = new MyPageHelper<>(list,page,pageSize);

        List<UserCommentDetailDto> records = pageHelper.getList();
        long total = pageHelper.getTotal();
        long size = pageHelper.getPageSize();
        long pages = pageHelper.getTotalPages();
        long current = pageHelper.getCurrentPage();
        boolean next = pageHelper.isHasNext();
        boolean previous = pageHelper.isHasPrevious();
        //放入map中
        map.put("commentList",records);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("current",current);
        map.put("next",next);
        map.put("previous",previous);
        return map;
    }
    //通过主键查询课程评论
    @Override
    public CourseComment getCourseCommentById(String id) {
        if (StringUtils.isNotBlank(id)) {
            CourseComment courseComment = courseCommentMapper.selectById(id);
            return courseComment;
        }
        return null;
    }
}
