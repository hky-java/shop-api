package com.shop.api.product.biz;

import com.shop.api.common.ServerResponse;
import com.shop.api.product.po.Product;

import java.util.List;

public interface ProductService {

    public ServerResponse findHotList();

    public List<Product> findStockList();

    ServerResponse findProductList();
}
