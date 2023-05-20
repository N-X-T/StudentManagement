package com.nxt.ltw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {
    private Long id;
    private MonHoc monhoc;
    private String nhommonhoc;
    private String phonghoc;
    private Long id_giangvien;
    private String lop;
    private String ngay;

    public String getHTML() {
        return "Mã môn học: " + monhoc.getMamonhoc() +"<br>" +
                "Tên môn học: " + monhoc.getTenmonhoc() +"<br>" +
                "Số tín chỉ: " + monhoc.getSotinchi() +"<br>" +
                "Phòng học: " + phonghoc + "<br>" +
                "Lớp: " + lop + "<br>" +
                "Ngày: " + ngay;
    }
}
