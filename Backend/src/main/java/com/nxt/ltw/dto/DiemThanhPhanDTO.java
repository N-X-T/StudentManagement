package com.nxt.ltw.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiemThanhPhanDTO {
    private String nhommonhoc;
    private String msv;
    private double diem_chuyencan,diem_bai_tap,diem_cuoi_ky,diem_thi_nghiem,diem_trung_binh_kiem_tra_tren_lop,diem_tong_ket;
}
