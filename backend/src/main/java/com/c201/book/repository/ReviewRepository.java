package com.c201.book.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	public Review findByUserIdAndBookId(Long userId, Long bookId);

	public Page<Review> findByBookId(Long bookId, Pageable pageable);
}
