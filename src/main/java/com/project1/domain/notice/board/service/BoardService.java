package com.project1.domain.notice.board.service;

import com.project1.global.exception.BusinessLogicException;
import com.project1.global.exception.ExceptionCode;
import com.project1.global.generic.GenericCrudService;
import com.project1.global.generic.GenericMapper;
import com.project1.domain.notice.board.dto.BoardDto;
import com.project1.domain.notice.board.entity.Board;
import com.project1.domain.notice.board.mapper.BoardMapper;
import com.project1.domain.notice.board.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService
    extends GenericCrudService.GenericCrud<Board, BoardDto.PostDto, BoardDto.ResponseDto, BoardDto.PatchDto, Long> {
    private final BoardRepository boardRepository;
    private final BoardMapper mapper;

    public BoardService(BoardRepository boardRepository, BoardMapper mapper) {
        this.boardRepository = boardRepository;
        this.mapper = mapper;
    }

    public BoardDto.ResponseDto createBoard(BoardDto.PostDto boardPostDto) {
//        Board board = mapper.boardPostDtoToBoard(boardPostDto);
//        board.setMember(memberVerifyService.findTokenMember());
//       board =  boardRepository.save(board);
        return mapper.entityToResponseDto(new Board());
//        return null;
    }

    public BoardDto.ResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
        board.setViewCount(board.getViewCount()+1);
        boardRepository.save(board);
        return  mapper.entityToResponseDto(board);
    }

    public Page<BoardDto.PageDto> getAllBoards(int page, int size) {
        List<Board> boardList =boardRepository.findAll();
        List<BoardDto.PageDto> boardResponseDtoList = new ArrayList<>();
        for(Board board : boardList){
            boardResponseDtoList.add(mapper.entityToPageDto(board));
        }
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return new PageImpl<>(boardResponseDtoList, pageRequest, boardResponseDtoList.size());
    }



    private Board verifyExistBoard(long boardId) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));
    }

    public void deleteBoard(Long boardId) {
        Board board = verifyExistBoard(boardId);
        boardRepository.delete(board);
    }

    @Override
    protected JpaRepository<Board, Long> getRepository() {
        return boardRepository;
    }

    @Override
    protected void setId(Board newEntity, Long aLong) {
        newEntity.setBoardId(aLong);
    }

    @Override
    protected GenericMapper<Board, BoardDto.PostDto, BoardDto.ResponseDto, BoardDto.PatchDto, Long> getMapper() {
        return mapper;
    }

    @Override
    protected List<Board> findByName(String str) {
        return boardRepository.findAll();
    }
}
