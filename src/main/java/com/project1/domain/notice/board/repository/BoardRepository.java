package com.project1.domain.notice.board.repository;

import com.project1.domain.notice.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

}