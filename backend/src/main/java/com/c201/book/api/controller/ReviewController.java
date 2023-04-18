package com.c201.book.api.controller;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.utils.DtoValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서평 관련 컨트롤러
 */
@Tag(name = "서평 Controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final DtoValidationUtils dtoValidationUtils;

    @Operation(summary = "서평 등록", description = "새 서평을 등록합니다.")
    @PostMapping(
            path = "/books/{isbn}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<?> saveReview(
            @RequestBody ReviewReqDto reviewReqDto
    ) {
        // DTO NOT NULL 검증
        dtoValidationUtils.validateReviewReqDto(reviewReqDto);

        return null;
    }
}
