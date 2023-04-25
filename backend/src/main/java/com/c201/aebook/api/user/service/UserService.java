package com.c201.aebook.api.user.service;

import com.c201.aebook.api.user.presentation.dto.response.UserResponeDTO;
import com.c201.aebook.api.vo.UserSO;

public interface UserService {

    public void duplicatedUserByNickname(String nickname);

    public String getProfileImage(long userId);

    public UserResponeDTO updateUserInfo(long userId, UserSO userSO);

}
