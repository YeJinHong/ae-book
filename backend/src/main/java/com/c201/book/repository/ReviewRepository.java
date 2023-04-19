package com.c201.book.repository;

import com.c201.book.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    public Review findByUserIdAndBookId(Long userId, Long bookId);
}
