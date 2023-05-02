package com.c201.aebook.api.review.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.review.persistence.entity.ReviewEntity;
import com.c201.aebook.api.review.persistence.repository.ReviewRepository;
import com.c201.aebook.api.review.presentation.dto.response.ReviewResponseDTO;
import com.c201.aebook.api.review.service.ReviewService;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.c201.aebook.api.user.persistence.repository.UserRepository;
import com.c201.aebook.api.vo.ReviewSO;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional
	public void saveReview(String userId, String isbn, ReviewSO reviewSO) {
		// 1. isbn 유효성 검증
		BookEntity bookEntity = bookRepository.findByIsbn(isbn)
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// 2. userId 유효성 검증
		UserEntity userEntity = userRepository.findById(Long.valueOf(userId))
			.orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 3. 해당 책에 서평을 작성한 적 없는지 검증
		ReviewEntity reviewEntity = reviewRepository.findByUserIdAndBookId(userEntity.getId(), bookEntity.getId());
		if (reviewEntity != null) {
			throw new CustomException(ErrorCode.DUPLICATED_REVIEW);
		}

		// 4. 서평 저장
		reviewRepository.save(ReviewEntity.builder()
			.content(reviewSO.getContent())
			.score(reviewSO.getScore())
			.user(userEntity)
			.book(bookEntity)
			.build());

		// 5. 도서 별점 정보 갱신
		bookEntity.updateScoreInfo(reviewSO.getScore(), 1);
	}

	@Override
	public Page<ReviewResponseDTO> getBookReviewList(String isbn, Pageable pageable) {
		// 1. isbn 유효성 검증
		BookEntity bookEntity = bookRepository.findByIsbn(isbn)
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// 2. Review List
		Page<ReviewEntity> reviews = reviewRepository.findByBookId(bookEntity.getId(), pageable);

		return reviews.map(review -> ReviewResponseDTO.builder()
			.id(review.getId())
			.reviewerNickname(review.getUser().getNickname())
			.score(review.getScore())
			.content(review.getContent())
			.createdAt(review.getCreatedAt())
			.updatedAt(review.getUpdatedAt())
			.build());
	}

	@Override
	public Page<ReviewResponseDTO> getMyReviewList(String userId, Pageable pageable) {
		Page<ReviewEntity> reviews = reviewRepository.findByUserId(Long.valueOf(userId), pageable);

		return reviews.map(review -> ReviewResponseDTO.builder()
			.id(review.getId())
			.reviewerNickname(review.getUser().getNickname())
			.score(review.getScore())
			.content(review.getContent())
			.isbn(review.getBook().getIsbn())
			.createdAt(review.getCreatedAt())
			.updatedAt(review.getUpdatedAt())
			.build());
	}

	@Override
	public ReviewResponseDTO getReview(Long reviewId) {
		ReviewEntity review = reviewRepository.findById(reviewId)
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
		return ReviewResponseDTO.builder()
			.id(review.getId())
			.reviewerNickname(review.getUser().getNickname())
			.score(review.getScore())
			.content(review.getContent())
			.isbn(review.getBook().getIsbn())
			.createdAt(review.getCreatedAt())
			.updatedAt(review.getUpdatedAt())
			.build();
	}

	@Override
	@Transactional
	public void modifyReview(Long reviewId, String userId, ReviewSO reviewSO) {
		// 1. reviewId 유효성, 작성자 아이디 일치 검증
		ReviewEntity reviewEntity = reviewRepository.findByIdAndUserId(reviewId, Long.valueOf(userId))
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		// 2. 해당 도서의 별점 정보 변경
		BookEntity bookEntity = bookRepository.findById(reviewEntity.getBook().getId())
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
		bookEntity.updateScoreInfo(-reviewEntity.getScore(), 0);
		bookEntity.updateScoreInfo(reviewSO.getScore(), 0);

		// 3. 서평 수정
		reviewEntity.updateReviewEntity(reviewSO.getContent(), reviewSO.getScore());
		reviewRepository.save(reviewEntity);
	}

	@Override
	@Transactional
	public void deleteReview(Long reviewId, String userId) {
		// 1. reviewId 유효성, 작성자 아이디 일치 검증
		ReviewEntity reviewEntity = reviewRepository.findByIdAndUserId(reviewId, Long.valueOf(userId))
			.orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

		// 2. 해당 도서의 별점 정보 변경
		BookEntity bookEntity = bookRepository.findById(reviewEntity.getBook().getId())
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
		bookEntity.updateScoreInfo(-reviewEntity.getScore(), -1);

		// 3. 서평 삭제
		reviewRepository.delete(reviewEntity);
	}

	@Override
	public List<ReviewResponseDTO> getLatestReviewList() {
		List<ReviewEntity> reviews = reviewRepository.findTop12ByOrderByIdDesc();

		return reviews.stream()
			.map(review -> ReviewResponseDTO.builder()
				.id(review.getId())
				.reviewerNickname(review.getUser().getNickname())
				.score(review.getScore())
				.content(review.getContent())
				.isbn(review.getBook().getIsbn())
				.createdAt(review.getCreatedAt())
				.updatedAt(review.getUpdatedAt())
				.build())
			.collect(Collectors.toList());
	}
}
