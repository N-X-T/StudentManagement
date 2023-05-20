package com.nxt.ltw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonHoc {
    private Long id;
    private String tenmonhoc;
    private String mamonhoc;
    private int sotinchi;
}
