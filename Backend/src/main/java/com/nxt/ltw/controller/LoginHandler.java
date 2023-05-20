package com.nxt.ltw.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        for(GrantedAuthority grantedAuthority:authentication.getAuthorities()){
            if(grantedAuthority.getAuthority().equals("ROLE_USER")) {
                redirectStrategy.sendRedirect(request,response,"/api/dashboard");
            } else if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                redirectStrategy.sendRedirect(request,response,"/api/danhsachnhomlop");
            }
        }
    }
}

