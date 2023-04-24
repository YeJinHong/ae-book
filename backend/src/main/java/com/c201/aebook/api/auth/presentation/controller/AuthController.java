package com.c201.aebook.api.auth.presentation.controller;

import com.c201.aebook.api.auth.service.impl.AuthServiceImpl;
import com.c201.aebook.api.common.BaseResponse;
import com.c201.aebook.api.vo.TokenSO;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.config.jwt.JwtProperties;
import com.c201.aebook.converter.TokenConverter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Tag(name="회원인증")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;
    private final RedisTemplate redisTemplate;
    private final TokenConverter tokenConverter;

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

    @Operation(summary = "토큰 재발행", description = "토큰 재발행을 합니다.")
    @PostMapping(path = "/access-token")
    public BaseResponse<?> reissueAccessToken(
            HttpServletResponse response,
            HttpServletRequest request
    ) {
        // 받은 token 정보 가져오기
        String accessToken = authService.resolveToken(request, JwtProperties.AUTHORIZATION_HEADER);
        String refreshToken = authService.resolveToken(request, JwtProperties.REFRESH_HEADER);
        log.info("jwt : {} ", accessToken);
        log.info("refresh : {}", refreshToken);

        // requestTokenDto에 받은 토큰 정보 저장하기
        TokenSO requestTokenSO = tokenConverter.toTokenSO(accessToken, refreshToken);
        log.info("requesTokenSO : {}", requestTokenSO.getAccessToken());

        // 토큰 재발행
        TokenDTO tokenDto = authService.reissueAccessToken(requestTokenSO);

        // 헤더에 토큰 정보 담기
        response.setHeader(JwtProperties.AUTHORIZATION_HEADER, tokenDto.getGrantType() + " " + tokenDto.getAccessToken());
        response.setHeader(JwtProperties.REFRESH_HEADER, tokenDto.getGrantType() + " " + tokenDto.getRefreshToken());
        return new BaseResponse<>(null, 200, "token 재발행 성공");
    }

}
