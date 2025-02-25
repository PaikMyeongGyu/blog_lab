package com.search.search.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.search.search.board.domain.BoardDescription;

public interface BoardDescriptionJpaRepository extends JpaRepository<BoardDescription, Long> {
    Optional<BoardDescription> findByBoardId(Long boardId);
}
