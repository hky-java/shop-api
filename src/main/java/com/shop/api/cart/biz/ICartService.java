package com.shop.api.cart.biz;

import com.shop.api.common.ServerResponse;

public interface ICartService {

    public ServerResponse addItem(Long memberId, Long goodsId, int num);

    ServerResponse findItemList(Long memberId);

    ServerResponse findNum(Long memberId);
}
