package com.project1.domain.shopping.review.controller;

import com.project1.domain.shopping.review.entity.UserVote;
import com.project1.global.response.SingleResponseDto;
import com.project1.domain.shopping.review.dto.ReviewPatchDto;
import com.project1.domain.shopping.review.dto.ReviewPostDto;
import com.project1.domain.shopping.review.dto.ReviewResponseDto;
import com.project1.domain.shopping.review.service.Layer1.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;



@RestController
@RequestMapping("/review")
@Validated
@Slf4j
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private  final HttpStatus ok = HttpStatus.OK;

    @PostMapping("/{item-id}/{score}")
    public ResponseEntity<SingleResponseDto<ReviewResponseDto>> createReview(@PathVariable("item-id")long itemId,
                                                                             @RequestBody @Valid ReviewPostDto reviewPostDto,
                                                                             @PathVariable("score") int score) {
        reviewPostDto.setScore(score);
        reviewPostDto.setItemId(itemId);
        SingleResponseDto<ReviewResponseDto> response = new SingleResponseDto<>(reviewService.createReview(reviewPostDto),ok);
        return new ResponseEntity<>(response, ok);

    }
    @PatchMapping("/{review-id}")
    public ResponseEntity<SingleResponseDto<ReviewResponseDto>> patchReview(@PathVariable("review-id") @Positive long reviewId,
                                      @Valid @RequestBody ReviewPatchDto reviewPatchDto){
        SingleResponseDto<ReviewResponseDto> responses = new SingleResponseDto<>(reviewService.updateReview(reviewId, reviewPatchDto),ok);
        return new ResponseEntity<>(responses, ok);
    }
    @DeleteMapping("/{review-id}")
    public ResponseEntity<HttpStatus> deleteReview(@PathVariable("review-id") @Positive long reviewId){
        reviewService.deleteReview(reviewId);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/{review-id}/vote")
    public ResponseEntity<SingleResponseDto<Long>> handleLike(@PathVariable("review-id") Long reviewId, @RequestParam UserVote.VoteType voteType) {
        SingleResponseDto<Long> response = new SingleResponseDto<>(reviewService.handleLike(reviewId,voteType),ok);
        return new ResponseEntity<>(response, ok);
        }


}


