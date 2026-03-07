package com.dms.documentservice.repository;

import com.dms.documentservice.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findTopByNameAndOwnerIdOrderByVersionDesc(String name, String ownerId);
}
