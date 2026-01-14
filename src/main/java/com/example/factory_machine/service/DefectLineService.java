package com.example.factory_machine.service;

public List<TopDefectLineDto> getTopDefectLines(
        String factoryId,
        Instant from,
        Instant to,
        int limit
        ) {

        List<TopDefectLineDto> result =
        repository.findTopDefectLines(factoryId, from, to);

        // Apply limit in service (JPQL portability)
        return result.size() > limit
        ? result.subList(0, limit)
        : result;
        }
