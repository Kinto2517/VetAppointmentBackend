package com.kinto2517.vetappointmentbackend.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "vetappointmentkafka";

    @PostMapping("/send")
    public String sendMessageToKafkaTopic(@RequestParam String message) {
        kafkaTemplate.send(TOPIC, message);
        return "Message sent to Kafka: " + message;
    }
}
