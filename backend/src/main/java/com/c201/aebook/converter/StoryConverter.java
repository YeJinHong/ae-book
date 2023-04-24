package com.c201.aebook.converter;

import org.mapstruct.Mapper;

import com.c201.aebook.api.story.presentation.dto.request.StoryRequestDTO;
import com.c201.aebook.api.vo.StoryDeleteSO;
import com.c201.aebook.api.vo.StorySO;

@Mapper(componentModel = "spring")
public interface StoryConverter {
	StorySO toStorySO(Long userId, String imgUrl, StoryRequestDTO storyRequestDTO);

	StoryDeleteSO toStoryDeleteSO(Long userId, Long storyID);
}
