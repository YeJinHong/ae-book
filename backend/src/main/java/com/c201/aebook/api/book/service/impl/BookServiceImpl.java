package com.c201.aebook.api.book.service.impl;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.book.service.BookService;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookEntity searchBookDetail(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
        return book;
    }
}
