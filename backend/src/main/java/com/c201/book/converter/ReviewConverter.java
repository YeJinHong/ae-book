package com.c201.book.converter;

import org.mapstruct.Mapper;

import com.c201.book.api.review.presentation.dto.request.ReviewRequestDTO;
import com.c201.book.api.vo.ReviewSO;

@Mapper(componentModel = "spring")
public interface ReviewConverter {

	ReviewSO toReviewSO(ReviewRequestDTO reviewRequestDTO);

}
