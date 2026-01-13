package com.example.factory_machine.dto;

import java.time.Instant;

public class EventRequestDto {
    public String eventId;
    public Instant eventTime;
    public String machineId;
    public long durationMs;
    public int defectCount;
}

