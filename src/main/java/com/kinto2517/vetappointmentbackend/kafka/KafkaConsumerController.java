package com.kinto2517.vetappointmentbackend.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerController {

    private static final String TOPIC = "vetappointmentkafka";

    @KafkaListener(topics = TOPIC, groupId = "com.kinto2517.vetappointmentbackend")
    public void consumeMessage(String message) {
        System.out.println("Received message from Kafka: " + message);
    }
}
