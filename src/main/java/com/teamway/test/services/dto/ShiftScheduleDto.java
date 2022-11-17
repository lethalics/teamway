package com.teamway.test.services.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ShiftScheduleDto(Long id, Long workerId, String workerName, LocalDate date) {
}
