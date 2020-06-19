package com.bbu.shenma.client;

import com.bbu.shenma.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
@Component
public interface UserClient {
    @GetMapping("/userservice/center/getUserById")
    public Result getUserById(@RequestParam String uId);
}
