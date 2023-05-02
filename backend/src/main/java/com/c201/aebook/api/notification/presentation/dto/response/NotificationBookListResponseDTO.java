package com.c201.aebook.api.notification.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationBookListResponseDTO {

    @Schema(description = "알림 ID", defaultValue = "1")
    private Long id;

    @Schema(description = "사용자 상한가", defaultValue = "1000")
    private int upperLimit;

    @Schema(description = "도서 제목", defaultValue = "철학이 필요한 시간 - 강신주의 인문학 카운슬링")
    private String title;

    @Schema(description = "13자리 ISBN", defaultValue = "9788958285342")
    private String isbn;

    @Schema(description = "도서 가격", defaultValue = "12460")
    private int price;

    @Schema(description = "표지 이미지 주소", defaultValue = "주소")
    private String coverImageUrl;

    @Builder
    public NotificationBookListResponseDTO(Long id, int upperLimit, String title, String isbn, int price, String coverImageUrl) {
        this.id = id;
        this.upperLimit = upperLimit;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.coverImageUrl = coverImageUrl;
    }
}
