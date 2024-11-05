package com.itheima.reggie.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal>{
    public void saveWithDish(SetmealDto setmealDto);

    //刪除套餐，同時需要刪除套餐和菜品的關聯數據
    public void removeWithDish(List<Long> ids);
}
