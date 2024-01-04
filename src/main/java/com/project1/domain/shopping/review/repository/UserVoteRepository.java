package com.project1.domain.shopping.review.repository;

import com.project1.domain.shopping.review.entity.UserVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserVoteRepository extends  JpaRepository<UserVote, Long> {
    List<UserVote> findByMemberIdAndReviewId(Long memberId, Long reviewId);
    @Modifying
    @Query("DELETE FROM UserVote e WHERE e.expirationDate <= :expirationDateTime")
    void deleteExpiredData(LocalDateTime expirationDateTime);
}
