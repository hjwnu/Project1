package com.project1.domain.member.service.Layer2;

import com.project1.domain.member.dto.PasswordPatchDto;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.global.utils.CustomBeanUtils;
import com.project1.domain.member.auth.userdetails.MemberAuthority;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ProfileService {
    Member createMember(Member member);
    Member updateProfile(Member member, Member findMember);
    void changePassword(PasswordPatchDto passwordPatchDto, Member findMember);
    void deleteMember(Member member);

    @Service
     class ProfileServiceImpl implements ProfileService {
        private final PasswordEncoder passwordEncoder;
        private final MemberRepository memberRepository;

        public ProfileServiceImpl(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
            this.passwordEncoder = passwordEncoder;
            this.memberRepository = memberRepository;
        }

        @Override
        @Transactional
        public Member createMember(Member member) {
            MemberAuthority memberAuthority = new MemberAuthority();

            encodePrivate(member);
            member.setRoles(memberAuthority.createRoles(member.getEmail()));

            return memberRepository.save(member);
        }

        @Override
        public Member updateProfile(Member member, Member findMember) {
            CustomBeanUtils<Member> beanUtils = new CustomBeanUtils<>();
            Member updatedMember =
                    beanUtils.copyNonNullProperties(member, findMember);

            return memberRepository.save(updatedMember);
        }

        @Override
        public void changePassword(PasswordPatchDto passwordPatchDto, Member findMember) {

            matchPassword(passwordPatchDto);

            String encodedPassword = passwordEncoder.encode(passwordPatchDto.getAfterPassword());
            findMember.setPassword(encodedPassword);
            memberRepository.save(findMember);
        }

        @Override
        public void deleteMember(Member member) {
            memberRepository.delete(member);
        }


        private static void matchPassword(PasswordPatchDto passwordPatchDto) {
            boolean matchNewPassword = passwordPatchDto.getAfterPassword().equals(passwordPatchDto.getConfirmPassword());
            if(!matchNewPassword){ throw new BusinessLogicException(ExceptionCode.DO_NOT_MATCH_PASSWORD);}
        }
        private void encodePrivate(Member member) {
            String encryptedPassword = passwordEncoder.encode(member.getPassword());
            String encryptedPhone = passwordEncoder.encode(member.getPhone());
            String encryptedAddress = passwordEncoder.encode(member.getAddress());
            member.setPassword(encryptedPassword);
            member.setPhone(encryptedPhone);
            member.setAddress(encryptedAddress);
        }
    }


}
