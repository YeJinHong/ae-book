package com.c201.book.api.common;

import com.c201.book.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginUserInfoDto {

    private User user;
    private TokenDto tokenDto;

}
