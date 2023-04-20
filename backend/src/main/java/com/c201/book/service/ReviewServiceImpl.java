package com.c201.book.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c201.book.api.request.ReviewRequestDTO;
import com.c201.book.entity.BookEntity;
import com.c201.book.entity.ReviewEntity;
import com.c201.book.entity.UserEntity;
import com.c201.book.repository.BookRepository;
import com.c201.book.repository.ReviewRepository;
import com.c201.book.repository.UserRepository;
import com.c201.book.utils.exeption.CustomException;
import com.c201.book.utils.exeption.ErrorCode;

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
		BookEntity book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// 2. 유효한 userId인지 검증
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

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
}
