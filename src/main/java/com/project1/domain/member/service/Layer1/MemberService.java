package com.project1.domain.member.service.Layer1;

import com.project1.domain.member.dto.MemberPatchDto;
import com.project1.domain.member.dto.MemberPostDto;
import com.project1.domain.member.dto.MemberResponseDto;
import com.project1.domain.member.dto.PasswordPatchDto;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.service.Layer2.MemberEnDecodeService;
import com.project1.domain.member.service.Layer2.MemberVerificationService;
import com.project1.domain.member.service.Layer2.ProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
    MemberResponseDto createMember(MemberPostDto memberPostDto);
    void createMember(Member member);
    MemberResponseDto updateProfile(MemberPatchDto requestBody);
    void changePassword(PasswordPatchDto passwordPatchDto);
    void deleteMember(String email, String password);
    MemberResponseDto getProfile();
    MemberResponseDto getOtherProfile(String name);


    @Service
    @Transactional
    class MemberServiceImpl implements MemberService {
        private final ProfileService profileService;
        private final MemberEnDecodeService enDecodeService;
        private final MemberVerificationService verificationService;

        public MemberServiceImpl(ProfileService profileService, MemberEnDecodeService enDecodeService, MemberVerificationService verificationService) {
            this.profileService = profileService;
            this.enDecodeService = enDecodeService;
            this.verificationService = verificationService;
        }

        // 직접 회원가입
        public MemberResponseDto createMember(MemberPostDto memberPostDto) {
            verificationService.verifyExistsEmail(memberPostDto.getEmail());

            Member created = profileService.createRole(memberPostDto);
            enDecodeService.encodePrivate(created);
            return enDecodeService.memberToMemberResponse(created);
        }

//         Oauth 회원 생성
        public void createMember(Member member) {
            verificationService.verifyExistsEmail(member.getEmail());
            profileService.createRole(member);
        }


        public MemberResponseDto updateProfile(MemberPatchDto requestBody) {  // 주소, 핸드폰번호 변경.
            Member findMember = verificationService.verifyEmailPassword(requestBody.getEmail(), requestBody.getPassword());
            Member response = profileService.updateProfile(requestBody, findMember);
            return enDecodeService.memberToMemberResponse(response);
        }

        public void changePassword(PasswordPatchDto passwordPatchDto) {

           Member findMember = verificationService.verifyEmailPassword(passwordPatchDto.getEmail(), passwordPatchDto.getPassword());
           String encodedPassword =  enDecodeService.matchPassword(passwordPatchDto);
            findMember.setPassword(encodedPassword);
            profileService.save(findMember);
        }

        public void deleteMember(String email, String password){
            Member member = verificationService.verifyEmailPassword(email,password);
            profileService.deleteMember(member);
        }
        public MemberResponseDto getProfile() {
            return enDecodeService.memberToMemberResponse(verificationService.findTokenMember());
        }

        @Override
        public MemberResponseDto getOtherProfile(String name) {
            MemberResponseDto responseDto = enDecodeService.memberToMemberResponse(profileService.findMemberByName(name));
            
            if(verificationService.isAdmin()
                || verificationService.findTokenMember().getName().equals(name)) return responseDto;
            
            responseDto.setName(responseDto.getName().charAt(0) + "**");
            responseDto.setEmail(maskingEmail(responseDto.getEmail()));
            responseDto.setAddress("*".repeat(10));
            responseDto.setPhone("010-****-****");
            return responseDto;
        }

        private String maskingEmail(String email) {
            int atMarkIndex = email.indexOf('@');
            int maskingNumber = email.substring(0, atMarkIndex).length();
            String emailDomain = email.substring(atMarkIndex);
           return "*".repeat(maskingNumber)+emailDomain;
        }
    }
}
