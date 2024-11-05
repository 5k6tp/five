package com.fiveshop.fiveshop.controller;

import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.entity.Layout;
import com.fiveshop.fiveshop.entity.LayoutImage;
import com.fiveshop.fiveshop.service.LayoutImageService;
import com.fiveshop.fiveshop.service.LayoutService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


@RestController
@Slf4j
@RequestMapping("/layout")
public class LayoutController {

    @Autowired
    private LayoutService layoutService;

    @Autowired
    private LayoutImageService layoutImageService;

    // 根据 ID 获取布局
    @GetMapping("/{id}")
    public Result<Layout> getLayoutById(@PathVariable Long id) {
        // 获取布局
        Layout lay = layoutService.getById(id);
        if (lay == null) {
            return Result.fail("獲取布局錯誤！");
        }

        // 根据布局 ID 获取相关的布局图片
        LambdaQueryWrapper<LayoutImage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LayoutImage::getLayoutId, id);
        List<LayoutImage> list = layoutImageService.list(lambdaQueryWrapper);

        // 设置布局的图片 URLs
        lay.setImageUrls(list);

        return Result.success(lay);
    }



    




    // 更新布局及其图片
    @PutMapping("/update/{id}")
    public Result<String> update(@PathVariable Long id, @RequestBody Layout layout) {
        layout.setId(id); // 设置 ID 以确保更新正确的记录
        log.info("更新布局，图片 URLs: {}", layout.getImageUrls());

        // 更新 Layout
        boolean updateLayout = layoutService.updateById(layout);
        if (!updateLayout) {
            return Result.fail("更新 Layout 失败！");
        }
   

        // 更新 LayoutImages
        boolean updateImages = layoutImageService.updateLayoutImages(id, layout.getImageUrls());
        if (!updateImages) {
            return Result.fail("更新 LayoutImage 失败！");
        }

        return Result.success("更新成功！");
    }

}
