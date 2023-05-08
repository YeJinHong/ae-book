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

}
