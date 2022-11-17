package com.teamway.test.controllers.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

//todo add pagination and sorting
@Builder
public record SearchShiftScheduleDto(Long workerId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate endDate) {
}
