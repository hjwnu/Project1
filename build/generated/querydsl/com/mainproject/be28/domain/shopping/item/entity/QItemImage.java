package com.mainproject.be28.domain.shopping.item.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemImage is a Querydsl query type for ItemImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemImage extends EntityPathBase<ItemImage> {

    private static final long serialVersionUID = 2048505746L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemImage itemImage = new QItemImage("itemImage");

    public final com.mainproject.be28.global.auditable.QAuditable _super = new com.mainproject.be28.global.auditable.QAuditable(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath imageName = createString("imageName");

    public final QItem item;

    public final NumberPath<Long> itemImageId = createNumber("itemImageId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath originalName = createString("originalName");

    public final StringPath path = createString("path");

    public final StringPath representationImage = createString("representationImage");

    public QItemImage(String variable) {
        this(ItemImage.class, forVariable(variable), INITS);
    }

    public QItemImage(Path<? extends ItemImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemImage(PathMetadata metadata, PathInits inits) {
        this(ItemImage.class, metadata, inits);
    }

    public QItemImage(Class<? extends ItemImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item")) : null;
    }

}

