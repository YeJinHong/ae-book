package com.c201.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.api.response.ReviewResDto;
import com.c201.book.model.Book;
import com.c201.book.model.Review;
import com.c201.book.model.User;
import com.c201.book.repository.BookRepository;
import com.c201.book.repository.ReviewRepository;
import com.c201.book.repository.UserRepository;
import com.c201.book.utils.RegexValidationUtils;
import com.c201.book.utils.exeption.CustomException;
import com.c201.book.utils.exeption.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final RegexValidationUtils regexValidationUtils;
	private final ReviewRepository reviewRepository;
	private final UserRepository userRepository;
	private final BookRepository bookRepository;

	@Override
	@Transactional
	public void saveReview(Long userId, String isbn, ReviewReqDto reviewReqDto) {
		// 1. isbn 유효성 검증
		regexValidationUtils.IsbnValidation(isbn);
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// 2. userId 유효성 검증
		User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

		// 3. 해당 책에 서평을 작성한 적 없는지 검증
		Review review = reviewRepository.findByUserIdAndBookId(userId, book.getId());
		if (review != null) {
			throw new CustomException(ErrorCode.DUPLICATED_REVIEW);
		}

		// 4. 서평 저장
		reviewRepository.save(Review.builder()
			.content(reviewReqDto.getContent())
			.score(reviewReqDto.getScore())
			.user(user)
			.book(book)
			.build());

		// 5. 도서 별점 정보 갱신
		book.updateScoreInfo(reviewReqDto.getScore());
	}

	@Override
	public List<ReviewResDto> getBookReviewList(String isbn, int pageNumber, int pageSize, String sort) {
		// 1. isbn 유효성 검증
		regexValidationUtils.IsbnValidation(isbn);
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));

		// keyword, search, orderby, size 등의 파라미터 ... 필드명 알아서 맞춰넣기.. sort도 찾아보기
		// pageable 객체 타입 ..
		// 2. Pageable 객체 생성
		Pageable pageable = null;
		switch (sort) {
			case "SCORE_HIGHEST":
				pageable = PageRequest.of(pageNumber, pageSize, Sort.by("score").descending());
				break;
			case "SCORE_LOWEST":
				pageable = PageRequest.of(pageNumber, pageSize, Sort.by("score").ascending());
				break;
			default: // LATEST
				pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending();
				break;
		}

		// 2. Review List
		List<Review> reviews = reviewRepository.findAllByBookId(book.getId());

		return reviews.stream()
			.map(a -> ReviewResDto.builder()
				.reviewId(a.getId())
				.reviewerId(a.getUser().getId())
				.score(a.getScore())
				.content(a.getContent())
				.createAt(a.getCreatedAt())
				.updateAt(a.getUpdatedAt())
				.build())
			.collect(Collectors.toList());
	}
}
