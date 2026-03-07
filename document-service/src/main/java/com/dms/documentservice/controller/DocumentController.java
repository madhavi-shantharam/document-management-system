package com.dms.documentservice.controller;

import com.dms.documentservice.entity.Document;
import com.dms.documentservice.service.DocumentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public Document uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("ownerId") String ownerId
            ) throws Exception {
        return documentService.uploadDocument(file, ownerId);
    }
}
