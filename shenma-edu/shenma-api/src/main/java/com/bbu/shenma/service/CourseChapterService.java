package com.bbu.shenma.service;

import com.bbu.shenma.bean.CourseChapter;
import java.util.List;

public interface CourseChapterService {

    public List<CourseChapter> getAllCourseChapter();

    public boolean addCourseChapter(CourseChapter courseChapter);

    public boolean modifyCourseChapter(CourseChapter courseChapter);

    public boolean removeCourseChapterPrimaryKey(String id);

    public boolean removeCourseChapterByCourseId(String courseId);

    CourseChapter getChapterById(String id);

    List<CourseChapter> getChapterByCourseId(String id);
}
