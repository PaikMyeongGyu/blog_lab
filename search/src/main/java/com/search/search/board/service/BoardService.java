package com.search.search.board.service;

import static com.search.search.board.domain.Board.newBoard;
import static com.search.search.board.domain.BoardDescription.createDescription;
import static com.search.search.util.PageUtils.PAGE_SIZE;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.search.search.board.domain.Board;
import com.search.search.board.domain.BoardDescription;
import com.search.search.board.presentation.dto.BoardDto;
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

    @Transactional
    public void updateBoard(Long boardId, String title, String description, String author) {
        Board board = getBoard(boardId);
        board.updateTitle(title);
        board.updateAuthor(author);
        boardJpaRepository.save(board);

        BoardDescription boardDescription = getDescription(boardId);
        boardDescription.updateDescription(description);
        boardDescriptionJpaRepository.save(boardDescription);
    }

    @Transactional(readOnly = true)
    public List<BoardDto> getBoardsByKeyword(String keyword, Long pageNumber) {
        List<Long> boardIds = getBoardIdsByKeyword(keyword, pageNumber);
        return getBoardsByIds(boardIds);
    }

    @Transactional(readOnly = true)
    public Long getBoardSizeByKeyword(String keyword) {
        return boardJpaRepository.countBoardByKeyword(keyword);
    }

    private List<BoardDto> getBoardsByIds(List<Long> boardIds) {
         return boardJpaRepository.findAllById(boardIds)
                .stream()
                .map(board -> new BoardDto(board.getId(), board.getTitle(), board.getAuthor()))
                .toList();
    }

    private List<Long> getBoardIdsByKeyword(String keyword, Long pageNumber) {
        return boardJpaRepository.searchBoardIdByKeyword(keyword, PAGE_SIZE, (pageNumber - 1) * PAGE_SIZE);
    }

    private Board getBoard(Long boardId) {
        return boardJpaRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 번호입니다."));
    }

    private BoardDescription getDescription(Long boardId) {
        return boardDescriptionJpaRepository.findByBoardId(boardId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 게시글 번호입니다."));
    }
}
