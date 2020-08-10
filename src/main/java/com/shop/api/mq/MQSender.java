package com.shop.api.mq;

import com.shop.api.config.MQConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component //该注解为进行实例化*/
public class MQSender {
    @Autowired   //引入AmqpTemplate类
    private AmqpTemplate  amqpTemplate;
    public void  sendMail(String info){
        amqpTemplate.convertAndSend(MQConfig.MAILEXCHANGE,MQConfig.MAILROUTEREY,info);//该方法来自AmqpTemplate类
    }
}
