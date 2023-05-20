package com.nxt.ltw.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_monhoc")
    private MonHoc monhoc;

    private String nhommonhoc;
    private String phonghoc;
    private Long id_giangvien;
    private String lop;
    @Column(columnDefinition = "DATETIME")
    private String ngay;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private CustomUserDetail customUserDetail;

    public String getHTML() {
        return "Mã môn học: " + monhoc.getMamonhoc() +"<br>" +
                "Tên môn học: " + monhoc.getTenmonhoc() +"<br>" +
                "Số tín chỉ: " + monhoc.getSotinchi() +"<br>" +
                "Phòng học: " + phonghoc + "<br>" +
                "Lớp: " + lop + "<br>" +
                "Ngày: " + ngay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TimeTable timeTable = (TimeTable) o;
        return getId() != null && Objects.equals(getId(), timeTable.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
