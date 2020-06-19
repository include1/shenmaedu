package com.bbu.shenma.client;

import com.bbu.shenma.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service",url = "http://localhost:8004/") //发现指定的服务
@Component // 防止其他地方调用client,idea报错
public interface UserClient {

    @GetMapping("/userservice/center/getRegisterNum")
    public Result getRegisterNum(@RequestParam("day") String day);
}
