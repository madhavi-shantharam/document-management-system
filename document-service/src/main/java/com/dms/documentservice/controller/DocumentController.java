package com.dms.documentservice.controller;

import com.dms.documentservice.entity.Document;
import com.dms.documentservice.repository.DocumentRepository;
import com.dms.documentservice.service.DocumentService;
import com.dms.documentservice.service.FileStorageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final FileStorageService  fileStorageService;

    public DocumentController(
            DocumentService documentService,
            DocumentRepository documentRepository,
            FileStorageService fileStorageService
    ) {
        this.documentService = documentService;
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public Document uploadDocument(
            @RequestParam("file") MultipartFile file,
            @RequestParam("ownerId") String ownerId
            ) throws Exception {
        return documentService.uploadDocument(file, ownerId);
    }

    // Get latest version download URL
    @GetMapping("/{ownerId}/{name}/latest")
    public URL downloadLatest(@PathVariable String ownerId, @PathVariable String name){
        Document doc = documentRepository.findTopByNameAndOwnerIdOrderByVersionDesc(name, ownerId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        String fileUrl = doc.getFileUrl();
        String key = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        return fileStorageService.generatePresignedUrl(key, Duration.ofMinutes(10));
    }

    // Get all the versions of a file(name)
    @GetMapping("{ownerId}/{name}/versions")
    public List<Document> downloadAllVersions(@PathVariable String ownerId, @PathVariable String name){
        return documentRepository.findByOwnerIdAndNameOrderByVersionDesc(ownerId, name);
    }

    // Download specific versions
    @GetMapping("{ownerId}/{name}/version/{version}")
    public URL downloadSpecificVersion(@PathVariable String ownerId, @PathVariable String name, @PathVariable Integer version){
        Document doc = documentRepository.findByOwnerIdAndNameOrderByVersionDesc(ownerId, name)
                .stream()
                .filter(d -> d.getVersion().equals(version))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Document version not found"));

        String fileUrl = doc.getFileUrl();
        String key = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        return fileStorageService.generatePresignedUrl(key, Duration.ofMinutes(10));
    }

}
