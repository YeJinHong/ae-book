package com.c201.aebook.api.painting.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c201.aebook.api.painting.service.impl.PaintingServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "그림 Controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/paintings")
public class PaintingController {
	private final PaintingServiceImpl paintingService;

	// TODO : savePainting
	// TODO : downloadPainting
	// TODO : deletePainting
	// TODO : updatePaintingTitle
	// TODO : getPaintingDetails
	// TODO : getPaintingList

}
