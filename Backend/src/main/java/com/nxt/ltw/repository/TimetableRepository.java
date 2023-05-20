package com.nxt.ltw.repository;

import com.nxt.ltw.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimetableRepository extends JpaRepository<TimeTable,Long> {
    @Query(value = "SELECT * FROM TIME_TABLE WHERE NGAY between :start and :end AND USER_ID = :user_id", nativeQuery = true)
    List<TimeTable> findTabletimes(@Param("start") String start,@Param("end") String end ,@Param("user_id") Long user_id);
    @Query(value = "SELECT NHOMMONHOC,ID_MONHOC FROM TIME_TABLE WHERE ID_GIANGVIEN = :id_giangvien GROUP BY NHOMMONHOC,ID_MONHOC", nativeQuery = true)
    List<Object[]> findById_giangvien(@Param("id_giangvien")Long id_giangvien);
}
