package com.c201.aebook.api.book.persistence.repository;

import static com.c201.aebook.api.book.persistence.entity.QBookEntity.*;

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
public class BookRepositoryImpl implements BookCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<BookEntity> findByTitleContaining(String keyword, boolean[] searchType, Pageable pageable) {
		QueryResults<BookEntity> bookList = queryFactory
			.selectFrom(bookEntity)
			.where(checkSearchOption(searchType, keyword))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetchResults();

		return new PageImpl<>(bookList.getResults(), pageable, bookList.getTotal());
	}

	// 체크된 검색 옵션으로 동적 쿼리 만들기
	private BooleanBuilder checkSearchOption(boolean[] searchType, String keyword) {
		BooleanBuilder booleanBuilder = new BooleanBuilder();

		if (searchType[0] == true) { // title
			booleanBuilder.or(bookEntity.title.containsIgnoreCase(keyword));
		}
		if (searchType[1] == true) { // author
			booleanBuilder.or(bookEntity.author.containsIgnoreCase(keyword));
		}
		if (searchType[2] == true) { // publisher
			booleanBuilder.or(bookEntity.publisher.containsIgnoreCase(keyword));
		}

		return booleanBuilder;
	}
}
