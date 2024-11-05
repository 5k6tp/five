package com.fiveshop.fiveshop.entity;

import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import com.baomidou.mybatisplus.annotation.TableField;

@Data
public class Layout implements Serializable {
    private Long id;            // 主鍵

    @TableField("main_title")
    private String mainTitle;   // 主標題

    @TableField("subtitle")
    private String subtitle;     // 副標題

    @TableField("content")
    private String content;      // 文稿內容

    @TableField("status")
    private Integer status;      // 狀態

    @TableField("created_at")
    private LocalDateTime createdAt; // 創建時間

    @TableField("updated_at")
    private LocalDateTime updatedAt; // 更新時間

    @TableField(exist = false)
    private List<LayoutImage> layoutImages = new ArrayList<>();  // 图片 URL 列表

    // 这里不需要手动编写 getter 和 setter，@Data 注解已经生成了
    // 但是你需要修正 getImageUrls() 和 setImageUrls() 方法

    public List<LayoutImage> getImageUrls() {
        return layoutImages;  // 返回 layoutImages 而不是递归调用
    }

    public void setImageUrls(List<LayoutImage> imageUrls) {
        this.layoutImages = imageUrls;  // 正确设置 layoutImages
    }
}
