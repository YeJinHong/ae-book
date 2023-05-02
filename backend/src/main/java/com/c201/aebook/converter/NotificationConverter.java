package com.c201.aebook.converter;

import com.c201.aebook.api.notification.presentation.dto.request.NotificationRequestDTO;
import com.c201.aebook.api.vo.NotificationSO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationConverter {

    NotificationSO toNotificationSO(NotificationRequestDTO notificationRequestDTO);

}
