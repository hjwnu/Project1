package com.mainproject.be28.domain.shopping.review.entity;

import com.mainproject.be28.domain.member.entity.Member;
import com.mainproject.be28.domain.shopping.item.entity.Item;
import com.mainproject.be28.global.auditable.Auditable;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table @Builder
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    @Column(length = 1000)
    private String content;

    @Column
    private Long  likeCount;

    @Column
    private Long unlikeCount;

    @Column
    private int score;

    public void addLike(UserVote.VoteType voteType) {
        if(voteType.equals(UserVote.VoteType.LIKE)) {
            this.likeCount = this.likeCount == null? 1 : ++this.likeCount;
        }
        else this.unlikeCount = this.unlikeCount == null? 1 : ++this.unlikeCount;
    }
    public void removeLike(UserVote.VoteType voteType) {
        if(voteType.equals(UserVote.VoteType.LIKE)) --this.likeCount;
        else --this.unlikeCount;
    }

}
