package com.bbu.shenma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2Config显示网站中心api文档
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build();
    }

    private ApiInfo webApiInfo() {
        return  new ApiInfoBuilder()
                .title("神马教育网站-用户中心API文档")
                .description("本文档描述了后台管理微服务接口定义")
                .version("1.0")
                .contact(new Contact("zm","http://shenma.com","963371608@qq.com"))
                .build();
    }

}
