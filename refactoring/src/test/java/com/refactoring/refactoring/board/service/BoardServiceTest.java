package com.refactoring.refactoring.board.service;

import com.refactoring.refactoring.board.domain.Board;
import com.refactoring.refactoring.board.domain.repository.BoardJpaRepository;
import com.refactoring.refactoring.board.domain.repository.BoardQueryRepository;
import com.refactoring.refactoring.board.presentation.response.BoardResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.refactoring.refactoring.board.common.PageSize.SMALL_PAGE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {
    @Autowired
    BoardService boardService;

    @Autowired
    BoardJpaRepository boardJpaRepository;

    @Autowired
    BoardQueryRepository boardQueryRepository;

    @AfterEach
    void tearDown() {
        boardJpaRepository.deleteAllInBatch();
    }

    @DisplayName("게시글 생성 테스트")
    @Test
    void boardTest1() {
        // when
        Board board = Board.of("board", "boardTest");
        boardJpaRepository.save(board);

        // given
        Board findBoard = boardJpaRepository.findById(board.getId()).get();

        // then
        Assertions.assertThat(findBoard.getId()).isEqualTo(board.getId());
        Assertions.assertThat(findBoard.getTitle()).isEqualTo("board");
        Assertions.assertThat(findBoard.getContent()).isEqualTo("boardTest");
    }

    @DisplayName("게시글 다중 생성 및 조회 테스트")
    @Test
    void boardTest2() {
        // when
        for (int i = 0; i < 15; i++) {
            Board board = Board.of("board" + i, "boardTest" + i);
            boardJpaRepository.save(board);
        }

        // given
        List<Board> boards = boardQueryRepository.getBoardByIdDesc(null, SMALL_PAGE.getSize() + 1);

        Boolean hasNext = boards.size() > SMALL_PAGE.getSize();
        int size = hasNext ? SMALL_PAGE.getSize() : boards.size();
        if (hasNext) {
            boards = boards.subList(0, size);
        }
        Long lastBoardId = !boards.isEmpty() ? boards.get(boards.size() - 1).getId() : null;

        // then
        Assertions.assertThat(lastBoardId).isEqualTo(boards.get(boards.size() - 1).getId());
        Assertions.assertThat(hasNext).isTrue();
        Assertions.assertThat(size).isEqualTo(SMALL_PAGE.getSize());

        // when
        List<Board> nextBoards = boardQueryRepository.getBoardByIdDesc(lastBoardId, SMALL_PAGE.getSize() + 1);
        Boolean hasNext2 = nextBoards.size() > SMALL_PAGE.getSize();
        int size2 = hasNext2 ? SMALL_PAGE.getSize() : nextBoards.size();
        if (hasNext2) {
            nextBoards = nextBoards.subList(0, size);
        }
        Long lastBoardId2 = !nextBoards.isEmpty() ? nextBoards.get(nextBoards.size() - 1).getId() : null;

        // then
        Assertions.assertThat(lastBoardId2).isEqualTo(nextBoards.get(nextBoards.size() - 1).getId());
        Assertions.assertThat(hasNext2).isFalse();
        Assertions.assertThat(size2).isEqualTo(5);
    }
}