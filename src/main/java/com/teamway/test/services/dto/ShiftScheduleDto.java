package com.teamway.test.services.dto;

import java.time.LocalDate;

public record ShiftScheduleDto(Long id, Long workerId, String fullName, LocalDate date) {
}
