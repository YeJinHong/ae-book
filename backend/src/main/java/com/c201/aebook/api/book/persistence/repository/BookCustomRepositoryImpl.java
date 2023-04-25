package com.c201.aebook.api.book.persistence.repository;

import static com.c201.aebook.api.book.persistence.entity.QBookEntity.*;

import java.util.Arrays;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BookCustomRepositoryImpl implements BookCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<BookEntity> searchBookList(String keyword, String[] searchTarget, Pageable pageable) {
		QueryResults<BookEntity> bookList = queryFactory
			.selectFrom(bookEntity)
			.where(checkSearchOption(searchTarget, keyword))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetchResults();

		return new PageImpl<>(bookList.getResults(), pageable, bookList.getTotal());
	}

	// 체크된 검색 옵션으로 동적 쿼리 만들기
	private BooleanBuilder checkSearchOption(String[] searchTarget, String keyword) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();
		Arrays.stream(searchTarget)
			.forEach(target -> {
				if ("TITLE".equals(target)) {
					booleanBuilder.or(bookEntity.title.containsIgnoreCase(keyword));
				} else if ("AUTHOR".equals(target)) {
					booleanBuilder.or(bookEntity.author.containsIgnoreCase(keyword));
				} else if ("PUBLISHER".equals(target)) {
					booleanBuilder.or(bookEntity.publisher.containsIgnoreCase(keyword));
				}
			});

		return booleanBuilder;
	}
}
