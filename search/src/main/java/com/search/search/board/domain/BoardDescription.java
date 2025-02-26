package com.search.search.board.domain;

import static com.search.search.board.domain.BoardStatus.PUBLISHED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
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

    @Enumerated(STRING)
    @Column(name = "board_description_status", nullable = false)
    private BoardStatus status;

    private BoardDescription(String description, Long boardId, BoardStatus status) {
        this.description = description;
        this.boardId = boardId;
        this.status = status;
    }

    public static BoardDescription createDescription(String description, Long boardId) {
        return new BoardDescription(description, boardId, PUBLISHED);
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }
}
