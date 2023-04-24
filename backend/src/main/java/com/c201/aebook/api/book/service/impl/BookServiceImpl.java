package com.c201.aebook.api.book.service.impl;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.book.presentation.dto.response.BookResponseDTO;
import com.c201.aebook.api.book.presentation.dto.response.BookSearchResponseDTO;
import com.c201.aebook.api.book.service.BookService;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public BookResponseDTO searchBookDetail(String isbn) {
        BookEntity book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
        BookResponseDTO bookResponseDTO = BookResponseDTO.builder()
                .aladinUrl(book.getAladinUrl())
                .price(book.getPrice())
                .author(book.getAuthor())
                .coverImageUrl(book.getCoverImageUrl())
                .title(book.getTitle())
                .description(book.getDescription())
                .page(book.getPage())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate())
                .publisher(book.getPublisher())
                .build();
        return bookResponseDTO;
    }

    @Override
    public List<String> getAutocompleteTitle(String keyword) {
        List<BookEntity> bookList = bookRepository.findTop5ByTitleContaining(keyword);
        List<String> titleList = bookList.stream()
                .map(BookEntity::getTitle)
                .collect(Collectors.toList());
        return titleList;
    }

    @Override
    public List<BookSearchResponseDTO> searchBookList(String keyword) {
        List<BookEntity> bookList = bookRepository.findByTitleContaining(keyword);
        List<BookSearchResponseDTO> result = bookList.stream()
                .map(book -> BookSearchResponseDTO.builder()
                        .aladinUrl(book.getAladinUrl())
                        .author(book.getAuthor())
                        .publishDate(book.getPublishDate())
                        .title(book.getTitle())
                        .isbn(book.getIsbn())
                        .coverImageUrl(book.getCoverImageUrl())
                        .price(book.getPrice())
                        .publisher(book.getPublisher())
                        .build())
                .collect(Collectors.toList());
        return result;
    }


}
