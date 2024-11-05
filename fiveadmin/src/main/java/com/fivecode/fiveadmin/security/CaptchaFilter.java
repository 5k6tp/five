package com.fivecode.fiveadmin.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fivecode.fiveadmin.common.exception.CaptchaException;
import com.fivecode.fiveadmin.common.lang.Const;
import com.fivecode.fiveadmin.utils.RedisUtil;

import cn.hutool.json.JSONObject;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CaptchaFilter extends OncePerRequestFilter {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String requestBody = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Request Body: " + requestBody);
        String url = httpServletRequest.getRequestURI();

        if ("/login".equals(url) && httpServletRequest.getMethod().equals("POST")) {

            try {
                // 校驗驗證碼
                validate(httpServletRequest);
            } catch (CaptchaException e) {
                log.info("驗證碼驗證失敗: {} ", e.getMessage());  // 增加日志輸出
                // 交給認證失敗處理器，檢查這裡是否會重定向
                loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;  // 重要：當發生錯誤時，要避免繼續進行請求過濾
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    // 校验验证码逻辑
    private void validate(HttpServletRequest httpServletRequest) {

        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = httpServletRequest.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new CaptchaException("讀取請求體錯誤");
        }

        // 使用 Hutool 的 JSONObject 來解析請求體
        String requestBody = sb.toString();
        JSONObject json = new JSONObject(requestBody); // 解析 JSON 請求體
        String code = json.getStr("code"); // 獲取 "code" 的值
        String key = json.getStr("token"); // 獲取 "token" 的值

        if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {
            log.info("看這裡1 {} {}", code, key);
            throw new CaptchaException("验证码错误");
        }

        if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
            log.info("看這裡2 {} ");
            throw new CaptchaException("验证码错误");
        }

        // 一次性使用
        redisUtil.hdel(Const.CAPTCHA_KEY, key);
    }
}
