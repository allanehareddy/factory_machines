package com.example.factory_machine.service;

import com.example.factory_machine.dto.StatsResponseDto;
import com.example.factory_machine.model.EventEntity;
import com.example.factory_machine.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class StatsService {

    private final EventRepository repository;

    public StatsService(EventRepository repository) {
        this.repository = repository;
    }

    public StatsResponseDto getStats(String machineId, Instant start, Instant end) {

        List<EventEntity> events =
                repository.findByMachineIdAndEventTimeGreaterThanEqualAndEventTimeLessThan(
                        machineId, start, end
                );

        long eventsCount = events.size();

        long defectsCount = events.stream()
                .filter(e -> e.getDefectCount() != -1)
                .mapToLong(EventEntity::getDefectCount)
                .sum();

        double windowHours =
                Duration.between(start, end).toSeconds() / 3600.0;

        double avgDefectRate =
                windowHours == 0 ? 0 : defectsCount / windowHours;

        String status = avgDefectRate < 2.0 ? "Healthy" : "Warning";

        StatsResponseDto response = new StatsResponseDto();
        response.machineId = machineId;
        response.start = start;
        response.end = end;
        response.eventsCount = eventsCount;
        response.defectsCount = defectsCount;
        response.avgDefectRate = avgDefectRate;
        response.status = status;

        return response;
    }
}
