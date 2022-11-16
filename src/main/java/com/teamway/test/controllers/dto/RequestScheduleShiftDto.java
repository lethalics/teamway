package com.teamway.test.controllers.dto;

import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public record RequestScheduleShiftDto(@NotNull @Positive Long workerId, @NotNull ShiftDto shift, @NotNull @Future LocalDate date) {
}
