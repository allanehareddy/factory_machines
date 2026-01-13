package com.example.factory_machine.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "events",
        uniqueConstraints = @UniqueConstraint(columnNames = "eventId")
)
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String eventId;

    @Column(nullable = false)
    private Instant eventTime;

    @Column(nullable = false)
    private Instant receivedTime;

    @Column(nullable = false)
    private String machineId;

    @Column(nullable = false)
    private long durationMs;

    @Column(nullable = false)
    private int defectCount;

    public Long getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public Instant getEventTime() {
        return eventTime;
    }

    public Instant getReceivedTime() {
        return receivedTime;
    }

    public String getMachineId() {
        return machineId;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public int getDefectCount() {
        return defectCount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setEventTime(Instant eventTime) {
        this.eventTime = eventTime;
    }

    public void setReceivedTime(Instant receivedTime) {
        this.receivedTime = receivedTime;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public void setDurationMs(long durationMs) {
        this.durationMs = durationMs;
    }

    public void setDefectCount(int defectCount) {
        this.defectCount = defectCount;
    }
// getters & setters (or Lombok @Data)
}
