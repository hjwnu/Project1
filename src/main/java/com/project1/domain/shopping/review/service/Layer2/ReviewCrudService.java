package com.project1.domain.shopping.review.service.Layer2;

import com.project1.domain.shopping.review.dto.ReviewDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.repository.ReviewRepository;
import com.project1.global.generic.GenericCrudService;
import com.project1.global.generic.GenericMapper;
import com.project1.domain.member.entity.Member;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.review.mapper.ReviewMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ReviewCrudService
        extends GenericCrudService.GenericCrud<Review, ReviewDto.ReviewPostDto, ReviewDto.ReviewResponseDto, ReviewDto.ReviewPatchDto, Long> {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper mapper;

    public ReviewCrudService(ReviewRepository reviewRepository, ReviewMapper mapper) {
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
    }
    public  Review create(ReviewDto.ReviewPostDto postDto, Item item, Member member) {
        Review review = Review.builder()
                .item(item)
                .content(postDto.getContent())
                .score(postDto.getScore())
                .member(member)
                .build();
        return reviewRepository.save(review);
    }
    @Override
    protected JpaRepository<Review, Long> getRepository() {
        return reviewRepository;
    }

    @Override
    protected void setId(Review newEntity, Long id) {
        newEntity.setReviewId(id);
    }

    @Override
    protected GenericMapper<Review, ReviewDto.ReviewPostDto, ReviewDto.ReviewResponseDto, ReviewDto.ReviewPatchDto> getMapper() {
        return mapper;
    }


}
