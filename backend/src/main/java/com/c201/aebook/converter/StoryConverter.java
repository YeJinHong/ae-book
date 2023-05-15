package com.c201.aebook.converter;

import org.mapstruct.Mapper;

import com.c201.aebook.api.story.persistence.entity.StoryEntity;
import com.c201.aebook.api.story.presentation.dto.request.StoryPatchRequestDTO;
import com.c201.aebook.api.story.presentation.dto.request.StoryRequestDTO;
import com.c201.aebook.api.story.presentation.dto.response.StoryDeleteResponseDTO;
import com.c201.aebook.api.story.presentation.dto.response.StoryDetailResponseDTO;
import com.c201.aebook.api.story.presentation.dto.response.StoryListResponseDTO;
import com.c201.aebook.api.story.presentation.dto.response.StoryPatchResponseDTO;
import com.c201.aebook.api.story.presentation.dto.response.StorySaveResponseDTO;
import com.c201.aebook.api.vo.StoryDeleteSO;
import com.c201.aebook.api.vo.StoryPatchSO;
import com.c201.aebook.api.vo.StorySO;

@Mapper(componentModel = "spring")
public interface StoryConverter {
	StorySO toStorySO(Long userId, String voiceUrl, String imgUrl, StoryRequestDTO storyRequestDTO);

	StorySaveResponseDTO toStorySaveResponseDTO(String voiceUrl, String imgUrl, StoryRequestDTO storyRequestDTO);

	StoryDeleteSO toStoryDeleteSO(Long userId, Long storyId);

	StoryDetailResponseDTO toStoryDetailResponseDTO(StoryEntity storyEntity, Long storyId, String nickname);

	StoryPatchSO toStoryPatchSO(String userId, Long storyId, StoryPatchRequestDTO storyPatchRequestDTO);

	StoryPatchResponseDTO toStoryPatchResponseDTO(StoryPatchRequestDTO storyPatchRequestDTO, Long storyId);

	StoryDeleteResponseDTO toStoryDeleteResponseDTO(StoryDeleteSO storyDeleteSO, StoryEntity storyEntity);

	StoryListResponseDTO toStoryListResponseDTO(StoryEntity storyEntity, Long storyId, String nickname);
}
