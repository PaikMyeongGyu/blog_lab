package com.search.search.board.presentation;

import static com.search.search.util.PageUtils.PAGE_SIZE;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.search.search.board.presentation.dto.BoardDto;
import com.search.search.board.presentation.request.PublishBoardRequest;
import com.search.search.board.presentation.request.UpdateBoardRequest;
import com.search.search.board.presentation.response.BoardsResponse;
import com.search.search.board.presentation.response.PublishBoardResponse;
import com.search.search.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<BoardsResponse> getBoardsByKeyword(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "pageNumber", required = false) Long pageNumber
    ) {
        pageNumber = pageNumber == null ? 1L : pageNumber;

        Long boardSize = boardService.getBoardSizeByKeyword(keyword);
        List<BoardDto> boards = boardService.getBoardsByKeyword(keyword, pageNumber);
        Boolean hasNext = PAGE_SIZE * pageNumber + boards.size() < boardSize;

        BoardsResponse response = new BoardsResponse(boardSize, pageNumber, hasNext, boards.size(), boards);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v2")
    public ResponseEntity<BoardsResponse> getBoardsByKeywordV2(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "pageNumber", required = false) Long pageNumber
    ) {
        pageNumber = pageNumber == null ? 1L : pageNumber;

        List<BoardDto> boards = boardService.getBoardByTitleOrDescription(keyword, pageNumber);
        Boolean hasNext = boards.size() > PAGE_SIZE;
        if (hasNext) {
            boards = boards.stream()
                            .limit(PAGE_SIZE)
                            .toList();
        }

        BoardsResponse response = new BoardsResponse((long)boards.size(), pageNumber, hasNext, boards.size(), boards);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PublishBoardResponse> publishBoard(
            @RequestBody PublishBoardRequest req
    ) {
        Long boardId = boardService.publishBoard(req.title(), req.description(), req.author());
        return ResponseEntity.ok(new PublishBoardResponse(boardId));
    }

    @PutMapping
    public ResponseEntity<Void> updateBoard(
            @RequestBody UpdateBoardRequest req
    ) {
        boardService.updateBoard(req.boardId(), req.title(), req.description(), req.author());
        return ResponseEntity.noContent().build();
    }
}
