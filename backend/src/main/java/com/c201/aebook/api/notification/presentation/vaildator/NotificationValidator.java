package com.c201.aebook.api.notification.presentation.vaildator;

import com.c201.aebook.api.notification.presentation.dto.request.NotificationRequestDTO;
import com.c201.aebook.api.notification.presentation.dto.request.NotificationUpdateRequestDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class NotificationValidator {
	public void validateNotificationRequestDTO(NotificationRequestDTO notificationRequestDTO) {
		checkStringType(notificationRequestDTO.getIsbn(), "isbn");
		checkIntType(notificationRequestDTO.getUpperLimit(), "금액 상한선");
		checkNotificationType(notificationRequestDTO.getNotificationType(), "알림 타입");
	}

	public void checkNotificationType(String notificationType, String name) {
		if (!Arrays.asList("S", "D").contains(notificationType)) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값이며 S 또는 D만 가능합니다.");
		}
	}

	public void checkLongType(Long value, String name) {
		if (value == null || value <= 0) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값이며 양수 값만 가능합니다.");
		}
	}

	public void checkIntType(int value, String name) {
		if (value < 0) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값이며 0 또는 양수 값만 가능합니다.");
		}
	}

	public void checkStringType(String value, String name) {
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값입니다.");
		}
	}

	public void validateNotificationUpdateRequestDTO(NotificationUpdateRequestDTO notificationUpdateRequestDTO) {
		checkIntType(notificationUpdateRequestDTO.getUpperLimit(), "금액 상한선");
		checkNotificationType(notificationUpdateRequestDTO.getNotificationType(), "알림 타입");
	}
}
