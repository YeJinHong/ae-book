package com.c201.aebook.converter;

import org.mapstruct.Mapper;

import com.c201.aebook.api.review.presentation.dto.request.ReviewRequestDTO;
import com.c201.aebook.api.vo.ReviewSO;

@Mapper(componentModel = "spring")
public interface ReviewConverter {

	ReviewSO toReviewSO(ReviewRequestDTO reviewRequestDTO);

}
