package com.teamway.test.repositories.models;

import com.teamway.test.repositories.entities.Shift;
import java.time.LocalDate;

public record ShiftScheduleData(Long id, Long workerId, String workerFirstName, String workerLastName, Shift shift, LocalDate date) {
}
