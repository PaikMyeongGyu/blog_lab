package com.search.search.board.service;

import static com.search.search.board.domain.Board.newBoard;
import static com.search.search.board.domain.BoardDescription.createDescription;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.search.search.board.domain.Board;
import com.search.search.board.domain.BoardDescription;
import com.search.search.board.repository.BoardDescriptionJpaRepository;
import com.search.search.board.repository.BoardJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardDummyService {
    private final BoardJpaRepository boardJpaRepository;
    private final BoardDescriptionJpaRepository boardDescriptionJpaRepository;

    @Transactional
    public void insertThousandData(List<String> titles) {
        titles.forEach(title -> {
            Board board = newBoard(title, "레프리");
            boardJpaRepository.save(board);

            BoardDescription description = createDescription(title, board.getId());
            boardDescriptionJpaRepository.save(description);
        });
    }
}
