package com.fiveshop.fiveshop.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.ProductImage;
import com.fiveshop.fiveshop.mapper.ProductImageMapper;
import com.fiveshop.fiveshop.service.ProductImageService;

@Service
public class ProductImageServiceImpl extends ServiceImpl<ProductImageMapper, ProductImage> implements ProductImageService{

        @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    public List<ProductImage> findImagesByProductId(Long productId) {
        // 使用 QueryWrapper 根据 productId 查询图片
        QueryWrapper<ProductImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        return productImageMapper.selectList(queryWrapper);
    }
}
