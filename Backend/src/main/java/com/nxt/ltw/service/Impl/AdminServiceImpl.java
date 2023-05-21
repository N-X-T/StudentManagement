package com.nxt.ltw.service.Impl;

import com.nxt.ltw.dto.DiemThanhPhanDTO;
import com.nxt.ltw.dto.UserDetailDTO;
import com.nxt.ltw.entity.CustomUserDetail;
import com.nxt.ltw.entity.DiemThanhPhan;
import com.nxt.ltw.entity.MonHoc;
import com.nxt.ltw.entity.ResponseObject;
import com.nxt.ltw.repository.DiemThanhPhanRepository;
import com.nxt.ltw.repository.MonHocRepository;
import com.nxt.ltw.repository.TimetableRepository;
import com.nxt.ltw.repository.UserRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl{
    @Autowired
    UserRepository userRepository;
    @Autowired
    MonHocRepository monHocRepository;
    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    DiemThanhPhanRepository diemThanhPhanRepository;

    public ResponseEntity<ResponseObject> handleDanhsachnhomlop(Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        List<Object[]> nhommonhoc=timetableRepository.findById_giangvien(current.getId());
        List<HashMap<String,Object>> data = new ArrayList<>();
        for(int i=0;i<nhommonhoc.size();i++) {
            HashMap<String,Object> tmp = new HashMap<>();
            tmp.put("nhommonhoc", nhommonhoc.get(i)[0]);
            tmp.put("tenmonhoc", monHocRepository.findById((Long) nhommonhoc.get(i)[1]).get().getTenmonhoc());
            data.add(tmp);
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query Danhsachnhomlop successfully",data));
    }
    public ResponseEntity<ResponseObject> handleQuanlysinhvien(String nhommonhoc,Model model,Authentication auth){
        List<UserDetailDTO> userDetailDTOS=diemThanhPhanRepository.findByNhommonhoc(nhommonhoc).stream().map(
                diemThanhPhan -> {
                    return new UserDetailDTO(diemThanhPhan.getCustomUserDetail());
                }
        ).collect(Collectors.toList());

        //HashMap<String,Object> data = new HashMap<>();
        //data.put("nhommonhoc",nhommonhoc);
        //data.put("users",userDetailDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query Quanlysinhvien successfully",userDetailDTOS));
    }
    /*public ResponseEntity<ResponseObject> handleThemsinhvienForm(String nhommonhoc, Model model, Authentication auth){
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query Quanlysinhvien successfully",nhommonhoc));

    }*/
    public ResponseEntity<ResponseObject> handleThemsinhvien(String nhommonhoc, String msv, Model model){
        CustomUserDetail sinhvien=userRepository.findByMasv(msv);
        if(sinhvien != null){
            List<DiemThanhPhan> nhomhientai= diemThanhPhanRepository.findByNhommonhoc(nhommonhoc);
            for(DiemThanhPhan d:nhomhientai){
                if(d.getCustomUserDetail().getId()==sinhvien.getId())
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail","Students already exist in the classroom",""));
            }

            DiemThanhPhan diemThanhPhan=new DiemThanhPhan();

            try{
                diemThanhPhan.setMonhoc(nhomhientai.get(0).getMonhoc());
            }catch(Exception ex){
                String mamonhoc = nhommonhoc.substring(0,nhommonhoc.indexOf("-"));
                MonHoc monHoc = monHocRepository.findByMamonhoc(mamonhoc);
                diemThanhPhan.setMonhoc(monHoc);
            }

            diemThanhPhan.setNhommonhoc(nhommonhoc);

            try{
                diemThanhPhan.setKihoc(nhomhientai.get(0).getKihoc());
            }catch(Exception ex) {
                //diemThanhPhan.setKihoc(null);
            }
            diemThanhPhan.setDiem_chuyencan(0);
            diemThanhPhan.setDiem_bai_tap(0);
            diemThanhPhan.setDiem_cuoi_ky(0);
            diemThanhPhan.setDiem_thi_nghiem(0);
            diemThanhPhan.setDiem_trung_binh_kiem_tra_tren_lop(0);
            diemThanhPhan.setDiem_tong_ket(0);
            diemThanhPhan.setQuamon(false);
            diemThanhPhan.setCustomUserDetail(sinhvien);

            diemThanhPhanRepository.save(diemThanhPhan);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Add student successfully",""));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail","Student not found!",""));
        }
    }
    public ResponseEntity<ResponseObject> handleQuanlydiemForm(String nhommonhoc, String msv, Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByMasv(msv);
        DiemThanhPhan diemthanhphan = diemThanhPhanRepository.capnhatDiem(nhommonhoc,current.getId());

        model.addAttribute("diemthanhphan",diemthanhphan);

        HashMap<String,Object> data = new HashMap<>();
        data.put("hoten",current.getHoten());
        data.put("msv",current.getMasv());
        data.put("nhommonhoc",nhommonhoc);
        data.put("diem_chuyencan",diemthanhphan.getDiem_chuyencan());
        data.put("diem_bai_tap",diemthanhphan.getDiem_bai_tap());
        data.put("diem_trung_binh_kiem_tra_tren_lop",diemthanhphan.getDiem_trung_binh_kiem_tra_tren_lop());
        data.put("diem_thi_nghiem",diemthanhphan.getDiem_thi_nghiem());
        data.put("diem_cuoi_ky",diemthanhphan.getDiem_cuoi_ky());
        data.put("diem_tong_ket",diemthanhphan.getDiem_tong_ket());

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query DiemThanhPhan Successfully",data));
    }
    public ResponseEntity<ResponseObject> handleQuanlydiem(DiemThanhPhanDTO diemThanhPhanDTO, Model model, Authentication auth){
        try {
            CustomUserDetail current = userRepository.findByMasv(diemThanhPhanDTO.getMsv());
            DiemThanhPhan diemThanhPhan = diemThanhPhanRepository.capnhatDiem(diemThanhPhanDTO.getNhommonhoc(), current.getId());
            diemThanhPhan.setDiem_chuyencan(diemThanhPhanDTO.getDiem_chuyencan());
            diemThanhPhan.setDiem_bai_tap(diemThanhPhanDTO.getDiem_bai_tap());
            diemThanhPhan.setDiem_cuoi_ky(diemThanhPhanDTO.getDiem_cuoi_ky());
            diemThanhPhan.setDiem_thi_nghiem(diemThanhPhanDTO.getDiem_thi_nghiem());
            diemThanhPhan.setDiem_trung_binh_kiem_tra_tren_lop(diemThanhPhanDTO.getDiem_trung_binh_kiem_tra_tren_lop());
            diemThanhPhan.setDiem_tong_ket(diemThanhPhanDTO.getDiem_tong_ket());

            if (diemThanhPhanDTO.getDiem_tong_ket() >= 4) {
                diemThanhPhan.setQuamon(true);
            } else {
                diemThanhPhan.setQuamon(false);
            }

            diemThanhPhanRepository.save(diemThanhPhan);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Update DiemThanhPhan Successfully", diemThanhPhanDTO.getNhommonhoc()));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", "Update DiemThanhPhan Fail", diemThanhPhanDTO.getNhommonhoc()));
        }
    }
    public ResponseEntity<ResponseObject> handleXoasinhvien(String nhommonhoc, String msv, Model model, Authentication auth){
        try {
            diemThanhPhanRepository.delete(diemThanhPhanRepository.capnhatDiem(nhommonhoc, userRepository.findByMasv(msv).getId()));
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Update DiemThanhPhan Successfully", nhommonhoc));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", "Update DiemThanhPhan Fail", nhommonhoc));
        }
    }
    public ResponseEntity<Resource> exportDiemThanhPhanToExcel(String nhommonhoc) {
        List<DiemThanhPhan> diemThanhPhans = diemThanhPhanRepository.findByNhommonhoc(nhommonhoc);
        // Tạo workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Tạo header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Họ tên");
        headerRow.createCell(1).setCellValue("Mã sinh viên");
        headerRow.createCell(2).setCellValue("Điểm chuyên cần");
        headerRow.createCell(3).setCellValue("Điểm bài tập");
        headerRow.createCell(4).setCellValue("Điểm kiểm tra trên lớp");
        headerRow.createCell(5).setCellValue("Điểm thực hành");
        headerRow.createCell(6).setCellValue("Điểm cuối kỳ");
        headerRow.createCell(7).setCellValue("Điểm tổng kết (hệ 10)");

        // Tạo data rows
        int rowNum = 1;
        for (DiemThanhPhan x : diemThanhPhans) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(x.getCustomUserDetail().getHoten());
            row.createCell(1).setCellValue(x.getCustomUserDetail().getMasv());
            row.createCell(2).setCellValue(x.getDiem_chuyencan());
            row.createCell(3).setCellValue(x.getDiem_bai_tap());
            row.createCell(4).setCellValue(x.getDiem_trung_binh_kiem_tra_tren_lop());
            row.createCell(5).setCellValue(x.getDiem_thi_nghiem());
            row.createCell(6).setCellValue(x.getDiem_cuoi_ky());
            row.createCell(7).setCellValue(x.getDiem_tong_ket());
        }

        // Ghi workbook ra file
        try {
            File file = File.createTempFile(nhommonhoc, ".xlsx");
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            Resource resource = (Resource) new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+nhommonhoc+".xlsx");
            return ResponseEntity.ok().headers(headers).body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public ResponseEntity<ResponseObject> uploadExcel(MultipartFile file, String nhommonhoc, Model model) {
        Workbook workbook=null;
        try {
            if (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("xls")) {
                workbook = new HSSFWorkbook(file.getInputStream());
            } else if (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("xlsx")) {
                workbook = new XSSFWorkbook(file.getInputStream());
            }
            InputStream inputStream = file.getInputStream();
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                for(Cell cell:currentRow){
                    if(cell.getStringCellValue().equalsIgnoreCase("mã sinh viên")){
                        int cellmsv=cell.getColumnIndex();
                        while (iterator.hasNext()) {
                            CustomUserDetail sinhvien=userRepository.findByMasv(iterator.next().getCell(cellmsv).getStringCellValue());
                            if(sinhvien != null){
                                List<DiemThanhPhan> nhomhientai= diemThanhPhanRepository.findByNhommonhoc(nhommonhoc);
                                boolean existed = false;
                                for(DiemThanhPhan d:nhomhientai){
                                    if(d.getCustomUserDetail().getId()==sinhvien.getId()) {
                                        existed=true;
                                        break;
                                    }
                                }
                                if(!existed) {
                                    DiemThanhPhan diemThanhPhan = new DiemThanhPhan();

                                    try {
                                        diemThanhPhan.setMonhoc(nhomhientai.get(0).getMonhoc());
                                    } catch (Exception ex) {
                                        String mamonhoc = nhommonhoc.substring(0, nhommonhoc.indexOf("-"));
                                        MonHoc monHoc = monHocRepository.findByMamonhoc(mamonhoc);
                                        diemThanhPhan.setMonhoc(monHoc);
                                    }

                                    diemThanhPhan.setNhommonhoc(nhommonhoc);

                                    try {
                                        diemThanhPhan.setKihoc(nhomhientai.get(0).getKihoc());
                                    } catch (Exception ex) {
                                        //diemThanhPhan.setKihoc(null);
                                    }
                                    diemThanhPhan.setDiem_chuyencan(0);
                                    diemThanhPhan.setDiem_bai_tap(0);
                                    diemThanhPhan.setDiem_cuoi_ky(0);
                                    diemThanhPhan.setDiem_thi_nghiem(0);
                                    diemThanhPhan.setDiem_trung_binh_kiem_tra_tren_lop(0);
                                    diemThanhPhan.setDiem_tong_ket(0);
                                    diemThanhPhan.setQuamon(false);
                                    diemThanhPhan.setCustomUserDetail(sinhvien);

                                    diemThanhPhanRepository.save(diemThanhPhan);
                                }
                            }
                        }
                    }

                }
            }
            workbook.close();
            inputStream.close();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Add Student Successfully", ""));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail", "Add Student Fail", ex.getMessage()));
        }
    }
}
