package com.shop.api.order.biz;

import com.shop.api.common.ServerResponse;
import com.shop.api.order.param.OrderParam;

public interface IOrderService {

    ServerResponse generateOrder(OrderParam orderParam);

    void createOrder(OrderParam orderParam);
}
