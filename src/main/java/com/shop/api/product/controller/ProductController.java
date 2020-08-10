package com.shop.api.product.controller;


import com.shop.api.annotation.Check;
import com.shop.api.common.ServerResponse;
import com.shop.api.product.biz.ProductService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Resource(name = "productService")
    private ProductService productService;

    @GetMapping("findHotList")
    @Check
    public ServerResponse findHotList(){
        return productService.findHotList();
    }

    @GetMapping("findProductList")
    public ServerResponse findProductList(){
        return productService.findProductList();
    }

}
