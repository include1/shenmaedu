package com.bbu.shenma.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.Teacher;



import java.util.List;
import java.util.Map;

public interface TeacherService {

    public List<Teacher> findList();

    public void findListByPage(IPage<Teacher> iPage, Wrapper<Teacher> wrapper);

    public Teacher fingTeacherById(String id);

    public void getPageByCondition(IPage<Teacher> iPage,Map<String,Object> map);

    public void add(Teacher teacher);

    public void modify(Teacher teacher);

    public boolean removeById(String id);

    public boolean physicalDel(String id);

    boolean batchDelByIds(String[] selectionList);

    Map<String, Object> getFrontTeacherListPage(Page<Teacher> teacherPage);

    List<Course> getCourseListByTeacherId(String id);
}
