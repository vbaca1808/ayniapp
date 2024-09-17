package com.ayni.coperacion.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HtmlService {
    
    private final RestTemplate restTemplate;

    public HtmlService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchHtml(String url) {
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

}
