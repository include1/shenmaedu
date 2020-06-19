package com.bbu.shenma;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


//exclude = DataSourceAutoConfiguration.class排除数据库资源自动加载
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableEurekaClient
@EnableFeignClients//发现其他服务
public class ShenMaVideoServiceApplication {
    public  static void main(String [] args){
        SpringApplication.run(ShenMaVideoServiceApplication.class,args);
    }
}
