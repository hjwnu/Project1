package com.project1.domain.shopping.item.repository;

import com.project1.domain.shopping.item.dto.ItemSearchCondition;
import com.project1.domain.shopping.item.entity.Item;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.project1.domain.shopping.item.entity.QItem.item;
import static com.project1.domain.shopping.review.entity.QReview.review;


public interface CustomItemRepository {
    List<Item> searchByCondition(ItemSearchCondition condition, Pageable pageable); // 다중 검색 조건 메서드.  Impl 파일에서 메서드 구현

    @RequiredArgsConstructor
    @Slf4j
    class CustomItemRepositoryImpl implements CustomItemRepository{
        private final JPAQueryFactory queryFactory;

        public List<Item> searchByCondition(ItemSearchCondition condition, Pageable pageable){


            return queryFactory
                    .select(Projections.fields(Item.class,
                            item.itemId
                            , item.name, item.price, item.detail, item.stock,
                            item.color, item.brand, item.category,
                            Expressions.as(
                                    JPAExpressions.select(review.count().intValue())
                                            .from(review)
                                            .where(review.item.eq(item)),
                                    "reviewCount"
                            ),
                            Expressions.as(
                                    JPAExpressions.select(review.score.avg())
                                            .from(review)
                                            .where(review.item.eq(item)),
                                    "score"
                            )
                    ))
                    .from(item)
                    .leftJoin(item.reviews, review)
                    .where(createCondition(condition))
                    .groupBy(item)
                    .orderBy(sortCondition(condition).toArray(new OrderSpecifier[0]))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }


        private BooleanExpression createCondition(ItemSearchCondition condition) {
            Map<String, Supplier<BooleanExpression>> conditionSuppliers = new HashMap<>();
            putStrategy(condition, conditionSuppliers);

            BooleanExpression result = null;
            for (String key : conditionSuppliers.keySet()) {
                Supplier<BooleanExpression> supplier = conditionSuppliers.get(key);
                BooleanExpression newCondition = supplier.get();
                if (newCondition != null) {
                    result = result == null ? newCondition : result.and(newCondition);
                }
            }

            return result;
        }


        private List<OrderSpecifier<?>> sortCondition(ItemSearchCondition condition) {
            List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
            Order direction = "desc".equalsIgnoreCase(condition.getOrder()) ? Order.DESC : Order.ASC;

            if (condition.getSort() != null) {
                switch (condition.getSort()) {
                    case "name": orderSpecifiers.add(new OrderSpecifier<>(direction, item.name));
                        break;
                    case "price":orderSpecifiers.add(new OrderSpecifier<>(direction, item.price));
                        break;
                    case "reviewCount":
                        orderSpecifiers.add(new OrderSpecifier<>(direction,
                                JPAExpressions.select(review.count().intValue())
                                        .from(review)
                                        .where(review.item.eq(item))));
                        break;
                    case "score":
                        orderSpecifiers.add(new OrderSpecifier<>(direction,
                                JPAExpressions.select(review.score.avg())
                                        .from(review)
                                        .where(review.item.eq(item))));
                        break;
                    default:
                        orderSpecifiers.add(new OrderSpecifier<>(direction, item.itemId));
                }
            } else {
                orderSpecifiers.add(new OrderSpecifier<>(direction, item.itemId));
            }
            return orderSpecifiers;
        }
        private void putStrategy(ItemSearchCondition condition, Map<String, Supplier<BooleanExpression>> conditionSuppliers) {
            conditionSuppliers.put("category", () -> equalsCategory(condition.getCategory()));
            conditionSuppliers.put("brand", () -> equalsBrand(condition.getBrand()));
            conditionSuppliers.put("color", () -> colorLike(condition.getColor()));
            conditionSuppliers.put("minimumPrice", () -> minimumPrice(condition.getLowPrice()));
            conditionSuppliers.put("maximumPrice", () -> maximumPrice(condition.getHighPrice()));
            conditionSuppliers.put("name", () -> nameLike(condition.getName()));
        }
        private BooleanExpression equalsCategory(String searchCategory){
            return searchCategory == null ? null : item.category.toUpperCase().contains(searchCategory.toUpperCase());
        }

        private BooleanExpression equalsBrand(String searchBrand){
            return searchBrand == null ? null : item.brand.toUpperCase().contains(searchBrand.toUpperCase());
        }
        private BooleanExpression colorLike(String searchColor){
            return searchColor == null ? null : item.color.toUpperCase().like("%"+searchColor.toUpperCase()+"%");
        }
        private BooleanExpression minimumPrice(Long lowPrice){
            return lowPrice==null ? null: item.price.goe(lowPrice);
        }
        private BooleanExpression maximumPrice(Long highPrice){
            return highPrice==null ? null: item.price.loe(highPrice);
        }

        private BooleanExpression nameLike(String searchQuery) {
            return searchQuery == null ? null : item.name.toUpperCase().like("%" + searchQuery.toUpperCase() + "%");
        }

    }
}
