package com.fiveshop.fiveshop.controller;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.entity.Product;
import com.fiveshop.fiveshop.entity.ProductImage;
import com.fiveshop.fiveshop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import com.fiveshop.fiveshop.service.ProductImageService;



@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody Product product) {
        System.out.println("接收到的商品数据: " + product);
        productService.addProduct(product); // 调用 addProduct 方法
        return Result.success("新建商品成功！");
    }


    @GetMapping("/add/{id}")
    public Result<Product> getbyid(@PathVariable String id) {
        Product prod = productService.getById(id);
        return Result.success(prod);
    }

    @GetMapping("/list")
public Result<Page<Product>> list(
    @RequestParam(defaultValue = "1") int currentPage,
    @RequestParam(defaultValue = "10") int currentSize,
    @RequestParam(required = false) String searchName,
    @RequestParam(required = false) Integer statusFilter, // 修改此处为 Integer
    @RequestParam(required = false) LocalDateTime startDate,
    @RequestParam(required = false) LocalDateTime endDate
) {
    Page<Product> page = new Page<>(currentPage, currentSize);
    LambdaQueryWrapper<Product> lq = new LambdaQueryWrapper<>();
    
    if (searchName != null) {
        lq.like(Product::getName, searchName);
    }
    
    if (statusFilter != null) {
        lq.eq(Product::getStatus, statusFilter);
    }
    
    if (startDate != null && endDate != null) {
        lq.between(Product::getSaleStartTime, startDate, endDate);
    }

    Page<Product> allProducts = productService.page(page, lq);
    
    for (Product prod : allProducts.getRecords()) {
        List<ProductImage> images = productImageService.findImagesByProductId(prod.getId());
        prod.setImageUrls(images);
    }

    return Result.success(allProducts); // 返回包含图片的商品列表
}


@GetMapping("/productDetail/{id}")
public Result<Product> getProductDetail(@PathVariable Long id) {
    Product prod = productService.getById(id);
    LambdaQueryWrapper<ProductImage> lq = new LambdaQueryWrapper<>();
    lq.eq(ProductImage::getProductId, id);

    List<ProductImage> list = productImageService.list(lq);

    prod.setImageUrls(list);

    return Result.success(prod);
}

}
