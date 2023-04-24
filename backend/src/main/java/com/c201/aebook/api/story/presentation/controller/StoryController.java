package com.c201.aebook.api.story.presentation.controller;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.c201.aebook.api.common.BaseResponse;
import com.c201.aebook.api.story.presentation.dto.request.StoryRequestDTO;
import com.c201.aebook.api.story.presentation.validator.StoryValidator;
import com.c201.aebook.api.story.service.StoryService;
import com.c201.aebook.api.vo.StorySO;
import com.c201.aebook.converter.StoryConverter;
import com.c201.aebook.utils.S3Uploader;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "동화 Controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stories")
public class StoryController {

	private final StoryValidator storyValidator;
	private final S3Uploader s3Uploader;
	private final StoryConverter storyConverter;
	private final StoryService storyService;

	@Operation(summary = "동화 등록", description = "새 동화를 등록합니다.")
	@PostMapping(
		path = "",
		consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE}
	)
	public BaseResponse<?> saveStory(
		@RequestPart(value = "imageFile") MultipartFile multipartFile,
		@RequestPart(value = "data") StoryRequestDTO storyRequestDTO
		//,@AuthenticationPrincipal CustomUserDetails customUserDetails // TODO: 시큐리티 완성되면 주석 해제하고 쉼표 옮기기
	) throws IOException {
		// TODO: 토큰 유효성 검증 후 임시 dirName 변수 삭제
		// UserEntity loginUser = tokenUtils.validateGetUser(customUserDetails);
		// Long userId = loginUser.getId();
		Long userId = 1l;
		String dirName = String.valueOf(userId);

		// DTO NOT NULL 검증
		storyValidator.validateStoryRequestDTO(storyRequestDTO);

		// aws s3 file upload
		String uploadImageUrl = s3Uploader.upload(multipartFile, dirName);

		// 동화 내용 DB 저장
		StorySO storySO = storyConverter.toStorySO(userId, uploadImageUrl, storyRequestDTO);
		storyService.saveStory(storySO);

		return new BaseResponse<>(null, 200, "동화 작성 완료");
	}

	// TODO : getStoryList
	// TODO : getStoryDetail
	// TODO : updateStoryTitle
	// TODO : deleteStory

}
