package com.mainproject.be28.domain.member.auth.refresh;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRefreshToken is a Querydsl query type for RefreshToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshToken extends EntityPathBase<RefreshToken> {

    private static final long serialVersionUID = 1409095395L;

    public static final QRefreshToken refreshToken = new QRefreshToken("refreshToken");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath jws = createString("jws");

    public final NumberPath<Long> refreshTokenId = createNumber("refreshTokenId", Long.class);

    public final StringPath username = createString("username");

    public QRefreshToken(String variable) {
        super(RefreshToken.class, forVariable(variable));
    }

    public QRefreshToken(Path<? extends RefreshToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRefreshToken(PathMetadata metadata) {
        super(RefreshToken.class, metadata);
    }

}

