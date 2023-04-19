package com.c201.book.service;

import com.c201.book.model.Book;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    public Book searchBookDetail(String isbn);
}
