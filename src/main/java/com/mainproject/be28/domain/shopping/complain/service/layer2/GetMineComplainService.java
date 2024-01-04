package com.mainproject.be28.domain.shopping.complain.service.layer2;

import com.mainproject.be28.domain.member.service.Layer2.MemberVerifyService;
import com.mainproject.be28.domain.shopping.complain.dto.ComplainResponseDto;
import com.mainproject.be28.domain.shopping.complain.entity.Complain;
import com.mainproject.be28.domain.shopping.complain.mapper.ComplainMapper;
import com.mainproject.be28.global.generic.GetMineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
public class GetMineComplainService
        extends GetMineService.GetMineServiceImpl<Complain, ComplainResponseDto> {
    private final ComplainMapper mapper;

    protected GetMineComplainService(MemberVerifyService memberVerifyService, EntityManager entityManager, ComplainMapper mapper) {
        super(memberVerifyService, entityManager, Complain.class);
        this.mapper = mapper;
    }

    @Override
    protected ComplainResponseDto entityToResponseDto(Complain complain) {
        return mapper.entityToResponseDto(complain);
    }

    @Override
    protected void setMemberAndItem(ComplainResponseDto complainResponseDto, Complain complain) {
        complainResponseDto.setItemName(complain.getItem().getName());
        complainResponseDto.setName(complain.getMember().getName());
    }

}
