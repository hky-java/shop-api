package com.shop.api.brand.biz;

import com.shop.api.brand.po.Brand;
import com.shop.api.common.ServerResponse;

import java.util.List;

public interface IBrandService {

    public ServerResponse addBrand(Brand brand);


    ServerResponse findList();

    ServerResponse delete(Integer id);

    ServerResponse update(Brand brand);

    ServerResponse deleteBatch(String ids);
}
