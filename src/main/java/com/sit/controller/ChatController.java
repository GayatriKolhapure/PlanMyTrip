package com.sit.controller;

import com.sit.dto.ChatRequestDto;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

    private final String API_KEY =
            "AIzaSyCEBALMqv0iUR2LaVx74l-FWoyXi9xDY00";

    @PostMapping
    public Map<String, Object> chat(
            @RequestBody ChatRequestDto request
    ) {

    	String url =
    			"https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
    			+ API_KEY;

        RestTemplate restTemplate =
                new RestTemplate();

        Map<String, Object> body =
                new HashMap<>();

        List<Map<String, Object>> contents =
                new ArrayList<>();

        Map<String, Object> content =
                new HashMap<>();

        List<Map<String, String>> parts =
                new ArrayList<>();

        Map<String, String> text =
                new HashMap<>();

        text.put("text", request.getMessage());

        parts.add(text);

        content.put("parts", parts);

        contents.add(content);

        body.put("contents", contents);

        Map response =
                restTemplate.postForObject(
                        url,
                        body,
                        Map.class
                );

        return response;
    }
}
