package com.c201.aebook.api.book.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.c201.aebook.api.notification.persistence.repository.NotificationRepository;
import com.c201.aebook.auth.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookCustomRepository;
import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.book.presentation.dto.response.BookResponseDTO;
import com.c201.aebook.api.book.presentation.dto.response.BookSearchResponseDTO;
import com.c201.aebook.api.book.presentation.dto.response.BookSimpleResponseDTO;
import com.c201.aebook.api.book.service.BookService;
import com.c201.aebook.converter.BookConverter;
import com.c201.aebook.utils.exception.CustomException;
import com.c201.aebook.utils.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	private final BookRepository bookRepository;
	private final BookCustomRepository bookCustomRepository;
	private final BookConverter bookConverter;
	private final NotificationRepository notificationRepository;

	@Override
	public BookResponseDTO searchBookDetail(String isbn, CustomUserDetails customUserDetails) {
		BookEntity book = bookRepository.findByIsbn(isbn)
			.orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
		BookResponseDTO bookResponseDTO = bookConverter.toBookResponseDTO(book);

		// 사용자 정보 찾기
		boolean notitication = false;
		if(customUserDetails != null) {
			// 사용자Id와 bookId로 알림 정보 찾기
			notitication = notificationRepository.existsByBookId(book.getId(), Long.valueOf(customUserDetails.getUsername()));
		}

		bookResponseDTO.setNotification(notitication);

		return bookResponseDTO;
	}

	@Override
	public List<String> getAutocompleteTitle(String keyword) {
		List<BookEntity> bookList = bookRepository.findTop5ByTitleContaining(keyword);
		List<String> titleList = bookList.stream()
			.map(BookEntity::getTitle)
			.collect(Collectors.toList());
		return titleList;
	}

	@Override
	public Page<BookSearchResponseDTO> searchBookList(String keyword, String[] searchTarget, Pageable pageable) {
		Page<BookEntity> bookList = bookCustomRepository.searchBookList(keyword, searchTarget, pageable);
		Page<BookSearchResponseDTO> result = bookList.map(book -> bookConverter.toBookSearchResponseDTO(book));
		return result;
	}

	@Override
	public List<BookSimpleResponseDTO> getNewBookList() {
		List<BookEntity> bookList = bookRepository.findTop16ByOrderByUpdatedAtDesc();
		List<BookSimpleResponseDTO> result = bookList.stream().map(book -> bookConverter.toBookSimpleResponseDTO(book))
			.collect(Collectors.toList());
		return result;
	}

}
