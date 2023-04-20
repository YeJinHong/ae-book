package com.c201.book.api.review.presentation.dto.response;

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

	@Schema(description = "서평 작성자 ID", defaultValue = "1")
	private Long reviewerId;

	@Schema(description = "별점", defaultValue = "5")
	private int score;

	@Schema(description = "서평 내용", defaultValue = "절경이고요 장관이네요")
	private String content;

	@Schema(description = "서평 작성 시간", defaultValue = "2023-04-14 10:30:15")
	private LocalDateTime createAt;

	@Schema(description = "서평 수정 시간", defaultValue = "2023-04-17 10:30:15")
	private LocalDateTime updateAt;

	@Builder
	public ReviewResponseDTO(Long reviewId, Long reviewerId, int score, String content, LocalDateTime createAt,
		LocalDateTime updateAt) {
		this.reviewId = reviewId;
		this.reviewerId = reviewerId;
		this.score = score;
		this.content = content;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
}
