package com.fiveshop.fiveshop.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.Cart;
import com.fiveshop.fiveshop.mapper.CartMapper;
import com.fiveshop.fiveshop.service.CartService;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService{

}
