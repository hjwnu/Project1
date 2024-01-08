package com.project1.domain.shopping.review.repository;

import com.project1.domain.shopping.review.entity.ReviewImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project1.domain.shopping.review.entity.QReviewImage.reviewImage;
@Repository
public interface CustomReviewImageRepository {
    Map<Long, List<ReviewImage>> fetchReviewImages(List<Long> itemIds);

    class CustomReviewImageRepositoryImpl implements CustomReviewImageRepository {
        private final JPAQueryFactory queryFactory;

        public CustomReviewImageRepositoryImpl(JPAQueryFactory queryFactory) {
            this.queryFactory = queryFactory;
        }

        @Override
        public Map<Long, List<ReviewImage>> fetchReviewImages(List<Long> reviewIds) {
            List<ReviewImage> images = queryFactory
                    .selectFrom(reviewImage)
                    .where(reviewImage.review.reviewId.in(reviewIds))
                    .fetch();

            return images.stream().collect(Collectors.groupingBy(image -> image.getReview().getReviewId()));
        }
    }
}
