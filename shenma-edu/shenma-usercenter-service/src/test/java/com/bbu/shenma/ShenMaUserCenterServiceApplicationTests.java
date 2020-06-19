package com.bbu.shenma;

import com.bbu.shenma.util.RedisUtil;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShenMaUserCenterServiceApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test(){
        Jedis jedis = redisUtil.getJedis();
        jedis.set("shenma:user:123456",new Gson().toJson("dsfdfdsf"));
        jedis.expire("shenma:user:123456",3600);
        System.out.println(jedis);
    }
}
