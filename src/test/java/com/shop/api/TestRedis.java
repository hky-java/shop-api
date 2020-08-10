package com.shop.api;

import com.shop.api.util.RedisUtil;
import org.junit.jupiter.api.Test;


public class TestRedis {
    @Test
    public void   test1(){
        RedisUtil.set("userName","wangwu");
        RedisUtil.delete("userName");
    }
    @Test
    public void testSendMsg(){

    }
}
