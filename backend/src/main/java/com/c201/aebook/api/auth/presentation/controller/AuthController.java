package com.c201.aebook.api.auth.presentation.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.c201.aebook.api.common.LoginUserInfoDTO;
import com.c201.aebook.api.common.TokenDTO;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.auth.presentation.dto.response.LoginResDTO;
import com.c201.aebook.api.auth.service.AuthService;
import com.c201.aebook.auth.dto.KakaoTokenDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        KakaoTokenDTO kakaoTokenDto = authService.getAccessToken(code);
        // log.info("KakaoTokenDto : {} ", kakaoTokenDto);

        // 2. 발급 받은 accessToken으로 카카오 회원 정보 확인 후 DB 저장 또는 로그인(토큰 발급)
        LoginUserInfoDTO loginUserInfoDto = authService.saveUserOrLogin(kakaoTokenDto.getAccess_token());
        // log.info("access Token : {} ", loginUserInfoDto.getTokenDto().getAccessToken());
        // log.info("user name : {}", loginUserInfoDto.getUser().getNickname());

        // 3. 헤더에 사용자 token 정보 담기
        TokenDTO tokenDto = loginUserInfoDto.getTokenDto();
        HttpHeaders headers = new HttpHeaders();
        headers.add(tokenDto.getAuthorizationHeader(),
                tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        headers.add(tokenDto.getRefreshHeader(),
                tokenDto.getGrantType() + " " + tokenDto.getRefreshToken());
        // log.info("headers : {} ", headers);

        // 4. 로그인 유저 정보 담기
        UserEntity user = loginUserInfoDto.getUser();
        LoginResDTO loginResDto = LoginResDTO.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileUrl(user.getProfileUrl())
                .build();

        return ResponseEntity.ok().headers(headers).body(loginResDto);
    }

}
