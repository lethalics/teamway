package com.teamway.test.services;

import com.teamway.test.controllers.dto.RequestScheduleShiftDto;
import com.teamway.test.repositories.ShiftScheduleRepository;
import com.teamway.test.repositories.WorkerRepository;
import com.teamway.test.repositories.entities.Shift;
import com.teamway.test.repositories.entities.ShiftScheduleEntity;
import com.teamway.test.services.dto.ShiftScheduleDto;
import com.teamway.test.services.mappers.ShiftScheduleMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ShiftSchedulerService {

    private final ShiftScheduleRepository shiftScheduleRepository;
    private final WorkerRepository workerRepository;

    public ShiftScheduleDto addShiftSchedule(RequestScheduleShiftDto requestScheduleShiftDto) {
        var workerEntityResult = this.workerRepository.findByIdAndActive(requestScheduleShiftDto.workerId(), true);
        if (workerEntityResult.isEmpty()) {
            throw new IllegalArgumentException("Invalid worker ID");
        }
        var workerEntity = workerEntityResult.get();
        var shiftScheduleEntity = ShiftScheduleEntity.builder()
            .workerId(workerEntity.getId())
            .date(requestScheduleShiftDto.date())
            .shift(Shift.valueOf(requestScheduleShiftDto.shift().name()))
            .build();


        var savedShiftSchedule = saveShiftSchedule(shiftScheduleEntity);

        return ShiftScheduleMapper.toShiftScheduleDto(savedShiftSchedule, workerEntity);
    }

    public Optional<ShiftScheduleDto> getShiftSchedule(Long scheduleId) {
        var scheduleData = this.shiftScheduleRepository.getShiftSchedule(scheduleId);
        return scheduleData.map(ShiftScheduleMapper::toShiftScheduleDto);
    }

    private ShiftScheduleEntity saveShiftSchedule(ShiftScheduleEntity entity) {
        try {
            return shiftScheduleRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException(String.format("There is a shift already scheduled on %s for worker_id: %d", entity.getDate(), entity.getWorkerId()));
        }
    }
}
