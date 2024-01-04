package com.project1.domain.member.service.Layer1;

import com.project1.domain.member.dto.MemberPatchDto;
import com.project1.domain.member.dto.MemberPostDto;
import com.project1.domain.member.dto.MemberResponseDto;
import com.project1.domain.member.dto.PasswordPatchDto;
import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.member.service.Layer2.ProfileService;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
    MemberResponseDto createMember(MemberPostDto memberPostDto);
    void createMember(Member member);
    MemberResponseDto updateProfile(MemberPatchDto requestBody);
    void changePassword(PasswordPatchDto passwordPatchDto);
    void deleteMember(String email, String password);
    MemberResponseDto getProfile();
    MemberResponseDto getProfile(String name);


    @Service
    @Transactional
    class MemberServiceImpl implements MemberService {
        private final ProfileService profileService;
        private final MemberMapper mapper;
        private final MemberVerifyService memberVerifyService;
        public MemberServiceImpl(ProfileService profileService, MemberMapper mapper, MemberVerifyService memberVerifyService) {
            this.profileService = profileService;
            this.mapper = mapper;
            this.memberVerifyService = memberVerifyService;
        }

        // 직접 회원가입
        public MemberResponseDto createMember(MemberPostDto memberPostDto) {
            memberVerifyService.verifyExistsEmail(memberPostDto.getEmail());
            Member member = mapper.memberPostToMember(memberPostDto);
            Member created = profileService.createMember(member);
            return mapper.memberToMemberResponse(created);
        }

        // Oauth 회원 생성
        public void createMember(Member member) {
            memberVerifyService.verifyExistsEmail(member.getEmail());
            profileService.createMember(member);
        }


        public MemberResponseDto updateProfile(MemberPatchDto requestBody) {  // 주소, 핸드폰번호 변경.
            Member member = mapper.memberPatchToMember(requestBody);
            Member findMember = memberVerifyService.verifyEmailPassword(requestBody.getEmail(), requestBody.getPassword());
            Member response = profileService.updateProfile(member, findMember);
            return mapper.memberToMemberResponse(response);
        }

        public void changePassword(PasswordPatchDto passwordPatchDto) {

            Member findMember = memberVerifyService.verifyEmailPassword(passwordPatchDto.getEmail(), passwordPatchDto.getPassword());
            profileService.changePassword(passwordPatchDto, findMember);
        }

        //회원 탈퇴
        public void deleteMember(String email, String password){
            Member member = memberVerifyService.verifyEmailPassword(email,password);
            profileService.deleteMember(member);
        }
        @Override
        public MemberResponseDto getProfile() {

            return mapper.memberToMemberResponse(memberVerifyService.findMemberByName(memberVerifyService.findTokenMember().getName()));
        }

        @Override
        public MemberResponseDto getProfile(String name) {

            return mapper.memberToMemberResponse(memberVerifyService.findMemberByName(name));
        }

//
    }
}
