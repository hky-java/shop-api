package com.shop.api;


import com.shop.api.mq.MQSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopApiApplicationTests {

    @Autowired
    private MQSender mqSender;


    @Test
    void contextLoads() {
    }

     @Test
    public void testSend(){
        mqSender.sendMail("我找您有事");
    }

}
