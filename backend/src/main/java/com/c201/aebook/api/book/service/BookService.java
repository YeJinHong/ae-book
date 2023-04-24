package com.c201.aebook.api.book.service;

import com.c201.aebook.api.book.presentation.dto.response.BookResponseDTO;

public interface BookService {

    // 1. ISBN으로 도서 상세 정보 조회
    public BookResponseDTO searchBookDetail(String isbn);
}
