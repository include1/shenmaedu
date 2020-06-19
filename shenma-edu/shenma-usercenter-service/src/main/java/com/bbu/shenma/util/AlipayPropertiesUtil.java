package com.bbu.shenma.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@PropertySource("classpath:alipay.properties")
public class AlipayPropertiesUtil implements InitializingBean {

    @Value("${alipay_url}")
    private String  alipay_url;
    @Value("${app_id}")
    private String  app_id;
    @Value("${app_private_key}")
    private String  app_private_key;
    @Value("${alipay_public_key}")
    private String  alipay_public_key;

    @Value("${return_url}")
    private String  return_url;


    public static String ALIPAY_URL;
    public static String APP_ID;
    public static  String APP_PRIVATE_KEY;
    public static  String APP_PUBLIC_KEY;
    public static  String RETURN_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.ALIPAY_URL = alipay_url;
        this.APP_ID = app_id;
        this.APP_PRIVATE_KEY = app_private_key;
        this.APP_PUBLIC_KEY = alipay_public_key;
        this.RETURN_URL = return_url;
    }
}
