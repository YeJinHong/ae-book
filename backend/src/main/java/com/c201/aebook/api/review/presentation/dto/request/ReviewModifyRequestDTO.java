package com.c201.aebook.api.review.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * [ 문희주 ] 서평 수정 DTO
 * 내용, 별점
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewModifyRequestDTO {
	@Schema(description = "서평 아이디", defaultValue = "1")
	private Long reviewId;
	@Schema(description = "서평 내용", defaultValue = "끔찍하네요 최악이고요")
	private String content;
	@Schema(description = "별점", defaultValue = "1")
	private int score;
}
