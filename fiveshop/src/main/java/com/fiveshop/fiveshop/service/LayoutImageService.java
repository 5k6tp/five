package com.fiveshop.fiveshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fiveshop.fiveshop.entity.LayoutImage;
import java.util.List;

public interface LayoutImageService extends IService<LayoutImage>{
    boolean updateLayoutImages(Long layoutId, List<LayoutImage> layoutImages);
}

