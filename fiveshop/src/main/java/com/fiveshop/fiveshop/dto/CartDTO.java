package com.fiveshop.fiveshop.dto;

import lombok.Data;

import java.math.BigDecimal;


@Data
public class CartDTO {
    private Long id;
    private Long productId; // 商品ID
    private String productName; // 商品名稱
    private String imageUrl; // 商品圖片URL
    private Integer quantity; // 購物車中此商品的數量
    private BigDecimal unitPrice; // 單價
    private BigDecimal totalPrice; // 總價
}
