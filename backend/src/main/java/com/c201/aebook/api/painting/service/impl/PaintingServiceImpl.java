package com.c201.aebook.api.painting.service.impl;

import org.springframework.stereotype.Service;

import com.c201.aebook.api.painting.persistence.entity.PaintingEntity;
import com.c201.aebook.api.painting.persistence.repository.PaintingRepository;
import com.c201.aebook.api.painting.service.PaintingService;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.vo.PaintingSO;
import com.c201.aebook.converter.PaintingConverter;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaintingServiceImpl implements PaintingService {
	private final PaintingRepository paintingRepository;
	private final UserRepository userRepository;
	private final PaintingConverter paintingConverter;

	@Override
	public void savePainting(PaintingSO paintingSO) {
		UserEntity user = userRepository.findById(paintingSO.getUserId())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		paintingRepository.save(PaintingEntity.builder()
			.title(paintingSO.getTitle())
			.fileUrl(paintingSO.getFileUrl())
			.type(paintingSO.getType())
			.user(user)
			.build());
	}
}
