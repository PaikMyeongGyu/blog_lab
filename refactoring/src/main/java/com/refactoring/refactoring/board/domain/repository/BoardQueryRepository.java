package com.refactoring.refactoring.board.domain.repository;

import static com.refactoring.refactoring.board.domain.QBoard.board;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.refactoring.refactoring.board.domain.Board;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Board> getBoardByIdDesc(Long boardId, int pageSize) {
        BooleanBuilder boardIdLt = new BooleanBuilder();
        if (boardId != null) {
            boardIdLt.and(board.id.lt(boardId));
        }

        return queryFactory.selectFrom(board)
                .where(boardIdLt)
                .orderBy(board.id.desc())
                .limit(pageSize)
                .fetch();
    }
}
