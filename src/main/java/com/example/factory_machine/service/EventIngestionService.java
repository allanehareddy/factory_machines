package com.example.factory_machine.service;

import com.example.factory_machine.dto.BatchIngestResponse;
import com.example.factory_machine.dto.EventRequestDto;
import com.example.factory_machine.dto.RejectionDto;
import com.example.factory_machine.model.EventEntity;
import com.example.factory_machine.repository.EventRepository;
import com.example.factory_machine.util.PayloadComparator;
import com.example.factory_machine.validation.EventValidator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class EventIngestionService {

    private final EventRepository repository;
    private final EventValidator validator;
    private final PayloadComparator comparator;
    private final Clock clock;

    public EventIngestionService(
            EventRepository repository,
            EventValidator validator,
            PayloadComparator comparator,Clock clock) {
        this.repository = repository;
        this.validator = validator;
        this.comparator = comparator;
        this.clock = clock;
    }

    @Transactional
    public BatchIngestResponse ingest(List<EventRequestDto> events) {

        BatchIngestResponse response = new BatchIngestResponse();

        for (EventRequestDto dto : events) {

            //  Validate
            Optional<String> error = validator.validate(dto);
            if (error.isPresent()) {
                response.rejected++;
                response.rejections.add(new RejectionDto(dto.eventId, error.get()));
                continue;
            }

            Instant receivedTime = Instant.now(clock);


            //  Dedup / Update
            Optional<EventEntity> existingOpt = repository.findByEventId(dto.eventId);

            if (existingOpt.isEmpty()) {
                // Insert new
                repository.save(toEntity(dto, receivedTime));
                response.accepted++;
                continue;
            }

            EventEntity existing = existingOpt.get();

            if (comparator.isSamePayload(existing, dto)) {
                response.deduped++;
                continue;
            }

            if (receivedTime.isAfter(existing.getReceivedTime())) {
                // Update
                existing.setEventTime(dto.eventTime);
                existing.setMachineId(dto.machineId);
                existing.setDurationMs(dto.durationMs);
                existing.setDefectCount(dto.defectCount);
                existing.setReceivedTime(receivedTime);
                repository.save(existing);
                response.updated++;
            } else {
                response.deduped++;
            }
        }

        return response;
    }

    private EventEntity toEntity(EventRequestDto dto, Instant receivedTime) {
        return new EventEntity(
                dto.eventId,
                dto.factoryId,
                dto.lineId,
                dto.machineId,
                dto.eventTime,
                receivedTime,
                dto.durationMs,
                dto.defectCount
        );
    }

}
