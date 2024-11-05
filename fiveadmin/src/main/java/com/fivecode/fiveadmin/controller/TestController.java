package com.fivecode.fiveadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fivecode.fiveadmin.common.lang.Result;
import com.fivecode.fiveadmin.service.SysUserService;

@RestController
public class TestController {
   @Autowired
   SysUserService sysUserServiceuser;
   @GetMapping("/test")
   public Result test() {
      return Result.succ(sysUserServiceuser.list());
   }
}