package com.fiveshop.fiveshop.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveshop.fiveshop.entity.ProductImage;

public interface ProductImageService extends IService<ProductImage>{

    List<ProductImage> findImagesByProductId(Long productId);

}
