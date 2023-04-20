package com.c201.book.api.review.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResDTO {
    private final Long userId;
    private final String nickname;
    private final String profileUrl;
}
