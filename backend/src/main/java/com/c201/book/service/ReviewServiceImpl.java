package com.c201.book.service;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.request.ReviewReqDto;
import com.c201.book.model.Review;
import com.c201.book.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    @Override
    public BaseResponse<?> saveReview(ReviewReqDto reviewReqDto) {
        return null;
    }

    @Override
    public Review findReviewByUserIdAndBookId(Long userId, Long bookId) {
        return reviewRepository.findByUserIdAndBookId(userId, bookId);
    }
}
