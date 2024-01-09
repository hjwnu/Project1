package com.project1.domain.notice.board.mapper;

import com.project1.domain.notice.board.dto.BoardDto.PatchDto;
import com.project1.domain.notice.board.dto.BoardDto.PostDto;
import com.project1.domain.notice.board.entity.Board;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-09T09:50:34+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class BoardMapperImpl implements BoardMapper {

    @Override
    public Board postDtoToEntity(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Board board = new Board();

        return board;
    }

    @Override
    public Board patchDtoToEntity(PatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Board board = new Board();

        board.setBoardId( patchDto.getBoardId() );
        board.setTitle( patchDto.getTitle() );
        board.setContent( patchDto.getContent() );
        board.setBoardCategory( patchDto.getBoardCategory() );

        return board;
    }
}
