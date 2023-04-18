package com.c201.book.api.controller;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.auth.CustomUserDetails;
import com.c201.book.model.Review;
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

    // 로그인 완성되면 주석 풀고 사용
//    @Operation(summary = "서평 등록", description = "새 서평을 등록합니다.")
//    @PostMapping(
//            path = "/books/{isbn}",
//            consumes = MediaType.APPLICATION_JSON_VALUE
//    )
//    public BaseResponse<?> saveReview(
//            @RequestBody ReviewReqDto reviewReqDto,
//            @AuthenticationPrincipal CustomUserDetails customUserDetails // 시큐리티 완성되면 주석 푸쇼 !
//    ) {
//        // DTO NOT NULL 검증
//        dtoValidationUtils.validateReviewReqDto(reviewReqDto);
//
//        // 해당 책에 서평을 작성한 적 없는지 검증
//        Review review = reviewService.findReviewByUserIdAndBookId(Long.parseLong(customUserDetails.getUsername()), reviewReqDto.getBookId());
////        if(review != null) throw new CustomException(ErrorCode.DUPLICATED_REVIEW);
//
//        // 서평 등록
//        reviewService.saveReview(reviewReqDto);
//
//        return new BaseResponse<>(null, 200, "서평 작성 완료");
//    }
    
    // 로그인 없이 DB로 테스트
    @Operation(summary = "서평 등록", description = "새 서평을 등록합니다.")
    @PostMapping(
            path = "/books/{isbn}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse<?> saveReview(
            @RequestBody ReviewReqDto reviewReqDto,
            @PathVariable String isbn
    ) {
        // DTO NOT NULL 검증
        dtoValidationUtils.validateReviewReqDto(reviewReqDto);
    
        // 해당 책에 서평을 작성한 적 없는지 검증
        Review review = reviewService.findReviewByUserIdAndBookId(Long.parseLong(customUserDetails.getUsername()), reviewReqDto.getBookId());
    //        if(review != null) throw new CustomException(ErrorCode.DUPLICATED_REVIEW);
    
        // 서평 등록
        reviewService.saveReview(reviewReqDto);
    
        return new BaseResponse<>(null, 200, "서평 작성 완료");
    }
}
