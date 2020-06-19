package com.bbu.shenma.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CourseInfoDto implements Serializable {
    private String id;

    private String name;

    private String coverImg;

    private BigDecimal price;

    private String description;

    private Integer viewNum;

    private Integer buyNum;

    private String learnTime;

    private Integer free;

    private String teacherName;

    private String oneCategory;

    private String twoCategory;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public String getLearnTime() {
        return learnTime;
    }

    public void setLearnTime(String learnTime) {
        this.learnTime = learnTime;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getOneCategory() {
        return oneCategory;
    }

    public void setOneCategory(String oneCategory) {
        this.oneCategory = oneCategory;
    }

    public String getTwoCategory() {
        return twoCategory;
    }

    public void setTwoCategory(String twoCategory) {
        this.twoCategory = twoCategory;
    }
}
