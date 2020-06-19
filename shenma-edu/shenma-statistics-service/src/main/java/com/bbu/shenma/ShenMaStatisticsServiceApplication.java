package com.bbu.shenma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //发现注册中心的其他服务
public class ShenMaStatisticsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShenMaStatisticsServiceApplication.class,args);
    }
}
