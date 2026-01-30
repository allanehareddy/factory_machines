package com.example.factory_machine.service;

import com.example.factory_machine.dto.EventRequestDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Consumer {

    private final EventIngestionService ingestionService;

    public Consumer(EventIngestionService ingestionService) {
        this.ingestionService = ingestionService;
    }

    @KafkaListener(
            topics = "factory-events",
            containerFactory = "batchFactory"
    )
    public void consume(List<EventRequestDto> events) {
        ingestionService.ingest(events);
    }
}
