package com.project1.domain.member.auth.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberAuthority {
    private final List<String> USER_ROLES = List.of("USER");
    private final List<String> ADMIN_ROLES = List.of("USER", "ADMIN");


    // admin 계정 정보는 환경 변수로 빼기
    public List<GrantedAuthority> createAuthority(List<String> roles) {

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());
    }
    // DB 저장 용
    public List<String> createRoles(String username) {
        //    @Value("${mail.address.admin}")
        //    private String admin;
        String admin = "admin@gmail.com";
        if (username.equals(admin)) {
            return ADMIN_ROLES;
        } else return USER_ROLES;
    }
}