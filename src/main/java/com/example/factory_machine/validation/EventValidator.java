package com.example.factory_machine.validation;

import com.example.factory_machine.dto.EventRequestDto;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

@Component
public class EventValidator {

    private static final long MAX_DURATION_MS = 6 * 60 * 60 * 1000;
    private final Clock clock;

    public EventValidator(Clock clock) {
        this.clock = clock;
    }

    public Optional<String> validate(EventRequestDto dto) {

        if (dto.durationMs < 0 || dto.durationMs > MAX_DURATION_MS) {
            return Optional.of("INVALID_DURATION");
        }

        Instant now = Instant.now(clock);
        if (dto.eventTime.isAfter(now.plusSeconds(15 * 60))) {
            return Optional.of("EVENT_TIME_IN_FUTURE");
        }

        return Optional.empty();
    }
}


