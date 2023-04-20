package com.c201.book.api.review.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c201.book.api.review.persistence.entity.BookEntity;
import com.c201.book.api.review.persistence.entity.ReviewEntity;
import com.c201.book.api.review.persistence.entity.UserEntity;
import com.c201.book.api.review.persistence.repository.ReviewRepository;
import com.c201.book.api.review.persistence.repository.UserRepository;
import com.c201.book.api.review.presentation.controller.BookRepository;
import com.c201.book.api.review.presentation.dto.request.ReviewRequestDTO;
import com.c201.book.api.review.presentation.dto.response.ReviewResponseDTO;
import com.c201.book.api.review.service.ReviewService;
import com.c201.book.utils.exception.CustomException;
import com.c201.book.utils.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional
	public void saveReview(Long userId, String isbn, ReviewRequestDTO reviewReqDto) {
		// 1. 유효한 isbn인지 검증
		BookEntity book = bookRepository.findByIsbn(isbn)
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// 2. 유효한 userId인지 검증
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 3. 해당 책에 서평을 작성한 적 없는지 검증
		ReviewEntity review = reviewRepository.findByUserIdAndBookId(userId, book.getId());
		if (review != null) {
			throw new CustomException(ErrorCode.DUPLICATED_REVIEW);
		}

		// 4. 서평 저장
		reviewRepository.save(ReviewEntity.builder()
			.content(reviewReqDto.getContent())
			.score(reviewReqDto.getScore())
			.user(user)
			.book(book)
			.build());

		// 5. 도서 별점 정보 갱신
		book.updateScoreInfo(reviewReqDto.getScore());
	}

	@Override
	public Page<ReviewResponseDTO> getBookReviewList(String isbn, Pageable pageable) {
		// 1. isbn 유효성 검증
		BookEntity bookEntity = bookRepository.findByIsbn(isbn)
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// 2. Review List
		Page<ReviewEntity> reviews = reviewRepository.findByBookId(bookEntity.getId(), pageable);

		return reviews.map(a -> ReviewResponseDTO.builder()
			.reviewId(a.getId())
			.reviewerId(a.getUser().getId())
			.score(a.getScore())
			.content(a.getContent())
			.createAt(a.getCreatedAt())
			.updateAt(a.getUpdatedAt())
			.build());
	}
}
