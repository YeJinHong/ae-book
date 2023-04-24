package com.c201.aebook.api.book.service;

import com.c201.aebook.api.book.presentation.dto.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    // 1. ISBN으로 도서 상세 정보 조회
    public BookResponseDTO searchBookDetail(String isbn);
    // 2. 검색시 책 제목 자동 완성
    public List<String> getAutocompleteTitle(String keyword);
}
