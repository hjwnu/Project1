package com.project1.domain.shopping.complain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class ComplainDto {
    @Setter    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComplainPatchDto {
        private Long complainId;
        private String title;
        private String content;
    }

    @Getter    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComplainPostDto {
        @Positive
        private Long ItemId;
        @NotBlank(message = "문의제목을 적어주세요")
        private String title;
        @NotBlank(message = "문의내용을 적어주세요")
        private String content;
    }

    @Getter    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComplainResponseDto {
        private String title;
        private String content;
        private String name;
        private String itemName;//추가
        private LocalDateTime modifiedAt;
        private LocalDateTime createdAt;
    }

    @Getter    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ComplainResponsesDto {
        private String name;
        private String itemName;
        private String title;
    }
}
