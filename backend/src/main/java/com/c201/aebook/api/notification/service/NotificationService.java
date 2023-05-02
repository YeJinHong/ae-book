package com.c201.aebook.api.notification.service;

import com.c201.aebook.api.vo.NotificationSO;

public interface NotificationService {

    public void saveNotification(String userId, NotificationSO notificationSO);
}
