package com.bbu.shenma.bean;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

@TableName("tb_order")
public class Order implements Serializable {
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    private String openid;//第三方用户支付标识
    private String outTradeNo;//订单号
    private Integer state;//订单的支付状态
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;//创建时间
    private Date notifyTime;//支付回调时间
    private Double totalFee;//支付的总金额
    private String type;//第三方支付类型
    private String courseId;//购买的课程id
    private String courseTitle;//购买的课程名
    private String courseImg;//购买课程的图片
    private String userId;//用户id
    private String username;//用户名
    @TableLogic
    private Integer del;//逻辑删除


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Date notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseImg() {
        return courseImg;
    }

    public void setCourseImg(String courseImg) {
        this.courseImg = courseImg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }
}
