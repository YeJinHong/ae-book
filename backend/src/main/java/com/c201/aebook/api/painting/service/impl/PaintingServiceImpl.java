package com.c201.aebook.api.painting.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.c201.aebook.api.painting.persistence.entity.PaintingEntity;
import com.c201.aebook.api.painting.persistence.entity.PaintingType;
import com.c201.aebook.api.painting.persistence.repository.PaintingRepository;
import com.c201.aebook.api.painting.presentation.dto.response.PaintingResponseDTO;
import com.c201.aebook.api.painting.service.PaintingService;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.vo.PaintingPatchSO;
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

	@Override
	public Page<PaintingResponseDTO> getPaintingList(Long userId, PaintingType type, Pageable pageable) {
		UserEntity userEntity = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
		Page<PaintingEntity> paintingEntities = paintingRepository.findAllByUserIdAndType(userId, type, pageable);
		Page<PaintingResponseDTO> result = paintingEntities.map(
			painting -> paintingConverter.toPaintingResponseDTO(painting));
		return result;
	}

	@Override
	public void deletePainting(Long paintingId, Long userId) {
		// 1. 사용자 유효성 검사
		UserEntity userEntity = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 2. 그림 유효성 검사
		PaintingEntity paintingEntity = paintingRepository.findById(paintingId)
			.orElseThrow(() -> new CustomException(ErrorCode.PAINTING_NOT_FOUND));

		// 3. 작성자와 로그인한 사용자가 일치하는지 검사
		if (paintingEntity.getUser().getId() != userEntity.getId()) {
			throw new CustomException(ErrorCode.FORBIDDEN_USER);
		}
		paintingRepository.delete(paintingEntity);
	}

	@Override
	public String downloadPainting(Long paintingId, Long userId) {
		// 1. 사용자 유효성 검사
		UserEntity userEntity = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 2. 그림 유효성 검사
		PaintingEntity paintingEntity = paintingRepository.findById(paintingId)
			.orElseThrow(() -> new CustomException(ErrorCode.PAINTING_NOT_FOUND));

		// 3. 작성자와 로그인한 사용자가 일치하는지 검사
		if (paintingEntity.getUser().getId() != userEntity.getId()) {
			throw new CustomException(ErrorCode.FORBIDDEN_USER);
		}
		return paintingEntity.getFileUrl().substring(53);
	}

	@Override
	public void updatePaintingTitle(PaintingPatchSO paintingPatchSO) {
		// 1. 사용자 유효성 검사
		UserEntity userEntity = userRepository.findById(paintingPatchSO.getUserId())
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 2. 그림 유효성 검사
		PaintingEntity paintingEntity = paintingRepository.findById(paintingPatchSO.getPaintingId())
			.orElseThrow(() -> new CustomException(ErrorCode.PAINTING_NOT_FOUND));

		// 3. 작성자와 로그인한 사용자가 일치하는지 검사
		if (paintingEntity.getUser().getId() != userEntity.getId()) {
			throw new CustomException(ErrorCode.FORBIDDEN_USER);
		}
		paintingEntity.updatePainting(paintingPatchSO.getTitle());

		paintingRepository.save(paintingEntity);
	}

	@Override
	public PaintingResponseDTO getPaintingDetails(Long userId, Long paintingId) {
		// 1. 사용자 유효성 검사
		UserEntity userEntity = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 2. 그림 유효성 검사
		PaintingEntity paintingEntity = paintingRepository.findById(paintingId)
			.orElseThrow(() -> new CustomException(ErrorCode.PAINTING_NOT_FOUND));

		// 3. 작성자와 로그인한 사용자가 일치하는지 검사
		if (paintingEntity.getUser().getId() != userEntity.getId()) {
			throw new CustomException(ErrorCode.FORBIDDEN_USER);
		}

		PaintingResponseDTO paintingResponseDTO = paintingConverter.toPaintingResponseDTO(paintingEntity);
		return paintingResponseDTO;
	}
}
