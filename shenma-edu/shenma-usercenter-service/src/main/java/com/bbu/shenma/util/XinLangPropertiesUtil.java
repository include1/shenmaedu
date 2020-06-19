package com.bbu.shenma.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
public class XinLangPropertiesUtil implements InitializingBean {
    @Value("${xl.open.app_id}")
    private String appId;
    @Value("${xl.open.app_secret}")
    private String appSecret;
    @Value("${xl.open.redirect_url}")
    private String redirectUrl;


    public static  String XL_OPEN_APP_ID;
    public static  String XL_OPEN_APP_SECRET;
    public static  String XL_OPEN_REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.XL_OPEN_APP_ID = appId;
        this.XL_OPEN_APP_SECRET = appSecret;
        this.XL_OPEN_REDIRECT_URL = redirectUrl;
    }
}
