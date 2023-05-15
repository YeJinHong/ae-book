package com.c201.aebook.api.painting.presentation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.c201.aebook.api.painting.presentation.dto.request.PaintingRequestDTO;
import com.c201.aebook.api.painting.presentation.validator.PaintingValidator;
import com.c201.aebook.api.painting.service.impl.PaintingServiceImpl;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.vo.PaintingSO;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.converter.PaintingConverter;
import com.c201.aebook.utils.S3Downloader;
import com.c201.aebook.utils.S3Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@Import(PaintingController.class)
public class PaintingControllerTest {
	@Autowired
	private PaintingController paintingController;

	private MockMvc mockMvc;
	@MockBean
	private PaintingServiceImpl paintingService;
	@MockBean
	private PaintingConverter paintingConverter;
	@MockBean
	private PaintingValidator paintingValidator;
	@MockBean
	private S3Uploader s3Uploader;
	@MockBean
	private S3Downloader s3Downloader;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	protected void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(paintingController).build();
	}

	@DisplayName("testSavePainting: Happy Case")
	@Test
	public void testSavePainting() throws Exception {
		// given
		Map<String, String> data = new HashMap<>();
		data.put("title", "제목");
		data.put("type", "COLOR");
		String contents = objectMapper.writeValueAsString(data);

		Long userId = 1L;
		UserEntity user = UserEntity.builder().id(userId).build();
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		PaintingRequestDTO paintingRequestDTO = new PaintingRequestDTO();
		paintingRequestDTO.setTitle("title");

		String uploadImageUrl = "uploadImageUrl";
		BDDMockito.given(s3Uploader.upload(any(MultipartFile.class), any(String.class))).willReturn(uploadImageUrl);

		MockMultipartFile paintingFile = new MockMultipartFile(
			"paintingFile",
			"test.png",
			MediaType.IMAGE_PNG_VALUE,
			"<<png data>>".getBytes(StandardCharsets.UTF_8)
		);

		PaintingSO paintingSO = new PaintingSO();
		BDDMockito.given(paintingConverter.toPaintingSO(userId, uploadImageUrl, paintingRequestDTO))
			.willReturn(paintingSO);
		// when
		mockMvc.perform(multipart("/paintings")
				.file(paintingFile)
				.file(new MockMultipartFile("data", "", "application/json", contents.getBytes(StandardCharsets.UTF_8)))
				.contentType("multipart/form-data")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.with(user(customUserDetails)))
			.andExpect(status().isOk());

		// then
		BDDMockito.verify(paintingService, times(1)).savePainting(paintingSO);
		BDDMockito.verify(paintingConverter, times(1)).toPaintingSO(userId, uploadImageUrl, paintingRequestDTO);
	}

	@Test
	public void testDownloadPainting() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testDeletePainting() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testUpdatePaintingTitle() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetPaintingDetails() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetPaintingList() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetNewPaintingList() {
		throw new RuntimeException("not yet implemented");
	}

}
