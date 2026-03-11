package com.dms.notificationservice.events;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DocumentEventConsumer {

    @KafkaListener(topics = "document-uploaded-topic", groupId = "notification-group")
    public void handleDocumentUploaded(DocumentUploadedEvent event){
        System.out.println("Received document uploaded event: " + event.getName());
        // Call your existing notification logic here
        sendNotification(event);
    }

    private void sendNotification(DocumentUploadedEvent event) {
        // Example: send email or push notification
        System.out.printf("Notifying owner %s about document %s%n", event.getOwnerId(), event.getName());
    }
}
