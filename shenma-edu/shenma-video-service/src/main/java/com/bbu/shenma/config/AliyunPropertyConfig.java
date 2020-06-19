package com.bbu.shenma.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//配置为Spring容器的bean对象，使代码解耦
@Component
public class AliyunPropertyConfig implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecret}")
    private String keysecret;

    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.KEY_ID = keyid;
        this.KEY_SECRET = keysecret;
    }
}
