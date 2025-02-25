package com.search.search.board.domain;

import static com.search.search.board.domain.BoardStatus.PUBLISHED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
    name = "board",
    indexes = {
    }
)
public class Board {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Enumerated(STRING)
    @Column(name = "board_status", nullable = false)
    private BoardStatus status;

    private Board(String title, String author, BoardStatus status) {
        this.title = title;
        this.author = author;
        this.status = status;
    }

    public static Board newBoard(String title, String author) {
        return new Board(title, author, PUBLISHED);
    }

    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateAuthor(String newAuthor) {
        this.author = newAuthor;
    }
}
