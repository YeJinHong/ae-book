package com.c201.aebook.api.notification.service.impl;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.notification.persistence.entity.NotificationEntity;
import com.c201.aebook.api.notification.persistence.repository.NotificationRepository;
import com.c201.aebook.api.notification.presentation.dto.request.NotificationRequestDTO;
import com.c201.aebook.api.notification.presentation.dto.response.NotificationBookDetailResponseDTO;
import com.c201.aebook.api.notification.presentation.dto.response.NotificationBookListResponseDTO;
import com.c201.aebook.api.notification.presentation.dto.response.NotificationResponseDTO;
import com.c201.aebook.api.notification.presentation.dto.response.NotificationUpdateResponseDTO;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.vo.NotificationPatchSO;
import com.c201.aebook.api.vo.NotificationSO;
import com.c201.aebook.converter.NotificationConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private BookRepository bookRepository;

	@Mock
	private NotificationRepository notificationRepository;
	@Mock
	private NotificationConverter notificationConverter;

	@InjectMocks
	private NotificationServiceImpl subject;

	@BeforeEach
	protected void setUp() throws Exception {
	}

	@Test
	public void testSaveNotification() {
		// given
		String userId = "1";
		int upperLimit = 1000;
		String notificationType = "D";
		String isbn = "9788915016521";

		NotificationSO notificationSO = NotificationSO.builder()
				.notificationType(notificationType).upperLimit(upperLimit).isbn("9788915016521").build();

		BookEntity bookEntity = BookEntity.builder()
				.id(1L)
				.isbn(isbn)
				.title("Test Book")
				.build();
		BDDMockito.given(bookRepository.findByIsbn(isbn)).willReturn(Optional.of(bookEntity));

		UserEntity userEntity = UserEntity.builder()
				.id(1L)
				.nickname("Test User")
				.build();
		BDDMockito.given(userRepository.findById(Long.valueOf(userId))).willReturn(Optional.of(userEntity));

		BDDMockito.given(notificationRepository.findByUserIdAndBookId(userEntity.getId(), bookEntity.getId())).willReturn(null);
		NotificationEntity savedNotificationEntity = NotificationEntity.builder()
				.id(1L)
				.notificationType(notificationSO.getNotificationType())
				.upperLimit(notificationSO.getUpperLimit())
				.book(bookEntity)
				.user(userEntity)
				.build();
		NotificationEntity notificationEntity = new NotificationEntity();

		BDDMockito.given(notificationRepository.save(Mockito.any(NotificationEntity.class))).willReturn(savedNotificationEntity);
		NotificationResponseDTO notificationResponseDTO = NotificationResponseDTO.builder()
				.id(savedNotificationEntity.getId())
				.notificationType(savedNotificationEntity.getNotificationType())
				.upperLimit(savedNotificationEntity.getUpperLimit())
				.build();
		BDDMockito.given(notificationConverter.toNotificationResponseDTO(savedNotificationEntity)).willReturn(notificationResponseDTO);


		// when
		NotificationResponseDTO ret = subject.saveNotification(userId, notificationSO);

		// then

		Assertions.assertAll("결괏값 검증", () -> {
			Assertions.assertNotNull(ret);
			Assertions.assertEquals(ret.getId(), 1L);
			Assertions.assertTrue(ret.getNotificationType().equals("S") || ret.getNotificationType().equals("D"));
			Assertions.assertTrue(ret.getUpperLimit() >= 0);
		});
		BDDMockito.then(bookRepository).should(times(1)).findByIsbn(isbn);
		BDDMockito.then(userRepository).should(times(1)).findById(Long.valueOf(userId));
		BDDMockito.then(notificationRepository).should(times(1)).findByUserIdAndBookId(userEntity.getId(), bookEntity.getId());

	}

	@Test
	public void testGetMyNotificationBookList() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetMyNotificationBookDetail() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testUpdateNotification() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testDeleteNotification() {
		throw new RuntimeException("not yet implemented");
	}

}
