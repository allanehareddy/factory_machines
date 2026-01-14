package com.example.factory_machine.util;

import com.example.factory_machine.dto.EventRequestDto;
import com.example.factory_machine.model.EventEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PayloadComparator {

    public boolean isSamePayload(EventEntity e, EventRequestDto d) {
        return Objects.equals(e.getFactoryId(), d.factoryId)
                && Objects.equals(e.getMachineId(), d.machineId)
                && Objects.equals(e.getLineId(), d.lineId)
                && Objects.equals(e.getEventTime(), d.eventTime)
                && e.getDurationMs() == d.durationMs
                && e.getDefectCount() == d.defectCount;
    }
}

