package com.search.search.board.presentation.response;

import java.util.List;

import com.search.search.board.presentation.dto.BoardDto;

public record BoardsResponse(
        Long totalCount,
        Long pageNumber,
        Boolean hasNext,
        Integer size,
        List<BoardDto> boards
) {
}
