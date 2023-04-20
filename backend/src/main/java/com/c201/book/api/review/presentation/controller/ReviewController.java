package com.c201.book.api.review.presentation.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.common.constants.ApplicationConstants;
import com.c201.book.api.review.presentation.dto.request.ReviewRequestDTO;
import com.c201.book.api.review.presentation.dto.response.ReviewResponseDTO;
import com.c201.book.api.review.presentation.validator.ReviewValidator;
import com.c201.book.api.review.service.impl.ReviewServiceImpl;
import com.c201.book.api.vo.ReviewSO;
import com.c201.book.converter.ReviewConverter;
import com.c201.book.utils.RegexValidator;

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

	private final ReviewValidator reviewValidator;
	private final RegexValidator regexValidator;
	private final ReviewServiceImpl reviewService;
	private final ReviewConverter reviewConverter;
	//	private final TokenUtils tokenUtils; // TODO: 선영아 "해줘"

	@Operation(summary = "서평 등록", description = "새 서평을 등록합니다.")
	@PostMapping(
		path = "/{isbn}",
		consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public BaseResponse<?> saveReview(
		@PathVariable String isbn,
		@RequestBody ReviewRequestDTO reviewRequestDTO
		//            ,@AuthenticationPrincipal CustomUserDetails customUserDetails // TODO: 시큐리티 완성되면 주석 해제하고 쉼표 옮기기
	) {
		// TODO: 토큰 유효성 검증
		// User loginUser = tokenUtils.validateGetUser(customUserDetails);

		// DTO NOT NULL 검증
		reviewValidator.validateReviewRequestDTO(reviewRequestDTO);

		// isbn 검증
		regexValidator.validateIsbn(isbn);

		ReviewSO reviewSO = reviewConverter.toReviewSO(reviewRequestDTO);
		// 서평 등록
		// reviewService.saveReview(Long.parseLong(customUserDetails.getUsername()), reviewRequestDTO); // TODO: 로그인 완성되면 아래 삭제하고 사용
		reviewService.saveReview(4L, isbn, reviewSO); // 로그인 완성 전 하드코딩

		return new BaseResponse<>(null, 200, "서평 작성 완료");
	}

	@Operation(summary = "특정 도서의 서평 리스트", description = "도서 상세 페이지에서 보여줄 서평 리스트입니다.")
	@GetMapping
		(
			path = "/{isbn}"
		)
	public BaseResponse<?> getBookReviewList(
		@PathVariable String isbn,
		@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
	) {
		// isbn 검증
		regexValidator.validateIsbn(isbn);

		// 해당 도서 서평 리스트 찾기
		Page<ReviewResponseDTO> reviews = reviewService.getBookReviewList(isbn, pageable);

		return new BaseResponse<>(reviews, 200, "해당 책의 서평 리스트가 도착했읍니다 ^_^b");
	}

	@Operation(summary = "특정 서평 조회", description = "선택한 서평의 정보를 보여줍니다.")
	@GetMapping(
		path = "/detail/{reviewid}"
	)
	public BaseResponse<?> getReview(
		@PathVariable(name = "reviewid") Long reviewId
	) {
		ReviewResponseDTO review = reviewService.getReview(reviewId);

		return new BaseResponse<>(review, 200, ApplicationConstants.SUCCESS);
	}
}
