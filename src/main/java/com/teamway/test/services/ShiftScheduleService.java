package com.teamway.test.services;

import com.teamway.test.controllers.dto.RequestShiftScheduleDto;
import com.teamway.test.controllers.dto.SearchShiftScheduleDto;
import com.teamway.test.repositories.ShiftScheduleRepository;
import com.teamway.test.repositories.WorkerRepository;
import com.teamway.test.repositories.entities.Shift;
import com.teamway.test.repositories.entities.ShiftScheduleEntity;
import com.teamway.test.services.dto.ShiftScheduleDto;
import com.teamway.test.services.mappers.ShiftScheduleMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ShiftScheduleService {

    private final ShiftScheduleRepository shiftScheduleRepository;
    private final WorkerRepository workerRepository;

    public ShiftScheduleDto addShiftSchedule(RequestShiftScheduleDto requestShiftScheduleDto) {
        var workerEntityResult = this.workerRepository.findByIdAndActive(requestShiftScheduleDto.workerId(), true);
        if (workerEntityResult.isEmpty()) {
            throw new IllegalArgumentException("Invalid worker ID");
        }
        var workerEntity = workerEntityResult.get();
        var shiftScheduleEntity = ShiftScheduleEntity.builder()
            .workerId(workerEntity.getId())
            .date(requestShiftScheduleDto.date())
            .shift(Shift.valueOf(requestShiftScheduleDto.shift().name()))
            .build();


        var savedShiftSchedule = saveShiftSchedule(shiftScheduleEntity);

        return ShiftScheduleMapper.toShiftScheduleDto(savedShiftSchedule, workerEntity);
    }

    public Optional<ShiftScheduleDto> getShiftSchedule(Long scheduleId) {
        var scheduleData = this.shiftScheduleRepository.getShiftSchedule(scheduleId);
        return scheduleData.map(ShiftScheduleMapper::toShiftScheduleDto);
    }

    public Optional<ShiftScheduleDto> updateShiftSchedule(Long scheduleId, RequestShiftScheduleDto requestShiftScheduleDto) {
        var existingShiftScheduleEntityResult = this.shiftScheduleRepository.findById(scheduleId);
        if (existingShiftScheduleEntityResult.isEmpty()) {
            return Optional.empty();
        }

        var updatedShiftScheduleEntity = this.updateShiftScheduleEntityData(existingShiftScheduleEntityResult.get(), requestShiftScheduleDto);

        this.saveShiftSchedule(updatedShiftScheduleEntity);

        return getShiftSchedule(scheduleId);
    }

    public Boolean deleteShiftSchedule(Long shiftScheduleId) {
        int rowsDeleted = this.shiftScheduleRepository.deleteByIdWithResult(shiftScheduleId);
        return rowsDeleted > 0;
    }

    public List<ShiftScheduleDto> searchShiftSchedule(SearchShiftScheduleDto searchShiftScheduleDto) {
        var results = this.shiftScheduleRepository.searchShiftSchedule(searchShiftScheduleDto);
        return results.stream()
            .map(ShiftScheduleMapper::toShiftScheduleDto)
            .collect(Collectors.toList());
    }

    private ShiftScheduleEntity saveShiftSchedule(ShiftScheduleEntity entity) {
        try {
            return shiftScheduleRepository.save(entity);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException(String.format("There is already a shift scheduled on %s for worker_id: %d", entity.getDate(), entity.getWorkerId()));
        }
    }

    private ShiftScheduleEntity updateShiftScheduleEntityData(ShiftScheduleEntity shiftScheduleEntity, RequestShiftScheduleDto requestShiftScheduleDto) {
        shiftScheduleEntity.setWorkerId(requestShiftScheduleDto.workerId());
        shiftScheduleEntity.setShift(Shift.valueOf(requestShiftScheduleDto.shift().name()));
        shiftScheduleEntity.setDate((requestShiftScheduleDto.date()));

        return shiftScheduleEntity;
    }
}
