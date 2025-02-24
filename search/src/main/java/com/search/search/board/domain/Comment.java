package com.search.search.board.domain;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(
    name = "comment",
    indexes = {
        @Index(name = "idx_comment_team_id_desc", columnList = "boardId, comment_id desc")
    }
)
public class Comment {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "author", length = 50, nullable = false)
    private String author;

    @Column(name = "description", length = 200, nullable = false)
    private String description;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    private Comment(Long boardId, String author, String description , Long parentCommentId) {
        this.boardId = boardId;
        this.author = author;
        this.description = description;
        this.parentCommentId = parentCommentId;
    }

    public Comment createComment(Long boardId, String author, String description) {
        return new Comment(boardId, author, description, null);
    }

    public Comment createChildComment(Long boardId, String author, String description, Long parentCommentId) {
        return new Comment(boardId, author, description, parentCommentId);
    }
}
