package com.c201.book.api.controller;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.auth.CustomUserDetails;
import com.c201.book.model.Review;
import com.c201.book.model.User;
import com.c201.book.service.ReviewServiceImpl;
import com.c201.book.utils.DtoValidationUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * [문희주] 서평 관련 컨트롤러
 */
@Tag(name = "서평 Controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final DtoValidationUtils dtoValidationUtils;
    private final ReviewServiceImpl reviewService;
//    private final TokenUtils tokenUtils;

    // 로그인 완성되면 주석 풀고 사용
    @Operation(summary = "서평 등록", description = "새 서평을 등록합니다.")
    @PostMapping(
            path = "/{isbn}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<?> saveReview(
            @PathVariable String isbn,
            @RequestBody ReviewReqDto reviewReqDto
//            ,@AuthenticationPrincipal CustomUserDetails customUserDetails // 시큐리티 완성되면 주석 해제하고 쉼표 옮기기
    ) {
        // 토큰 유효성 검증
        // User loginUser = tokenUtils.validateGetUser(customUserDetails);

        // DTO NOT NULL 검증
        dtoValidationUtils.validateReviewReqDto(reviewReqDto);

        // 서평 등록
//        reviewService.saveReview(Long.parseLong(customUserDetails.getUsername()), reviewReqDto); // 로그인 완성되면 사용
        reviewService.saveReview(1L, isbn, reviewReqDto); // 로그인 완성 전 하드코딩

        return new BaseResponse<>(null, 200, "서평 작성 완료");
    }
}
