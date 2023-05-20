package com.nxt.ltw.repository;

import com.nxt.ltw.entity.CustomUserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<CustomUserDetail, Long> {
    CustomUserDetail findByUsername(String username);

    CustomUserDetail findByMasv(String masv);
    List<CustomUserDetail> findByLophanhchinh(String lophanhchinh);
}
