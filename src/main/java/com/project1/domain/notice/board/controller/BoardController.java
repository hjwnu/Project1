package com.project1.domain.notice.board.controller;

import com.project1.domain.notice.board.service.BoardService;
import com.project1.global.response.SingleResponseDto;
import com.project1.domain.notice.board.dto.BoardDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Deprecated
@RestController
@RequestMapping("/boards")
public class BoardController {
    private BoardService boardService;

    @GetMapping("/{boardId}")
    public ResponseEntity getBoardById(@PathVariable("boardId") Long boardId) {
        BoardDto.ResponseDto board =boardService.getBoard(boardId);
        SingleResponseDto response = new SingleResponseDto(board, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}