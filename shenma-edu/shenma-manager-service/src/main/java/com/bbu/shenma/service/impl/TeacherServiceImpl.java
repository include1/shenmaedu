package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bbu.shenma.bean.Course;
import com.bbu.shenma.bean.Teacher;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.mapper.TeacherMapper;
import com.bbu.shenma.service.CourseCommentService;
import com.bbu.shenma.service.CourseService;
import com.bbu.shenma.service.TeacherService;

import com.bbu.shenma.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserCourseServiceImpl userCourseService;
    @Autowired
    private CourseCommentService courseCommentService;

    @Override
    public List<Teacher> findList() {
        return teacherMapper.selectList(null);
    }

    /**
     * mybatis-自带的分页插件
     * @param iPage
     * @param wrapper
     */
    @Override
    public void findListByPage(IPage<Teacher> iPage, Wrapper<Teacher> wrapper) {

        teacherMapper.selectPage(iPage, wrapper);

    }

    @Override
    public Teacher fingTeacherById(String id) {

        return teacherMapper.selectById(id);
    }

    private QueryWrapper<Teacher> getQueryWrapper(Map<String, Object> searchMap) {
        if(searchMap == null){
            return  null;
        }else {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            if (searchMap.get("name") != null && !searchMap.get("name").equals("")) {
                queryWrapper.like("name", searchMap.get("name"));
            }
            if (searchMap.get("career") != null && !searchMap.get("career").equals("")) {
                queryWrapper.like("career", searchMap.get("career"));
            }
            if (searchMap.get("level") != null) {
                queryWrapper.eq("level", searchMap.get("level"));
            }
            return queryWrapper;
        }
    }

    /**
     * 通过条件进行分页查询
     * @param iPage
     * @param map
     */
    @Override
    public void getPageByCondition(IPage<Teacher> iPage, Map<String, Object> map) {
        QueryWrapper<Teacher> queryWrapper = getQueryWrapper(map);
        teacherMapper.selectPage(iPage,queryWrapper);
    }


    @Override
    public void add(Teacher teacher) {
        try {
            teacherMapper.insert(teacher);

        }catch (Exception e){
             e.printStackTrace();
             throw new EduException(40000,"添加失败");
        }

    }

    /**
     * 更新
     * @param teacher
     * @return
     */
    @Override
    public void modify(Teacher teacher) {

        try {
            teacherMapper.updateById(teacher);
        }catch (Exception e){
            e.printStackTrace();
            throw new EduException(40000,"更新失败");
        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    public boolean removeById(String id) {
        int count = teacherMapper.deleteById(id);
        return count > 0;
    }

    @Override
    public boolean physicalDel(String id) {

        int count = teacherMapper.deletePhysical(id);

        return count > 0;
    }

    /**
     * 批量删除
     * @param selectionList
     * @return
     */
    @Override
    public boolean batchDelByIds(String[] selectionList) {
        if(selectionList.length > 0) {
            int count = teacherMapper.deleteBatchIds(Arrays.asList(selectionList));
            return count > 0;
        }
        return false;
    }

    /**
     * 前台的teacher的分页列表
     * @param teacherPage
     * @return
     */
    @Override
    public Map<String, Object> getFrontTeacherListPage(Page<Teacher> teacherPage) {
        Map<String,Object> map = new HashMap<>();
        teacherMapper.selectPage(teacherPage, null);
        //从teacherPage分页数据获取出来，放到map中
        List<Teacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        long current = teacherPage.getCurrent();
        boolean next = teacherPage.hasNext();
        boolean previous = teacherPage.hasPrevious();
        //放入map中
        map.put("teacherList",records);
        map.put("total",total);
        map.put("size",size);
        map.put("pages",pages);
        map.put("current",current);
        map.put("next",next);
        map.put("previous",previous);
        return map;
    }

    /**
     * 根据讲师id查询所讲课程
     * @param id
     * @return
     */
    @Override
    public List<Course> getCourseListByTeacherId(String id) {
        List<Course> newList = new ArrayList<>();
        List<Course> courseList = courseService.getCourseListByTeacherId(id);
        //获取学习人数和评论人数
        for(Course course : courseList){
            int commentNum = courseCommentService.getCountByCourseId(course.getId());
            int studyNum = userCourseService.getCountByCourseId(course.getId());
            course.setCommentNum(commentNum);
            course.setStudyNum(studyNum);
            newList.add(course);
        }
        return  newList;
    }
}
