package com.example.factory_machine.dto;

import java.util.ArrayList;
import java.util.List;

public class BatchIngestResponse {
    public int accepted;
    public int deduped;
    public int updated;
    public int rejected;
    public List<RejectionDto> rejections = new ArrayList<>();
}
