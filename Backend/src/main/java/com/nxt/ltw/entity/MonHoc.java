package com.nxt.ltw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonHoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "NVARCHAR(255)")
    private String tenmonhoc;
    private String mamonhoc;
    private int sotinchi;
//    @OneToOne(mappedBy = "monhoc")
//    private DiemThanhPhan diemThanhPhan;
//    @OneToOne(mappedBy = "monhoc")
//    private TimeTable timeTable;
}
