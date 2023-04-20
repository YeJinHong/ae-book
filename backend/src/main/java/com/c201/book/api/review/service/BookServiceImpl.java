package com.c201.book.service;

import com.c201.book.model.Book;
import com.c201.book.model.Review;
import com.c201.book.repository.BookRepository;
import com.c201.book.utils.exeption.CustomException;
import com.c201.book.utils.exeption.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;

    @Override
    public Book searchBookDetail(String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
        return book;
    }
}
