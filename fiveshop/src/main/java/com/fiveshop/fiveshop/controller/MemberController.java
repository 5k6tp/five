package com.fiveshop.fiveshop.controller;

import com.fiveshop.fiveshop.service.MemberService; // 注意 'service' 是小写的
import com.fiveshop.fiveshop.service.RecipientsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fiveshop.fiveshop.common.Result;
import com.fiveshop.fiveshop.entity.Member;
import com.fiveshop.fiveshop.entity.Recipients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;




@RestController
@RequestMapping("/memberlist")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private RecipientsService recipientsService;

    @GetMapping("/list")
    public Result<Page<Member>> list(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "10") int currentSize,
            @RequestParam(required = false) String name) {

        Page<Member> page = new Page<>(currentPage, currentSize);
        LambdaQueryWrapper<Member> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // 只有當 name 不為 null 或空字符串時才添加查詢條件
        if (name != null && !name.isEmpty()) {
            lambdaQueryWrapper.like(Member::getName, name);
        }

        return Result.success(memberService.page(page, lambdaQueryWrapper));
    }

    @GetMapping("/get/{id}")
    public Result<Member> getmemberList(@PathVariable Long id) {
        // 直接根据主键 id 查询会员信息
        Member member = memberService.getById(id);
        return Result.success(member);
    }

    @PutMapping("/edit/{id}")
    public Result<String> editmemberList(@RequestBody Member member) {
        if(member != null){
            boolean edit = memberService.updateById(member);;
            if(edit){
                return Result.success("會員資料編輯成功");
            }else{
                return Result.fail("會員資料編輯失敗");
            }
        }else{
            return Result.fail("查無此會員！");
        }
    }

    @PostMapping("/recipients/add")
    public Result<String> addrecipients(@RequestBody Recipients recipients) {

        LambdaQueryWrapper<Recipients> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Recipients::getId, recipients.getId());
        Recipients one = recipientsService.getOne(lambdaQueryWrapper);
        if(one != null){
            boolean updateById = recipientsService.updateById(one);
            if(updateById){
                return Result.success("更新收件人成功!");
            }else{
                return Result.fail("操作錯誤!!");
            }
        }else{
            boolean save = recipientsService.save(recipients);
            if(save){
                return Result.success("新建收件人成功!");
            }else{
                return Result.fail("操作錯誤!!");
            }
        }
    }

    @GetMapping("/recipients/list")
    public Result<List<Recipients>> getrecipients() {
        List<Recipients> list = recipientsService.list();
        if(list != null){
            return Result.success(list);
        }else{
            return Result.fail("查詢失敗!!");
        }

    }
    
    
    
}
