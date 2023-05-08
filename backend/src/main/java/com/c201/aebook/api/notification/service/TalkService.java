package com.c201.aebook.api.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;

public interface TalkService {

    public String createToken() throws JsonProcessingException, ParseException;

}
