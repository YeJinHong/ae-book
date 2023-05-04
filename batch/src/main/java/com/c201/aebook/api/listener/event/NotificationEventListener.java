package com.c201.aebook.api.listener.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.c201.aebook.api.book.persistence.entity.BookEntity;
import com.c201.aebook.api.event.NotificationEvent;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class NotificationEventListener implements ApplicationListener<NotificationEvent> {

	@Override
	public void onApplicationEvent(NotificationEvent event) {
		BookEntity book = event.getBookEntity();//최저가가 생신된 BookEntity


	}

	//TODO : 선영님이 여기에 알림 만들어야 함
	
}
