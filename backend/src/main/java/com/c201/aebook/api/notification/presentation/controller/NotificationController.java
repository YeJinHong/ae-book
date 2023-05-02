package com.c201.aebook.api.notification.presentation.controller;

import com.c201.aebook.api.common.BaseResponse;
import com.c201.aebook.api.notification.presentation.dto.request.NotificationRequestDTO;
import com.c201.aebook.api.notification.presentation.vaildator.NotificationValidator;
import com.c201.aebook.api.notification.service.impl.NotificationServiceImpl;
import com.c201.aebook.api.vo.NotificationSO;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.converter.NotificationConverter;
import com.c201.aebook.utils.RegexValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name="알림톡")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationValidator notificationValidator;
    private final RegexValidator regexValidator;
    private final NotificationConverter notificationConverter;
    private final NotificationServiceImpl notificationService;

    @Operation(summary = "알림 신청", description = "사용자가 하나의 책에 알림 신청을 합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public BaseResponse<?> saveNotification(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody NotificationRequestDTO notificationRequestDTO

    ) {
        // 1. DTO NOT NULL 검증
        notificationValidator.validateNotificationRequestDTO(notificationRequestDTO);

        // 2. isbn 검증
        regexValidator.validateIsbn(notificationRequestDTO.getIsbn());

        // 3. 알림 등록
        NotificationSO notificationSO = notificationConverter.toNotificationSO(notificationRequestDTO);
        notificationService.saveNotification(customUserDetails.getUsername(), notificationSO);

        return new BaseResponse<>(null, HttpStatus.OK.value(), "알림 신청 성공");
    }

}
