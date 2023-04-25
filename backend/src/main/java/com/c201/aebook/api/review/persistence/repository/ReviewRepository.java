package com.c201.aebook.api.review.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.review.persistence.entity.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
	// https://github.com/lmHOLO/SOCK/blob/master/back/sock/src/main/java/com/holo/sock/repository/snack/ReviewRepository.java
	@Query("SELECT re FROM ReviewEntity re LEFT JOIN FETCH re.user WHERE re.id = :reviewId")
	public Optional<ReviewEntity> findById(Long reviewId);

	public ReviewEntity findByUserIdAndBookId(Long userId, Long bookId);

	@Query(value = "SELECT re FROM ReviewEntity re LEFT JOIN FETCH re.user WHERE re.book.id = :bookId",
		countQuery = "select count(re) from ReviewEntity re")
	public Page<ReviewEntity> findByBookId(Long bookId, Pageable pageable);

	public Optional<ReviewEntity> findByIdAndUserId(Long reviewId, Long userId);

	@Query(value = "SELECT re FROM ReviewEntity re LEFT JOIN FETCH re.user WHERE re.user.id = :userId",
		countQuery = "select count(re) from ReviewEntity re")
	public Page<ReviewEntity> findByUserId(Long userId, Pageable pageable);

	@Query("SELECT re FROM ReviewEntity re LEFT JOIN FETCH re.user ORDER BY re.updatedAt DESC")
	public List<ReviewEntity> findTop12ByOrderByIdDesc();

}
