package com.c201.aebook.api.review.presentation.controller;

import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.c201.aebook.api.review.presentation.dto.request.ReviewRequestDTO;
import com.c201.aebook.api.review.presentation.dto.response.ReviewResponseDTO;
import com.c201.aebook.api.review.presentation.validator.ReviewValidator;
import com.c201.aebook.api.review.service.impl.ReviewServiceImpl;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.vo.ReviewSO;
import com.c201.aebook.auth.CustomUserDetails;
import com.c201.aebook.converter.ReviewConverter;
import com.c201.aebook.utils.RegexValidator;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@Import(ReviewController.class)
public class ReviewControllerTest {
	
	@Autowired
	private ReviewController reviewController;
	
	private MockMvc mockMvc;
	
	@MockBean
	private ReviewValidator reviewValidator;
	
	@MockBean
	private RegexValidator regexValidator;
	
	@MockBean
	private ReviewServiceImpl reviewService;
	
	@MockBean
	private ReviewConverter reviewConverter;

	@SuppressWarnings("deprecation")
	@BeforeEach
	protected void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				.standaloneSetup(reviewController)
				.setCustomArgumentResolvers(new AuthenticationPrincipalArgumentResolver())
				.addFilters(new SecurityContextPersistenceFilter())
				.build();
	}

	@Test
    @DisplayName("saveReview Test")
    public void testSaveReview() throws Exception {
        // given
        String isbn = "1234567890";

        CustomUserDetails customUserDetails = new CustomUserDetails(UserEntity.builder().id(1L).build());
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO("장관이고요 죽겠읍니다", 5);
        ReviewSO reviewSO = ReviewSO.builder()
            .content(reviewRequestDTO.getContent())
            .score(reviewRequestDTO.getScore())
            .build();
        BDDMockito.given(reviewConverter.toReviewSO(Mockito.any(ReviewRequestDTO.class))).willReturn(reviewSO);

        BDDMockito.given(reviewService.saveReview(customUserDetails.getUsername(), isbn, reviewSO))
            .willReturn(new ReviewResponseDTO());

        // when
        mockMvc.perform(post("/reviews/" + isbn)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(reviewSO))
        		.with(user(customUserDetails)))
            .andExpect(status().isOk())
            .andDo(print());

        // then
        BDDMockito.then(reviewService).should(times(1)).saveReview(customUserDetails.getUsername(), isbn, reviewSO);
    }

	@Test
	public void testGetBookReviewList() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetMyReviewList() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetMyReview() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testModifyReview() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testDeleteReview() {
		throw new RuntimeException("not yet implemented");
	}

	@Test
	public void testGetLatestReviewList() {
		throw new RuntimeException("not yet implemented");
	}

}
