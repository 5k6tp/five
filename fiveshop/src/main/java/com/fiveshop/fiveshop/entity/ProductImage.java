package com.fiveshop.fiveshop.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("product_images")
public class ProductImage {

    @TableId
    private Long id;

    @TableField("product_id")
    private Long productId;

    @TableField("image_url")
    private String imageUrl;

    public ProductImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
