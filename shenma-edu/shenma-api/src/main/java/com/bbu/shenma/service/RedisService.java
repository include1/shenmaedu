package com.bbu.shenma.service;

public interface RedisService {
    public void saveToken(String key,Object value);
    public String getToken(String key);
    public void destoryToken(String key);
}
