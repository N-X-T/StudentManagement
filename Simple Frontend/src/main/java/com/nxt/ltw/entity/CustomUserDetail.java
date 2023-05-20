package com.nxt.ltw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetail{
    private long id;
    private String hoten;
    private String email;
    private String sodienthoai;
    private String masv;
    private String ngaysinh;
    private boolean gioitinh;
    private String lophanhchinh;
    private String username;
    private String password;
}
