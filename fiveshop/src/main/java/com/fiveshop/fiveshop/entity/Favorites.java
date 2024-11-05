package com.fiveshop.fiveshop.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("favorites")
public class Favorites {
    @TableId
    private Long id; // 自增主键

    private Long memberId; // 用户 ID
    private Long productId; // 商品 ID
    private String productName;
    private BigDecimal productPrice; // 商品价格
    private LocalDateTime createdAt; // 创建时间
}
