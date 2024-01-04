package com.project1.domain.shopping.review.service.Layer2;

import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.shopping.review.dto.ReviewResponseDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.mapper.ReviewMapper;
import com.project1.global.generic.GetMineService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class GetMineReviewService
    extends GetMineService.GetMineServiceImpl<Review, ReviewResponseDto> {
    private final ReviewMapper mapper;

    protected GetMineReviewService(MemberVerifyService memberVerifyService, EntityManager entityManager, ReviewMapper mapper) {
        super(memberVerifyService, entityManager, Review.class);
        this.mapper = mapper;
    }

    @Override
    protected ReviewResponseDto entityToResponseDto(Review review) {
        return mapper.entityToResponseDto(review);
    }

    @Override
    protected void setMemberAndItem(ReviewResponseDto reviewResponseDto, Review review) {
        reviewResponseDto.setItemName(review.getItem().getName());
        reviewResponseDto.setMemberName(review.getMember().getName());
    }


}
