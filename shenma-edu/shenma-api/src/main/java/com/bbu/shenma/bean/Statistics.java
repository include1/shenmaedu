package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

//统计表
@TableName("tb_statistics")
public class Statistics implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String dateCalculated;//统计日期
    private Integer registerNum;//注册数
    private Integer loginNum;//登录数
    private Integer videoViewNum;//视频的观看数
    private Integer courseNum;//课程的增加数
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;//更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateCalculated() {
        return dateCalculated;
    }

    public void setDateCalculated(String dateCalculated) {
        this.dateCalculated = dateCalculated;
    }

    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Integer getVideoViewNum() {
        return videoViewNum;
    }

    public void setVideoViewNum(Integer videoViewNum) {
        this.videoViewNum = videoViewNum;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
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
}
