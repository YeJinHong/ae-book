package com.c201.aebook.api.painting.presentation.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.c201.aebook.api.painting.presentation.dto.request.PaintingRequestDTO;
import com.c201.aebook.api.painting.presentation.dto.request.PaintingTitleRequestDTO;

@Component
public class PaintingValidator {
	public void validatePaintingRequestDTO(PaintingRequestDTO paintingRequestDTO) {
		checkStringType(paintingRequestDTO.getTitle(), "그림 제목");
	}

	public void validatePaintingTitleRequestDTO(PaintingTitleRequestDTO paintingTitleRequestDTO) {
		checkStringType(paintingTitleRequestDTO.getTitle(), "그림 제목");
	}

	public void checkStringType(String value, String name) {
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값입니다.");
		}
	}
}
