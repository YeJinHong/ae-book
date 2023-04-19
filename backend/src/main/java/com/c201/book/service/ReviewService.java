package com.c201.book.service;

import java.util.List;

import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.api.response.ReviewResDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
	// 1. saveReview : 서평 등록
	public void saveReview(Long userId, String isbn, ReviewReqDto reviewReqDto);

	// 2. getBookReviewList : 특정 책에 달린 서평 리스트
	public Page<ReviewResDto> getBookReviewList(String isbn, Pageable pageable);

	// 3. getMyReviewList : 현재 로그인한 유저가 등록한 서평 리스트

	// 4. getReview : 특정 서평 조회

	// 5. modifyReview : 특정 서평 수정

	// 6. deleteReview : 특정 서평 삭제

}
