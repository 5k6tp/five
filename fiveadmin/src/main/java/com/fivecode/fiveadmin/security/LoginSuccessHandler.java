package com.fivecode.fiveadmin.security;

import java.io.IOException;
import java.util.Date;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 生成 JWT
        String jwt = generateJwt(authentication);
        
        // 设置响应内容类型和响应头
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Authorization", jwt); // 在响应头中设置 JWT
        response.getWriter().write("{\"token\": \"" + jwt + "\", \"message\": \"Login successful\"}");
    }

    private String generateJwt(Authentication authentication) {
        // 获取用户名或其他信息
        String username = authentication.getName();

        // JWT 生成逻辑
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 天过期
            .signWith(SignatureAlgorithm.HS512, "your_secret_key") // 替换为你的密钥
            .compact();
    }
}



