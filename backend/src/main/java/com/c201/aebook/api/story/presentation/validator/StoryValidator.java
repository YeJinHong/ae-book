package com.c201.aebook.api.story.presentation.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.c201.aebook.api.story.presentation.dto.request.StoryRequestDTO;

@Component
public class StoryValidator {

	/**
	 * [홍예진] StoryRequestDTO NOT NULL 검증
	 * @param storyRequestDTO
	 */
	public void validateStoryRequestDTO(StoryRequestDTO storyRequestDTO) {
		checkStringType(storyRequestDTO.getTitle(), "동화 제목");
		checkStringType(storyRequestDTO.getContent(), "동화 내용");
	}

	public void checkStringType(String value, String name) {
		if (StringUtils.isBlank(value)) {
			throw new IllegalArgumentException(name + "은/는 필수 입력값입니다.");
		}
	}
}
