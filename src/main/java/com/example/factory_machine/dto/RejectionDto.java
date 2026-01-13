package com.example.factory_machine.dto;

public class RejectionDto {
    public String eventId;
    public String reason;

    public RejectionDto(String eventId, String reason) {
        this.eventId = eventId;
        this.reason = reason;
    }
}
