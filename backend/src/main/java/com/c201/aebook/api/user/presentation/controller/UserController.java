package com.c201.aebook.api.user.presentation.controller;

import com.c201.aebook.api.common.BaseResponse;
import com.c201.aebook.api.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name="회원관리")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "닉네임 중복 검사", description = "사용자의 닉네임 중복 검사를 합니다.")
    @PostMapping(path = "/exists")
    public BaseResponse<?> checkUserNickname(
            @RequestParam(name = "nickname") String nickname
    ) {
        userService.duplicatedUserByNickname(nickname); // 닉네임 중복 검사\
        return new BaseResponse<>(true, HttpStatus.OK.value(), "닉네임 중복 없음");
    }

}