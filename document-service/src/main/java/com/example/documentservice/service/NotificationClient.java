package com.example.documentservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationClient {
    private final RestTemplate restTemplate;

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "fallbackNotification")
    public void sendNotification(String title) {
        String url =
                "http://localhost:8083/notifications?message=Document Created:"
                + title;
        restTemplate.postForObject(url, null, String.class);
    }

    public void fallbackNotification(String title, Exception e) {
        System.out.println("Notification service unavailable. Saving event locally.");
    }
}
