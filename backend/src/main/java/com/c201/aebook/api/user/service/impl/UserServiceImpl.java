package com.c201.aebook.api.user.service.impl;

import com.c201.aebook.api.user.service.UserService;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.user.service.UserService;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void duplicatedUserByNickname(String nickname) {
        // 닉네임 존재 여부를 true, false로 반환
        boolean userNickname = userRepository.existsByNickname(nickname);

        // 닉네임이 존재한다면 중복이므로 에러 던지기
        if(userNickname) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }
    }
}
