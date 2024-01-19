package com.project1.domain.shopping.review.entity;

import com.project1.global.generic.Auditable;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.item.entity.Item;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ReviewImage> Images;

    public void addLike(UserVote.VoteType voteType) {
        long startCount = 1;
        switch (voteType) {
            // If given type count is null, init the value 1.
            // Otherwise, return the count by adding 1 to it.
            case LIKE:
                this.likeCount=this.likeCount==null? startCount:++this.likeCount;
                break;
            case UNLIKE:
                this.unlikeCount=this.unlikeCount==null?  startCount:++this.unlikeCount;
                break;
            default: throw  new IllegalArgumentException("wrong vote type");
        }
    }
    public void removeLike(UserVote.VoteType voteType) {
        if(voteType.equals(UserVote.VoteType.LIKE)) --this.likeCount;
        else --this.unlikeCount;
    }

}
