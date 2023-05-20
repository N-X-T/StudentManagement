package com.nxt.ltw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserDetail{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "hoten", columnDefinition = "NVARCHAR(255)")
    private String hoten;
    private String email;
    private String sodienthoai;
    private String masv;
    @Column(columnDefinition = "DATE")
    private String ngaysinh;
    private boolean gioitinh;
    private String lophanhchinh;
    @Column(unique=true)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="users_roles",
            joinColumns = {@JoinColumn(name="USER_ID", referencedColumnName="ID")},
            inverseJoinColumns = {@JoinColumn(name="ROLE_ID", referencedColumnName="ID")}
    )
    private List<Role> role = new ArrayList<>();
    @OneToMany(mappedBy = "customUserDetail", cascade = CascadeType.ALL)
    private List<TimeTable> timeTables = new ArrayList<>();

    @OneToMany(mappedBy = "customUserDetail", cascade = CascadeType.ALL)
    private List<DiemThanhPhan> diemThanhPhans = new ArrayList<>();
    @OneToMany(mappedBy = "customUserDetail", cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return this.role.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
