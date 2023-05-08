package com.c201.aebook.api.notification.presentation.controller;

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

    @GetMapping("/customize-lower-price")
    public ResponseEntity customizeLowestPriceTalk() throws ParseException, JsonProcessingException {
        // 토큰 생성
        String token = talkService.createToken();

        // 최저가 알림톡 탬플릿 만들어서 알림톡 전송
        // 결과코드(code)가 0이면 성공 -99이면 전송 실패
        ResponseEntity<String> customizeLowestPrice = talkService.CustomizeLowestPriceTalk(token);

        return ResponseEntity.ok().body(customizeLowestPrice);
    }

    @GetMapping("/book")
    public ResponseEntity bookLowestPriceTalk(
            @RequestParam(name = "bookId") Long bookId
    ) throws ParseException, JsonProcessingException {
        // 1. notificationRepository에서 bookId로 알림 정보랑 유저들 정보 찾기
        List<UserEntity> userList = talkService.getNotificationUserInfoByBookId(bookId);

        // 2. bookId로 book title 가져오기 -> 배치에서는 바로 가져올 수 있지만 현재는 불가능하므로 repository에서 가져오기
        String bookTitle = talkService.getBookTitle(bookId);

        // 3. token 정보 얻어오기
        String token = talkService.createToken();

        // 4. 유저 정보로 알림톡 맞춰서 보내서 결과 받기
        ResponseEntity<String> LowestPriceResponse = talkService.LowestPriceTalk(token, userList, bookTitle);

        return ResponseEntity.status(200).body(LowestPriceResponse);
    }

}
