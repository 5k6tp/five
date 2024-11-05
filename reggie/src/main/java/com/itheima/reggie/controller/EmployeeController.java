package com.itheima.reggie.controller;

import jakarta.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;



@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest req, @RequestBody Employee employee) {
        
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(qw);

        if (emp == null) {
            log.error("Login failed: user not found");
            return R.error("登入失敗，查無此使用者！");
        }

        if (!emp.getPassword().equals(password)) {
            log.error("Login failed: incorrect password");
            return R.error("登入失敗，密碼錯誤！");
        }

        if (emp.getStatus() == 0) {
            log.error("Login failed: user is disabled");
            return R.error("登入失敗，此用戶已被禁用！");
        }

        req.getSession().setAttribute("employee", emp.getId());
        log.info("Login successful for user: {}", employee.getUsername());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest req){
        req.getSession().removeAttribute("employee");
        return R.success("登出成功！");
    }

    @PostMapping
    public R<String> save(HttpServletRequest req ,@RequestBody Employee employee){
        log.info("獲取到的資料 {}",employee.toString());

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        // employee.setCreateTime(LocalDateTime.now());
        // employee.setUpdateTime(LocalDateTime.now());
        // Long ByID = (Long) req.getSession().getAttribute("employee");
        // employee.setCreateUser(ByID);
        // employee.setUpdateUser(ByID);
        employeeService.save(employee);        
        return R.success("新增員工成功！");
    }
    

    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("Page = {}, PageSize = {}, name = {}", page, pageSize, name);
    
        // 使用参数化类型 Page<Employee>
        Page<Employee> pageinfo = new Page<>(page, pageSize);
    
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        
        // 如果 name 不为 null，则执行查询条件
        if (name != null && !name.isEmpty()) {
            qw.like(Employee::getName, name);
        }
        
        // 始终按更新时间降序排列
        qw.orderByDesc(Employee::getUpdateTime);
    
        // 分页查询
        employeeService.page(pageinfo, qw);
    
        // 返回带有分页结果的成功响应
        return R.success(pageinfo);
    }
    
    @PutMapping
    public R<String> update(HttpServletRequest req, @RequestBody Employee employee) {

        // Long empId = (Long)req.getSession().getAttribute("employee");
        
        // employee.setUpdateUser(empId);
        // employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        
        return R.success("員工訊息修改成功！");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);

        if(employee != null){
            return R.success(employee);
        }
            return R.error("查無員工訊息！");
    }
    
    
}

