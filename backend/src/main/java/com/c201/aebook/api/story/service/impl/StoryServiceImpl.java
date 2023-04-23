package com.c201.aebook.api.story.service.impl;

import org.springframework.stereotype.Service;

import com.c201.aebook.api.story.persistence.entity.StoryEntity;
import com.c201.aebook.api.story.persistence.repository.StoryRepository;
import com.c201.aebook.api.story.service.StoryService;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.vo.StorySO;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {
	private final StoryRepository storyRepository;
	private final UserRepository userRepository;

	@Override
	public void saveStory(StorySO storySO) {
		// 유효한 userId인지 검증
		UserEntity user = userRepository.findById(storySO.getUserId())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		storyRepository.save(StoryEntity.builder()
			.title(storySO.getTitle())
			.content(storySO.getContent())
			.imgUrl(storySO.getImgUrl())
			.user(user)
			.build());
	}
}
