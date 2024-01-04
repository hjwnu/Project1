package com.mainproject.be28.domain.shopping.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = 642001353L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.mainproject.be28.global.auditable.QAuditable _super = new com.mainproject.be28.global.auditable.QAuditable(this);

    public final com.mainproject.be28.domain.shopping.cart.entity.QCart cart;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QDeliveryInfo deliveryInfo;

    public final com.mainproject.be28.domain.member.entity.QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final ListPath<OrderItem, QOrderItem> orderItemList = this.<OrderItem, QOrderItem>createList("orderItemList", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final com.mainproject.be28.domain.shopping.payment.entity.QPayInfo payInfo;

    public final EnumPath<Order.Status> status = createEnum("status", Order.Status.class);

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cart = inits.isInitialized("cart") ? new com.mainproject.be28.domain.shopping.cart.entity.QCart(forProperty("cart"), inits.get("cart")) : null;
        this.deliveryInfo = inits.isInitialized("deliveryInfo") ? new QDeliveryInfo(forProperty("deliveryInfo")) : null;
        this.member = inits.isInitialized("member") ? new com.mainproject.be28.domain.member.entity.QMember(forProperty("member")) : null;
        this.payInfo = inits.isInitialized("payInfo") ? new com.mainproject.be28.domain.shopping.payment.entity.QPayInfo(forProperty("payInfo"), inits.get("payInfo")) : null;
    }

}

