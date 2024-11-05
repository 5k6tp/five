package com.fiveshop.fiveshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.entity.Category;
import com.fiveshop.fiveshop.service.CategoryService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping("/list")
    public Result<List<Category>> list(){

        List<Category> list = categoryService.list();
        return Result.success(list);
    }

    @PostMapping("/add")
    public Result<String> addCategory(@RequestBody Category category) {
        
        log.info("看這裡 {}", category);
        if(category.getName() == null){
            return Result.fail("名稱不能為空！");
        }
        
        LambdaQueryWrapper<Category> lq = new LambdaQueryWrapper<>();
        lq.eq(Category::getName, category.getName());
    
        // 检查是否已存在同名的分类
        if (categoryService.getOne(lq) != null) {
            return Result.fail("主分類名稱重複！");
        }
    
        // 尝试保存新的分类
        boolean save = categoryService.save(category);
        if (save) {
            return Result.success("新建主分類成功！");
        } else {
            return Result.fail("新增主分類失敗！");
        }
    }

    @GetMapping("/id")
    public Result<Category> getByid(@RequestParam Long categoryId) {

        Category byId = categoryService.getById(categoryId);
        return Result.success(byId);
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> delete(@PathVariable Long id){

        Object delete = categoryService.removeById(id);
        if((boolean) delete){
            return Result.success("刪除成功!");
        }else{

            return Result.fail("刪除失敗!");
        }

    }
    
    }
