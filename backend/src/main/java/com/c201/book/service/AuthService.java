package com.c201.book.service;

import com.c201.book.api.common.LoginUserInfoDTO;
import com.c201.book.auth.KakaoProfile;
import com.c201.book.auth.KakaoTokenDTO;
import com.c201.book.api.common.TokenDTO;
import com.c201.book.config.jwt.JwtTokenProvider;
import com.c201.book.entity.UserEntity;
import com.c201.book.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    @Value("${oauth2.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${oauth2.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 인가코드로 요청하여 카카오 AccessToken을 발급 받는 기능
     * @param code
     * @return KakaoTokenDto
     * */
    public KakaoTokenDTO getAccessToken(String code) {
        RestTemplate rt = new RestTemplate();

        // 1. Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 2. 카카오 Access token을 발급 받기 위해 카카오 API key 및 Redirect Uri 입력
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId); //REST API KEY
        params.add("redirect_uri", kakaoRedirectUri); //REDIRECT URI
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // 3. 카카오 Access token 발급 받기
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // log.info("access : {}", accessTokenResponse);

        // 4. 발급 받은 카카오 Access token 정보 중 필요한 정보만 KakaoTokenDto에 저장
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoTokenDTO kakaoTokenDto = null;
        try {
            kakaoTokenDto = objectMapper.readValue(accessTokenResponse.getBody(), KakaoTokenDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoTokenDto;
    }

    /**
     * 카카오 Access token으로 유저 정보 요청하기
     * @param token
     * @return KakaoProfile
     * */
    public KakaoProfile findProfile(String token) {
        RestTemplate rt = new RestTemplate();

        // 1. Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);

        // 2. Http 요청 (POST 방식) 후, response 변수에 유저 프로필 정보를 받기
        ResponseEntity<String> kakaoProfileResponse = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        // 3. 받은 유저 프로필 정보 중 필요한 정보만 KakaoProfile에 저장
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper.readValue(kakaoProfileResponse.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return kakaoProfile;
    }

    /**
     * 회원가입 또는 로그인 기능
     * DB에 정보가 없는 회원은 회원가입을 하고, 정보가 있다면 로그인을 진행
     * @param token
     * @return loginUserInfo
     * */
    public LoginUserInfoDTO saveUserOrLogin(String token) {
        KakaoProfile profile = findProfile(token);

        // 1. 카카오 아이디로 유저 정보 찾기
        UserEntity user = userRepository.findByKakaoId(profile.getId());

        // 2. 유저 정보가 없다면
        if(user == null) {
            // 2-1. 유저 닉네임 중복 검사
            Long userCnt = userRepository.countByNicknameStartingWith(profile.getKakao_account().getProfile().getNickname());
            if (userCnt++ > 0) {
                profile.getKakao_account().getProfile().setNickname(profile.getKakao_account().getProfile().getNickname() + userCnt);
            };

            // 2-2. DB에 유저 정보 저장
            user = UserEntity.builder()
                    .kakaoId(profile.getId())
                    .nickname(profile.getKakao_account().getProfile().getNickname())
                    //.phone(profile.kakao_account.getPhone_number())
                    .phone("010-0000-0000") // 추후 권한을 받으면 변경 예정
                    .profileUrl(profile.getKakao_account().getProfile().getProfile_image_url())
                    .status(1)
                    .build();

            userRepository.save(user);
        }

        // 3. 유저의 token 발급
        TokenDTO tokenDto = jwtTokenProvider.generateTokenDto(user.getId().toString());

        // 4. 유저 정보와 토큰 정보를 함께 전달
        LoginUserInfoDTO loginUserInfoDto = LoginUserInfoDTO.builder()
                .user(user)
                .tokenDto(tokenDto)
                .build();

        return loginUserInfoDto;
    }


}
