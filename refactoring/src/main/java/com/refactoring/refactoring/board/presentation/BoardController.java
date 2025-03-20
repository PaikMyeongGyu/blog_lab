package com.refactoring.refactoring.board.presentation;

import static com.refactoring.refactoring.board.common.PageSize.SMALL_PAGE;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.refactoring.refactoring.board.common.PageResponse;
import com.refactoring.refactoring.board.domain.Board;
import com.refactoring.refactoring.board.presentation.response.BoardResponse;
import com.refactoring.refactoring.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Void> createBoard(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        boardService.createBoard(title, content);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<BoardResponse> getBoardsByIdDesc(
            @RequestParam(value = "boardId", required = false) Long boardId
    ) {
        List<Board> boards = boardService.getBoardByIdDesc(boardId, SMALL_PAGE.getSize() + 1);
        Boolean hasNext = boards.size() > SMALL_PAGE.getSize();
        int size = hasNext ? SMALL_PAGE.getSize() : boards.size();
        if (hasNext) {
            boards = boards.subList(0, size - 1);
        }
        Long lastBoardId = !boards.isEmpty() ? boards.get(boards.size() - 1).getId() : null;
        BoardResponse boardResponse = new BoardResponse(size, hasNext, lastBoardId, boards);
        return ResponseEntity.ok().body(boardResponse);
    }

    @GetMapping("/v2")
    public ResponseEntity<PageResponse<Board>> getBoardsByIdDesc2(
            @RequestParam(value = "boardId", required = false) Long boardId
    ) {
        List<Board> boards = boardService.getBoardByIdDesc(boardId, SMALL_PAGE.getSize() + 1);
        PageResponse<Board> pageResponse = new PageResponse<>(boards, Board::getId, SMALL_PAGE);
        System.out.println(pageResponse);
        return ResponseEntity.ok().body(pageResponse);
    }
}
