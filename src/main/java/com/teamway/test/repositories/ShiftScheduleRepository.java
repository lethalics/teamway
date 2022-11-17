package com.teamway.test.repositories;

import com.teamway.test.repositories.entities.ShiftScheduleEntity;
import com.teamway.test.repositories.models.ShiftScheduleData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftScheduleEntity, Long> {

    @Query("SELECT new com.teamway.test.repositories.models.ShiftScheduleData(sc.id, sc.workerId, w.firstName, w.lastName, sc.date) from ShiftScheduleEntity AS sc LEFT JOIN WorkerEntity AS w ON sc.workerId = w.id WHERE sc.id=:scheduleId ")
    Optional<ShiftScheduleData> getShiftSchedule(@Param("scheduleId") Long scheduleId);
}
