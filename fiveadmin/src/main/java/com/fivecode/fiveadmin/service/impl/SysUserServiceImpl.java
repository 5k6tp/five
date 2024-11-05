package com.fivecode.fiveadmin.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fivecode.fiveadmin.entity.SysUser;
import com.fivecode.fiveadmin.mapper.SysUserMapper;
import com.fivecode.fiveadmin.service.SysUserService;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
