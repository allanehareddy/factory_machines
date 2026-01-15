package com.example.factory_machine.service;

import com.example.factory_machine.dto.EventRequestDto;
import com.example.factory_machine.model.EventEntity;
import com.example.factory_machine.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.factory_machine.dto.BatchIngestResponse;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.Clock;
@SpringBootTest
@Transactional
class EventIngestionServiceTest {

    @Autowired
    private EventIngestionService ingestionService;

    @Autowired
    private EventRepository eventRepository;

    private EventRequestDto baseEvent(String id) {
        EventRequestDto e = new EventRequestDto();
        e.setEventId(id);
        e.setMachineId("M1");
        e.setEventTime(Instant.now().minusSeconds(60));
        e.setDurationMs(10);
        e.setDefectCount(2);
        return e;
    }

    @Test
    void duplicateEventId_isDeduped() {
        EventRequestDto e1 = baseEvent("E1");

        ingestionService.ingest(List.of(e1));
        BatchIngestResponse res = ingestionService.ingest(List.of(e1));

        assertEquals(1, res.deduped);
        assertEquals(1, eventRepository.count());
    }


    @Test
    void newerPayload_updatesEvent() throws InterruptedException {
        EventRequestDto e1 = baseEvent("E2");
        ingestionService.ingest(List.of(e1));

        Thread.sleep(5); // force newer receivedTime

        EventRequestDto e2 = baseEvent("E2");
        e2.setDefectCount(5);

        BatchIngestResponse res =
                ingestionService.ingest(List.of(e2));

        assertEquals(1, res.updated);
    }

    @Test
    void differentPayload_isUpdated_whenReceivedLater() {
        EventRequestDto original = baseEvent("E3");
        ingestionService.ingest(List.of(original));

        EventRequestDto modified = modifiedPayload(original);

        BatchIngestResponse res = ingestionService.ingest(List.of(modified));

        assertEquals(1, res.updated);
        assertEquals(1, eventRepository.count());
    }

    private EventRequestDto modifiedPayload(EventRequestDto original) {
        EventRequestDto e = baseEvent(original.getEventId());
        e.setDurationMs(original.getDurationMs() + 100); // change payload
        return e;
    }


    @Test
    void invalidDuration_rejected() {
        EventRequestDto e = baseEvent("E4");
        e.setDurationMs(-10);

        BatchIngestResponse res =
                ingestionService.ingest(List.of(e));

        assertEquals(1, res.rejected);
    }

    @Test
    void futureEventTime_rejected() {
        EventRequestDto e = baseEvent("E5");
        e.setEventTime(Instant.now().plusSeconds(3600));

        BatchIngestResponse res =
                ingestionService.ingest(List.of(e));

        assertEquals(1, res.rejected);
    }

    @Test
    void defectMinusOne_notStored() {
        EventRequestDto e = baseEvent("E6");
        e.setDefectCount(-1);

        ingestionService.ingest(List.of(e));

        EventEntity saved =
                eventRepository.findByEventId("E6").get();

        assertEquals(-1, saved.getDefectCount());
    }

    @Test
    void startEndBoundary_respected() {
        // Arrange
        Instant start = Instant.parse("2026-01-10T10:00:00Z");
        Instant end   = Instant.parse("2026-01-10T11:00:00Z");

        EventRequestDto atStart = baseEvent("E_START");
        atStart.setEventTime(start);              // INCLUDED

        EventRequestDto atEnd = baseEvent("E_END");
        atEnd.setEventTime(end);                  // EXCLUDED

        ingestionService.ingest(List.of(atStart, atEnd));

        // Act
        long count =
                eventRepository
                        .findByMachineIdAndEventTimeGreaterThanEqualAndEventTimeLessThan(
                                "M1", start, end
                        )
                        .size();

        // Assert
        assertEquals(1, count);
    }


    @Test
    void concurrentIngestion_dedupedCorrectly() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Runnable task = () ->
                ingestionService.ingest(List.of(baseEvent("E9")));

        for (int i = 0; i < 20; i++) {
            executor.submit(task);
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(1, eventRepository.count());
    }
}
