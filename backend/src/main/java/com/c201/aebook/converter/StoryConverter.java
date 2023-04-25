package com.c201.aebook.converter;

import java.time.LocalDateTime;

import org.mapstruct.Mapper;

import com.c201.aebook.api.story.persistence.entity.StoryEntity;
import com.c201.aebook.api.story.presentation.dto.request.StoryRequestDTO;
import com.c201.aebook.api.story.presentation.dto.response.StoryResponseDTO;
import com.c201.aebook.api.vo.StoryDeleteSO;
import com.c201.aebook.api.vo.StorySO;

@Mapper(componentModel = "spring")
public interface StoryConverter {
	StorySO toStorySO(Long userId, String imgUrl, StoryRequestDTO storyRequestDTO);

	StoryDeleteSO toStoryDeleteSO(Long userId, Long storyId);

	StoryResponseDTO toStoryResponseDTO(StoryEntity storyEntity, Long storyId, String storyAuthorNickname,
		LocalDateTime createdAt, LocalDateTime updatedAt);

	StoryResponseDTO toStoryResponseDTO(StoryEntity storyEntity, Long storyId, String storyAuthorNickname);

}
