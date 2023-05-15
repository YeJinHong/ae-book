package com.c201.aebook.api.user.service.impl;

import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.user.presentation.dto.response.UserResponseDTO;
import com.c201.aebook.api.vo.UserSO;
import com.c201.aebook.converter.UserConverter;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	@Mock
	private UserConverter userConverter;

	@InjectMocks
	private UserServiceImpl subject;

	@BeforeEach
	protected void setUp() throws Exception {
	}

	@Test
	@DisplayName("testDuplicatedUserByNickname: Happy Case")
	public void testDuplicatedUserByNickname() {
		// given
		String nickname = "test nickname";
		Boolean checkNickname = false;

		BDDMockito.given(userRepository.existsByNickname(nickname)).willReturn(checkNickname);

		// when
		subject.duplicatedUserByNickname(nickname);

		// then

	}

	@Test
	@DisplayName("testDuplicatedUserByNickname: sad Case")
	public void testDuplicatedUserByNickname1() {
		// given
		String nickname = "test nickname";

		BDDMockito.given(userRepository.existsByNickname(nickname)).willReturn(true);

		// when
		Throwable throwable = Assertions.assertThrows(CustomException.class, () -> {
			subject.duplicatedUserByNickname(nickname);
		});

		// then
		Assertions.assertAll("결과값 검증", () -> {
			Assertions.assertEquals(ErrorCode.DUPLICATE_RESOURCE, ((CustomException)throwable).getErrorCode());
		});
	}

	@Test
	@DisplayName("testGetProfileImage: Happy Case")
	public void testGetProfileImage() {
		// given
		Long userId = 1L;
		String profileUrl = "profile url";

		BDDMockito.given(userRepository.findProfileUrlById(userId)).willReturn(profileUrl);

		// when
		subject.getProfileImage(userId);

		// then

	}

	@Test
	@DisplayName("testUpdateUserInfo: Happy Case")
	public void testUpdateUserInfo() {
		// given
		Long userId = 1L;
		String nickname = "test nickname";
		String profileUrl = "test profile url";

		UserSO userSO = UserSO.builder().nickname(nickname).profileUrl(profileUrl).build();

		UserEntity user = UserEntity.builder().nickname("test nickname").profileUrl("test profile url").build();
		BDDMockito.given(userRepository.findById(userId)).willReturn(Optional.of(user));

		user.updateUserEntity(userSO.getNickname(), userSO.getProfileUrl());

		UserResponseDTO userResponseDTO = UserResponseDTO.builder()
				.nickname(userSO.getNickname()).profileUrl(userSO.getProfileUrl()).build();
		BDDMockito.given(userConverter.toUserResponse(user.getNickname(), user.getProfileUrl())).willReturn(userResponseDTO);

		// when
		UserResponseDTO ret = subject.updateUserInfo(userId, userSO);

		// then
		Assertions.assertAll("결과값 검증", () -> {
			Assertions.assertNotNull(ret);
			Assertions.assertEquals(ret.getNickname(), "test nickname");
			Assertions.assertEquals(ret.getProfileUrl(), "test profile url");
		});
		BDDMockito.then(userRepository).should(times(1)).findById(userId);

//		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testDeleteUserInfo() {
		throw new RuntimeException("not yet implemented");
	}

}
