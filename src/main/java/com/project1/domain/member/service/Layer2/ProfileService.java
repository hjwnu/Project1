package com.project1.domain.member.service.Layer2;

import com.project1.domain.member.auth.userdetails.MemberAuthority;
import com.project1.domain.member.dto.MemberPatchDto;
import com.project1.domain.member.dto.MemberPostDto;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.mapper.MemberMapper;
import com.project1.domain.member.repository.MemberRepository;
import com.project1.global.utils.CustomBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
 public class ProfileService {

    private final MemberRepository memberRepository;
    private final MemberMapper mapper;

    public ProfileService(MemberRepository memberRepository, MemberMapper mapper) {
        this.memberRepository = memberRepository;
        this.mapper = mapper;
    }

    @Transactional
    public Member createRole(MemberPostDto postDto) {
        MemberAuthority memberAuthority = new MemberAuthority();
        Member member = mapper.memberPostToMember(postDto);

        member.setRoles(memberAuthority.createRoles(member.getEmail()));

        return memberRepository.save(member);
    }
    public void createRole(Member member) {
        MemberAuthority memberAuthority = new MemberAuthority();

        member.setRoles(memberAuthority.createRoles(member.getEmail()));

        memberRepository.save(member);
    }


    public Member save(Member member) {
        return memberRepository.save(member);
    }
    public Member updateProfile(MemberPatchDto patchDto, Member findMember) {
        CustomBeanUtils<Member> beanUtils = new CustomBeanUtils<>();
        Member member = mapper.memberPatchToMember(patchDto);
        Member updatedMember =
                beanUtils.copyNonNullProperties(member, findMember);

        return memberRepository.save(updatedMember);
    }


    public void deleteMember(Member member) {
        memberRepository.delete(member);
    }



}
