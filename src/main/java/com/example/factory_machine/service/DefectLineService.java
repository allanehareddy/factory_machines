package com.example.factory_machine.service;

import com.example.factory_machine.dto.TopDefectLineDto;
import com.example.factory_machine.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DefectLineService {

    private final EventRepository repository;

    public DefectLineService(EventRepository repository) {
        this.repository = repository;
    }

    public List<TopDefectLineDto> getTopDefectLines(
            String factoryId,
            String machineId,
            Instant from,
            Instant to,
            int limit
    ) {

        List<TopDefectLineDto> results =
                repository.findTopDefectLines(factoryId,machineId,from, to);

        // Apply limit safely
        return results.size() > limit
                ? results.subList(0, limit)
                : results;
    }
}
