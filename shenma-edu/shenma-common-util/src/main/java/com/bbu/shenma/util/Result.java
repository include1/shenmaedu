package com.bbu.shenma.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Result implements Serializable {
    private Integer code;//0表示成功，1表示不成功
    private String message;
    private boolean success;
    private Map<String,Object> data = new HashMap<>();

    public static Result ok(){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS.getValue());
        result.setMessage("操作成功");
        result.setSuccess(true);
        return  result;
    }
    public static Result error(){
        Result result = new Result();
        result.setCode(ResultCode.ERROR.getValue());
        result.setMessage("操作失败");
        result.setSuccess(false);
        return  result;
    }

    public  Result message(String message){
        this.message = message;
        return this;
    }

    public  Result code(Integer code){
        this.code = code;
        return this;
    }
    //方法重载
    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }

    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
