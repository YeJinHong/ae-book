package com.c201.book.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * [ 문희주 ] 서평 등록 Dto
 * 내용, 별점
 */

@Data
public class ReviewReqDto {
    @Schema(description = "서평 내용", defaultValue = "장관이에요 절경이고요")
    private String content;
    @Schema(description = "별점", defaultValue = "5")
    private int score;
    @Schema(description = "도서 고유 아이디", defaultValue = "1")
    private long bookId;
}
