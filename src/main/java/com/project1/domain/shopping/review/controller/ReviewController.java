package com.project1.domain.shopping.review.controller;

import com.project1.domain.shopping.review.dto.ReviewDto;
import com.project1.domain.shopping.review.entity.UserVote;
import com.project1.global.response.SingleResponseDto;
import com.project1.domain.shopping.review.service.Layer1.ReviewService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/review")
@Validated
@Slf4j
@AllArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private  final HttpStatus ok = HttpStatus.OK;

    @PostMapping(value = "/{item-id}/{score}",
            consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ReviewDto.ReviewResponseDto>> createReview(@PathVariable("item-id")long itemId,
                                                                                       @RequestPart @Valid ReviewDto.ReviewPostDto requestBody,
                                                                                       @PathVariable("score")@PositiveOrZero int score ,
                                                                                       @Nullable @RequestPart(name = "images") List<MultipartFile> reviewImgFileList) throws IOException  {
        requestBody.setScore(score);
        requestBody.setItemId(itemId);
        SingleResponseDto<ReviewDto.ReviewResponseDto> response = new SingleResponseDto<>(reviewService.createReview(reviewImgFileList, requestBody),ok);
        return new ResponseEntity<>(response, ok);

    }
    @PatchMapping(value = "/{review-id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ReviewDto.ReviewResponseDto>> patchReview(@PathVariable("review-id") @Positive long reviewId,
                                                                                      @Valid @RequestPart ReviewDto.ReviewPatchDto requestBody
    ,@Nullable @RequestPart(name = "images") List<MultipartFile> reviewImgFileList) throws IOException {
        SingleResponseDto<ReviewDto.ReviewResponseDto> responses = new SingleResponseDto<>(reviewService.updateReview(reviewId, requestBody, reviewImgFileList),ok);
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


