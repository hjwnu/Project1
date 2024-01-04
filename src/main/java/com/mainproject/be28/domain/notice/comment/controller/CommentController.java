package com.mainproject.be28.domain.notice.comment.controller;

import com.mainproject.be28.domain.notice.comment.dto.CommentDto;
import com.mainproject.be28.domain.notice.comment.mapper.CommentMapper;
import com.mainproject.be28.domain.notice.comment.service.CommentService;
import com.mainproject.be28.global.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Deprecated
@RestController
@RequestMapping("/comments")
public class CommentController {
    private CommentService commentService;
    private CommentMapper mapper;

    @PostMapping("/new")
    public ResponseEntity createComment(@RequestBody CommentDto.PostDto postDto) {
        SingleResponseDto response = new SingleResponseDto(commentService.createComment(postDto), HttpStatus.CREATED);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity getCommentById(@PathVariable("commentId") Long commentId){
        CommentDto.ResponseDto comment = commentService.getCommentById(commentId);
        SingleResponseDto response = new SingleResponseDto(comment, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId, @RequestBody CommentDto.PatchDto patchDto) {
        CommentDto.ResponseDto comment = commentService.updateComment(commentId, patchDto);
        SingleResponseDto response = new SingleResponseDto(comment, HttpStatus.OK);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}