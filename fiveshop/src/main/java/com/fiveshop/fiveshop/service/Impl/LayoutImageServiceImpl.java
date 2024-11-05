package com.fiveshop.fiveshop.service.Impl;

import com.fiveshop.fiveshop.entity.LayoutImage;
import com.fiveshop.fiveshop.mapper.LayoutImageMapper; // 确保有相应的 Mapper
import com.fiveshop.fiveshop.service.LayoutImageService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LayoutImageServiceImpl extends ServiceImpl<LayoutImageMapper, LayoutImage> implements LayoutImageService {

    @Override
    public boolean updateLayoutImages(Long layoutId, List<LayoutImage> layoutImages) {
        // 清空该 layoutId 相关的图片记录
        this.remove(new QueryWrapper<LayoutImage>().eq("layout_id", layoutId)); // 删除所有与 layoutId 关联的图片

        // 遍历并插入新的 LayoutImage 数据
        for (LayoutImage image : layoutImages) {
            image.setLayoutId(layoutId); // 设置关联的布局 ID
            this.save(image); // 保存新图片
        }

        return true; // 或者根据操作结果返回
    }
}
