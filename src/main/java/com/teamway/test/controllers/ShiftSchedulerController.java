package com.teamway.test.controllers;

import com.teamway.test.controllers.dto.RequestScheduleShiftDto;
import com.teamway.test.services.dto.ShiftScheduleDto;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shift_schedule")
@AllArgsConstructor
@Slf4j
public class ShiftSchedulerController {

    @PostMapping
    public ResponseEntity<ShiftScheduleDto> scheduleShift(@RequestBody @Valid RequestScheduleShiftDto requestScheduleShiftDto) {
        log.info("Scheduling new shift for: {}", requestScheduleShiftDto);

        return null;
    }
}
