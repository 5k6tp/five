package com.fiveshop.fiveshop.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("layout_images")
public class LayoutImage implements Serializable {
    private Long id;        // 主鍵
    private Long layoutId;  // 外鍵，關聯 Layout 的 id
    private String imageUrl; // 圖片 URL

    // 添加一个接受字符串参数的构造函数，用于 JSON 反序列化
    public LayoutImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // 默认构造函数
    public LayoutImage() {
    }

    // Getter 和 Setter 方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
