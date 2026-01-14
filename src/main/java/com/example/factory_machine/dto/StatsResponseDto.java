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
}
