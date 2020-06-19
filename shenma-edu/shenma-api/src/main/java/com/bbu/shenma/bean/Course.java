package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName(value = "tb_course")
public class Course implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String name;
    private String teacherId;
    private String coverImg;
    private Integer status;
    private Integer free;
    private BigDecimal price;
    private String description;
    private String categoryId;
    private String categoryParentId;
    private String learnTime;
    private Integer buyNum;//购买数
    private Integer viewNum;//评论数
    private Integer recommend;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    private Integer del;
    private transient Integer commentNum;//评论人数
    private transient Integer studyNum;//学习人数

    public Integer getStudyNum() {
        return studyNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public String getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(String categoryParentId) {
        this.categoryParentId = categoryParentId;
    }
    //    @Override
//    public String toString() {
//        return "Course{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", teacherId='" + teacherId + '\'' +
//                ", coverImg='" + coverImg + '\'' +
//                ", status=" + status +
//                ", free=" + free +
//                ", price=" + price +
//                ", onsale=" + onsale +
//                ", description='" + description + '\'' +
//                ", recommend=" + recommend +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
//                ", del=" + del +
//                ", categoryId='" + categoryId + '\'' +
//                ", learnTime='" + learnTime + '\'' +
//                '}';
//    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }
}
