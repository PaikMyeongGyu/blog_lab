package com.search.search.board.presentation.request;

public record PublishBoardRequest(
        String title,
        String description,
        String author
) {
}
