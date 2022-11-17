package com.teamway.test.repositories.models;

import java.time.LocalDate;

public record ShiftScheduleData(Long id, Long workerId, String workerFirstName, String workerLastName, LocalDate date) {
}
