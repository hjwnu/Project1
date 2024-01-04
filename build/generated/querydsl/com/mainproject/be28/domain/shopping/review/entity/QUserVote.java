package com.mainproject.be28.domain.shopping.review.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserVote is a Querydsl query type for UserVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserVote extends EntityPathBase<UserVote> {

    private static final long serialVersionUID = 135581520L;

    public static final QUserVote userVote = new QUserVote("userVote");

    public final com.mainproject.be28.global.auditable.QAuditable _super = new com.mainproject.be28.global.auditable.QAuditable(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final BooleanPath unlike = createBoolean("unlike");

    public final BooleanPath userLike = createBoolean("userLike");

    public final NumberPath<Long> voteId = createNumber("voteId", Long.class);

    public QUserVote(String variable) {
        super(UserVote.class, forVariable(variable));
    }

    public QUserVote(Path<? extends UserVote> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserVote(PathMetadata metadata) {
        super(UserVote.class, metadata);
    }

}

