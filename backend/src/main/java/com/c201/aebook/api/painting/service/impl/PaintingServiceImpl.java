package com.c201.aebook.api.painting.service.impl;

import org.springframework.stereotype.Service;

import com.c201.aebook.api.painting.persistence.repository.PaintingRepository;
import com.c201.aebook.api.painting.service.PaintingService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaintingServiceImpl implements PaintingService {
	private final PaintingRepository paintingRepository;
}
