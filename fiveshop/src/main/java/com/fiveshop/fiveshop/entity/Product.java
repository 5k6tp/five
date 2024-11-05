package com.fiveshop.fiveshop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@TableName("products")
public class Product {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private String summary;
    private String description;
    private Long category; // 假设分类 ID 为 Long 类型
    private String specifications;
    private BigDecimal price;
    private Integer stock;

    @TableField("sale_start_time")
    private LocalDateTime saleStartTime;

    @TableField("sale_end_time")
    private LocalDateTime saleEndTime;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

    private Integer status;

    @TableField(exist = false)
    private List<ProductImage> imageUrls = new ArrayList<>(); // 初始化为一个空的列表

    // 修改后的 setImageUrls 方法，接收 List<ProductImage>
    public void setImageUrls(List<ProductImage> images) {
        if (images != null) {
            this.imageUrls.clear(); // 清空原有列表
            this.imageUrls.addAll(images); // 添加图片列表
        }
    }
}
