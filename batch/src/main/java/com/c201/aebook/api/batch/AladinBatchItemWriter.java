package com.c201.aebook.api.batch;

import java.util.List;
import java.util.Optional;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.event.NotificationEvent;

import lombok.RequiredArgsConstructor;

/*
* 알라딘 api에서 가져온 데이터 저장
* */

@Component
@RequiredArgsConstructor
public class AladinBatchItemWriter implements ItemWriter<BookEntity> {

	private final BookRepository bookRepository;
	private final ApplicationEventPublisher publisher;


	@Override
	public void write(List<? extends BookEntity> items) throws Exception {
		for(BookEntity item : items){
			Optional<BookEntity> book = bookRepository.findByIsbn(item.getIsbn());
			if(book.isPresent()){
				//기존 db 가격과 비교하여 최저가인 경우 가격만 갱신
				BookEntity updateBook = book.get();
				if(item.getPrice() < updateBook.getPrice()){
					updateBook.setPrice(item.getPrice());
					updateBook.setAladinUrl(item.getAladinUrl());
					updateBook.setId(item.getId());

					bookRepository.save(updateBook);

					//알림 이벤트 발생
					publisher.publishEvent(new NotificationEvent(this, updateBook, item));
				}
			}else{
				bookRepository.save(item);
			}
		}
	}

}
