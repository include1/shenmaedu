package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.beans.Transient;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

@TableName(value = "tb_category")
public class Category implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String title;
    private String parentId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //Transient该注解修饰的变量不可以序列化（把文件，视频，等，转换成二级机制），进行网络传输
    @TableField(exist = false)
    private transient List<Category> subCategoryList;

    private transient  String courseName;

    private transient String courseId;


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Category> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<Category> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
