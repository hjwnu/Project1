package com.project1.domain.member.service.Layer2;

import com.project1.domain.member.auth.jwt.JwtTokenizer;
import com.project1.domain.member.dto.MemberResponseDto;
import com.project1.domain.member.dto.PasswordPatchDto;
import com.project1.domain.member.entity.Member;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberEnDecodeService {
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;

    public MemberEnDecodeService(JwtTokenizer jwtTokenizer, PasswordEncoder passwordEncoder) {
        this.jwtTokenizer = jwtTokenizer;
        this.passwordEncoder = passwordEncoder;
    }
    public MemberResponseDto memberToMemberResponse(Member member){

        return  new MemberResponseDto(
                member.getEmail(),
                member.getName(),
                jwtTokenizer.dataEnDecrypt(getSecretKey(member),member.getPhone(),2),
                jwtTokenizer.dataEnDecrypt(getSecretKey(member),member.getAddress(),2)
        );
    }

    public String getAddress(Member member) {
        return jwtTokenizer.dataEnDecrypt(getSecretKey(member), member.getAddress(),2);
    }
    public String getPhone(Member member) {
        return jwtTokenizer.dataEnDecrypt(getSecretKey(member), member.getPhone(),2);
    }
    private String getSecretKey(Member member) {
        return jwtTokenizer.encodedBase64SecretKey(member.getEmail());
    }
    public void encodePrivate(Member member) {
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        String encryptedPhone = jwtTokenizer.dataEnDecrypt(getSecretKey(member),member.getPhone(),1);
        String encryptedAddress = jwtTokenizer.dataEnDecrypt(getSecretKey(member),member.getAddress(),1);

        member.setPassword(encryptedPassword);
        member.setPhone(encryptedPhone);
        member.setAddress(encryptedAddress);
    }

    public String matchPassword(PasswordPatchDto passwordPatchDto) {
        boolean matchNewPassword = passwordPatchDto.getAfterPassword().equals(passwordPatchDto.getConfirmPassword());
        if(!matchNewPassword){ throw new BusinessLogicException(ExceptionCode.DO_NOT_MATCH_PASSWORD);}
       return passwordEncoder.encode(passwordPatchDto.getAfterPassword());
    }

}
