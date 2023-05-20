package com.nxt.ltw.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxt.ltw.dto.*;
import com.nxt.ltw.entity.*;
import com.nxt.ltw.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MonHocRepository monHocRepository;
    @Autowired
    FeedbackRepository feedbackRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    TimetableRepository timetableRepository;
    @Autowired
    DiemThanhPhanRepository diemThanhPhanRepository;

    public String findName(Authentication auth){
        return userRepository.findByUsername(auth.getName()).getHoten();
    }
    public ResponseEntity<ResponseObject> save(CustomUserDetail customUserDetail){
        try {
            customUserDetail.setPassword(passwordEncoder.encode(customUserDetail.getPassword()));
            List<Role> roleList = roleRepository.findByName("ROLE_USER");
            if (roleList.isEmpty()) {
                roleList = Arrays.asList(new Role("ROLE_USER"));
            }
            customUserDetail.setRole(roleList);
            userRepository.save(customUserDetail);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Register Successfully", ""));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("Fail", "Register Failed", ""));
        }
    }
    public CustomUserDetail findUser(Authentication auth){
        return userRepository.findByUsername(auth.getName());
    }
    public ResponseEntity<ResponseObject>  update(CustomUserDetail customUserDetail,Authentication auth) {
        CustomUserDetail currentUserDetail1 = userRepository.findByUsername(auth.getName());
        currentUserDetail1.setNgaysinh(customUserDetail.getNgaysinh());
        currentUserDetail1.setGioitinh(customUserDetail.isGioitinh());
        currentUserDetail1.setEmail(customUserDetail.getEmail());
        currentUserDetail1.setSodienthoai(customUserDetail.getSodienthoai());
        userRepository.save(currentUserDetail1);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Save Information Successfully", ""));
    }
        public ResponseEntity<ResponseObject> handleTimeTable(String start, String end, Authentication auth) {
        CustomUserDetail customUserDetail = findUser(auth);
        LocalDate startL,endL;
        if(start == null || end == null){
            startL=LocalDate.now().plusDays(1-LocalDate.now().getDayOfWeek().getValue());
            endL=LocalDate.now().plusDays(7-LocalDate.now().getDayOfWeek().getValue());
        } else {
            startL=LocalDate.parse(start);
            endL=LocalDate.parse(end);
        }
        List<TimeTable> timeTables = timetableRepository.findTabletimes(startL.toString(),endL.plusDays(1).toString(),customUserDetail.getId());
        TimeTableDTO[][] timeTableDTOS =new TimeTableDTO[7][6];
        for(int i=0;i<7;i++){
            for(TimeTable t : timeTables){
                LocalDateTime x = LocalDateTime.parse(t.getNgay(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if(x.getDayOfWeek().getValue()==i+1){
                    switch (x.getHour()){
                        case 7: {
                            timeTableDTOS[i][0]=new TimeTableDTO(t);
                            timeTableDTOS[i][0].setTengiangvien(userRepository.findById(t.getId_giangvien()).get().getHoten());
                            break;
                        }
                        case 9: {
                            timeTableDTOS[i][1]=new TimeTableDTO(t);
                            timeTableDTOS[i][1].setTengiangvien(userRepository.findById(t.getId_giangvien()).get().getHoten());
                            break;
                        }
                        case 12: {
                            timeTableDTOS[i][2]=new TimeTableDTO(t);
                            timeTableDTOS[i][2].setTengiangvien(userRepository.findById(t.getId_giangvien()).get().getHoten());
                            break;
                        }
                        case 14: {
                            timeTableDTOS[i][3]=new TimeTableDTO(t);
                            timeTableDTOS[i][3].setTengiangvien(userRepository.findById(t.getId_giangvien()).get().getHoten());
                            break;
                        }
                        case 16: {
                            timeTableDTOS[i][4]=new TimeTableDTO(t);
                            timeTableDTOS[i][4].setTengiangvien(userRepository.findById(t.getId_giangvien()).get().getHoten());
                            break;
                        }
                        case 18: {
                            timeTableDTOS[i][5]=new TimeTableDTO(t);
                            timeTableDTOS[i][5].setTengiangvien(userRepository.findById(t.getId_giangvien()).get().getHoten());
                            break;
                        }
                        default: break;
                    }
                }
            }
        }
        HashMap<String,Object> data = new HashMap<>();
        data.put("hoten",customUserDetail.getHoten());
        data.put("timetable", timeTableDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query TimeTable Successfully",data));
    }
    public ResponseEntity<ResponseObject> handleLophanhchinh(Model model, Authentication auth){
        CustomUserDetail current=userRepository.findByUsername(auth.getName());
        List<CustomUserDetail> lophanhchinh=userRepository.findByLophanhchinh(current.getLophanhchinh());
        List<UserDetailDTO> userDetailDTOS = lophanhchinh.stream().map(
                customUserDetail -> {
                    UserDetailDTO userDetailDTO = new UserDetailDTO(customUserDetail);
                    return userDetailDTO;
                }
        ).collect(Collectors.toList());
        HashMap<String,Object> data = new HashMap<>();
        data.put("hoten", current.getHoten());
        data.put("lophanhchinh",userDetailDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK", "Query lophanhchinh successfully",data));
    }
    public ResponseEntity<ResponseObject> handleLoptinchi(String kihoc, Authentication auth){
        if(kihoc == null) kihoc="1";
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        List<DiemThanhPhan> loptinchi=diemThanhPhanRepository.findByKihoc(Integer.parseInt(kihoc),current.getId());

        List<LopTinChiDTO> lopTinChiDTOS = loptinchi.stream().map(
                diemThanhPhan -> {
                    LopTinChiDTO lopTinChiDTO = new LopTinChiDTO();
                    lopTinChiDTO.setMamonhoc(diemThanhPhan.getMonhoc().getMamonhoc());
                    lopTinChiDTO.setTenmonhoc(diemThanhPhan.getMonhoc().getTenmonhoc());
                    lopTinChiDTO.setNhommonhoc(diemThanhPhan.getNhommonhoc());
                    return lopTinChiDTO;
                }
        ).collect(Collectors.toList());

        HashMap<String, Object> data = new HashMap<>();
        data.put("hoten",current.getHoten());
        data.put("kihoc",Integer.parseInt(kihoc));
        data.put("loptinchi", lopTinChiDTOS);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query Loptinchi Successfully",data));
    }
    public ResponseEntity<ResponseObject> handleLoptinchiInfo(String nhommonhoc, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());

        List<DiemThanhPhan> loptinchiInfo=diemThanhPhanRepository.findByNhommonhoc(nhommonhoc);
        List<LopTinChiInfoDTO> lopTinChiInfoDTOS = loptinchiInfo.stream().map(
                diemThanhPhan -> {
                    LopTinChiInfoDTO lopTinChiInfoDTO = new LopTinChiInfoDTO();
                    lopTinChiInfoDTO.setHoten(diemThanhPhan.getCustomUserDetail().getHoten());
                    lopTinChiInfoDTO.setMasv(diemThanhPhan.getCustomUserDetail().getMasv());
                    lopTinChiInfoDTO.setEmail(diemThanhPhan.getCustomUserDetail().getEmail());
                    lopTinChiInfoDTO.setGioitinh(diemThanhPhan.getCustomUserDetail().isGioitinh());
                    return lopTinChiInfoDTO;
                }
        ).collect(Collectors.toList());
        HashMap<String,Object> data = new HashMap<>();
        data.put("hoten",current.getHoten());
        data.put("nhommonhoc",nhommonhoc);
        data.put("loptinchiinfo",lopTinChiInfoDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query SinhVien Successfully",data));
    }
    public ResponseEntity<ResponseObject> handleGochoctap(Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        List<GochoctapDTO> gochoctap=diemThanhPhanRepository.findByUserid(current.getId()).stream().map(
                diemThanhPhan -> {
                    return new GochoctapDTO(diemThanhPhan);
                }
        ).collect(Collectors.toList());
        HashMap<String,Object> data = new HashMap<>();
        data.put("hoten",current.getHoten());
        data.put("gochoctap",gochoctap);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query Gochoctap Successfylly",data));
    }
    public ResponseEntity<ResponseObject> handlePhanhoiForm(Model model, Authentication auth){
        CustomUserDetail current = userRepository.findByUsername(auth.getName());
        HashMap<String,Object> data = new HashMap<>();
        data.put("hoten",current.getHoten());
        data.put("feedback",current.getFeedbacks());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Query Feedback Successfylly",data));
    }
    public ResponseEntity<ResponseObject> handlePhanhoi(Feedback feedback, Model model, Authentication auth){
        try {
            feedback.setNgaytao(new Date());
            CustomUserDetail current = userRepository.findByUsername(auth.getName());
            feedback.setCustomUserDetail(current);
            feedbackRepository.save(feedback);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Save Feedback Success",""));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("Fail","Save Feedback Fail",e.getMessage()));
        }
    }

    public ResponseEntity<ResponseObject> accountcenter(Authentication auth) {
        CustomUserDetail userDetail = userRepository.findByUsername(auth.getName());
        HashMap<String,Object> data=new HashMap<>();
        data.put("hoten",userDetail.getHoten());
        data.put("masv",userDetail.getMasv());
        data.put("ngaysinh",userDetail.getNgaysinh());
        data.put("gioitinh",userDetail.isGioitinh());
        data.put("sodienthoai",userDetail.getSodienthoai());
        data.put("email",userDetail.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("OK","Get information successfully",data));
    }
    /*private UserDTOUnuse convertUser(CustomUserDetail customUserDetail){
        UserDTOUnuse userDTO=new UserDTOUnuse();
        userDTO.setName(customUserDetail.getName());
        userDTO.setUsername(customUserDetail.getUsername());
        return userDTO;
    }
    private CustomUserDetail convertUserDTO(UserDTOUnuse userDTO){
        CustomUserDetail customUserDetail=new CustomUserDetail();
        customUserDetail.setName(userDTO.getName());
        customUserDetail.setUsername(userDTO.getUsername());
        return customUserDetail;
    }*/
}
