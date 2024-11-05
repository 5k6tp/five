package com.fiveshop.fiveshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.dto.CartDTO;
import com.fiveshop.fiveshop.entity.Cart;
import com.fiveshop.fiveshop.entity.Product;
import com.fiveshop.fiveshop.service.CartService;
import com.fiveshop.fiveshop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public Result<String> addCart(@RequestBody Cart cart) {
        log.info("蠟筆小新 {}", cart);
        
        // 创建查询条件，查找相同商品
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getProductId, cart.getProductId());
        
        // 判断是否存在相同商品
        boolean exists = cartService.exists(queryWrapper);
        
        if (exists) {
            // 如果存在，更新数量
            Cart existingCart = cartService.getOne(queryWrapper);
            existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity()); // 增加数量
            
            boolean update = cartService.updateById(existingCart);
            if (update) {
                return Result.success("购物车数量更新成功！");
            } else {
                return Result.fail("购物车数量更新失败！");
            }
        } else {
            // 如果不存在，新增商品
            boolean save = cartService.save(cart);
            if (save) {
                return Result.success("加入购物车成功！");
            } else {
                return Result.fail("加入购物车失败！");
            }
        }
    }

    @SuppressWarnings("unused")
    @GetMapping("/list")
    public Result<List<CartDTO>> getCartList() {
        // 1. 獲取購物車中的所有商品ID
        LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cart::getMemberId, 11300000050L); // 替換為真實的會員ID
        List<Cart> cartList = cartService.list(lambdaQueryWrapper);
    
        // 2. 提取商品ID
        List<Long> productIds = cartList.stream()
                                         .map(Cart::getProductId)
                                         .collect(Collectors.toList());
    
        // 3. 根據商品ID查詢商品資料
        List<Product> products = productService.listByIds(productIds); // 確保productService有這個方法
    
        // 4. 構建CartItemDTO列表
        List<CartDTO> cartItemDTOList = new ArrayList<>();
        for (Cart cart : cartList) {
            Product product = products.stream()
                                      .filter(p -> p.getId().equals(cart.getProductId()))
                                      .findFirst()
                                      .orElse(null);
            
            if (product != null) {
                CartDTO cartItemDTO = new CartDTO();
                cartItemDTO.setProductId(cart.getProductId());
                cartItemDTO.setProductName(product.getName()); // 假設Product中有getName方法
                // cartItemDTO.setImageUrl(product.getImageUrl()); // 假設Product中有getImageUrl方法
                cartItemDTO.setQuantity(cart.getQuantity());
                cartItemDTO.setUnitPrice(cart.getUnitPrice());
                cartItemDTO.setTotalPrice(BigDecimal.valueOf(cart.getQuantity()).multiply(cart.getUnitPrice()));
                cartItemDTOList.add(cartItemDTO);
            }
        }
        if(cartItemDTOList != null){
            return Result.success(cartItemDTOList);
        }else{            return Result.fail("購物車目前是空的!@");
        }

    }

    @PutMapping("/update")
    public Result<String> updateCart(@RequestBody List<CartDTO> cartDTOs) {

        List<Cart> cartEntities = cartDTOs.stream().map(cartDTO -> {
            Cart cart = new Cart();
            LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Cart::getProductId, cartDTO.getProductId());
            Cart one = cartService.getOne(lambdaQueryWrapper);


            cart.setId(one.getId()); 
            cart.setProductId(cartDTO.getProductId());
            cart.setQuantity(cartDTO.getQuantity());
            cart.setUnitPrice(cartDTO.getUnitPrice());
            return cart;
        }).collect(Collectors.toList());


        @SuppressWarnings("unused")
        boolean updateBatchById = cartService.updateBatchById(cartEntities);
        log.info("更新資料 {}", cartEntities);

        return null;
    }


    @GetMapping("/{memId}")
    public Result<Long> getCartCount(@PathVariable Long memId) {

        LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Cart::getMemberId, memId);
        long count = cartService.count(lambdaQueryWrapper);
        return Result.success(count);
    }
    
    
}
