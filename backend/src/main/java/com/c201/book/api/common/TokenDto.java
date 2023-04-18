package com.c201.book.api.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    private String grantType; // Bearer
    private String accessToken; // access Token
    private long accessTokenExpiresIn; // access Token 만료 시간
    private String refreshToken; // refresh Token
}
