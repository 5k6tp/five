package com.fiveshop.fiveshop.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.Category;
import com.fiveshop.fiveshop.mapper.CategoryMapper;
import com.fiveshop.fiveshop.service.CategoryService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

}
