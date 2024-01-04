package com.project1.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberPatchDto {
    private String email;
    private String password;
    private String phone;
    private String address;
}