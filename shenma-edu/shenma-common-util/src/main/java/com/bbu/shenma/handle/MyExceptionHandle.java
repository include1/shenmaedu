package com.bbu.shenma.handle;

import com.bbu.shenma.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//自带的异常处理类
@ControllerAdvice
public class MyExceptionHandle {
    /**
     * 返回json格式字符串
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message(e.getMessage());
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e){
        e.printStackTrace();
        return Result.error().message(e.getMessage()).code(e.getCode());
    }
}
