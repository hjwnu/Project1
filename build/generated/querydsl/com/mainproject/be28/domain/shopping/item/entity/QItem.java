package com.mainproject.be28.domain.shopping.item.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 565192521L;

    public static final QItem item = new QItem("item");

    public final com.mainproject.be28.global.auditable.QAuditable _super = new com.mainproject.be28.global.auditable.QAuditable(this);

    public final StringPath brand = createString("brand");

    public final StringPath category = createString("category");

    public final StringPath color = createString("color");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath detail = createString("detail");

    public final ListPath<ItemImage, QItemImage> Images = this.<ItemImage, QItemImage>createList("Images", ItemImage.class, QItemImage.class, PathInits.DIRECT2);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final ListPath<com.mainproject.be28.domain.shopping.review.entity.Review, com.mainproject.be28.domain.shopping.review.entity.QReview> reviews = this.<com.mainproject.be28.domain.shopping.review.entity.Review, com.mainproject.be28.domain.shopping.review.entity.QReview>createList("reviews", com.mainproject.be28.domain.shopping.review.entity.Review.class, com.mainproject.be28.domain.shopping.review.entity.QReview.class, PathInits.DIRECT2);

    public final NumberPath<Long> stock = createNumber("stock", Long.class);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

