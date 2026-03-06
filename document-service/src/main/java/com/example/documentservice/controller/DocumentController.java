package com.example.documentservice.controller;

import com.example.documentservice.model.Document;
import com.example.documentservice.repository.DocumentRepository;
import com.example.documentservice.service.NotificationClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentRepository documentRepository;
    private final NotificationClient notificationClient;

    public DocumentController(
            DocumentRepository documentRepository,
            NotificationClient notificationClient) {
        this.documentRepository = documentRepository;
        this.notificationClient = notificationClient;
    }

    @PostMapping
    public Document save(@RequestBody Document document) {
        Document saved = documentRepository.save(document);
        System.out.println("Calling Notification Service");
        notificationClient.sendNotification(saved.getTitle());

        return saved;
    }

    @GetMapping
    public List<Document> findAll() {
        return documentRepository.findAll();
    }
}
