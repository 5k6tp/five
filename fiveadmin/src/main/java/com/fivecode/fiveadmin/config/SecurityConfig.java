package com.fivecode.fiveadmin.config;

import jakarta.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fivecode.fiveadmin.security.CaptchaFilter;
import com.fivecode.fiveadmin.security.LoginFailureHandler;
import com.fivecode.fiveadmin.security.LoginSuccessHandler;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig {

    // @Autowired
    // private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private CaptchaFilter captchaFilter;

    public static final String[] URL_WHITELIST = {
        "/webjars/**",
        "/favicon.ico",
        "/captcha",
        "/login", // 确保允许登录
        "/logout",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // 禁用 CSRF
            .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 启用 CORS 并使用统一配置
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(URL_WHITELIST).permitAll()  // 白名单 URL
                .anyRequest().authenticated())  // 其他请求需要认证
            .formLogin(form -> form
                .loginPage("/login") // 自定义 login 表单
                .successHandler(loginSuccessHandler)  // 成功处理
                .failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
                    response.getWriter().flush();
                })) // 失败处理，返回 JSON
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 无状态会话
            .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class);  // 添加验证码过滤器

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080"); // 允許的來源
        corsConfiguration.addAllowedHeader("*"); // 允許所有標頭
        corsConfiguration.addAllowedMethod("*"); // 允許所有方法
        corsConfiguration.setAllowCredentials(true); // 允許攜帶憑證

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
