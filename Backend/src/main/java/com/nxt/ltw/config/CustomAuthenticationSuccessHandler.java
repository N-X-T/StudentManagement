package com.nxt.ltw.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Xử lý sau khi đăng nhập thành công
        response.setStatus(HttpStatus.OK.value());
        response.addHeader("Content-Type","application/json");
        response.getWriter().write("{\"status\":\"OK\",\"message\":\"Login successful\",\"data\":\"\"}");
    }
}