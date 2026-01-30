package com.example.factory_machine.controller;

import com.example.factory_machine.dto.BatchIngestResponse;
import com.example.factory_machine.dto.EventRequestDto;
import com.example.factory_machine.service.EventIngestionService;
import com.example.factory_machine.service.Producer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventIngestionService service;

    public EventController(EventIngestionService service, Producer producer) {
        this.service = service;
        this.producer = producer;
    }

    private final Producer producer;

    @PostMapping("/batch")
    public ResponseEntity<Void> ingest(@RequestBody List<EventRequestDto> events) {
        events.forEach(producer::send);
        return ResponseEntity.accepted().build();
    }

}
