package com.example.factory_machine.dto;

public class TopDefectLineDto {

    public String lineId;
    public Long totalDefects;

    public TopDefectLineDto(String lineId, Long totalDefects) {
        this.lineId = lineId;
        this.totalDefects = totalDefects == null ? 0 : totalDefects;
    }
}
