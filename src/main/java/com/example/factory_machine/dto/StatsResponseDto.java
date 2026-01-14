package com.example.factory_machine.dto;

import java.time.Instant;

public class StatsResponseDto {

    public String machineId;
    public Instant start;
    public Instant end;

    public long eventsCount;
    public long defectsCount;
    public double avgDefectRate;
    public String status;
    public StatsResponseDto(){

    }
    public StatsResponseDto(
            Long eventsCount,
            Long defectsCount
    ) {
        this.eventsCount = eventsCount == null ? 0 : eventsCount;
        this.defectsCount = defectsCount == null ? 0 : defectsCount;
    }


}
