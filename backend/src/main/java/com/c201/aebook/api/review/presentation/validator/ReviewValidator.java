package com.c201.aebook.api.review.presentation.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.c201.aebook.api.review.presentation.dto.request.ReviewModifyRequestDTO;
import com.c201.aebook.api.review.presentation.dto.request.ReviewRequestDTO;

/**
 * [문희주] DTO NOT NULL 검증
 */

@Component
public class ReviewValidator {

	/**
	 * [문희주] ReviewRequestDTO NOT NULL 검증
	 * @param reviewRequestDTO
	 */
	public void validateReviewRequestDTO(ReviewRequestDTO reviewRequestDTO) {
		checkStringType(reviewRequestDTO.getContent(), "서평 내용");
		checkIntType(reviewRequestDTO.getScore(), "별점");
	}

	public void validateReviewModifyRequestDTO(ReviewModifyRequestDTO reviewModifyRequestDTO) {
		checkLongType(reviewModifyRequestDTO.getReviewId(), "서평 아이디");
		checkStringType(reviewModifyRequestDTO.getContent(), "서평 내용");
		checkIntType(reviewModifyRequestDTO.getScore(), "별점");
	}

	public void checkLongType(Long value, String name) {
		if (value == null || value <= 0) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값이며 양수 값만 가능합니다.");
		}
	}

	public void checkIntType(int value, String name) {
		if (value <= 0) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값이며 양수 값만 가능합니다.");
		}
	}

	public void checkStringType(String value, String name) {
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값입니다.");
		}
	}
}
