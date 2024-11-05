package com.fiveshop.fiveshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fiveshop.fiveshop.entity.Product;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {}
