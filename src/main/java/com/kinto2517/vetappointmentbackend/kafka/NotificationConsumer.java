package com.kinto2517.vetappointmentbackend.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kinto2517.vetappointmentbackend.entity.Notification;
import com.kinto2517.vetappointmentbackend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
@Service
public class NotificationConsumer {

    private final String notificationTopic = "notifications-topic";

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationConsumer(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = notificationTopic, groupId = "com.kinto2517.vetappointmentbackend")
    public void receiveNotification(String notificationJson) {
        Notification notification = convertJsonToNotification(notificationJson);
        if (notification != null) {
            notificationRepository.save(notification);
        }

    }

    private Notification convertJsonToNotification(String notificationJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(notificationJson, Notification.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
