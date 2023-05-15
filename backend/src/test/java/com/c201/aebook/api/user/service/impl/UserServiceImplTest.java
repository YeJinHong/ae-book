package com.c201.aebook.api.user.service.impl;

import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.converter.UserConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
	public void testGetProfileImage() {
		throw new RuntimeException("not yet implemented");
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
