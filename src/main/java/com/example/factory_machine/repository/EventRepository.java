package com.example.factory_machine.repository;

import com.example.factory_machine.dto.StatsResponseDto;
import com.example.factory_machine.dto.TopDefectLineDto;
import com.example.factory_machine.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<EventEntity, Long> {

    Optional<EventEntity> findByEventId(String eventId);
    List<EventEntity> findByMachineIdAndEventTimeGreaterThanEqualAndEventTimeLessThan(
            String machineId,
            Instant start,
            Instant end
    );
    @Query("""
    select new com.example.factory_machine.dto.TopDefectLineDto(
        e.lineId,
        sum(e.defectCount)
    )
    from EventEntity e
    where e.factoryId = :factoryId
      and e.machineId = :machineId
      and e.eventTime between :from and :to
      and e.defectCount > 0
    group by e.lineId
    order by sum(e.defectCount) desc
""")
    List<TopDefectLineDto> findTopDefectLines(
            String factoryId,
            String machineId,
            Instant from,
            Instant to
    );


}


