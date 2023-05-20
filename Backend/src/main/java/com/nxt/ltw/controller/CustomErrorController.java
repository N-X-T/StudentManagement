package com.nxt.ltw.controller;

import com.nxt.ltw.entity.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController{
    @RequestMapping("/error")
    public ResponseEntity<ResponseObject> error(HttpServletRequest request, HttpServletResponse response) {
        String status,message;
        switch (response.getStatus()){
            case 404: status="404 Not Found";message="Liên kết không tồn tại!";break;
            default: status="Error";message="";
        }
        return ResponseEntity.status(response.getStatus()).body(new ResponseObject(status,message,""));
    }

}
