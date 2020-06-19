package com.bbu.shenma.dto;

import java.math.BigDecimal;

public class FrontCourseDetailInfoDto {
    private String courseId;
    private String courseName;
    private String coverImg;//课程封面
    private BigDecimal price;
    private Integer free;
    private Integer buyNum;
    private String learnTime;
    private Integer viewNum;//观看数
    private String teacherId;
    private String avatar;//讲师头像
    private String intro;
    private String teacherName;
    private String oneCategory;
    private String twoCategory;
    private String description;


    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public Integer getViewNum() {
        return viewNum;
    }

    public void setViewNum(Integer viewNum) {
        this.viewNum = viewNum;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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
