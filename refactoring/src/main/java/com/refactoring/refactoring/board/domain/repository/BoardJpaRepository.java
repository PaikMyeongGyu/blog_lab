package com.refactoring.refactoring.board.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.refactoring.refactoring.board.domain.Board;

public interface BoardJpaRepository extends JpaRepository<Board, Long> {
}
