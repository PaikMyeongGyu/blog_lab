package com.refactoring.refactoring.board.presentation.response;

import java.util.List;

import com.refactoring.refactoring.board.domain.Board;

public record BoardResponse(
        Integer size,
        Boolean hasNext,
        Long lastBoardId,
        List<Board> boards
) {
}
