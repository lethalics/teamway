package com.teamway.test.controllers;

import com.teamway.test.controllers.dto.RequestScheduleShiftDto;
import com.teamway.test.services.ShiftSchedulerService;
import com.teamway.test.services.dto.ShiftScheduleDto;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shift_schedule")
@AllArgsConstructor
@Slf4j
public class ShiftSchedulerController {

    private final ShiftSchedulerService shiftSchedulerService;

    @PostMapping
    public ResponseEntity<ShiftScheduleDto> addShiftSchedule(@RequestBody @Valid RequestScheduleShiftDto requestScheduleShiftDto) {
        log.info("Scheduling new shift for: {}", requestScheduleShiftDto);
        return ResponseEntity.ok(this.shiftSchedulerService.addShiftSchedule(requestScheduleShiftDto));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ShiftScheduleDto> getShiftSchedule(@PathVariable Long id) {
        log.info("Getting shift schedule for id={}", id);
        return ResponseEntity.of(this.shiftSchedulerService.getShiftSchedule(id));
    }
}
