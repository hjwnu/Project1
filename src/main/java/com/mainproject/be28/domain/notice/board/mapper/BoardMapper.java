package com.mainproject.be28.domain.notice.board.mapper;

import com.mainproject.be28.domain.notice.board.dto.BoardDto;
import com.mainproject.be28.domain.notice.board.entity.Board;
import com.mainproject.be28.domain.notice.comment.dto.CommentDto;
import com.mainproject.be28.domain.notice.comment.entity.Comment;
import com.mainproject.be28.global.generic.GenericMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper
    extends GenericMapper<Board, BoardDto.PostDto, BoardDto.ResponseDto, BoardDto.PatchDto, Long> {

    default BoardDto.PageDto entityToPageDto(Board board) {
        if (board == null) {
            return null;
        }
        return BoardDto.PageDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .memberName(board.getMember().getName())
                .viewCount(board.getViewCount())
                .commentCount((long) board.getComments().size())
                .createdAt(board.getCreatedAt())
                .build();
    }

    default BoardDto.ResponseDto entityToResponseDto(Board board){
        if ( board == null ) {
            return null;
        }

        return BoardDto.ResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .memberName(board.getMember().getName())
                .viewCount(board.getViewCount())
                .comments(getCommentResponseDtoList(board))
                .createdAt(board.getCreatedAt())
                .build();
    }

    default List<CommentDto.ResponseDto> getCommentResponseDtoList(Board board) {
        List<Comment> commentList = board.getComments();
        List<CommentDto.ResponseDto> responseDtos = new ArrayList<>();
        if(commentList != null) {
            for (Comment comment : commentList) {
                responseDtos.add(CommentDto.ResponseDto.builder()
                        .memberName(comment.getMember().getName())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedAt())
                        .likeCount(comment.getLikeCount())
                        .build());
            }
        }
        return responseDtos;
    }

}
