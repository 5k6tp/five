package com.fiveshop.fiveshop.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.Favorites;
import com.fiveshop.fiveshop.mapper.FavoritesMapper;
import com.fiveshop.fiveshop.service.FavoritesService;

@Service
public class FavoritesServiceImpl extends ServiceImpl<FavoritesMapper, Favorites> implements FavoritesService{

}
