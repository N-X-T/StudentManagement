package com.nxt.ltw.controller;

import com.nxt.ltw.entity.CustomUserDetail;
import com.nxt.ltw.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @Autowired
    UserServiceImpl userService;
    @GetMapping(value = {"/","/login"})
    String login(Authentication auth){
        if(auth == null){
            return "login";
        } else {
            for(GrantedAuthority grantedAuthority: auth.getAuthorities()){
                if(grantedAuthority.getAuthority().equals("ROLE_USER")) {
                    return "redirect:/api/dashboard";
                } else if(grantedAuthority.getAuthority().equals("ROLE_ADMIN")){
                    return "redirect:/api/danhsachnhomlop";
                }
            }
        }
        return "login";
    }
    @GetMapping("/register")
    String registerForm(){
        return "register";
    }
    @PostMapping("/register")
    String register(@ModelAttribute("user") CustomUserDetail customUserDetail){
        userService.save(customUserDetail);
        return "redirect:/register?success";
    }
}
