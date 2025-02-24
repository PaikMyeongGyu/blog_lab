package com.search.search.board.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.search.search.board.presentation.request.PublishBoardRequest;
import com.search.search.board.presentation.response.PublishBoardResponse;
import com.search.search.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<PublishBoardResponse> publishBoard(
            @RequestBody PublishBoardRequest req
            ) {
        Long boardId = boardService.publishBoard(req.title(), req.description(), req.author());
        return ResponseEntity.ok(new PublishBoardResponse(boardId));
    }
}
