package com.fiveshop.fiveshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fiveshop.fiveshop.common.OrderNumberGenerator;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.dto.OrderDTO;
import com.fiveshop.fiveshop.entity.Cart;
import com.fiveshop.fiveshop.entity.Member;
import com.fiveshop.fiveshop.entity.OrderItem;
import com.fiveshop.fiveshop.entity.Orders;
import com.fiveshop.fiveshop.service.CartService;
import com.fiveshop.fiveshop.service.MemberService;
import com.fiveshop.fiveshop.service.OrderItemService;
import com.fiveshop.fiveshop.service.OrdersService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/order")
@Slf4j
public class OrdersController {
    
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/add")
    public Result<String> addOrder(@RequestBody OrderDTO orderDTO) {
        // log.info("收到的訂單信息: {}", orderDTO);
        List<OrderItem> products = orderDTO.getProducts();


        Orders orders = new Orders();
        BeanUtils.copyProperties(orderDTO, orders, "products");
        orders.setId(OrderNumberGenerator.generateOrderNumber());
        orders.setStatus(1);
        log.info("收到的訂單信息: {}", orders);
        boolean save = ordersService.save(orders);

        if(save){
            Long ordId = orders.getId();

            for(OrderItem item : products){
                item.setOrderId(ordId);
                BigDecimal unitPrice = item.getUnitPrice();
                BigDecimal quantity = BigDecimal.valueOf(item.getQuantity()); // 將 int 轉換為 BigDecimal
                BigDecimal totalPrice = unitPrice.multiply(quantity); // 使用 BigDecimal 進行乘法
                item.setTotalPrice(totalPrice);
                orderItemService.save(item);
            }

            LambdaQueryWrapper<Cart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            long memId = 11300000050L;            
            lambdaQueryWrapper.eq(Cart::getMemberId, memId);
            cartService.remove(lambdaQueryWrapper);
            return Result.success("訂單成立成功" + ordId);

        }else{
            return Result.fail("訂單成立失敗，請洽工程人員@");
        }

}

@GetMapping("/{orderId}")
public Result<OrderDTO> getOrderData(@PathVariable String orderId) {
    Orders orderData = ordersService.getById(orderId);

    OrderDTO orderDTO = new OrderDTO();
    BeanUtils.copyProperties(orderData, orderDTO);

    LambdaQueryWrapper<OrderItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(OrderItem::getOrderId, orderId);
    List<OrderItem> list = orderItemService.list(lambdaQueryWrapper);

    orderDTO.setProducts(list);

    return Result.success(orderDTO);
}

@GetMapping("/list")
public Result<List<HashMap<Object,Object>>> getOrderList() {

    List<Orders> list = ordersService.list();
    List<HashMap<Object,Object>> orderList = new ArrayList<>();

    for (Orders orders : list) {
        LambdaQueryWrapper<OrderItem> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderItem::getOrderId, orders.getId());
        List<OrderItem> products = orderItemService.list(lambdaQueryWrapper);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem prod : products) {
            totalPrice = totalPrice.add(prod.getTotalPrice());
        }

        Member member = memberService.getById(11300000005L); // 请确认成员ID是否正确，可能需要动态获取

        HashMap<Object,Object> orderMap = new HashMap<>();
        orderMap.put("id", orders.getId());
        orderMap.put("name", member.getName());
        orderMap.put("status", orders.getStatus());
        orderMap.put("total", totalPrice);
        orderMap.put("createdAt", orders.getCreatedAt());

        orderList.add(orderMap); // 将每个订单的 HashMap 添加到列表中
    }

    return Result.success(orderList); // 返回订单列表
}




}
