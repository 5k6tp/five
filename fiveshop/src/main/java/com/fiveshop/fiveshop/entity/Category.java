package com.fiveshop.fiveshop.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Data
@TableName("category")
public class Category implements Serializable {
    @TableId
    private Long categoryId; // 分类ID

    @TableField("name")
    private String name; // 分类名称

    @TableField("parent_id")
    private Long parentId; // 父分类ID

    @TableField("status")
    private Integer status; // 状态，1 为有效，0 为无效

    @TableField("created_date")
    private LocalDateTime createdDate; // 创建时间
}