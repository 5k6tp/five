package com.fiveshop.fiveshop.service;




import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveshop.fiveshop.entity.Product;


public interface ProductService extends IService<Product>{


    void addProduct(Product product);
}
