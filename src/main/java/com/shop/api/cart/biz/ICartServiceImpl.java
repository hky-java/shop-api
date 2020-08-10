package com.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.cart.vo.Cart;
import com.shop.api.cart.vo.CartItem;
import com.shop.api.common.ResponseEnum;
import com.shop.api.common.ServerResponse;
import com.shop.api.common.SystemConstant;
import com.shop.api.product.mapper.IProductMapper;
import com.shop.api.product.po.Product;
import com.shop.api.util.BigDecimalUtil;
import com.shop.api.util.KeyUtil;
import com.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IProductMapper productMapper;


    @Override
    public ServerResponse addItem(Long memberId, Long goodsId, int num) {
        //判断商品是否存在
        Product product = productMapper.selectById(goodsId);
        if(null==product){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_NULL);
        }
        //商品的状态是否正常
        if(product.getStatus()== SystemConstant.PRODUCT_IS_DOWN){
            return ServerResponse.error(ResponseEnum.CART_PRODUCT_IS_DOWN);
        }

        //如果会员已经有了对应的购物车
        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        if(StringUtils.isNotEmpty(cartJson)){
        //直接向购物车放入商品

            Cart cart = JSONObject.parseObject(cartJson, Cart.class);
            List<CartItem> cartItemList = cart.getCartItemList();
            //在购物车中查找对应的商品
            CartItem cartItem = null;
            for (CartItem item : cartItemList) {
                if (item.getGoodsId().longValue() == goodsId.longValue()){
                    cartItem = item;
                    break;
                }
            }

            if(cartItem!=null){
            //如果商品存在更新商品的数量和小计，更新购物车【总个数，总计】
                //更新商品的数量
                cartItem.setNum(cartItem.getNum()+num);
                int num1 = cartItem.getNum();
                if(num1 <=0){
                    //删除整个商品
                    cartItemList.remove(cartItem);
                }
                BigDecimal subPrice = BigDecimalUtil.mul(num1 + "", cartItem.getPrice().toString());
                //更新小计
                cartItem.setSubPrice(subPrice);
                //更新购物车
                updateCart(memberId, cart);
            }else {
             //如果商品不存在，添加商品更新购物车【总个数，总计】
                if(num<=0){
                    return ServerResponse.error(ResponseEnum.CART_NUM_IS_ERROR);
                }
                //构建商品
                CartItem cartItemInfo = buildCartItem(num, product);
                //加入购物车
                  cart.getCartItemList().add(cartItemInfo);
                //更新购物车
                updateCart(memberId, cart);
            }


        }else{
            //如果没有对应的购物车
            Cart cart = new Cart();
            List<CartItem> cartItemList = cart.getCartItemList();

            //创建购物车再添加商品，更新购物车
            //构建商品
            CartItem cartItemInfo = buildCartItem(num, product);
            //加入购物车
            cart.getCartItemList().add(cartItemInfo);
            //更新购物车
            updateCart(memberId, cart);
        }

        return ServerResponse.success();
    }



    @Override
    public ServerResponse findItemList(Long memberId) {
        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        return ServerResponse.success(cart);
    }

    @Override
    public ServerResponse findNum(Long memberId) {
        String cartKey = KeyUtil.buildCartKey(memberId);
        String cartJson = RedisUtil.get(cartKey);
        if(StringUtils.isEmpty(cartJson)){
            return ServerResponse.error(ResponseEnum.CART_IS_NO);
        }
        // 转为java对象
        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        return ServerResponse.success(cart.getTotalNum());
    }









    private CartItem buildCartItem(int num, Product product) {
        CartItem cartItemInfo = new CartItem();
        cartItemInfo.setGoodsId(product.getId());
        cartItemInfo.setPrice(product.getPrice());
        cartItemInfo.setImageUrl(product.getMainImagePath());
        cartItemInfo.setGoodsName(product.getProductName());
        cartItemInfo.setNum(num);
        BigDecimal subPrice = BigDecimalUtil.mul(num + "", product.getPrice().toString());
        cartItemInfo.setSubPrice(subPrice);
        return cartItemInfo;
    }

    private void updateCart(Long memberId, Cart cart) {
        List<CartItem> cartItemList = cart.getCartItemList();
        int totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);
        String cartKey = KeyUtil.buildCartKey(memberId);
        if(cartItemList.size()==0){
            //删除整个购物车
            RedisUtil.delete(cartKey);
            return;
        }
        //更新购物车
        for (CartItem item : cartItemList) {
            totalCount += item.getNum();
            totalPrice = BigDecimalUtil.add(totalPrice.toString(), item.getSubPrice().toString());
        }

        cart.setTotalNum(totalCount);
        cart.setTotalPrice(totalPrice);
        //往redis中更新
        String cartNewJson = JSONObject.toJSONString(cart);

        RedisUtil.set(cartKey, cartNewJson);
    }
}
