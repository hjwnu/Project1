package com.project1.domain.notice.board.entity;

import com.project1.domain.notice.comment.entity.Comment;
import com.project1.global.generic.Auditable;
import com.project1.domain.member.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Board extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
//    private Long memberId;

    @Column( length = 1000)
    private String content;

    @Column(columnDefinition = "bigint default 0")
    private Long viewCount = 0L;

    @Column(columnDefinition = "bigint default 0")
    private Long likeCount = 0L;

    @Column(nullable = false)
    private String boardCategory;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private List<Comment> comments;
}
