package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.UserCourse;


import java.util.List;
import java.util.Map;

public interface UserCourseService {
    //获取分页集合
    public void getUserCourseListPage(IPage<UserCourse> iPage, Map<String,Object> searchMap);
    //通过课程id获取数量
    public int getCountByCourseId(String courseId);

    public boolean addUserCourse(UserCourse userCourse);

    public boolean modifyUserCourse(UserCourse userCourse);

    public boolean removeUserCourseById(String id);

    Map<String, Object> getUserCourseByUId(Page<UserCourse> userCoursePage, String uId);

    UserCourse getUserCourseByUserIdAndCourseId(UserCourse userCourse);

    boolean removeUserCourse(String uId,String courseId);
}
