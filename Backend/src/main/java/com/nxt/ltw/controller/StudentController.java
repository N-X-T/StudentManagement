package com.nxt.ltw.controller;

import com.nxt.ltw.entity.CustomUserDetail;
import com.nxt.ltw.entity.Feedback;
import com.nxt.ltw.entity.ResponseObject;
import com.nxt.ltw.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    @Autowired
    UserServiceImpl userService;
    @GetMapping("/api/dashboard")
    ResponseEntity<ResponseObject> dashboard(@RequestParam(required = false) String start, @RequestParam(required = false) String end, Authentication auth) {
        return userService.handleTimeTable(start,end,auth);
    }
    @GetMapping("/api/hoctap/loptinchi")
    ResponseEntity<ResponseObject> loptinchi(@RequestParam(required = false) String kihoc, Authentication auth){
        return userService.handleLoptinchi(kihoc, auth);
    }
    @GetMapping("/api/hoctap/loptinchi/{nhommonhoc}")
    ResponseEntity<ResponseObject> loptinchiInfo(@PathVariable String nhommonhoc, Authentication auth){
        return userService.handleLoptinchiInfo(nhommonhoc, auth);
    }
    @GetMapping("/api/hoctap/lophanhchinh")
    ResponseEntity<ResponseObject> lophanhchinh(Model model, Authentication auth){
        return userService.handleLophanhchinh(model, auth);
    }
    @GetMapping("/api/hoctap/gochoctap")
    ResponseEntity<ResponseObject> gochoctap(Model model, Authentication auth){
        return userService.handleGochoctap(model, auth);
    }
    @GetMapping("/api/tienichkhac/phanhoi")
    ResponseEntity<ResponseObject> phanhoi(Model model, Authentication auth){
        return userService.handlePhanhoiForm(model, auth);
    }
    @PostMapping("/api/tienichkhac/phanhoi")
    ResponseEntity<ResponseObject> PostPhanhoi(@ModelAttribute("feedback") Feedback feedback, Model model, Authentication auth){
        return userService.handlePhanhoi(feedback, model, auth);
    }
    @GetMapping("/api/account/center")
    ResponseEntity<ResponseObject> accountcenter(Model model, Authentication auth){
        return userService.accountcenter(auth);
    }
    /*@GetMapping("/api/account/center/edit")
    String accountcentereditForm(Model model, Authentication auth){
        model.addAttribute("users", userService.findUser(auth));
        return "/user/accountcenter-edit";
    }*/
    @PostMapping("/api/account/center/edit")
    ResponseEntity<ResponseObject> accountcenteredit(@ModelAttribute("update-user") CustomUserDetail customUserDetail, Authentication auth){
        return userService.update(customUserDetail,auth);
    }

    //Đang phát triển :))
    @GetMapping("/api/tienichkhac/vanbanhuongdan")
    ResponseEntity<ResponseObject> vanbanhuongdan(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/tienichkhac/khaosat")
    ResponseEntity<ResponseObject> khaosat(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/tienichkhac/khaibaosuckhoe")
    ResponseEntity<ResponseObject> khaibaosuckhoe(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    /*@GetMapping("/api/tienichkhac/gioithieu")
    ResponseEntity<ResponseObject> gioithieu(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }*/
    @GetMapping("/api/tintuc")
    ResponseEntity<ResponseObject> tintuc(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/dichvumotcuasv")
    ResponseEntity<ResponseObject> dichvumotcua(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/congnosinhvien")
    ResponseEntity<ResponseObject> congnosinhvien(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/quanlythuviensinhvien/quanlyluanansinhvien")
    ResponseEntity<ResponseObject> quanlyluanansinhvien(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/quanlythuviensinhvien/quanlyluanvansinhvien")
    ResponseEntity<ResponseObject> quanlyluanvansinhvien(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
    @GetMapping("/api/quanlythuviensinhvien/quanlykhoaluansinhvien")
    ResponseEntity<ResponseObject> quanlykhoaluansinhvien(Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Chức năng đang phát triển",""));
    }
}
