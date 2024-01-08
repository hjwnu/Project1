package com.project1.domain.member.service.Layer2;

import com.project1.domain.member.entity.Member;
import com.project1.domain.member.repository.MemberRepository;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberVerificationService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void verifyAdmin(){
        Member member = findTokenMember();
        List<String> roles = member.getRoles();
        if(roles.contains("ADMIN")) return;
        throw new BusinessLogicException(ExceptionCode.ONLY_ADMIN_CAN);
    }

    public Member findTokenMember() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return memberRepository.findMemberByEmail(email).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)) ;
    }

    public Member verifyEmailPassword(String email, String password) {
        Member currentMember = findTokenMember();
        String currentEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // 현재 로그인되어 있는 회원의 메일
        Member member = memberRepository.findMemberByEmail(currentEmail).orElseThrow(() -> new BusinessLogicException(ExceptionCode.VERIFY_FAILURE));
        boolean matchMember = member.equals(currentMember);
        boolean matchEmail = email.equals(currentEmail); //실제 이메일과 입력한 이메일이 일치하는지
        boolean matchPassword = passwordEncoder.matches(password, member.getPassword());
        if(!matchMember||!matchEmail||!matchPassword){
            throw new BusinessLogicException(ExceptionCode.VERIFY_FAILURE); // 둘 중하나라도 다르다면 인증 실
        }
        return member;
    }
    // 회원이 존재하는지 검사 , 존재하면 예외
    public void verifyExistsEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BusinessLogicException(ExceptionCode.USER_EXIST);
        }
    }

    public Member findMemberByName(String name) {
        return memberRepository.findMemberByName(name).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

}