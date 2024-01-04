package com.project1.domain.notice.comment.repository;

import com.project1.domain.notice.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByMember_MemberId(long memberId);

    List<Comment> findCommentsByMember_Name(String name);
}
