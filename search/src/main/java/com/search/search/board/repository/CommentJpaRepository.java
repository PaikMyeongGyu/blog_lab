package com.search.search.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.search.search.board.domain.Board;

public interface CommentJpaRepository extends JpaRepository<Board, Long> {
}
