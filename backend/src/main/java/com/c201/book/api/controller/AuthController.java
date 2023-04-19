package com.c201.book.api.controller;

import com.c201.book.api.common.LoginUserInfo;
import com.c201.book.api.response.LoginResDto;
import com.c201.book.auth.KakaoTokenDto;
import com.c201.book.api.common.TokenDto;
import com.c201.book.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="회원인증")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 소셜 로그인", description = "카카오로 소셜 로그인을 합니다.")
    @GetMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam(name = "code") String code
    ) {
        // log.info("code : {} ", code);

        // 1. 프론트에서 받은 kakao 인가 코드를 통해 kakao accessToken 발급
        KakaoTokenDto kakaoTokenDto = authService.getAccessToken(code);
        // log.info("KakaoTokenDto : {} ", kakaoTokenDto);

        // 2. 발급 받은 accessToken으로 카카오 회원 정보 확인 후 DB 저장 또는 로그인(토큰 발급)
        LoginUserInfo loginUserInfo = authService.saveUserOrLogin(kakaoTokenDto.getAccess_token());
        // log.info("access Token : {} ", loginUserInfo.getTokenDto().getAccessToken());
        // log.info("user name : {}", loginUserInfo.getUser().getNickname());

        // 3. 헤더에 사용자 token 정보 담기
        HttpHeaders headers = new HttpHeaders();
        headers.add(loginUserInfo.getTokenDto().getAuthorizationHeader(),
                loginUserInfo.getTokenDto().getGrantType() + " " + loginUserInfo.getTokenDto().getAccessToken());
        headers.add(loginUserInfo.getTokenDto().getRefreshHeader(),
                loginUserInfo.getTokenDto().getGrantType() + " " + loginUserInfo.getTokenDto().getRefreshToken());
        // log.info("headers : {} ", headers);

        // 4. 로그인 유저 정보 담기
        LoginResDto loginResDto = new LoginResDto(loginUserInfo.getUser().getId(), loginUserInfo.getUser().getNickname(), loginUserInfo.getUser().getProfileUrl());

        return ResponseEntity.ok().headers(headers).body(loginResDto);
    }

}
