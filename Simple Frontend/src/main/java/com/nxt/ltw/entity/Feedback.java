package com.nxt.ltw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    private long id;
    private String phongban;
    private String noidung;
    private String noidungphanhoi;
    private LocalDateTime ngaytao;
}
