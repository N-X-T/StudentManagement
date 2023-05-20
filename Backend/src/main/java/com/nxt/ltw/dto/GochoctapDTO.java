package com.nxt.ltw.dto;

import com.nxt.ltw.entity.DiemThanhPhan;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GochoctapDTO {
    private String tenmonhoc;
    private int sotinchi;
    private double diem_tong_ket;
    public GochoctapDTO(DiemThanhPhan diemThanhPhan){
        this.tenmonhoc=diemThanhPhan.getMonhoc().getTenmonhoc();
        this.sotinchi=diemThanhPhan.getMonhoc().getSotinchi();
        this.diem_tong_ket=diemThanhPhan.getDiem_tong_ket();
    }
}
