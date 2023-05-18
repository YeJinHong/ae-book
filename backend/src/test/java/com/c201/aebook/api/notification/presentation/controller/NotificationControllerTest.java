package com.c201.aebook.api.notification.presentation.controller;

import com.c201.aebook.api.notification.presentation.dto.request.NotificationRequestDTO;
import com.c201.aebook.api.notification.presentation.dto.response.NotificationResponseDTO;
import com.c201.aebook.api.notification.presentation.vaildator.NotificationValidator;
import com.c201.aebook.api.notification.service.impl.NotificationServiceImpl;
import com.c201.aebook.api.review.presentation.dto.request.ReviewRequestDTO;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.vo.NotificationSO;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.converter.NotificationConverter;
import com.c201.aebook.utils.RegexValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@Import(NotificationController.class)
public class NotificationControllerTest {

	@Autowired
	NotificationController notificationController;

	private MockMvc mockMvc;

	@MockBean
	private NotificationServiceImpl notificationService;

	@MockBean
	private RegexValidator regexValidator;

	@MockBean
	private NotificationValidator notificationValidator;
	@MockBean
	private NotificationConverter notificationConverter;

	@BeforeEach
	protected void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(notificationController)
				.setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver(),
						new PageableHandlerMethodArgumentResolver())
				.addFilters(new SecurityContextPersistenceFilter())
				.build();
	}

	@Test
	@DisplayName("testSaveNotification: Happy Case")
	public void testSaveNotification() throws Exception {
		// given
		CustomUserDetails customUserDetails = new CustomUserDetails(UserEntity.builder().id(1L).build());
		NotificationRequestDTO notificationRequestDTO = NotificationRequestDTO.builder().isbn("123456789").upperLimit(5000).notificationType("S").build();

		NotificationSO notificationSO = NotificationSO.builder().isbn("123456789").upperLimit(5000).notificationType("S").build();
		BDDMockito.given(notificationConverter.toNotificationSO(Mockito.any(NotificationRequestDTO.class))).willReturn(notificationSO);

		NotificationResponseDTO notificationResponseDTO = NotificationResponseDTO.builder().upperLimit(5000).notificationType("S").build();
		BDDMockito.given(notificationService.saveNotification(customUserDetails.getUsername(), notificationSO)).willReturn(notificationResponseDTO);

		// when
		mockMvc.perform(post("/notifications")
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(notificationRequestDTO))
						.with(user(customUserDetails)))
				.andExpect(status().isOk())
				.andDo(print());

		// then
		BDDMockito.then(notificationService).should(times(1)).saveNotification(customUserDetails.getUsername(), notificationSO);
	}

	@Test
	public void testGetNotificationBookList() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetNotificationBookDetail() {
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
