package com.project1.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponseDto {
    private String email;
    private String name;
    private String phone;
    private String address;
}
