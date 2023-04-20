package com.c201.aebook.api.auth.service;

import com.c201.aebook.api.common.LoginUserInfoDTO;
import com.c201.aebook.auth.dto.KakaoTokenDTO;
import com.c201.aebook.auth.profile.KakaoProfile;

public interface AuthService {

    public KakaoTokenDTO getAccessToken(String code);

    public KakaoProfile findProfile(String token);

    public LoginUserInfoDTO saveUserOrLogin(String token);


}
