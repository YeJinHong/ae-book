package com.c201.aebook.api.notification.service.impl;

import com.c201.aebook.api.book.persistence.repository.BookRepository;
import com.c201.aebook.api.notification.persistence.entity.NotificationEntity;
import com.c201.aebook.api.notification.persistence.repository.NotificationRepository;
import com.c201.aebook.api.notification.service.TalkService;
import com.c201.aebook.api.user.persistence.entity.UserEntity;
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

    private final NotificationRepository notificationRepository;
    private final BookRepository bookRepository;

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

    public ResponseEntity<String> LowestPriceTalk(String token, List<NotificationEntity> userList, String bookTitle) {
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

        int receiverSize = userList.size();
        for (int i=0; i<receiverSize; i++) {
            body.add("receiver_"+ (i+1), userList.get(i).getUser().getPhone()); // 수신자 연락처
            body.add("subject_"+ (i+1), subject); // 알림톡 제목(발신자만 보임)
            body.add("message_"+ (i+1), userList.get(i).getUser().getNickname() + " 님\n" +
                    "아이북에서 알림 신청한 '" + bookTitle + "' 도서의 최저가격이 갱신되었습니다.\n" +
                    "아이북에 방문하여 확인해주세요."); // 알림톡 내용
            body.add("button_"+ (i+1), buttonInfo); // 버튼 정보
        }
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

    public ResponseEntity<String> CustomizeLowestPriceTalk(String token, List<NotificationEntity> userList, String bookTitle, int price) {
        String subject = "사용지 지정 도서 최저가 갱신";

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
        body.add("tpl_code", CustomizeLowestPriceTplCode); // 템플릿 코드
        body.add("sender", TalkSender); // 발신자 연락처

        /**
         * 어떤 방법이 좀 더 현명한 방법일까.....
         *
         * 1번
         * 다른 곳에서 현재 도서의 최저가와 사용자가 지정한 최저가를 비교해서
         * 사용자 지정가가 더 낮은 경우만 모아서 userList로 보내기
         * 그리고 여기서 for문 돌면서 탬플릿에 맞춰서 보내기
         *
         * 2번
         * 어차피 여기서 탬플릿 때문에 for문을 도니까
         * 여기서 if문으로 현재 도서의 최저가와 사용자가 지정한 최저가를 비교해서
         * 사용자 지정가가 더 낮은 경우만 탬플릿에 맞춰서 바로 알림톡 보내기
         * */
        int receiverSize = userList.size();
        for (int i=0; i<receiverSize; i++) {
            // 2번 적용 -> 문제점 발견 : 만약에 조건에 맞는게 없다면..? -> 밑에서 receiver로 체크
            if(price > userList.get(i).getUpperLimit() && userList.get(i).getNotificationType().equals("S")) {
                body.add("receiver_" + (i + 1), userList.get(i).getUser().getPhone()); // 수신자 연락처
                body.add("subject_" + (i + 1), subject); // 알림톡 제목(발신자만 보임)
                body.add("message_" + (i + 1), userList.get(i).getUser().getNickname() + " 님\n" +
                        "아이북에서 알림 신청한 '" + bookTitle + "' 도서의 최저가격이 지정하신 " + userList.get(i).getUpperLimit() + "원 이하로 갱신되었습니다.\n" +
                        "아이북에 방문하여 확인해주세요!"); // 알림톡 내용
                body.add("button_" + (i + 1), buttonInfo); // 버튼 정보
            }
        }
        body.add("testMode", "N"); // 테스트 모드

        // 문제점 해결을 위해 receiver가 있는지 체크
        ResponseEntity<String> customizeLowestPriceTalk = null;
        if(containsKeyStartingWith(body,"receiver")) {
            //전송 객체 생성
            HttpEntity<MultiValueMap<String, Object>> LowestPriceRequest = new HttpEntity<>(body);

            //post로 보내고 결과 받기
            customizeLowestPriceTalk = rt.exchange(
                    "https://kakaoapi.aligo.in/akv10/alimtalk/send/",
                    HttpMethod.POST,
                    LowestPriceRequest,
                    String.class
            );   
        }

        return customizeLowestPriceTalk;
    }

    @Override
    public List<NotificationEntity> getNotificationUserInfoByBookId(Long bookId) {
        // 만약에 해당 책에 알림이 없는 경우를 먼저 확인해야 됨
        List<NotificationEntity> notificationUserList = notificationRepository.findByBookId(bookId);
        return notificationUserList;
    }

    @Override
    public String getBookTitle(Long bookId) {
        // 책 정보가 없는 경우 error -> 현재는 해야되지만 배치에서는 책정보가 있는것만 오지 않을까...? 그래도 해야될듯...
        String bookTitle = bookRepository.findTitleById(bookId);
        return bookTitle;
    }

    @Override
    public int getBookPrice(Long bookId) {
        // 책 정보가 없는 경우 error -> 현재는 해야되지만 배치에서는 책정보가 있는것만 오지 않을까....? 그래도 느낌이 해야될듯...
        int bookPrice = bookRepository.findPriceById(bookId);
        return bookPrice;
    }

    @Override
    public boolean containsKeyStartingWith(MultiValueMap<String, Object> map, String keyPrefix) {
        for (String key : map.keySet()) {
            if (key.startsWith(keyPrefix)) {
                return true;
            }
        }
        return false;
    }


}
