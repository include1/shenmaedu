package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName(value = "tb_course_chapter")
public class CourseChapter implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String courseId;
    private String name;
    private Integer sort;
    private Integer free;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    private Integer del;
    @TableField(exist = false)
    private transient List<Video> videoList;

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }


    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
}
