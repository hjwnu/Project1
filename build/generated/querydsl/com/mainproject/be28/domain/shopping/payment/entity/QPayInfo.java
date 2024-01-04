package com.mainproject.be28.domain.shopping.payment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPayInfo is a Querydsl query type for PayInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPayInfo extends EntityPathBase<PayInfo> {

    private static final long serialVersionUID = 1011448313L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPayInfo payInfo = new QPayInfo("payInfo");

    public final StringPath impUid = createString("impUid");

    public final com.mainproject.be28.domain.shopping.order.entity.QOrder order;

    public final NumberPath<Long> paymentId = createNumber("paymentId", Long.class);

    public QPayInfo(String variable) {
        this(PayInfo.class, forVariable(variable), INITS);
    }

    public QPayInfo(Path<? extends PayInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPayInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPayInfo(PathMetadata metadata, PathInits inits) {
        this(PayInfo.class, metadata, inits);
    }

    public QPayInfo(Class<? extends PayInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new com.mainproject.be28.domain.shopping.order.entity.QOrder(forProperty("order"), inits.get("order")) : null;
    }

}

