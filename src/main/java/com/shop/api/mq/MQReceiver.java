package com.shop.api.mq;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rabbitmq.client.Channel;
import com.shop.api.cart.vo.Cart;
import com.shop.api.cart.vo.CartItem;
import com.shop.api.config.MQConfig;
import com.shop.api.order.biz.IOrderService;
import com.shop.api.order.param.OrderParam;
import com.shop.api.product.mapper.IProductMapper;
import com.shop.api.product.po.Product;
import com.shop.api.util.KeyUtil;
import com.shop.api.util.RedisUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MQReceiver{

      @Resource(name = "OrderService")
      private IOrderService orderService;

      @Autowired
      private IProductMapper productMapper;
            //创建一个处理mail消息的方法，参数用来接收消息
            //注解为监听指定的队列
    @RabbitListener(queues = MQConfig.MAILQUEUE)
    public void handleMailMessage(String msg){
        System.out.println(msg);
  }

  //创建一个处理订单Order的消息的方法，参数用来接收消息
    //注解为监听指定的队列  Channel:通道
  @RabbitListener(queues = MQConfig.ORDERQUEUE)
    public void handleOrderMessage(String msg, Message message, Channel channel){
        //设置手动ack 【若不设置手动ack则rabbitmq默认自动ack】
      OrderParam orderParam = JSONObject.parseObject(msg, OrderParam.class);
      Long memberId = orderParam.getMemberId();
      //获取redis中的购物车信息
      String cartJson = RedisUtil.get(KeyUtil.buildCartKey(memberId));
      Cart cart = JSONObject.parseObject(cartJson, Cart.class);
      //之后购买的商品的数量和数据库中对应，发现库存不足进行提醒
      List<CartItem> cartItemList = cart.getCartItemList();
      //为获取id集合，若用老方法，则需要遍历集合并获取id值，后依次添加到一个新集合里
       //而此处我们利用jdk1.8的新特性，做如下，其中map代表转换的作用，x代表每一项，x.getGoodsLd()就代表我们要的值
      List<Long> goodIdList = cartItemList.stream().map(x -> x.getGoodsId()).collect(Collectors.toList());
      //根据id集合从数据库中查找对应的商品列表
      QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
      //如下其中id与数据库中商品表的字段id对应,,如下的in()类似于where id in（）
      //如下即获取与购物车中的商品相对应的商品表中的商品
      productQueryWrapper.in("id",goodIdList);
      List<Product> productList = productMapper.selectList(productQueryWrapper);
   //循环对比，看库存是否为空
      for (CartItem cartItem : cartItemList) {
          for (Product product : productList) {
              //如下的判断是为了确保同一商品进行判断是否超出库存
              if(cartItem.getGoodsId().longValue()==product.getId().longValue()){
                  if(cartItem.getNum()>product.getStock()){
                      //提醒库存不足  如下参数memberId是为了指明哪个商品 库存不足,第二个参数自定义
                      //buildStockLess为自定义
                      RedisUtil.set(KeyUtil.buildStockLess(memberId),"stock:less");
                      return; //即一旦有一种商品库存不足，则整个循环返回，订单无法生成
                  }
              }

          }
      }
      //创建订单   需要调用订单的service接口，且传参时是存有订单信息的OrderParam对像
    /*  orderService.createOrder(orderParam);*/
  }
}

