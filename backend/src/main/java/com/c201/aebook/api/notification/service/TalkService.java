package com.c201.aebook.api.notification.service;

import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TalkService {

    public String createToken() throws JsonProcessingException, ParseException;

    public ResponseEntity<String> LowestPriceTalk(String token, List<UserEntity> userList, String bookTitle);

    public ResponseEntity<String> CustomizeLowestPriceTalk(String token);

    public List<UserEntity> getNotificationUserInfoByBookId(Long bookId);

    public String getBookTitle(Long bookId);
}
