package com.example.learn;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {

    @Resource
    private ValueOperations<String, String> valueOperations;

    @Test
    public void testRedisTemplate() {
        valueOperations.set("test", "testValue1233333");
        String value = valueOperations.get("test");
        Assert.assertEquals(value, "testValue1233333");
    }
}
