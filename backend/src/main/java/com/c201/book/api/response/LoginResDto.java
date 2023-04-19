package com.c201.book.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResDto {
    private final Long userId;
    private final String nickname;
    private final String profileUrl;
}
