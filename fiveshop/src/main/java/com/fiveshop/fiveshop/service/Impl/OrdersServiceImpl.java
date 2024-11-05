package com.fiveshop.fiveshop.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.Orders;
import com.fiveshop.fiveshop.mapper.OrdersMapper;
import com.fiveshop.fiveshop.service.OrdersService;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService{

}
