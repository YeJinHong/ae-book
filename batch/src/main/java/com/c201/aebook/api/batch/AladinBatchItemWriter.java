package com.c201.aebook.api.batch;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookRepository;

import lombok.RequiredArgsConstructor;

/*
* 알라딘 api에서 가져온 데이터 저장
* */

@Component
@RequiredArgsConstructor
public class AladinBatchItemWriter implements ItemWriter<BookEntity> {

	private final BookRepository bookRepository;

	@Override
	public void write(List<? extends BookEntity> items) throws Exception {

		for(BookEntity item : items){
			Optional<BookEntity> book = bookRepository.findByIsbn(item.getIsbn());
			if(book.isPresent()){
				//최저가만 갱신
				BookEntity updateBook = book.get();
				updateBook.setAladinUrl(item.getAladinUrl());
				updateBook.setPrice(item.getPrice());

				bookRepository.save(updateBook);
			}else{
				bookRepository.save(item);
			}
		}

	}

}
