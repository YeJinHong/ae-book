package com.c201.book.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@NoArgsConstructor
public class Book extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, length = 50)
    private String author;

    @Column(length = 50)
    private String publisher;

    private Date publishDate;

    @Column(nullable = false, length = 40)
    private String isbn;

    private int page;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 200)
    private String aladinUrl;

    @Column(length = 200)
//    @Column(length = 200, columnDefinition = "표지없음 s3 링크")
    private String coverImageUrl;

    @ColumnDefault("0")
    private int scoreSum;

    @ColumnDefault("0")
    private int reviewCount;

    @Builder
    public Book(String title, String description, String author, String publisher, Date publishDate, String isbn, int page, int price, String aladinUrl, String coverImageUrl, int scoreSum, int reviewCount) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.isbn = isbn;
        this.page = page;
        this.price = price;
        this.aladinUrl = aladinUrl;
        this.coverImageUrl = coverImageUrl;
        this.scoreSum = scoreSum;
        this.reviewCount = reviewCount;
    }
}
