package com.atguigu.test.readistest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;


/**
 * @author
 * @since 2018/12/13
 */


@RunWith(SpringRunner.class)
//@SpringJUnitWebConfig
public  class CodeGenerator {

    @Autowired
     RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void run() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("key","1");
        String key = ops.get("key");
        System.out.println(key);
    }
}
