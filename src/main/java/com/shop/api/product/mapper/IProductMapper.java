package com.shop.api.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.api.product.po.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface IProductMapper extends BaseMapper<Product> {
    //利用mybatis，sql语句要不在xml文件中，要不用注解如下
    @Update("update t_product set stock =stock - #{num} where id =#{goodsId} and stock >= #{num}")
    int updateStock(@Param("goodsId") Long goodsId,@Param("num") int num);
}
