package com.fiveshop.fiveshop.service.Impl;

import com.fiveshop.fiveshop.entity.Product;
import com.fiveshop.fiveshop.entity.ProductImage;
import com.fiveshop.fiveshop.mapper.ProductImageMapper;
import com.fiveshop.fiveshop.mapper.ProductMapper;
import com.fiveshop.fiveshop.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    public void addProduct(Product product) {
        // 插入产品
        productMapper.insert(product);
        
        // 确认产品 ID 已经设置
        Long productId = product.getId();
        if (productId == null) {
            throw new RuntimeException("产品 ID 未能正确生成");
        }

        // 插入图片
        List<ProductImage> images = product.getImageUrls();
        if (images != null && !images.isEmpty()) {
            for (ProductImage image : images) {
                image.setProductId(productId); // 设置产品 ID
                productImageMapper.insert(image); // 插入图片
            }
        }

        

    }
}
