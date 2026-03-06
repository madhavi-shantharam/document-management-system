package com.dms.documentservice.controller;

import com.dms.documentservice.entity.Document;
import com.dms.documentservice.repository.DocumentRepository;
import com.dms.documentservice.service.NotificationClient;
import org.springframework.web.bind.annotation.*;

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
