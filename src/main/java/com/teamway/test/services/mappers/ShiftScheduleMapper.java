package com.teamway.test.services.mappers;

import com.teamway.test.repositories.entities.ShiftScheduleEntity;
import com.teamway.test.repositories.entities.WorkerEntity;
import com.teamway.test.repositories.models.ShiftScheduleData;
import com.teamway.test.services.dto.ShiftScheduleDto;

public class ShiftScheduleMapper {
    public static ShiftScheduleDto toShiftScheduleDto(ShiftScheduleEntity shiftScheduleEntity, WorkerEntity workerEntity) {
        return ShiftScheduleDto.builder()
            .id(shiftScheduleEntity.getId())
            .workerId(shiftScheduleEntity.getWorkerId())
            .workerName(workerEntity.getFirstName() + " " + workerEntity.getLastName())
            .date(shiftScheduleEntity.getDate())
            .build();
    }

    public static ShiftScheduleDto toShiftScheduleDto(ShiftScheduleData shiftScheduleData) {
        return ShiftScheduleDto.builder()
            .id(shiftScheduleData.id())
            .workerId(shiftScheduleData.workerId())
            .workerName(shiftScheduleData.workerFirstName() + " " + shiftScheduleData.workerLastName())
            .date(shiftScheduleData.date())
            .build();
    }
}
