package com.fiveshop.fiveshop.service.Impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fiveshop.fiveshop.entity.Recipients;
import com.fiveshop.fiveshop.mapper.RecipientsMapper;
import com.fiveshop.fiveshop.service.RecipientsService;

@Service
public class RecipientsServiceImpl extends ServiceImpl<RecipientsMapper, Recipients> implements RecipientsService {

}
