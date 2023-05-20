package com.nxt.ltw.repository;

import com.nxt.ltw.entity.DiemThanhPhan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DiemThanhPhanRepository extends JpaRepository<DiemThanhPhan,Long> {
    @Query(value = "SELECT * FROM DIEM_THANH_PHAN WHERE USER_ID = :user_id", nativeQuery = true)
    List<DiemThanhPhan> findByUserid(@Param("user_id")Long user_id);
    @Query(value = "SELECT * FROM DIEM_THANH_PHAN WHERE KIHOC = :kihoc AND USER_ID = :user_id", nativeQuery = true)
    List<DiemThanhPhan> findByKihoc(@Param("kihoc")int kihoc,@Param("user_id")Long user_id);
    @Query(value = "SELECT * FROM DIEM_THANH_PHAN WHERE NHOMMONHOC = :nhommonhoc AND USER_ID = :user_id", nativeQuery = true)
    DiemThanhPhan capnhatDiem(@Param("nhommonhoc")String nhommonhoc,@Param("user_id")Long user_id);
    List<DiemThanhPhan> findByNhommonhoc(String nhommonhoc);
}
