package com.c201.aebook.api.story.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.c201.aebook.api.story.presentation.dto.response.StoryResponseDTO;
import com.c201.aebook.api.vo.StoryDeleteSO;
import com.c201.aebook.api.vo.StoryPatchSO;
import com.c201.aebook.api.vo.StorySO;

public interface StoryService {
	public void saveStory(StorySO storySO);

	Page<StoryResponseDTO> getStoryList(Long userId, Pageable pageable);

	public void deleteStory(StoryDeleteSO storyId);

	StoryResponseDTO getStoryDetail(Long storyId);

	void updateStoryTitle(StoryPatchSO storyPatchSO);
}
