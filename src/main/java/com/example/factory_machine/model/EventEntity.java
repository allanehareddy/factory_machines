package com.example.factory_machine.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "events", uniqueConstraints = @UniqueConstraint(columnNames = "eventId"))
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;
    private String factoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getFactoryId() {
        return factoryId;
    }

    public void setFactoryId(String factoryId) {
        this.factoryId = factoryId;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public Instant getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public int getDefectCount() {
        return defectCount;
    }

    public void setDefectCount(int defectCount) {
        this.defectCount = defectCount;
    }

    private String lineId;
    private String machineId;

    private Instant eventTime;
    private Instant receivedTime;

    private long durationMs;
    private int defectCount;


    public EventEntity() {}

    public EventEntity(
            String eventId,
            String factoryId,
            String lineId,
            String machineId,
            Instant eventTime,
            Instant receivedTime,
            long durationMs,
            int defectCount
    ) {
        this.eventId = eventId;
        this.factoryId = factoryId;
        this.lineId = lineId;
        this.machineId = machineId;
        this.eventTime = eventTime;
        this.receivedTime = receivedTime;
        this.durationMs = durationMs;
        this.defectCount = defectCount;
    }
}
