package com.nxt.ltw.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String phongban;
    @Column(columnDefinition = "TEXT")
    private String noidung;
    @Column(columnDefinition = "TEXT")
    private String noidungphanhoi;
    @Column(columnDefinition = "DATE")
    private Date ngaytao;
    @ManyToOne()
    @JoinColumn(name="USER_ID")
    @JsonIgnore
    private CustomUserDetail customUserDetail;
}
