package com.dms.documentservice.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DocumentEventProducer {
    @Autowired
    private final KafkaTemplate<String, DocumentUploadedEvent> kafkaTemplate;

    public DocumentEventProducer(KafkaTemplate<String, DocumentUploadedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishDocumentUploaded(DocumentUploadedEvent event) {
        kafkaTemplate.send("document-uploaded-topic", event);
    }
}
