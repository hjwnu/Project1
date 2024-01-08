package com.project1.domain.shopping.review.mapper;

import com.project1.domain.shopping.review.dto.ReviewDto;
import com.project1.domain.shopping.review.dto.ReviewImageResponseDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.entity.ReviewImage;
import com.project1.global.generic.GenericMapper;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper  extends GenericMapper<Review, ReviewDto.ReviewPostDto, ReviewDto.ReviewResponseDto, ReviewDto.ReviewPatchDto, Long> {
    default ReviewDto.ReviewResponseDto entityToResponseDto(Review entity) {
        if ( entity == null ) {
            return null;
        }

        return ReviewDto.ReviewResponseDto.builder()
                .itemName(entity.getItem().getName())
                .memberName(entity.getMember().getName())
                .content(entity.getContent())
                .score(entity.getScore()) //상품별점추가
                .modifiedAt(entity.getModifiedAt())//수정일 추가
                .createdAt(entity.getCreatedAt())
                .likeCount(entity.getLikeCount()==null?0: entity.getLikeCount())
                .unlikeCount(entity.getUnlikeCount()==null?0: entity.getUnlikeCount())
                .imageURLs(getImageResponseDto(entity))
                .build();
    }
    default List<ReviewImageResponseDto> getImageResponseDto(Review review) {
        List<ReviewImageResponseDto> reviewImageResponseList = new ArrayList<>();
        List<ReviewImage> imageList = review.getImages();

        if (imageList != null) {
            for (ReviewImage image : imageList) {
                ReviewImageResponseDto reviewImageResponseDto = imageToResponse(image);
                reviewImageResponseList.add(reviewImageResponseDto);
            }
        }
        return reviewImageResponseList;
    }
    default ReviewImageResponseDto imageToResponse(ReviewImage image) {
        return ReviewImageResponseDto.builder()
                .imageName(image.getImageName())
                .URL(image.getBaseUrl() + image.getImageName())
                .representationImage(image.getRepresentationImage())
                .build();
    }
}