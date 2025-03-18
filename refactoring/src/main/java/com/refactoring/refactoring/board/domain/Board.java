package com.refactoring.refactoring.board.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "board"
)
public class Board {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "content", length = 100, nullable = false)
    private String content;

    private Board(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Board of(String title, String content) {
        return new Board(title, content);
    }
}
