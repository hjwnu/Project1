package com.mainproject.be28.domain.notice.board.dto;

import com.mainproject.be28.domain.notice.comment.dto.CommentDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter @Builder
public class BoardDto {

    public static class PostDto {
        @NotNull
        private String title;
        @NotNull
        private String content;
        @NotNull
        private String boardCategory;

    }


    @Getter @Setter @Builder
    public static class ResponseDto {
        private String memberName;
        private String title;
        private String content;
        private long viewCount;
        private List<CommentDto.ResponseDto> comments;
        private LocalDateTime createdAt;
    }
    @Getter @Setter @Builder
    public static class PageDto  {
        private String memberName;
        private String title;
        private String content;
        private Long viewCount;
        private Long commentCount;
        private LocalDateTime createdAt;
    }

    @Getter
    @Setter
    public static class PatchDto {
        @NotNull
        private Long boardId;
        @NotNull
        private Long memberId;
        private String title;

        private String content;
        private String boardCategory;
    }
}
