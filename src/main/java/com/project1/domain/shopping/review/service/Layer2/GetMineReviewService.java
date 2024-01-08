package com.project1.domain.shopping.review.service.Layer2;

import com.project1.domain.member.service.Layer2.MemberVerificationService;
import com.project1.domain.shopping.review.dto.ReviewDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.mapper.ReviewMapper;
import com.project1.global.generic.GetMineService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class GetMineReviewService
    extends GetMineService.GetMineServiceImpl<Review, ReviewDto.ReviewResponseDto> {
    private final ReviewMapper mapper;

    protected GetMineReviewService(MemberVerificationService memberVerificationService, EntityManager entityManager, ReviewMapper mapper) {
        super(memberVerificationService, entityManager, Review.class);
        this.mapper = mapper;
    }

    @Override
    protected ReviewDto.ReviewResponseDto entityToResponseDto(Review review) {
        return mapper.entityToResponseDto(review);
    }

    @Override
    protected void setMemberAndItem(ReviewDto.ReviewResponseDto reviewResponseDto, Review review) {
        reviewResponseDto.setItemName(review.getItem().getName());
        reviewResponseDto.setMemberName(review.getMember().getName());
    }


}
