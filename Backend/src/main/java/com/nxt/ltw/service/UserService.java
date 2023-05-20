package com.nxt.ltw.service;

import com.nxt.ltw.entity.CustomUserDetail;
import com.nxt.ltw.entity.Feedback;
import com.nxt.ltw.entity.TimeTable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

public interface UserService {
    String findName(Authentication auth);
    void save(CustomUserDetail customUserDetail);
    CustomUserDetail findUser(Authentication auth);
    void update(CustomUserDetail customUserDetail,Authentication auth);
    List<TimeTable> findTimetable(Authentication auth);

    void handleTimeTable(String start, String end, Model model, Authentication auth);
    void handleLophanhchinh(Model model, Authentication auth);
    void handleLoptinchi(String kihoc, Model model, Authentication auth);
    void handleLoptinchiInfo(String nhommonhoc, Model model, Authentication auth);
    void handleGochoctap(Model model, Authentication auth);
    void handlePhanhoiForm(Model model, Authentication auth);
    void handlePhanhoi(Feedback feedback, Model model, Authentication auth);
}
