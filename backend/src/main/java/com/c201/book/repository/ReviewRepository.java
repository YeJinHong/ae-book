package com.c201.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.book.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	public Review findByUserIdAndBookId(Long userId, Long bookId);

	public List<Review> findAllByBookId(Long bookId);
}
