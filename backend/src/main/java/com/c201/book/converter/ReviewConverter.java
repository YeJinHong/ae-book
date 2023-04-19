package com.c201.book.converter;

import org.mapstruct.Mapper;

import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.api.vo.ReviewSO;

@Mapper(componentModel = "spring")
public interface ReviewConverter {

	ReviewSO toReviewSo(ReviewReqDto reviewReqDto);

}
