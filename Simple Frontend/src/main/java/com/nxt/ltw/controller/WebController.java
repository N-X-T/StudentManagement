package com.nxt.ltw.controller;

import com.nxt.ltw.dto.DiemThanhPhanDTO;
import com.nxt.ltw.entity.CustomUserDetail;
import com.nxt.ltw.entity.Feedback;
import com.nxt.ltw.entity.ResponseObject;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
public class WebController {
    private static RestTemplate rest = new RestTemplate();
    @GetMapping("/logout")
    String logout(@CookieValue(name = "JSESSIONID") String JSESSIONID){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/logout",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        System.out.println(response);
        return "login";
    }
    @GetMapping(value = {"/","/user","/user/login"})
    String loginUserForm(){
        return "login";
    }
    @GetMapping(value = {"","/admin","/admin/login"})
    String loginAdminForm(){
        return "loginAdmin";
    }
    @PostMapping(value = {"/","/user","/user/login"})
    String loginUser(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletResponse httpServletResponse){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username);
        formData.add("password", password);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<ResponseObject> response = rest.postForEntity("http://localhost:8080/api/login", requestEntity, ResponseObject.class);
        if(response.getBody().getStatus().equals("OK")){
            httpServletResponse.addHeader("Set-Cookie",response.getHeaders().get("Set-Cookie").get(0));
            return "redirect:/dashboard";
        }
        return "login";
    }
    @PostMapping(value = {"/admin","/admin/login"})
    String loginAdmin(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletResponse httpServletResponse){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("username", username);
        formData.add("password", password);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<ResponseObject> response = rest.postForEntity("http://localhost:8080/api/login", requestEntity, ResponseObject.class);
        if(response.getBody().getStatus().equals("OK")){
            httpServletResponse.addHeader("Set-Cookie",response.getHeaders().get("Set-Cookie").get(0));
            return "redirect:/danhsachnhomlop";
        }
        return "login";
    }
    @GetMapping("/register")
    String registerForm(){
        return "register";
    }
    @PostMapping("/register")
    String register(@ModelAttribute("user") CustomUserDetail customUserDetail){
        return "redirect:/register?success";
    }
    @GetMapping("/dashboard")
    String dashboard(@CookieValue(name = "JSESSIONID") String JSESSIONID, @RequestParam(required = false) String start,@RequestParam(required = false) String end, Model model){
        LocalDate startL,endL;
        StringBuffer url = new StringBuffer("http://localhost:8080/api/dashboard?");
        if(start == null || end == null){
            startL=LocalDate.now().plusDays(1-LocalDate.now().getDayOfWeek().getValue());
            endL=LocalDate.now().plusDays(7-LocalDate.now().getDayOfWeek().getValue());
            url.append(endL);
        } else {

            startL=LocalDate.parse(start);
            endL=LocalDate.parse(end);
        }
        url.append("start=");
        url.append(startL);
        url.append("&end=");
        url.append(endL);

        model.addAttribute("start", startL);
        model.addAttribute("end", endL);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange(url.toString(),HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject responseJSON = new JSONObject(response);
        JSONObject dataJson = responseJSON.getJSONObject("data");
        model.addAttribute("name",dataJson.getString("hoten"));
        model.addAttribute("timetable",new JSONArray(dataJson.getJSONArray("timetable").toString().replace("null","false")));
        return "/user/dashboard";
    }
    @GetMapping("/tintuc")
    String tintuc(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/tintuc",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/tintuc";
    }
    @GetMapping("/hoctap/loptinchi")
    String loptinchi(@RequestParam(required = false) String kihoc, @CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String url = "http://localhost:8080/api/hoctap/loptinchi";
        if(kihoc != null)  url+="?kihoc="+kihoc;
        String response = rest.exchange(url,HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");

        model.addAttribute("hoten",dataJson.getString("hoten"));
        model.addAttribute("kihoc",dataJson.getInt("kihoc"));
        JSONArray loptinchi = new JSONArray(dataJson.getJSONArray("loptinchi").toString().replaceAll("null","\"\""));
        model.addAttribute("loptinchi",loptinchi);
        return "/user/hoctap/loptinchi";
    }
    @GetMapping("/hoctap/loptinchi/{nhommonhoc}")
    String loptinchiInfo(@PathVariable String nhommonhoc, @CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String url = "http://localhost:8080/api/hoctap/loptinchi/"+nhommonhoc;
        String response = rest.exchange(url,HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");

        model.addAttribute("hoten",dataJson.getString("hoten"));
        model.addAttribute("nhommonhoc",dataJson.getString("nhommonhoc"));
        JSONArray loptinchiInfo = new JSONArray(dataJson.getJSONArray("loptinchiinfo").toString().replaceAll("null","\"\""));
        model.addAttribute("loptinchiInfo",loptinchiInfo);

        return "/user/hoctap/loptinchiInfo";
    }
    @GetMapping("/hoctap/lophanhchinh")
    String lophanhchinh( @CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/hoctap/lophanhchinh",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");

        model.addAttribute("hoten",dataJson.getString("hoten"));
        model.addAttribute("malophanhchinh",dataJson.getString("malophanhchinh"));
        JSONArray lophanhchinh = new JSONArray(dataJson.getJSONArray("lophanhchinh").toString().replaceAll("null","\"\""));
        model.addAttribute("lophanhchinh",lophanhchinh);

        return "/user/hoctap/lophanhchinh";
    }
    @GetMapping("/hoctap/gochoctap")
    String gochoctap(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/hoctap/gochoctap",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");

        model.addAttribute("hoten",dataJson.getString("hoten"));
        JSONArray gochoctap = new JSONArray(dataJson.getJSONArray("gochoctap").toString().replaceAll("null","\"\""));
        model.addAttribute("gochoctap",gochoctap);
        return "/user/hoctap/gochoctap";
    }
    @GetMapping("/dichvumotcuasv")
    String dichvumotcua(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/dichvumotcuasv",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/dichvumotcuasv";
    }
    @GetMapping("/congnosinhvien")
    String congnosinhvien(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/congnosinhvien",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/congnosinhvien";
    }
    @GetMapping("/quanlythuviensinhvien/quanlyluanansinhvien")
    String quanlyluanansinhvien(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/quanlythuviensinhvien/quanlyluanansinhvien",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/quanlythuviensinhvien/quanlyluanansinhvien";
    }
    @GetMapping("/quanlythuviensinhvien/quanlyluanvansinhvien")
    String quanlyluanvansinhvien(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/quanlythuviensinhvien/quanlyluanvansinhvien",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/quanlythuviensinhvien/quanlyluanvansinhvien";
    }
    @GetMapping("/quanlythuviensinhvien/quanlykhoaluansinhvien")
    String quanlykhoaluansinhvien(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/quanlythuviensinhvien/quanlykhoaluansinhvien",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/quanlythuviensinhvien/quanlykhoaluansinhvien";
    }
    @GetMapping("/tienichkhac/phanhoi")
    String phanhoi(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/tienichkhac/phanhoi",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");

        model.addAttribute("hoten",dataJson.getString("hoten"));
        JSONArray feedbacks = new JSONArray(dataJson.getJSONArray("feedback").toString().replaceAll("null","\"\""));
        model.addAttribute("feedbacks",feedbacks);
        return "/user/tienichkhac/phanhoi";
    }
    @PostMapping("/tienichkhac/phanhoi")
    String PostPhanhoi(Feedback feedback, @CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("phongban", feedback.getPhongban());
        formData.add("noidung", feedback.getNoidung());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<ResponseObject> response = rest.postForEntity("http://localhost:8080/api/tienichkhac/phanhoi", requestEntity, ResponseObject.class);
        return "redirect:/tienichkhac/phanhoi";
    }
    @GetMapping("/tienichkhac/vanbanhuongdan")
    String vanbanhuongdan(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/tienichkhac/vanbanhuongdan",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/tienichkhac/vanbanhuongdan";
    }
    @GetMapping("/tienichkhac/khaosat")
    String khaosat(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/tienichkhac/khaosat",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/tienichkhac/khaosat";
    }
    @GetMapping("/tienichkhac/khaibaosuckhoe")
    String khaibaosuckhoe(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/tienichkhac/khaibaosuckhoe",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response);
        model.addAttribute("hoten",dataJson.getString("data"));
        return "/user/tienichkhac/khaibaosuckhoe";
    }
    @GetMapping("/tienichkhac/gioithieu")
    String gioithieu(Model model){
        
        return "/user/tienichkhac/gioithieu";
    }
    @GetMapping("/account/center")
    String accountcenter(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/account/center",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");
        model.addAttribute("hoten",dataJson.getString("hoten"));
        model.addAttribute("masv",dataJson.getString("masv"));
        model.addAttribute("ngaysinh",dataJson.getString("ngaysinh"));
        model.addAttribute("gioitinh",dataJson.getBoolean("gioitinh"));
        model.addAttribute("sodienthoai",dataJson.getString("sodienthoai"));
        model.addAttribute("email",dataJson.getString("email"));
        return "/user/accountcenter";
    }
    @GetMapping("/account/center/edit")
    String accountcentereditForm(@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        String response = rest.exchange("http://localhost:8080/api/account/center",HttpMethod.GET, new HttpEntity(headers), String.class).getBody();
        JSONObject dataJson = new JSONObject(response).getJSONObject("data");
        model.addAttribute("hoten",dataJson.getString("hoten"));
        model.addAttribute("masv",dataJson.getString("masv"));
        model.addAttribute("ngaysinh",dataJson.getString("ngaysinh"));
        model.addAttribute("gioitinh",dataJson.getBoolean("gioitinh"));
        model.addAttribute("sodienthoai",dataJson.getString("sodienthoai"));
        model.addAttribute("email",dataJson.getString("email"));
        return "/user/accountcenter-edit";
    }
    @PostMapping("/account/center/edit")
    String accountcenteredit(CustomUserDetail customUserDetail,@CookieValue(name = "JSESSIONID") String JSESSIONID,Model model){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.COOKIE,"JSESSIONID="+JSESSIONID);
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", customUserDetail.getEmail());
        formData.add("sodienthoai", customUserDetail.getSodienthoai());
        formData.add("ngaysinh", customUserDetail.getNgaysinh());
        formData.add("gioitinh", String.valueOf(customUserDetail.isGioitinh()));
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(formData, headers);
        ResponseEntity<ResponseObject> response = rest.postForEntity("http://localhost:8080/api/account/center/edit", requestEntity, ResponseObject.class);
        return "redirect:/account/center";
    }

    @GetMapping("/danhsachnhomlop")
    String danhsachnhomlop(Model model){
        
        return "/admin/danhsachnhomlop";
    }
    @GetMapping("/quanlysinhvien/{nhommonhoc}")
    String quanlysinhvien(@PathVariable String nhommonhoc, Model model){
        return "/admin/quanlysinhvien";
    }
    @PostMapping("/themsinhvien/{nhommonhoc}")
    String themsinhvienForm(@PathVariable String nhommonhoc, Model model){
        return "/admin/themsinhvien";
    }
    @GetMapping("/themsinhvien")
    String themsinhvien(@RequestParam String nhommonhoc, @RequestParam String msv, Model model){
        //return adminService.handleThemsinhvien(nhommonhoc, msv,model);
        return "";
    }
    @GetMapping("/quanlydiem/{nhommonhoc}/{msv}")
    String quanlydiemForm(@PathVariable String nhommonhoc,@PathVariable String msv, Model model){
        return "/admin/quanlydiem";
    }
    @PostMapping("/quanlydiem")
    String quanlydiem(@ModelAttribute("CapNhatDiem") DiemThanhPhanDTO diemThanhPhan, Model model){
        return "redirect:/quanlysinhvien/"+diemThanhPhan.getNhommonhoc();
    }
    @GetMapping("/xoasinhvien")
    String xoasinhvien(@RequestParam String nhommonhoc, @RequestParam String masv, Model model){
        return "redirect:/quanlysinhvien/"+nhommonhoc;
    }
    @GetMapping("/xuatbangdiem/{nhommonhoc}")
    public ResponseEntity<Resource> exportUsersToExcel(@PathVariable String nhommonhoc) {
        return ResponseEntity.status(HttpStatus.OK).body((Resource) new Object());
        //return adminService.exportDiemThanhPhanToExcel(nhommonhoc);
    }
    @PostMapping("/uploadExcel")
    public String uploadExcel(@RequestParam("excelFile") MultipartFile file, @RequestParam("nhommonhoc") String nhommonhoc, Model model) {
        //return adminService.uploadExcel(file, nhommonhoc, model);
        return "";
    }
}
