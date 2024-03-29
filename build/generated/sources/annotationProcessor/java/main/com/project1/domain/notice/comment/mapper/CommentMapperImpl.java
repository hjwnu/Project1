package com.project1.domain.notice.comment.mapper;

import com.project1.domain.member.entity.Member;
import com.project1.domain.notice.board.entity.Board;
import com.project1.domain.notice.comment.dto.CommentDto.PatchDto;
import com.project1.domain.notice.comment.dto.CommentDto.PostDto;
import com.project1.domain.notice.comment.dto.CommentDto.ResponseDto;
import com.project1.domain.notice.comment.dto.CommentDto.ResponseDto.ResponseDtoBuilder;
import com.project1.domain.notice.comment.entity.Comment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-05T09:56:46+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comment commentPostDtoToComment(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setBoard( postDtoToBoard( postDto ) );
        comment.setContent( postDto.getContent() );

        return comment;
    }

    @Override
    public Comment commentPatchDtoToComment(PatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setBoard( patchDtoToBoard( patchDto ) );
        comment.setCommentId( patchDto.getCommentId() );
        comment.setContent( patchDto.getContent() );

        return comment;
    }

    @Override
    public ResponseDto commentToCommentResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        ResponseDtoBuilder responseDto = ResponseDto.builder();

        responseDto.memberName( commentMemberName( comment ) );
        responseDto.content( comment.getContent() );
        responseDto.likeCount( comment.getLikeCount() );
        responseDto.createdAt( comment.getCreatedAt() );

        return responseDto.build();
    }

    protected Board postDtoToBoard(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        Board board = new Board();

        board.setBoardId( postDto.getBoardId() );

        return board;
    }

    protected Board patchDtoToBoard(PatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        Board board = new Board();

        board.setBoardId( patchDto.getBoardId() );

        return board;
    }

    private String commentMemberName(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        Member member = comment.getMember();
        if ( member == null ) {
            return null;
        }
        String name = member.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
