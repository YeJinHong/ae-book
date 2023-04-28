package com.c201.aebook.api.review.presentation.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponseDTO {
	@Schema(description = "서평 ID", defaultValue = "1")
	private Long reviewId;

	@Schema(description = "서평 작성자 닉네임", defaultValue = "아응앵")
	private String reviewerNickname;

	@Schema(description = "별점", defaultValue = "5")
	private int score;

	@Schema(description = "서평 내용", defaultValue = "절경이고요 장관이네요")
	private String content;

	@Schema(description = "서평 작성 시간", defaultValue = "2023-04-14 10:30:15")
	private LocalDateTime createdAt;

	@Schema(description = "서평 수정 시간", defaultValue = "2023-04-17 10:30:15")
	private LocalDateTime updatedAt;

	@Builder
	public ReviewResponseDTO(Long reviewId, String reviewerNickname, int score, String content, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.reviewId = reviewId;
		this.reviewerNickname = reviewerNickname;
		this.score = score;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
