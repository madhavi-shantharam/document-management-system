package com.dms.documentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic documentUploadedTopic() {
        return TopicBuilder.name("document-uploaded-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}