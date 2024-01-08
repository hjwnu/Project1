package com.project1.domain.shopping.review.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPatchDto {
      private Long itemId;
         private Long reviewId;
        private String content;
        private Long score;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPostDto {
        private Long itemId;
        private String content;
        private int score;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ReviewResponseDto {
        private String itemName;
        private String memberName;
        private String content;
        private  int score;
        private LocalDateTime modifiedAt;
        private LocalDateTime createdAt;
        private Long likeCount;
        private Long unlikeCount;
        private List<ReviewImageResponseDto> imageURLs;
    }
}
