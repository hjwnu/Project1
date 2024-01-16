package com.project1.domain.shopping.item.repository;

import com.project1.domain.shopping.item.entity.ItemImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project1.domain.shopping.item.entity.QItemImage.itemImage;

@Repository
public interface CustomImageRepository {
    Map<Long, List<ItemImage>> fetchItemImages(List<Long> itemIds);

    class CustomImageRepositoryImpl implements CustomImageRepository {
        private final JPAQueryFactory queryFactory;

        public CustomImageRepositoryImpl(JPAQueryFactory queryFactory) {
            this.queryFactory = queryFactory;
        }

        @Override
        public Map<Long, List<ItemImage>> fetchItemImages(List<Long> itemIds) {
            List<ItemImage> images = queryFactory
                    .selectFrom(itemImage)
                    .where(itemImage.item.itemId.in(itemIds))
                    .fetch();

            return images.stream().collect(Collectors.groupingBy(image -> image.getItem().getItemId()));
        }
    }
}
