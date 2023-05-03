package com.c201.aebook.api.notification.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequestDTO {

    @Schema(description = "isbn", defaultValue = "0123456789103")
    private String isbn;
    @Schema(description = "금액 상한선", defaultValue = "5000")
    private int upperLimit;

}
