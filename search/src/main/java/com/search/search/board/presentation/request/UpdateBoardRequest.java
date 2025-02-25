package com.search.search.board.presentation.request;

public record UpdateBoardRequest(
        Long boardId,
        String title,
        String description,
        String author
) {
}
