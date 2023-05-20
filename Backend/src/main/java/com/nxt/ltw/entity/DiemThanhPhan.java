package com.nxt.ltw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiemThanhPhan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "id_monhoc")
    private MonHoc monhoc;
    private String nhommonhoc;
    private int kihoc;
    private double diem_chuyencan,diem_bai_tap,diem_cuoi_ky,diem_thi_nghiem,diem_trung_binh_kiem_tra_tren_lop,diem_tong_ket;
    private boolean quamon;

    @ManyToOne()
    @JoinColumn(name="USER_ID")
    private CustomUserDetail customUserDetail;
}
