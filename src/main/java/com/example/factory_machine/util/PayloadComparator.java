package com.example.factory_machine.util;

import com.example.factory_machine.dto.EventRequestDto;
import com.example.factory_machine.model.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class PayloadComparator {

    public boolean isSamePayload(EventEntity existing, EventRequestDto incoming) {
        return existing.getEventTime().equals(incoming.eventTime)
                && existing.getMachineId().equals(incoming.machineId)
                && existing.getDurationMs() == incoming.durationMs
                && existing.getDefectCount() == incoming.defectCount;
    }
}
