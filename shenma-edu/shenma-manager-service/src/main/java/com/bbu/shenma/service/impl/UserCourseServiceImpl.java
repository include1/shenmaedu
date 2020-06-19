package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.UserCourse;
import com.bbu.shenma.mapper.UserCourseMapper;
import com.bbu.shenma.service.CourseService;
import com.bbu.shenma.service.UserCourseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    private UserCourseMapper userCourseMapper;
    @Autowired
    private CourseService courseService;

    //分页
    @Override
    public void getUserCourseListPage(IPage<UserCourse> iPage, Map<String, Object> searchMap) {
        QueryWrapper<UserCourse> queryWrapper = getQueryWrapper(searchMap);
        userCourseMapper.selectPage(iPage,queryWrapper);
    }
    //校验条件
    private QueryWrapper<UserCourse> getQueryWrapper(Map<String, Object> searchMap) {

        QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();

        if(searchMap != null){
            if(StringUtils.isNotBlank((String)searchMap.get("userId"))){
                queryWrapper.eq("user_id",(String)searchMap.get("userId"));
            }
            if(StringUtils.isNotBlank((String)searchMap.get("courseId"))){
                queryWrapper.eq("course_id",(String)searchMap.get("courseId"));
            }
            if(StringUtils.isNotBlank((String)searchMap.get("chapterId"))){
                queryWrapper.eq("chapter_id",(String)searchMap.get("chapterId"));
            }
        }
        return queryWrapper;
    }
    //通过课程id，获取课程的学习人数
    @Override
    public int getCountByCourseId(String courseId) {
        QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        Integer count = userCourseMapper.selectCount(queryWrapper);
        return count;
    }
    //添加
    @Transactional
    @Override
    public boolean addUserCourse(UserCourse userCourse) {
        //查询该课程是否已被添加过了，防止前台多次提交
        UserCourse userCourseByUserIdAndCourseId = getUserCourseByUserIdAndCourseId(userCourse);
        //未添加，则进行添加，否则不进行添加
        if(userCourseByUserIdAndCourseId == null){
            //验证用户课程
            boolean flag = validateUserCourse(userCourse);
            if(flag){
                int count = userCourseMapper.insert(userCourse);
                return count > 0;
            }
        }
        return false;
    }

    private boolean validateUserCourse(UserCourse userCourse) {
        if(userCourse != null){
            if(StringUtils.isBlank(userCourse.getUserId())){
                return false;
            }else if(StringUtils.isBlank(userCourse.getCourseId())){
                return  false;
            }
            return true;
        }
        return false;
    }

    //修改
    @Override
    public boolean modifyUserCourse(UserCourse userCourse) {
        boolean flag = validateUserCourse(userCourse);
        if(flag){
            int count = userCourseMapper.updateById(userCourse);
            return count > 0;
        }
        return false;
    }
    //逻辑删除
    @Override
    public boolean removeUserCourseById(String id) {
        return false;
    }
    //通过用户id获取用户课程
    @Override
    public Map<String, Object> getUserCourseByUId(Page<UserCourse> userCoursePage, String uId) {
        Map<String,Object> map = new HashedMap();
        //获取用户课程集合
        QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",uId);
        userCourseMapper.selectPage(userCoursePage,queryWrapper);
        //在查询课程详细信息
        List<UserCourse> records = userCoursePage.getRecords();

        List<Course> courseList = new ArrayList<>();
        for(UserCourse userCourse:records){
            //获取课程信息
            String courseId = userCourse.getCourseId();
            //调用课程服务
            Course courseById = courseService.getCourseById(courseId);
            courseList.add(courseById);
        }

        //获取分页等信息
        map.put("courseList",courseList);
        long total = userCoursePage.getTotal();
        long current = userCoursePage.getCurrent();
        long size = userCoursePage.getSize();
        long pages = userCoursePage.getPages();
        boolean next = userCoursePage.hasNext();
        boolean previous = userCoursePage.hasPrevious();

        map.put("userCourseList",records);
        map.put("total",total);
        map.put("size",size);
        map.put("current",current);
        map.put("pages",pages);
        map.put("next",next);
        map.put("previous",previous);

        return map;
    }
    //用户id+课程id，获取用户课程
    @Override
    public UserCourse getUserCourseByUserIdAndCourseId(UserCourse userCourse) {
        boolean b = this.validateUserCourse(userCourse);
        if(b){
            QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",userCourse.getUserId());
            queryWrapper.eq("course_id",userCourse.getCourseId());
            UserCourse userCourse1 = userCourseMapper.selectOne(queryWrapper);
            return userCourse1;
        }
        return null;
    }

    @Override
    public boolean removeUserCourse(String uId,String courseId) {
        if(StringUtils.isNotBlank(uId)&&StringUtils.isNotBlank(courseId)) {
            QueryWrapper<UserCourse> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",uId);
            queryWrapper.eq("course_id",courseId);
            queryWrapper.eq("del",0);
            //查询出来，更新
            UserCourse userCourse = userCourseMapper.selectOne(queryWrapper);
            if(userCourse != null) {
                int count = userCourseMapper.deleteById(userCourse.getId());
                return count > 0;
            }
        }
        return false;
    }
}
