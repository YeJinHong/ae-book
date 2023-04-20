package com.c201.book.api.common;

import com.c201.book.api.review.persistence.entity.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginUserInfoDTO {

    private UserEntity user;
    private TokenDTO tokenDto;

}
