package com.nxt.ltw.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxt.ltw.entity.ResponseObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    ObjectMapper objectMapper;
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
        //return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/images/**","/login","/register","/assets/**").permitAll()

                                .requestMatchers(
                                        "/api/hoctap/**",
                                        "/api/quanlythuviensinhvien/**",
                                        "/api/tienichkhac/**",
                                        "/api/dashboard",
                                        "/api/account/center",
                                        "/api/account/center/edit",
                                        "/api/tintuc",
                                        "/api/dichvumotcuasv",
                                        "/api/congnosinhvien"
                                ).hasRole("USER")

                                .requestMatchers(
                                        "/api/uploadExcel",
                                        "/api/xuatbangdiem/**",
                                        "/api/danhsachnhomlop",
                                        "/api/quanlydiem/**",
                                        "/api/quanlysinhvien/**",
                                        "/api/themsinhvien/**",
                                        "/api/xoasinhvien"
                                ).hasRole("ADMIN")

                                .anyRequest().permitAll()
                ).formLogin()
                    .loginProcessingUrl("/api/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(authenticationSuccessHandler()) // Xử lý sau khi đăng nhập thành công
                    .failureHandler(authenticationFailureHandler()) // Xử lý sau khi đăng nhập thất bại
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/api/logout") // URL xử lý đăng xuất
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        return http.build();
    }
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(403);
                response.getWriter().write(objectMapper.writeValueAsString(new ResponseObject("403","Bạn không đủ quyền truy cập liên kết này!","")));
            }
        };
    }
}
