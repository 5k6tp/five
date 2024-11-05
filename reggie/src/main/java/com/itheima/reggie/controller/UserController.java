package com.itheima.reggie.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.service.UserService;
import com.itheima.reggie.utils.EmailSender;
import com.itheima.reggie.utils.ValidateCodeUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;



@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sendMail")
    public R<String> sendMail(@RequestBody User user,HttpSession session){
        //獲取信箱
        String phone = user.getPhone();

        if(io.micrometer.common.util.StringUtils.isNotEmpty(phone)){
        //生成驗證碼
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        //發送Mail
        EmailSender.sendEmail(phone, "信箱驗證碼測試", code);
        

        //將驗證存起來到Session
        session.setAttribute(phone, code);

        return R.success("驗證碼發送信箱成功");
        }


        return R.error("驗證碼發送信箱失敗");
    }

    @PostMapping("/login")
    public R<User> login(@SuppressWarnings("rawtypes") @RequestBody Map map, HttpSession session){

        log.info(map.toString());
    

        //Map獲取信箱
        String phone = map.get("phone").toString();

        //Map獲取驗證碼
        String code = map.get("code").toString();

        //從Session獲取驗證碼
        String sessionCode = session.getAttribute(phone).toString();

        //兩組驗證碼比對
        if (sessionCode != null && sessionCode.equals(code)) {
        //到資料庫查詢此信箱有沒有相同，沒有將資料建立在資料庫

        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getPhone, phone);
        User user = userService.getOne(qw);

        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setStatus(1);
            userService.save(user);
            
        }
        session.setAttribute("user", user.getId());
        return R.success(user);
            
        }

        return R.error("登入失敗");
        }


        @PostMapping("/loginout")
        public R<String> logout(HttpServletRequest req){
            req.getSession().removeAttribute("user");
            return R.success("登出成功！");
    }
}