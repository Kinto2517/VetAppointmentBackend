package com.kinto2517.vetappointmentbackend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.vetappointmentbackend.entity.Notification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String notificationTopic = "notifications-topic";

    public NotificationProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Notification notification) {
        String notificationJson = convertNotificationToJson(notification);
        kafkaTemplate.send(notificationTopic, notificationJson);
    }

    private String convertNotificationToJson(Notification notification) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
