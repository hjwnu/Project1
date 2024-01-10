package com.project1.domain.shopping.item.dto;

import com.project1.domain.shopping.review.dto.ReviewDto;
import lombok.*;

import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


public class ItemDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        private Long itemId;
        @NotBlank(message = "상품명을 입력해주세요.")
        private String name;
        @Min(100)
        private Long price;
        @NotNull
        private String detail;
        @NotNull
        private Long stock;
        @NotNull
        private String color;
        @NotNull
        private String brand;
        @NotNull
        private String category;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch{
        @Nullable
        private Long itemId;
        @Nullable
        private String name;
        @Nullable
        private Long price;
        @Nullable
        private String detail;
        @Nullable
        private Long stock;
        @Nullable
        private String color;
        @Nullable
        private String brand;
        @Nullable
        private String category;
    }

    @Getter
    @AllArgsConstructor
    public static class ResponseWithReview {
        private ResponseDtoWithoutReview item;
        private List<ReviewDto.ReviewResponseDto> reviews;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @Builder
    public static class ResponseDtoWithoutReview {
        private Long itemId;
        private String name;
        private Long price;
        private String detail;
        private String stocks;
        private String color;
        private Double score;
        private String brand;
        private String category;
        private Integer reviewCount;
        private List<ItemImageDto> imageURLs;
    }
}