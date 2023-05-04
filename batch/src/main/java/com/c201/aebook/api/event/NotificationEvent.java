package com.c201.aebook.api.event;

import org.springframework.context.ApplicationEvent;

import com.c201.aebook.api.book.persistence.entity.BookEntity;

/*
* 알림을 위한 이벤트 클래스
* */
public class NotificationEvent extends ApplicationEvent {

	private BookEntity bookEntity;

	public NotificationEvent(Object source, BookEntity bookEntity) {
		super(source);
		this.bookEntity = bookEntity;
	}

	public BookEntity getBookEntity() {
		return bookEntity;
	}

}
