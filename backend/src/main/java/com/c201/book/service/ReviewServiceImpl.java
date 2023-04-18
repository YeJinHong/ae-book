package com.c201.book.service;

import com.c201.book.api.common.BaseResponse;
import com.c201.book.api.request.ReviewReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    /**
     * [문희주] Review에 새로운 서평 추가
     * @param reviewReqDto
     * @return
     */
    @Override
    public BaseResponse<?> saveReview(ReviewReqDto reviewReqDto) {
        return null;
    }
}
