package com.c201.aebook.api.vo;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenSO {

    private String AuthorizationHeader; // Authorization
    private String RefreshHeader; // Refresh
    private String grantType; // Bearer
    private String accessToken; // access Token
    private long accessTokenExpiresIn; // access Token 만료 시간
    private String refreshToken; // refresh Token

}
