package com.example.factory_machine.service;

import com.example.factory_machine.dto.EventRequestDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    private final KafkaTemplate<String, EventRequestDto> kafkaTemplate;

    public Producer(KafkaTemplate<String, EventRequestDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(EventRequestDto dto) {
        kafkaTemplate.send("factory-events", dto.getEventId(), dto);
    }
}
