package com.fivecode.fiveadmin.controller;


import org.springframework.beans.factory.annotation.Autowired;

import com.fivecode.fiveadmin.utils.RedisUtil;

import jakarta.servlet.http.HttpServletRequest;

public class BaseController {
    
    @Autowired
    HttpServletRequest req;

	@Autowired
	RedisUtil redisUtil;

}
