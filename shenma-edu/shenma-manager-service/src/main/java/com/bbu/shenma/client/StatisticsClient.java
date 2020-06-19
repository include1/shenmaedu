package com.bbu.shenma.client;

import com.bbu.shenma.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "statistics-service",url = "http://localhost:8003")
@Component
public interface StatisticsClient {

    @PostMapping("/statistics/service/modifyStatisticsByCondition")
    public Result modifyStatisticsByCondition(@RequestParam("condition") String condition);
}
