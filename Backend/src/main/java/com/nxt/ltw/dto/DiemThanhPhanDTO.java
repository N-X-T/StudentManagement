package com.nxt.ltw.dto;

public class DiemThanhPhanDTO {
    private String nhommonhoc;
    private String masv;
    private double diem_chuyencan,diem_bai_tap,diem_cuoi_ky,diem_thi_nghiem,diem_trung_binh_kiem_tra_tren_lop,diem_tong_ket;
    public DiemThanhPhanDTO(){}

    public DiemThanhPhanDTO(String nhommonhoc, String masv, double diem_chuyencan, double diem_bai_tap, double diem_cuoi_ky, double diem_thi_nghiem, double diem_trung_binh_kiem_tra_tren_lop, double diem_tong_ket) {
        this.nhommonhoc = nhommonhoc;
        this.masv = masv;
        this.diem_chuyencan = diem_chuyencan;
        this.diem_bai_tap = diem_bai_tap;
        this.diem_cuoi_ky = diem_cuoi_ky;
        this.diem_thi_nghiem = diem_thi_nghiem;
        this.diem_trung_binh_kiem_tra_tren_lop = diem_trung_binh_kiem_tra_tren_lop;
        this.diem_tong_ket = diem_tong_ket;
    }

    public String getNhommonhoc() {
        return nhommonhoc;
    }

    public void setNhommonhoc(String nhommonhoc) {
        this.nhommonhoc = nhommonhoc;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public double getDiem_chuyencan() {
        return diem_chuyencan;
    }

    public void setDiem_chuyencan(double diem_chuyencan) {
        this.diem_chuyencan = diem_chuyencan;
    }

    public double getDiem_bai_tap() {
        return diem_bai_tap;
    }

    public void setDiem_bai_tap(double diem_bai_tap) {
        this.diem_bai_tap = diem_bai_tap;
    }

    public double getDiem_cuoi_ky() {
        return diem_cuoi_ky;
    }

    public void setDiem_cuoi_ky(double diem_cuoi_ky) {
        this.diem_cuoi_ky = diem_cuoi_ky;
    }

    public double getDiem_thi_nghiem() {
        return diem_thi_nghiem;
    }

    public void setDiem_thi_nghiem(double diem_thi_nghiem) {
        this.diem_thi_nghiem = diem_thi_nghiem;
    }

    public double getDiem_trung_binh_kiem_tra_tren_lop() {
        return diem_trung_binh_kiem_tra_tren_lop;
    }

    public void setDiem_trung_binh_kiem_tra_tren_lop(double diem_trung_binh_kiem_tra_tren_lop) {
        this.diem_trung_binh_kiem_tra_tren_lop = diem_trung_binh_kiem_tra_tren_lop;
    }

    public double getDiem_tong_ket() {
        return diem_tong_ket;
    }

    public void setDiem_tong_ket(double diem_tong_ket) {
        this.diem_tong_ket = diem_tong_ket;
    }
}
