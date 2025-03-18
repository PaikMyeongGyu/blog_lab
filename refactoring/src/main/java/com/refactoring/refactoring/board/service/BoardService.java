package com.refactoring.refactoring.board.service;

import static com.refactoring.refactoring.board.common.PageSize.SMALL_PAGE;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.refactoring.refactoring.board.domain.Board;
import com.refactoring.refactoring.board.domain.repository.BoardJpaRepository;
import com.refactoring.refactoring.board.domain.repository.BoardQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepository boardJpaRepository;
    private final BoardQueryRepository boardQueryRepository;

    @Transactional
    public void createBoard(String title, String content) {
        Board board = Board.of(title, content);
        boardJpaRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> getBoardByIdDesc(Long id, int i) {
        return boardQueryRepository.getBoardByIdDesc(id, SMALL_PAGE.getSize() + 1);
    }
}
