package com.search.search.board.service;

import static com.search.search.board.domain.Board.newBoard;
import static com.search.search.board.domain.BoardDescription.createDescription;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.search.search.board.domain.Board;
import com.search.search.board.domain.BoardDescription;
import com.search.search.board.repository.BoardDescriptionJpaRepository;
import com.search.search.board.repository.BoardJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardJpaRepository boardJpaRepository;
    private final BoardDescriptionJpaRepository boardDescriptionJpaRepository;

    @Transactional
    public Long publishBoard(String title, String description, String author) {
        Board board = newBoard(title, author);
        boardJpaRepository.save(board);

        BoardDescription boardDescription = createDescription(description, board.getId());
        boardDescriptionJpaRepository.save(boardDescription);
        return board.getId();
    }
}
