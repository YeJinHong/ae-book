package com.c201.book.api.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.service.ReviewServiceImpl;
import com.c201.book.utils.DtoValidationUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
		//            ,@AuthenticationPrincipal CustomUserDetails customUserDetails // TODO: 시큐리티 완성되면 주석 해제하고 쉼표 옮기기
	) {
		// TODO: 토큰 유효성 검증
		// User loginUser = tokenUtils.validateGetUser(customUserDetails);

		// DTO NOT NULL 검증
		dtoValidationUtils.validateReviewReqDto(reviewReqDto);

		// 서평 등록
		//        reviewService.saveReview(Long.parseLong(customUserDetails.getUsername()), reviewReqDto); // TODO: 로그인 완성되면 아래 삭제하고 사용
		reviewService.saveReview(1L, isbn, reviewReqDto); // 로그인 완성 전 하드코딩

		return new BaseResponse<>(null, 200, "서평 작성 완료");
	}

	@Operation(summary = "특정 도서의 서평 리스트", description = "도서 상세 페이지에서 보여줄 서평 리스트입니다.")
	@GetMapping
		(
			path = "/{isbn}"
		)
	public BaseResponse<?> getBookReviewList(@PathVariable String isbn) {
		// DTO NOT NULL 검증

		//
	}
}
