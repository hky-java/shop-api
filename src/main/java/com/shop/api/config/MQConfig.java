package com.shop.api.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MQConfig {
    public static final String MAILEXCHANGE="mailExchange";
    public static final String MAILQUEUE="mailQueue";
    public static final String MAILROUTEREY="mail";

    public static final String ORDERCHANGE="orderExchange";
    public static final String ORDERQUEUE="orderQueue";
    public static final String ORDERROUTEREY="order";
    @Bean
    public DirectExchange orderExchange(){
        //下属参数的含义为是否持久化：是，是否自动删除：否
        return new DirectExchange(ORDERCHANGE,true,false);
    }
    @Bean //Queue:行列队列
    public Queue orderQueue(){
        return new Queue(ORDERQUEUE,true);
    }
    @Bean       //Binding:捆绑绑定    routingKey:路由kay,即该参数是指明路由key
    public Binding orderBinding(){
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(ORDERROUTEREY);
    }
     @Bean
     public DirectExchange mailExchange(){
         //下属参数的含义为是否持久化：是，是否自动删除：否
        return new DirectExchange(MAILEXCHANGE,true,false);
    }

    @Bean //Queue:行列队列
    public Queue mailQueue(){
         return new Queue(MAILQUEUE,true);
    }
    @Bean       //Binding:捆绑绑定    routingKey:路由kay,即该参数是指明路由key
    public Binding mailBinding(){
          return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MAILROUTEREY);
    }

}
