package com.project1.domain.shopping.review.service.Layer1;

import com.project1.domain.member.service.Layer2.MemberVerifyService;
import com.project1.domain.shopping.item.entity.Item;
import com.project1.domain.shopping.item.service.layer2.ItemCrudService;
import com.project1.domain.shopping.review.dto.ReviewPatchDto;
import com.project1.domain.shopping.review.dto.ReviewPostDto;
import com.project1.domain.shopping.review.dto.ReviewResponseDto;
import com.project1.domain.shopping.review.entity.Review;
import com.project1.domain.shopping.review.entity.UserVote;
import com.project1.domain.shopping.review.service.Layer2.ReviewCrudService;
import com.project1.domain.shopping.review.service.Layer2.UserVoteService;
import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.domain.member.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    public final ReviewCrudService crudService;
    private final ItemCrudService itemService;
    private final MemberVerifyService memberVerifyService;
    private final UserVoteService voteService;

    public ReviewService(ReviewCrudService crudService, ItemCrudService itemService, MemberVerifyService memberVerifyService, UserVoteService voteService) {
        this.crudService = crudService;
        this.itemService = itemService;
        this.memberVerifyService = memberVerifyService;
        this.voteService = voteService;
    }

    public ReviewResponseDto createReview(ReviewPostDto postDto) {
        Item item = itemService.findEntity(postDto.getItemId());
        Member member = memberVerifyService.findTokenMember();
        Review review = crudService.create(postDto,item,member);
        return crudService.entityToResponse(crudService.save(review));
    }

    public ReviewResponseDto updateReview(long reviewId, ReviewPatchDto patchDto) {
        Review review = crudService.update(reviewId,patchDto);
        return crudService.entityToResponse(review);
    }

    public void deleteReview(long reviewId) {
        if(isReviewOwner(reviewId)) crudService.delete(reviewId);
        else throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_AUTHORIZED);
    }

    @Transactional
    public Long handleLike(Long reviewId, UserVote.VoteType voteType) {
        Long memberId = memberVerifyService.findTokenMember().getMemberId();
        Review review = crudService.findEntity(reviewId);

        if(voteService.verifyDuplicateVote(memberId, reviewId,voteType)) cancelVote(reviewId, voteType, memberId, review);
        else  addVote(reviewId, voteType, memberId, review);

        return voteType.equals(UserVote.VoteType.LIKE) ? review.getLikeCount() : review.getUnlikeCount();
    }

    private void addVote(Long reviewId, UserVote.VoteType voteType, Long memberId, Review review) {
        review.addLike(voteType);
        voteService.save(memberId, reviewId, voteType);
    }

    private void cancelVote(Long reviewId, UserVote.VoteType voteType, Long memberId, Review review) {
        review.removeLike(voteType);
        voteService.delete(memberId, reviewId,voteType);
    }


    private boolean isReviewOwner(long reviewId) {
        Review review = crudService.findEntity(reviewId);
        return review.getMember().equals(memberVerifyService.findTokenMember());
    }

}