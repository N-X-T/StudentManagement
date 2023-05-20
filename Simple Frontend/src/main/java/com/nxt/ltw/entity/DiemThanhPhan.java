package com.nxt.ltw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiemThanhPhan {
    private Long id;
    private MonHoc monhoc;
    private String nhommonhoc;
    private int kihoc;
    private double diem_chuyencan,diem_bai_tap,diem_cuoi_ky,diem_thi_nghiem,diem_trung_binh_kiem_tra_tren_lop,diem_tong_ket;
    private boolean quamon;
}
