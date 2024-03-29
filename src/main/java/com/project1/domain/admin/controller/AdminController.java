package com.project1.domain.admin.controller;

import com.project1.domain.admin.service.AdminService;
import com.project1.domain.notice.board.dto.BoardDto;
import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.global.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;
    private final HttpStatus ok = HttpStatus.OK;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping(value = "/item"
            , consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ItemDto.ResponseWithReview>> createItem(@Valid @RequestPart ItemDto.Post requestBody
            , @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList) throws IOException {

        ItemDto.ResponseWithReview item =  adminService.registerItem(requestBody, itemImgFileList);

        HttpStatus created = HttpStatus.CREATED;

        SingleResponseDto<ItemDto.ResponseWithReview> response = new SingleResponseDto<>(item, created);

        return new ResponseEntity<>(response, created);
    }

    @PatchMapping(value = "/item/{item-id}", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<SingleResponseDto<ItemDto.ResponseWithReview>> updateItem(@PathVariable("item-id") @Positive long itemId,
                                                                                    @Valid @RequestPart ItemDto.Patch requestBody, @Nullable @RequestPart(name = "images") List<MultipartFile> itemImgFileList)
            throws IOException {

        requestBody.setItemId(itemId);
        ItemDto.ResponseWithReview itemResponseWithReview =  adminService.updateItem(requestBody, itemImgFileList);

        SingleResponseDto<ItemDto.ResponseWithReview> response = new SingleResponseDto<>(itemResponseWithReview, ok);
        return new ResponseEntity<>(response,ok);
    }

    @DeleteMapping("/item/{item-id}")
    public ResponseEntity<HttpStatus> deleteItem(@PathVariable("item-id") @Positive long itemId){

        adminService.deleteItem(itemId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/board/register")
    public ResponseEntity<SingleResponseDto<BoardDto.ResponseDto>> createBoard(@RequestBody BoardDto.PostDto boardPostDto) {
        BoardDto.ResponseDto board = adminService.createBoard(boardPostDto);
        SingleResponseDto<BoardDto.ResponseDto> response = new SingleResponseDto<>(board, HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/board/update/{board-Id}")
    public ResponseEntity<SingleResponseDto<BoardDto.ResponseDto>> updateBoard(@PathVariable("board-Id") Long boardId, @RequestBody BoardDto.PatchDto patchDto) {
        BoardDto.ResponseDto boardResponseDto = adminService.updateBoard(boardId, patchDto);
        SingleResponseDto<BoardDto.ResponseDto> response = new SingleResponseDto<>(boardResponseDto, HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/board/delete/{board-Id}")
    public void deleteBoard(@PathVariable("board-Id") Long boardId) {
        adminService.deleteBoard(boardId);
    }

}
