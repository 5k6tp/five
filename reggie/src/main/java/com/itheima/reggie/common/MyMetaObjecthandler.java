package com.itheima.reggie.common;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自動填充[insert]...");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 使用 Long.valueOf(1) 或 1L 代替 new Long(1)
        
        metaObject.setValue("createUser", BaseContext.getCurrentId()); // 或者直接使用 1L
        metaObject.setValue("updateUser", BaseContext.getCurrentId()); // 或者直接使用 1L
        // 移除以下异常抛出代码
        // throw new UnsupportedOperationException("Unimplemented method 'insertFill'");
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自動填充[update]...");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId()); // 或者直接使用 1L
        // 移除以下异常抛出代码
        // throw new UnsupportedOperationException("Unimplemented method 'updateFill'");
    }
    
}
