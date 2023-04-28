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
		Page<PaintingEntity> paintingEntities = paintingRepository.findByUserIdAndType(userId, type, pageable);
		Page<PaintingResponseDTO> result = paintingEntities.map(
			painting -> paintingConverter.toPaintingResponseDTO(painting));
		return result;
	}

	@Override
	public void deletePainting(Long paintingId, Long userId) {
		PaintingEntity paintingEntity = getOwnPainting(userId, paintingId);
		paintingRepository.delete(paintingEntity);
	}

	@Override
	public String getFilePath(Long paintingId, Long userId) {
		PaintingEntity paintingEntity = getOwnPainting(userId, paintingId);
		return paintingEntity.getFileUrl().substring(53);
	}

	@Override
	public PaintingResponseDTO updatePaintingTitle(PaintingPatchSO paintingPatchSO) {
		PaintingEntity paintingEntity = getOwnPainting(paintingPatchSO.getUserId(),
			paintingPatchSO.getPaintingId());
		paintingEntity.updatePainting(paintingPatchSO.getTitle());
		paintingRepository.save(paintingEntity);
		PaintingResponseDTO paintingResponseDTO = paintingConverter.toPaintingResponseDTO(paintingEntity);
		return paintingResponseDTO;
	}

	@Override
	public PaintingResponseDTO getPaintingDetails(Long userId, Long paintingId) {
		PaintingEntity paintingEntity = getOwnPainting(userId, paintingId);
		PaintingResponseDTO paintingResponseDTO = paintingConverter.toPaintingResponseDTO(paintingEntity);
		return paintingResponseDTO;
	}

	// 그림 작성자와 로그인한 사용자가 일치하는지 확인하는 함수
	public PaintingEntity getOwnPainting(Long userId, Long paintingId) {
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
		return paintingEntity;
	}
}
