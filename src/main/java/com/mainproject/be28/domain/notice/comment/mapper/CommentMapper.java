package com.mainproject.be28.domain.notice.comment.mapper;

import com.mainproject.be28.domain.notice.comment.dto.CommentDto;
import com.mainproject.be28.domain.notice.comment.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "boardId", target="board.boardId")
    Comment commentPostDtoToComment(CommentDto.PostDto postDto);

    @Mapping(source = "boardId", target="board.boardId")
    Comment commentPatchDtoToComment(CommentDto.PatchDto patchDto);

    @Mapping(source= "member.name", target="memberName")
    CommentDto.ResponseDto commentToCommentResponseDto(Comment comment);

}

//    @Mapping(source = "commentPostDto.content", target = "content")
////    @Mapping(target = "board", expression = "java(boardRepository.findById(boardId).orElseThrow(() -> new RuntimeException(\"Board not found\")))")
//    Comment commentPostDtoToComment(CommentPostDto commentPostDto, Long boardId);
//}