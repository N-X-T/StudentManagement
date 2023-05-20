package com.nxt.ltw.controller;

import com.nxt.ltw.dto.DiemThanhPhanDTO;
import com.nxt.ltw.entity.ResponseObject;
import com.nxt.ltw.service.Impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TeacherController {
    @Autowired
    AdminServiceImpl adminService;
    //Admin API
    @GetMapping("/api/danhsachnhomlop")
    ResponseEntity<ResponseObject> danhsachnhomlop(Model model, Authentication auth){
        return adminService.handleDanhsachnhomlop(model, auth);
    }
    @GetMapping("/api/quanlysinhvien/{nhommonhoc}")
    ResponseEntity<ResponseObject> quanlysinhvien(@PathVariable String nhommonhoc, Model model, Authentication auth){
        return adminService.handleQuanlysinhvien(nhommonhoc,model,auth);
    }
    /*@PostMapping("/api/themsinhvien/{nhommonhoc}")
    ResponseEntity<ResponseObject> themsinhvienForm(@PathVariable String nhommonhoc, Model model, Authentication auth){
        return adminService.handleThemsinhvienForm(nhommonhoc,model,auth);
    }*/
    @GetMapping("/api/themsinhvien")
    ResponseEntity<ResponseObject> themsinhvien(@RequestParam String nhommonhoc, @RequestParam String msv, Model model){
        return adminService.handleThemsinhvien(nhommonhoc, msv,model);
    }
    @GetMapping("/api/quanlydiem/{nhommonhoc}/{msv}")
    ResponseEntity<ResponseObject> quanlydiemForm(@PathVariable String nhommonhoc,@PathVariable String msv, Model model, Authentication auth){
        return adminService.handleQuanlydiemForm(nhommonhoc,msv,model,auth);
    }
    @PostMapping("/api/quanlydiem")
    ResponseEntity<ResponseObject> quanlydiem(@ModelAttribute("CapNhatDiem") DiemThanhPhanDTO diemThanhPhan, Model model, Authentication auth){
        return adminService.handleQuanlydiem(diemThanhPhan,model,auth);
    }
    @GetMapping("/api/xoasinhvien")
    ResponseEntity<ResponseObject> xoasinhvien(@RequestParam String nhommonhoc, @RequestParam String masv, Model model, Authentication auth){
        return adminService.handleXoasinhvien(nhommonhoc,masv,model,auth);
    }
    @GetMapping("/api/xuatbangdiem/{nhommonhoc}")
    public ResponseEntity<Resource> exportUsersToExcel(@PathVariable String nhommonhoc) {
        return adminService.exportDiemThanhPhanToExcel(nhommonhoc);
    }
    @PostMapping("/api/uploadExcel")
    public ResponseEntity<ResponseObject> uploadExcel(@RequestParam("excelFile") MultipartFile file, @RequestParam("nhommonhoc") String nhommonhoc, Model model) {
        return adminService.uploadExcel(file, nhommonhoc, model);
    }
}
