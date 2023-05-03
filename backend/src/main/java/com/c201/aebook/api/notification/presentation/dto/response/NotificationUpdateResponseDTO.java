package com.c201.aebook.api.notification.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationUpdateResponseDTO {

    @Schema(description = "금액 상한선", defaultValue = "5000")
    private int upperLimit;

}