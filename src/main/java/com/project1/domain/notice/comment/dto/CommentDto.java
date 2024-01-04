package com.project1.domain.notice.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {

    @Getter
    @Setter
    public static class PostDto {
        private Long boardId;
        @NotNull
        private String content;
    }

    @Getter @Setter
    public static class PatchDto {
        private Long boardId;
        private Long commentId;
        private String content;
    }

    @Getter @Setter @Builder
    public static class ResponseDto {
        private String content;
        private String memberName;
        private Long likeCount;
        private LocalDateTime createdAt;
    }
}