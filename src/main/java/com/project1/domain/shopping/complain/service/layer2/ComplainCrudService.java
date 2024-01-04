package com.project1.domain.shopping.complain.service.layer2;

import com.project1.global.generic.GenericCrudService;
import com.project1.global.generic.GenericMapper;
import com.project1.domain.shopping.complain.dto.ComplainPatchDto;
import com.project1.domain.shopping.complain.dto.ComplainPostDto;
import com.project1.domain.shopping.complain.dto.ComplainResponseDto;
import com.project1.domain.shopping.complain.dto.ComplainResponsesDto;
import com.project1.domain.shopping.complain.entity.Complain;
import com.project1.domain.shopping.complain.mapper.ComplainMapper;
import com.project1.domain.shopping.complain.repository.ComplainRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ComplainCrudService
        extends GenericCrudService.GenericCrud<Complain, ComplainPostDto, ComplainResponseDto, ComplainPatchDto, Long> {
    private final ComplainRepository complainRepository;

    private final ComplainMapper mapper;
    public ComplainCrudService(ComplainRepository complainRepository, ComplainMapper mapper) {
        this.complainRepository = complainRepository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<Complain, Long> getRepository() {
        return complainRepository;
    }
    @Override
    protected void setId(Complain newEntity, Long aLong) {
        newEntity.setComplainId(aLong);
    }
    @Override
    protected GenericMapper<Complain, ComplainPostDto, ComplainResponseDto, ComplainPatchDto, Long> getMapper() {
        return mapper;
    }
    @Override
    protected List<Complain> findByName(String str) {
        return complainRepository.findAllByMember_Name(str);
    }

    public Page<ComplainResponsesDto> findAll(int page, int size) {
        List<ComplainResponsesDto> list = mapper.complainsToComplainResponsesDto(findList(page, size));

        return new PageImpl<>(list, PageRequest.of(page, size), list.size());
        }
}
