package com.c201.aebook.api.notification.presentation.controller;

import com.c201.aebook.api.notification.persistence.entity.NotificationEntity;
import com.c201.aebook.api.notification.service.impl.TalkServiceImpl;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name="알림톡 전송")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/talks")
public class TalkTestController {

    private final TalkServiceImpl talkService;

    @GetMapping("/token")
    public String createToken() throws ParseException, JsonProcessingException {
        return talkService.createToken();
    }

    // 최저가 알림톡 기본 형식
//    @GetMapping("/lower-price")
//    public ResponseEntity lowestPriceTalk() throws ParseException, JsonProcessingException {
//        // 토큰 생성
//        String token = talkService.createToken();
//
//        // 최저가 알림톡 탬플릿 만들어서 알림톡 전송
//        // 결과코드(code)가 0이면 성공 -99이면 전송 실패
//        ResponseEntity<String> LowestPriceResponse = talkService.LowestPriceTalk(token);
//
//        return ResponseEntity.ok().body(LowestPriceResponse);
//    }

    // 사용자 지정 최저가 알림톡 기본 형식
//    @GetMapping("/customize-lower-price")
//    public ResponseEntity customizeLowestPriceTalk() throws ParseException, JsonProcessingException {
//        // 토큰 생성
//        String token = talkService.createToken();
//
//        // 최저가 알림톡 탬플릿 만들어서 알림톡 전송
//        // 결과코드(code)가 0이면 성공 -99이면 전송 실패
//        ResponseEntity<String> customizeLowestPrice = talkService.CustomizeLowestPriceTalk(token);
//
//        return ResponseEntity.ok().body(customizeLowestPrice);
//    }

    @GetMapping("/book-lower-price")
    public ResponseEntity bookLowestPriceTalk(
            @RequestParam(name = "bookId") Long bookId
    ) throws ParseException, JsonProcessingException {
        // 1. notificationRepository에서 bookId로 알림 정보랑 유저들 정보 찾기
        List<NotificationEntity> userList = talkService.getNotificationUserInfoByBookId(bookId);

        // 2. bookId로 book title 가져오기 -> 배치에서는 바로 가져올 수 있지만 현재는 불가능하므로 repository에서 가져오기
        String bookTitle = talkService.getBookTitle(bookId);

        // 3. token 정보 얻어오기
        String token = talkService.createToken();

        // 4. 유저 정보로 알림톡 맞춰서 보내서 결과 받기
        ResponseEntity<String> LowestPriceResponse = talkService.LowestPriceTalk(token, userList, bookTitle);

        return ResponseEntity.status(200).body(LowestPriceResponse);
    }

    @GetMapping("/book-customize-lower-price")
    public ResponseEntity bookCustomizeLowestPriceTalk(
            @RequestParam(name = "bookId") Long bookId
    ) throws ParseException, JsonProcessingException {
        // 1. bookId로 book price 가져오기 -> 배치에서는 바로 가져올 수 있지만 현재는 불가능하므로 bookRepository에서 가져오기
        int bookPrice = talkService.getBookPrice(bookId);

        // 2. notificationRepository에서 bookId로 알림 정보랑 유저들 정보 찾기
        List<NotificationEntity> userList = talkService.getNotificationUserInfoByBookId(bookId);

        // 3. bookId로 book title 가져오기 -> 배치에서는 바로 가져올 수 있지만 현재는 불가능하므로 bookRepository에서 가져오기
        String bookTitle = talkService.getBookTitle(bookId);

        // 1번과 3번은 배치에서 bookEntity로 가져올 수도 있을듯?

        // 4. token 정보 얻어오기
        String token = talkService.createToken();

        // 5. 유저 정보로 알림톡 맞춰서 보내서 결과 받기
        ResponseEntity<String> customizeLowestPrice = talkService.CustomizeLowestPriceTalk(token, userList, bookTitle, bookPrice);
        if(customizeLowestPrice == null) {
            return ResponseEntity.status(200).body("전송 대상 없음");
        }

        return ResponseEntity.status(200).body(customizeLowestPrice);
    }

}
