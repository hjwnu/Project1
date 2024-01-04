package com.project1.domain.notice.comment.service;

import com.project1.domain.notice.comment.dto.CommentDto;
import com.project1.domain.notice.comment.repository.CommentRepository;
import com.project1.domain.member.entity.Member;
import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.notice.comment.entity.Comment;
import com.project1.domain.notice.comment.mapper.CommentMapper;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;
    private final MemberVerifyService memberVerifyService;

    public CommentService(CommentRepository commentRepository, CommentMapper mapper, MemberVerifyService memberVerifyService) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
        this.memberVerifyService = memberVerifyService;
    }

    public CommentDto.ResponseDto createComment(CommentDto.PostDto postDto){
        Comment comment = mapper.commentPostDtoToComment(postDto);
        Member member = memberVerifyService.findTokenMember();
        comment.setMember(member);
        comment.setLikeCount(0L);
        commentRepository.save(comment);
        return  mapper.commentToCommentResponseDto(comment);
    }

    public CommentDto.ResponseDto getCommentById(Long id){
        Comment comment  = commentRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        return mapper.commentToCommentResponseDto(comment);
    }

    public CommentDto.ResponseDto updateComment(Long id, CommentDto.PatchDto updatedComment) {
        Member member = memberVerifyService.findTokenMember();
        Comment originComment = commentRepository.findById(updatedComment.getCommentId()).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        Comment comment = mapper.commentPatchDtoToComment(updatedComment);

        verifySameMember(member, originComment);
        updateContent(originComment, comment);

        return mapper.commentToCommentResponseDto(originComment);
        }

    private void updateContent(Comment originComment, Comment comment) {
        if (originComment != null&& comment.getContent() != null) {
            originComment.setContent(comment.getContent());
            commentRepository.save(originComment);
        }
    }

    private static void verifySameMember(Member member, Comment comment) {
        if (!comment.getMember().equals(member)) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_AUTHORIZED);
        }
    }

    public void deleteComment(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND));
        verifySameMember(memberVerifyService.findTokenMember(),comment);

        commentRepository.delete(comment);
    }

    public Page<CommentDto.ResponseDto> findCommentsByMember(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        List<Comment> myComments = commentRepository.findCommentsByMember_Name(name);
        List<CommentDto.ResponseDto> myCommentsDto = new ArrayList<>();
        for (Comment comment : myComments) {
            myCommentsDto.add(mapper.commentToCommentResponseDto(comment));
        }
        return new PageImpl<>(myCommentsDto, pageRequest, myCommentsDto.size());
    }


}


