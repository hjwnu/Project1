package com.project1.domain.shopping.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryInfo is a Querydsl query type for DeliveryInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryInfo extends EntityPathBase<DeliveryInfo> {

    private static final long serialVersionUID = 1241124904L;

    public static final QDeliveryInfo deliveryInfo = new QDeliveryInfo("deliveryInfo");

    public final StringPath address = createString("address");

    public final NumberPath<Long> deliveryId = createNumber("deliveryId", Long.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath recipient = createString("recipient");

    public QDeliveryInfo(String variable) {
        super(DeliveryInfo.class, forVariable(variable));
    }

    public QDeliveryInfo(Path<? extends DeliveryInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryInfo(PathMetadata metadata) {
        super(DeliveryInfo.class, metadata);
    }

}

