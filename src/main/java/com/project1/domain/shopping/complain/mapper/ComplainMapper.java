package com.project1.domain.shopping.complain.mapper;

import com.project1.domain.shopping.complain.dto.*;
import com.project1.global.generic.GenericMapper;
import com.project1.domain.shopping.complain.entity.Complain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ComplainMapper
        extends GenericMapper<Complain, ComplainDto.ComplainPostDto, ComplainDto.ComplainResponseDto, ComplainDto.ComplainPatchDto> {
    //ComplainPostDto 객체를 Complain 엔티티로 변환
    @Mapping(source = "itemId", target = "item.itemId") //itemId 필드 값을 가져와서, Complain 객체의 item 필드의 itemId로 매핑
    Complain complainPostDtoToComplain(ComplainDto.ComplainPostDto complainPostDto);

   // Complain 엔티티를 ComplainResponseDto 객체로 변환할 때 필요한 매핑 정보를 제공
    @Mapping(source = "member.name", target = "name")
    @Mapping(source = "item.name", target = "itemName")
    ComplainDto.ComplainResponseDto complainToComplainResponseDto(Complain complain);

    default List<ComplainDto.ComplainResponsesDto> complainsToComplainResponsesDto(List<Complain> complains) {
        List<ComplainDto.ComplainResponsesDto> responseDtos = new ArrayList<>();

        for (Complain complain : complains) {
            ComplainDto.ComplainResponsesDto responseDto = new ComplainDto.ComplainResponsesDto();

            responseDto.setName(complain.getMember().getName());
            responseDto.setItemName(complain.getItem().getName());
            responseDto.setTitle(complain.getTitle());

            responseDtos.add(responseDto);
        }

        return responseDtos;
    }
}
