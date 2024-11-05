package com.fiveshop.fiveshop.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.OrderItem;
import com.fiveshop.fiveshop.mapper.OrderItemMapper;
import com.fiveshop.fiveshop.service.OrderItemService;

@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService{

}
