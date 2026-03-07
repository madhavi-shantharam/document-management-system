package com.dms.documentservice.service;

import com.dms.documentservice.entity.Document;
import com.dms.documentservice.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public DocumentService(DocumentRepository documentRepository, FileStorageService fileStorageService) {
        this.documentRepository = documentRepository;
        this.fileStorageService = fileStorageService;
    }

    public Document uploadDocument(MultipartFile multipartFile, String ownerId) throws Exception {
        // compute checksum
        byte[] bytes = multipartFile.getBytes();
        String checkSum = calculateCheckSum(bytes);

        // upload to S3
        String fileUrl = fileStorageService.uploadFile(multipartFile);

        // check if document already exists for this owner
        int newVersion = documentRepository
                .findTopByNameAndOwnerIdOrderByVersionDesc(multipartFile.getOriginalFilename(), ownerId)
                .map(Document::getVersion)
                .map(v -> v+1) // increment by 1 if it exists
                .orElse(1); // start at 1 if it doesn't exist

        // Create document metadata
        Document document = new Document();
        document.setName(multipartFile.getOriginalFilename());
        document.setFileUrl(fileUrl);
        document.setVersion(newVersion);
        document.setChecksum(checkSum);
        document.setOwnerId(ownerId);

        return documentRepository.save(document);
    }

    public String calculateCheckSum(byte[] bytes)  throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] digest = messageDigest.digest(bytes);
        return String.format("%032x", new BigInteger(1, digest));
    }
}
