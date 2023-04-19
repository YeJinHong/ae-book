package com.c201.book.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResDto {
    private final Long userId;
    private final String nickname;
    private final String profileUrl;
}
