package com.c201.aebook.api.notification.service;

import com.amazonaws.services.ec2.model.CpuOptionsRequest;
import com.c201.aebook.api.notification.persistence.entity.NotificationEntity;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.catalina.User;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface TalkService {

    public String createToken() throws JsonProcessingException, ParseException;

    public ResponseEntity<String> LowestPriceTalk(String token, List<NotificationEntity> userList, String bookTitle);

    public ResponseEntity<String> CustomizeLowestPriceTalk(String token, List<NotificationEntity> userList, String bookTitle, int price);

    public List<NotificationEntity> getNotificationUserInfoByBookId(Long bookId);
    public List<NotificationEntity> getNotificationUserInfoByBookIdAndNotificationType(Long bookId, String notificationType);

    public String getBookTitle(Long bookId);
    public int getBookPrice(Long bookId);
    public boolean containsKeyStartingWith(MultiValueMap<String, Object> map, String keyPrefix);

}
