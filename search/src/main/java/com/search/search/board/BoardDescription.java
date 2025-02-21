package com.search.search.board;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(
    name = "board_description",
    indexes = {
        @Index(name = "idx_board", columnList = "board_id, board_description_id desc")
    }
)
public class BoardDescription {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_description_id")
    private Long id;

    @Lob
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    public BoardDescription(String description, Long boardId) {
        this.description = description;
        this.boardId = boardId;
    }
}
