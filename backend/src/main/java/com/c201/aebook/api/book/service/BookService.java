package com.c201.aebook.api.book.service;

import com.c201.aebook.api.book.persistence.entity.BookEntity;

public interface BookService {
    public BookEntity searchBookDetail(String isbn);
}
