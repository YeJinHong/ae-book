package com.c201.aebook.api.book.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.book.persistence.entity.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
	public Optional<BookEntity> findByIsbn(String isbn);

	public List<BookEntity> findTop5ByTitleContaining(String keyword);
}
