package com.shop.api.order.biz;


import com.alibaba.fastjson.JSONObject;
import com.shop.api.cart.vo.Cart;
import com.shop.api.cart.vo.CartItem;
import com.shop.api.common.ServerResponse;
import com.shop.api.config.MQConfig;
import com.shop.api.order.param.OrderParam;
import com.shop.api.product.mapper.IProductMapper;
import com.shop.api.util.KeyUtil;
import com.shop.api.util.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("OrderService")
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
     @Autowired
     private IProductMapper productMapper;
    @Override
    public ServerResponse generateOrder(OrderParam orderParam) {
        //将订单信息发送到消息队列  下面方法的第三个参数为要传递的消息即数据
        String orderParamJson = JSONObject.toJSONString(orderParam);
        rabbitTemplate.convertAndSend(MQConfig.ORDERCHANGE,MQConfig.ORDERQUEUE,orderParamJson);
        return ServerResponse.success();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void createOrder(OrderParam orderParam) {
        //根据orderParam获取会员id
        Long memberId = orderParam.getMemberId();
        //根据会员id从缓存中获取并转化为购物车对象
        String cartJson = RedisUtil.get(KeyUtil.buildCartKey(memberId));
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        List<CartItem> cartItemList = cart.getCartItemList();
       //减库存
        //一般情况下为update t_product set stock = stock - num where id = productId
        //考虑到并发，比如同时执行多次减库存的操作，则库存有可能变为负数因此如下
        //update t_product set stock = stock - num where id = productId  and stock>=num
        //上述为解决减库存的并发问题的方式即为数据库的乐观锁的一种方式
        for (CartItem cartItem : cartItemList) {
            Long goodsId = cartItem.getGoodsId();
            int num = cartItem.getNum();
            //接下来调用productMapper,如下利用mybatis方法为自定义  返回值rowCount指的是改变的行数
            //若修改的返回值为int类型，则默认为该返回值为改变的行数，即改变数据的条数
        int rowCount = productMapper.updateStock(goodsId,num);
        if(rowCount==0){
            //没有更新成功，库存不足  且方法为无返，而且需要完成的功能为，
            // 如购物车中前三个商品在数据库中的商品表可以完成更新，但到第四个更新失败，则前三个也应该回滚。
            // 即既要起到回滚的作用又要起到提示的作用。 。则做法如下
            //因此加事务注解@Transactional(rollbackFor=Exception.class)  该注解也可以写在类上
            //接下来在 中自定义异常

        }
        }
    }
}
