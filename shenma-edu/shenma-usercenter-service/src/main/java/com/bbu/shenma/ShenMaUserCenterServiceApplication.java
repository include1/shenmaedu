package com.bbu.shenma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients//发现其他服务
public class ShenMaUserCenterServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShenMaUserCenterServiceApplication.class,args);
    }
}
