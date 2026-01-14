package com.example.factory_machine.controller;
import com.example.factory_machine.dto.TopDefectLineDto;
import com.example.factory_machine.service.DefectLineService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/stats")
public class DefectLineController {

    private final DefectLineService defectLineService;

    public DefectLineController(DefectLineService defectLineService) {
        this.defectLineService = defectLineService;
    }

    @GetMapping("/top-defect-lines")
    public List<TopDefectLineDto> getTopDefectLines(
            @RequestParam String machineId,
            @RequestParam String factoryId,
            @RequestParam Instant from,
            @RequestParam Instant to,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return defectLineService.getTopDefectLines(machineId,factoryId, from, to, limit);
    }
}

