package com.project1.domain.admin.service;

import com.project1.domain.notice.board.dto.BoardDto;
import com.project1.domain.notice.board.service.BoardService;
import com.project1.domain.shopping.item.dto.ItemDto;
import com.project1.domain.shopping.item.service.layer1.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class AdminService {
    private final BoardService boardService;
    private final ItemService itemService;

    public AdminService(BoardService boardService, ItemService itemService) {
        this.boardService = boardService;
        this.itemService = itemService;
    }

    public ItemDto.Response registerItem(ItemDto.Post requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        return itemService.createItem(requestBody, itemImgFileList);
    }

    public ItemDto.Response updateItem(ItemDto.Patch requestBody, List<MultipartFile> itemImgFileList) throws IOException {
        return itemService.updateItem(requestBody,itemImgFileList);
    }

    public void deleteItem(long itemId) {
        itemService.deleteItem(itemId);
    }

    public BoardDto.ResponseDto createBoard(BoardDto.PostDto requestBody) {
        return boardService.createBoard(requestBody);
    }

    public BoardDto.ResponseDto updateBoard(Long id, BoardDto.PatchDto requestBody) {
        return boardService.entityToResponse(boardService.update(id, requestBody));
    }

    public void deleteBoard(long boardId) {
        boardService.deleteBoard(boardId);
    }
}
