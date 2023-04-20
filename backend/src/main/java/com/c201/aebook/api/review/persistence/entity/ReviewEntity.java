package com.c201.aebook.api.review.persistence.entity;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.common.BaseEntity;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "review")
public class ReviewEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "score", nullable = false)
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    @Builder
    public ReviewEntity(String content, int score, UserEntity user, BookEntity book) {
        this.content = content;
        this.score = score;
        this.user = user;
        this.book = book;
    }
}
