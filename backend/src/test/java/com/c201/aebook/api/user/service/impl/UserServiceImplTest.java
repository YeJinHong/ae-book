package com.c201.aebook.api.user.service.impl;

import com.c201.aebook.api.user.persistence.repository.UserRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	public void testUpdateUserInfo() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testDeleteUserInfo() {
		throw new RuntimeException("not yet implemented");
	}

}
