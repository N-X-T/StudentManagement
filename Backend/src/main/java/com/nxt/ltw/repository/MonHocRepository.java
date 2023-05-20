package com.nxt.ltw.repository;

import com.nxt.ltw.entity.MonHoc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonHocRepository extends JpaRepository<MonHoc,Long> {
    MonHoc findByMamonhoc(String mamonhoc);
}
