package com.shop.api.cart.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shop.api.util.BigDecimalJackson;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable {

    //总价
    @JsonSerialize(using = BigDecimalJackson.class)
    private BigDecimal totalPrice;
    //总数
    private int totalNum;
    //购物车里物品集合
    private List<CartItem> cartItemList = new ArrayList<>();

}
