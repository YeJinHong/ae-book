package com.c201.aebook.api.notification.service.impl;

import com.c201.aebook.api.notification.service.TalkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TalkServiceImpl implements TalkService {

    @Value("${talk.api-key}")
    private String TalkApiKey;
    @Value("${talk.user-id}")
    private String TalkUserId;
    @Value("${talk.sender-key}")
    private String TalkSenderKey;
    @Value("${talk.lowest-price-tpl-code}")
    private String LowestPriceTplCode;
    @Value("${talk.customize-lowest-price-tpl-code}")
    private String CustomizeLowestPriceTplCode;
    @Value("${talk.sender}")
    private String TalkSender;
    @Value("${talk.aebook-url}")
    private String aebookUrl;

    @Override
    public String createToken() throws JsonProcessingException, ParseException {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("apikey", TalkApiKey); //api key
        params.add("userid", TalkUserId); //사이트 아이디
        HttpEntity<MultiValueMap<String, Object>> TokenRequest = new HttpEntity<>(params);

        //7일 동안 사용 가능한 엑세스 토큰을 발급 받기
        ResponseEntity<String> accessTokenResponse = rt.exchange(
                "https://kakaoapi.aligo.in/akv10/token/create/7/d/",
                HttpMethod.POST,
                TokenRequest,
                String.class
        );

        String token = null;
        log.info("access : {}", accessTokenResponse.getBody());
        log.info("type : {}", accessTokenResponse.getBody().getClass().getName());

        Object obj = null;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = new JSONObject();

        obj = jsonParser.parse(accessTokenResponse.getBody());
        jsonObj = (JSONObject) obj;

        log.info("jsonObj : {}", jsonObj);
        token = (String) jsonObj.get("urlencode");

        return token;
    }

    public ResponseEntity<String> LowestPriceTalk(String token) {
        /**
         * TODO: 알림톡 전송을 위해 정보 받아오기, notificationEntity를 통해 user 정보 가져오기
         * 일단 테스틀 위해 하드코딩
         * */
        String username = "도연";
        String bookTitle = "자바와 JUnit을 활용한 실용주의 단위 테스트";
        String subject = "도서 최저가 갱신";

        //버튼 정보 입력
        Map<String, String> buttonMap = new HashMap<>();
        buttonMap.put("name", "아이북 바로가기"); // 버튼명
        buttonMap.put("linkType", "WL"); // 버튼 링크타입(DS:배송조회, WL:웹링크, AL:앱링크, BK:봇키워드, MD:메시지전달)
        buttonMap.put("linkTypeName", "웹링크"); // 버튼 링크 타입네임, ( 배송조회, 웹링크, 앱링크, 봇키워드, 메시지전달 중에서 1개)
        buttonMap.put("linkM", aebookUrl); // WL일때 필수
        buttonMap.put("linkP", aebookUrl); // WL일때 필수
        List<Map> button = new ArrayList<>();
        button.add(buttonMap);
        Map<String, List<Map>> buttonInfo = new HashMap<>(); // 버튼 정보
        buttonInfo.put("button", button);

        // 전송 객체 생성
        RestTemplate rt = new RestTemplate();

        // header와 body 생성
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
        body.add("apikey", TalkApiKey); // api key
        body.add("userid", TalkUserId); // 사이트 아이디
        body.add("token", token); // 발급받은 토큰
        body.add("senderkey", TalkSenderKey); // 발신프로파일 키
        body.add("tpl_code", LowestPriceTplCode); // 템플릿 코드
        body.add("sender", TalkSender); // 발신자 연락처

        body.add("receiver_1", "01071276216"); // 수신자 연락처
        body.add("subject_1", subject); // 알림톡 제목(발신자만 보임)
        body.add("message_1", username + " 님\n" +
                "아이북에서 알림 신청한 '" + bookTitle + "' 도서의 최저가격이 갱신되었습니다.\n" +
                "아이북에 방문하여 확인해주세요."); // 알림톡 내용
        body.add("button_1", buttonInfo); // 버튼 정보
        body.add("testMode", "N"); // 테스트 모드

        //전송 객체 생성
        HttpEntity<MultiValueMap<String, Object>> LowestPriceRequest = new HttpEntity<>(body);

        //post로 보내고 결과 받기
        ResponseEntity<String> LowestPriceResponse = rt.exchange(
                "https://kakaoapi.aligo.in/akv10/alimtalk/send/",
                HttpMethod.POST,
                LowestPriceRequest,
                String.class
        );

        return LowestPriceResponse;
    }

}
