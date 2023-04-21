package com.c201.aebook.api.book.presentation.controller;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.presentation.dto.response.BookResponseDTO;
import com.c201.aebook.api.book.service.BookService;
import com.c201.aebook.api.common.BaseResponse;
import com.c201.aebook.api.common.constants.ApplicationConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "도서 Controller")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "ISBN으로 도서 검색", description = "해당 ISBN의 도서 상세 정보를 반환합니다.")
    @GetMapping("/{isbn}")
    public BaseResponse<?> searchBookDetail(@PathVariable String isbn){
        BookResponseDTO book = bookService.searchBookDetail(isbn);
        return new BaseResponse<>(book, 200, ApplicationConstants.SUCCESS);
    }
    // TODO : 키워드 검색으로 도서 리스트를 반환하는 searchBookList

    // TODO : 새로 들어온 책 리스트를 반환하는 getNewBookList()

    // TODO : 검색어 자동완성 getAutocompleteTitle()

}
