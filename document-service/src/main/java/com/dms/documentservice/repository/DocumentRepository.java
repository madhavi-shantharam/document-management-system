package com.dms.documentservice.repository;

import com.dms.documentservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Get latest version of a file by ownerId and name
    Optional<Document> findTopByNameAndOwnerIdOrderByVersionDesc(String name, String ownerId);

    // Get all versions of a file
    List<Document> findByOwnerIdAndNameOrderByVersionDesc(String ownerId, String name);
}
