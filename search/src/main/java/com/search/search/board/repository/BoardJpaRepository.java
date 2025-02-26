package com.search.search.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.search.search.board.domain.Board;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
    @Query(value = """
        SELECT b.board_id
        FROM board b
        JOIN board_description bd 
        ON b.board_id = bd.board_id
        WHERE (MATCH(b.title) AGAINST(:keyword IN BOOLEAN MODE) 
                    OR MATCH(bd.description) AGAINST(:keyword IN BOOLEAN MODE)) 
                    LIMIT :pageSize OFFSET :pageOffset
        """, nativeQuery = true)
    List<Long> searchBoardIdByKeyword(
            @Param("keyword") String keyword,
            @Param("pageSize") Long pageSize,
            @Param("pageOffset") Long pageOffset
    );

    @Query(value = """
        SELECT count(*)
        FROM board b
        JOIN board_description bd 
        ON b.board_id = bd.board_id
        WHERE (MATCH(b.title) AGAINST(:keyword IN BOOLEAN MODE) 
                    OR MATCH(bd.description) AGAINST(:keyword IN BOOLEAN MODE))
        """, nativeQuery = true)
    Long countBoardByKeyword(@Param("keyword") String keyword);
}
