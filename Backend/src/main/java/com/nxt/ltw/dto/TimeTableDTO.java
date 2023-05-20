package com.nxt.ltw.dto;

import com.nxt.ltw.entity.TimeTable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeTableDTO {
    private String mamonhoc;
    private String tenmonhoc;
    private int sotinchi;
    private String phonghoc;
    private String lop;
    private String ngay;
    private String tengiangvien;
    public TimeTableDTO(TimeTable timeTable){
        this.mamonhoc = timeTable.getMonhoc().getMamonhoc();
        this.tenmonhoc = timeTable.getMonhoc().getTenmonhoc();
        this.sotinchi = timeTable.getMonhoc().getSotinchi();
        this.phonghoc = timeTable.getPhonghoc();
        this.lop = timeTable.getLop();
        this.ngay = timeTable.getNgay();
    }
}
