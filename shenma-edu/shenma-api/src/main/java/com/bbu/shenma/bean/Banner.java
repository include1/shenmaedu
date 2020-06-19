package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "tb_banner")
public class Banner implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String title;
    private String imgUrl;
    private Integer sort;//排序
    @TableField(fill = FieldFill.INSERT)
    private Date addTime;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }
}
