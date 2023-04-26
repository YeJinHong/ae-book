package com.c201.aebook.api.painting.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.c201.aebook.api.painting.persistence.entity.PaintingType;
import com.c201.aebook.api.painting.presentation.dto.response.PaintingResponseDTO;
import com.c201.aebook.api.vo.PaintingSO;

public interface PaintingService {
	public void savePainting(PaintingSO paintingSO);

	public Page<PaintingResponseDTO> getPaintingList(Long userId, PaintingType type, Pageable pageable);

	public void deletePainting(Long paintingId, Long userId);
}
