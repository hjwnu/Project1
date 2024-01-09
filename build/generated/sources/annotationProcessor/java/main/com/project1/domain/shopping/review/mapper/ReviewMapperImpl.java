package com.project1.domain.shopping.review.mapper;

import com.project1.domain.shopping.review.dto.ReviewDto.ReviewPatchDto;
import com.project1.domain.shopping.review.dto.ReviewDto.ReviewPostDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.entity.Review.ReviewBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-09T04:17:55+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.2.1.jar, environment: Java 17.0.9 (Amazon.com Inc.)"
)
@Component
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public Review postDtoToEntity(ReviewPostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        ReviewBuilder review = Review.builder();

        review.content( postDto.getContent() );
        review.score( postDto.getScore() );

        return review.build();
    }

    @Override
    public Review patchDtoToEntity(ReviewPatchDto patchDto) {
        if ( patchDto == null ) {
            return null;
        }

        ReviewBuilder review = Review.builder();

        review.reviewId( patchDto.getReviewId() );
        review.content( patchDto.getContent() );
        if ( patchDto.getScore() != null ) {
            review.score( patchDto.getScore().intValue() );
        }

        return review.build();
    }
}
