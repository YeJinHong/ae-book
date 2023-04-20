package com.c201.book.api.review.presentation.controller;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.book.api.review.persistence.entity.BookEntity;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    public Optional<BookEntity> findByIsbn(String isbn);
}
