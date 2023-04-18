package com.c201.book.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Review extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "score", nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Builder
    public Review(String content, int score, User user, Book book) {
        this.content = content;
        this.score = score;
        this.user = user;
        this.book = book;
    }
}
