package com.bbu.shenma.service.impl;

import com.bbu.shenma.service.RedisService;
import com.bbu.shenma.util.RedisUtil;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void saveToken(String key, Object value) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            Gson gson = new Gson();
            jedis.set(key,gson.toJson(value));
            jedis.expire(key,3600*4);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }

    }

    @Override
    public String getToken(String key) {
        Jedis jedis = null;
        String jsonStr = "";
        try {
            jedis = redisUtil.getJedis();
            jsonStr = jedis.get(key);
            if(StringUtils.isNotBlank(jsonStr)){
                jedis.set(key,jsonStr);
                jedis.expire(key,3600*4);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }
        return jsonStr;

    }

    @Override
    public void destoryToken(String key) {
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
            if(StringUtils.isNotBlank(jedis.get(key))){
                jedis.del(key);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            jedis.close();
        }

    }
}
