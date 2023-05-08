package com.c201.aebook.api.notification.presentation.controller;

import com.c201.aebook.api.notification.service.impl.TalkServiceImpl;
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

    @GetMapping("/lower-price")
    public ResponseEntity lowestPriceTalk() throws ParseException, JsonProcessingException {
        // 토큰 생성
        String token = talkService.createToken();

        // 최저가 알림톡 탬플릿 만들어서 알림톡 전송
        // 결과코드(code)가 0이면 성공 -99이면 전송 실패
        ResponseEntity<String> LowestPriceResponse = talkService.LowestPriceTalk(token);

        return ResponseEntity.ok().body(LowestPriceResponse);
    }

    @GetMapping("/customize-lower-price")
    public ResponseEntity customizeLowestPriceTalk() throws ParseException, JsonProcessingException {
        // 토큰 생성
        String token = talkService.createToken();

        // 최저가 알림톡 탬플릿 만들어서 알림톡 전송
        // 결과코드(code)가 0이면 성공 -99이면 전송 실패
        ResponseEntity<String> customizeLowestPrice = talkService.CustomizeLowestPriceTalk(token);

        return ResponseEntity.ok().body(customizeLowestPrice);
    }

}
