package com.project1.domain.shopping.complain.mapper;

import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.complain.dto.ComplainDto.ComplainPatchDto;
import com.project1.domain.shopping.complain.dto.ComplainDto.ComplainPostDto;
import com.project1.domain.shopping.complain.dto.ComplainDto.ComplainResponseDto;
import com.project1.domain.shopping.complain.entity.Complain;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.entity.Item.ItemBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-16T11:27:00+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class ComplainMapperImpl implements ComplainMapper {

    @Override
    public Complain postDtoToEntity(ComplainPostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Complain complain = new Complain();

        complain.setTitle( postDto.getTitle() );
        complain.setContent( postDto.getContent() );

        return complain;
    }

    @Override
    public Complain patchDtoToEntity(ComplainPatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Complain complain = new Complain();

        complain.setComplainId( patchDto.getComplainId() );
        complain.setTitle( patchDto.getTitle() );
        complain.setContent( patchDto.getContent() );

        return complain;
    }

    @Override
    public ComplainResponseDto entityToResponseDto(Complain entity) {
        if ( entity == null ) {
            return null;
        }

        ComplainResponseDto complainResponseDto = new ComplainResponseDto();

        complainResponseDto.setTitle( entity.getTitle() );
        complainResponseDto.setContent( entity.getContent() );
        complainResponseDto.setModifiedAt( entity.getModifiedAt() );
        complainResponseDto.setCreatedAt( entity.getCreatedAt() );

        return complainResponseDto;
    }

    @Override
    public Complain complainPostDtoToComplain(ComplainPostDto complainPostDto) {
        if ( complainPostDto == null ) {
            return null;
        }

        Complain complain = new Complain();

        complain.setItem( complainPostDtoToItem( complainPostDto ) );
        complain.setTitle( complainPostDto.getTitle() );
        complain.setContent( complainPostDto.getContent() );

        return complain;
    }

    @Override
    public ComplainResponseDto complainToComplainResponseDto(Complain complain) {
        if ( complain == null ) {
            return null;
        }

        ComplainResponseDto complainResponseDto = new ComplainResponseDto();

        complainResponseDto.setName( complainMemberName( complain ) );
        complainResponseDto.setItemName( complainItemName( complain ) );
        complainResponseDto.setTitle( complain.getTitle() );
        complainResponseDto.setContent( complain.getContent() );
        complainResponseDto.setModifiedAt( complain.getModifiedAt() );
        complainResponseDto.setCreatedAt( complain.getCreatedAt() );

        return complainResponseDto;
    }

    protected Item complainPostDtoToItem(ComplainPostDto complainPostDto) {
        if ( complainPostDto == null ) {
            return null;
        }

        ItemBuilder item = Item.builder();

        item.itemId( complainPostDto.getItemId() );

        return item.build();
    }

    private String complainMemberName(Complain complain) {
        if ( complain == null ) {
            return null;
        }
        Member member = complain.getMember();
        if ( member == null ) {
            return null;
        }
        String name = member.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String complainItemName(Complain complain) {
        if ( complain == null ) {
            return null;
        }
        Item item = complain.getItem();
        if ( item == null ) {
            return null;
        }
        String name = item.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
