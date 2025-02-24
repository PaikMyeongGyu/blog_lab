package com.search.search.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.search.search.board.domain.BoardDescription;

public interface BoardDescriptionJpaRepository extends JpaRepository<BoardDescription, Long> {
}
