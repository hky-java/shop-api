package com.shop.api.category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.api.category.po.Category;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Mapper
@Resource
public interface CategoryMapper extends BaseMapper<Category> {
}
