package com.project1.domain.notice.comment.service;

import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.notice.comment.dto.CommentDto;
import com.project1.domain.notice.comment.entity.Comment;
import com.project1.domain.notice.comment.mapper.CommentMapper;
import com.project1.global.generic.GetMineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
@Transactional
public class GetMineCommentService extends GetMineService.GetMineServiceImpl<Comment, CommentDto.ResponseDto> {
    private final CommentMapper mapper;

    protected GetMineCommentService(MemberVerifyService memberVerifyService,
                                    EntityManager entityManager, CommentMapper mapper) {
        super(memberVerifyService, entityManager,  Comment.class);
        this.mapper = mapper;
    }
    @Override
    protected CommentDto.ResponseDto entityToResponseDto(Comment comment) {
        return mapper.commentToCommentResponseDto(comment);
    }

    @Override
    protected void setMemberAndItem(CommentDto.ResponseDto responseDto, Comment comment) {
    }

    @Override
    protected TypedQuery<Comment> getQuery() {
        return entityManager.createQuery(
                "SELECT e FROM " + entityClass.getSimpleName() + " e JOIN FETCH e. member m WHERE m.name = :name", entityClass);
    }

}
