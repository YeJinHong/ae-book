package com.c201.book.api.request;

import lombok.Data;

/**
 * [ 문희주 ] 서평 등록 Dto
 * 내용, 별점
 */

@Data
public class ReviewReqDto {
    private String content;
    private int score;
}
