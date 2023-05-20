package com.nxt.ltw.dto;

import com.nxt.ltw.entity.CustomUserDetail;
import lombok.Getter;

@Getter
public class UserDetailDTO {
    private String hoten;
    private String masv;
    private String email;
    private boolean gioitinh;
    public UserDetailDTO(CustomUserDetail customUserDetail){
        this.hoten = customUserDetail.getHoten();
        this.masv = customUserDetail.getMasv();
        this.email = customUserDetail.getEmail();
        this.gioitinh = customUserDetail.isGioitinh();
    }
}
