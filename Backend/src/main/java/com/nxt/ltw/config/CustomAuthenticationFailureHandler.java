package com.nxt.ltw.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // Xử lý sau khi đăng nhập thất bại
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.addHeader("Content-Type","application/json");
        response.getWriter().write("{\"status\":\"Fail\",\"message\":\"Login failed\",\"data\":\"\"}");
    }
}
