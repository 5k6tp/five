package com.fiveshop.fiveshop.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.entity.Favorites;
import com.fiveshop.fiveshop.entity.Product;
import com.fiveshop.fiveshop.service.FavoritesService;
import com.fiveshop.fiveshop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RequestMapping("/favorites")
@RestController
@Slf4j
public class FavoritesController {

    @Autowired
    private FavoritesService favoritesService;

    @Autowired
    private ProductService productService;

    @GetMapping("/getlist/{id}")
    public Result<List<Favorites>> getfavoritesList(@PathVariable Long id){

        LambdaQueryWrapper<Favorites> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorites::getMemberId, id);
        List<Favorites> list = favoritesService.list(lambdaQueryWrapper);

        for(Favorites favorites : list){
            Product prod = productService.getById(favorites.getProductId());
            favorites.setProductName(prod.getName());
        }

        return Result.success(list);
    }

    @PostMapping("/addlist")
    public Result<String> addfavoritesList(@RequestBody Favorites favorites){

        favoritesService.save(favorites);

        return Result.success("加入收藏成功！");
        
    }
}
