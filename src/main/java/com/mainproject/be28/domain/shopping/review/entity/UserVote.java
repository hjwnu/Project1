package com.mainproject.be28.domain.shopping.review.entity;

import com.mainproject.be28.global.auditable.Auditable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter @Setter

@RequiredArgsConstructor
public class UserVote extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voteId;
    private Long memberId;
    private Long reviewId;
    private Boolean userLike;
    private Boolean unlike;
    private LocalDate expirationDate;
    public UserVote(Long memberId, Long reviewId, boolean isLike) {
        this.memberId = memberId;
        this.reviewId = reviewId;
        if(isLike) this.userLike = true;
        else this.unlike = true;
    }

    public enum VoteType {
        LIKE, UNLIKE
    }
    @PrePersist
    public void prePersist() {
        if (expirationDate == null) {
            expirationDate = LocalDate.now().plusDays(3);
        }
    }
}
