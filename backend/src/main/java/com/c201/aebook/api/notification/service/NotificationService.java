package com.c201.aebook.api.notification.service;

import com.c201.aebook.api.notification.presentation.dto.response.NotificationBookDetailResponseDTO;
import com.c201.aebook.api.notification.presentation.dto.response.NotificationBookListResponseDTO;
import com.c201.aebook.api.vo.NotificationSO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    public void saveNotification(String userId, NotificationSO notificationSO);

    public Page<NotificationBookListResponseDTO> getMyNotificationBookList(String userId, Pageable pageable);

    public NotificationBookDetailResponseDTO getMyNotificationBookDetail(Long notificationId);

}
