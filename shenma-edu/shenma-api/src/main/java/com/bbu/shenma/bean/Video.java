package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "tb_video")
public class Video implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String title;
    private String time;
    private String chapterId;
    private String videoUrl;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private String coverImg;
    private String videoSourseId;
    private String courseId;

    private String videoOriginName;
    @TableLogic
    private String del;


    public String getVideoOriginName() {
        return videoOriginName;
    }

    public void setVideoOriginName(String videoOriginName) {
        this.videoOriginName = videoOriginName;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }
    public String getCoverImg() {
        return coverImg;
    }
    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getVideoSourseId() {
        return videoSourseId;
    }

    public void setVideoSourseId(String videoSourseId) {
        this.videoSourseId = videoSourseId;
    }
}
