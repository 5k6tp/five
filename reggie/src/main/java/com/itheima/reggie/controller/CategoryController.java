package com.itheima.reggie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;




@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService CategoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category: {}", category);
        CategoryService.save(category);
        return R.success("新增分類成功");
    }

    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize) {
    
        // 使用参数化类型 Page<Category>
        Page<Category> pageinfo = new Page<>(page, pageSize);
    
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();

        
        // 始终按更新时间降序排列
        qw.orderByAsc(Category::getSort);
    
        // 分页查询
        CategoryService.page(pageinfo, qw);
    
        // 返回带有分页结果的成功响应
        return R.success(pageinfo);
    }

    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("刪除分類 {}",ids);
        
        CategoryService.remove(ids);
        return R.success("移除分類成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分類訊息 {}",category);
        CategoryService.updateById(category);
        return R.success("修改分類成功");
}



    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = CategoryService.list(queryWrapper);
        return R.success(list);
    }

    
}  