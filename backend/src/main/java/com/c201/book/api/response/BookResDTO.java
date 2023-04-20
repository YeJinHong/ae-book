package com.c201.book.api.response;

import lombok.Getter;

import java.sql.Date;

@Getter
public class BookResDTO {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private Date publishDate;
    private int price;
    private String coverImageUrl;
    private String aladinUrl;
    private int page;

}
