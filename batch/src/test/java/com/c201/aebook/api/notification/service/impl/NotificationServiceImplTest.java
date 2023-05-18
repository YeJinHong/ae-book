package com.c201.aebook.api.notification.service.impl;

import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import com.c201.aebook.api.notification.persistence.entity.NotificationEntity;
import com.c201.aebook.api.notification.persistence.repository.NotificationRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {
	
	@Mock
	private NotificationRepository notificationRepository;

	@Mock
	private RestTemplate restTemplate;
	@Mock
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private NotificationServiceImpl subject;

	@BeforeEach
	protected void setUp() throws Exception {
		ReflectionTestUtils.setField(subject, "TalkApiKey", "TalkApiKey");
		ReflectionTestUtils.setField(subject, "TalkUserId", "TalkUserId");
		ReflectionTestUtils.setField(subject, "TalkSenderKey", "TalkSenderKey");
		ReflectionTestUtils.setField(subject, "LowestPriceTplCode", "LowestPriceTplCode");
		ReflectionTestUtils.setField(subject, "CustomizeLowestPriceTplCode", "CustomizeLowestPriceTplCode");
		ReflectionTestUtils.setField(subject, "TalkSender", "TalkSender");
		ReflectionTestUtils.setField(subject, "aebookUrl", "aebookUrl");
	}

	@Test
	@DisplayName("testCreateToken: Happy Case")
	public void testCreateToken() throws ParseException, JsonProcessingException {
		// given
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
		params.add("apikey", "TalkApiKey"); //api key
		params.add("userid", "TalkUserId"); //사이트 아이디
		HttpEntity<MultiValueMap<String, Object>> TokenRequest = new HttpEntity<>(params);

		ReflectionTestUtils.setField(subject, "TalkApiKey", "TalkApiKey");
		ReflectionTestUtils.setField(subject, "TalkUserId","TalkUserId");

		String response = "{\"code\": 0, \"message\": \"정상적으로 생성하였습니다.\", \"token\": \"token\", \"urlencode\": \"urlencode\"}";

		BDDMockito.given(restTemplate.exchange(
				eq("https://kakaoapi.aligo.in/akv10/token/create/7/d/"),
				eq(HttpMethod.POST),
				eq(TokenRequest),
				eq(String.class)
		)).willReturn(ResponseEntity.ok().body(response));

		// when
		String ret = subject.createToken();

		// then
		Assertions.assertAll("결괏값 검증", () -> {
			Assertions.assertNotNull(ret);
		});

	}

	@Test
	public void testLowestPriceTalk() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testCustomizeLowestPriceTalk() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	@DisplayName("testGetNotificationUserInfoByBookId : happy case")
	public void testGetNotificationUserInfoByBookId() {
		// given
		Long bookId = 1234567890l;
		List<NotificationEntity> notificationUserList = new ArrayList<>();
		BDDMockito.given(notificationRepository.findByBookId(bookId)).willReturn(notificationUserList);
		
		// when
		String notificationType = "notificationType"; 
		List<NotificationEntity> ret = subject.getNotificationUserInfoByBookId(bookId, notificationType);
		
		// then
		Assertions.assertAll("결괏값 검증", () -> {
			Assertions.assertEquals(ret, ret);
		});
		BDDMockito.then(notificationRepository).should(times(1)).findByBookId(bookId);
	}

	@Test
	public void testGetNotificationUserInfoByBookIdAndNotificationType() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testContainsKeyStartingWith() {
		throw new RuntimeException("not yet implemented");
	}

}
