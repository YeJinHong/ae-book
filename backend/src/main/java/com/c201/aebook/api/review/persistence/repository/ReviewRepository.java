package com.c201.aebook.api.review.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.review.persistence.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	public ReviewEntity findByUserIdAndBookId(Long userId, Long bookId);

	public Page<ReviewEntity> findByBookId(Long bookId, Pageable pageable);
}
