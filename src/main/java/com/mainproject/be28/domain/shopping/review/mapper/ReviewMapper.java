package com.mainproject.be28.domain.shopping.review.mapper;

import com.mainproject.be28.domain.shopping.review.dto.ReviewPatchDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewPostDto;
import com.mainproject.be28.domain.shopping.review.dto.ReviewResponseDto;
import com.mainproject.be28.domain.shopping.review.entity.Review;
import com.mainproject.be28.global.generic.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper  extends GenericMapper<Review, ReviewPostDto, ReviewResponseDto, ReviewPatchDto, Long> {
    default ReviewResponseDto entityToResponseDto(Review entity) {
        if ( entity == null ) {
            return null;
        }

        return ReviewResponseDto.builder()
                .itemName(entity.getItem().getName())
                .memberName(entity.getMember().getName())
                .content(entity.getContent())
                .score(entity.getScore()) //상품별점추가
                .modifiedAt(entity.getModifiedAt())//수정일 추가
                .createdAt(entity.getCreatedAt())
                .likeCount(entity.getLikeCount())
                .unlikeCount(entity.getUnlikeCount())
                .build();
    }
}