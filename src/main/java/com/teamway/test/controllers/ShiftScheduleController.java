package com.teamway.test.controllers;

import com.teamway.test.controllers.dto.RequestShiftScheduleDto;
import com.teamway.test.services.ShiftScheduleService;
import com.teamway.test.services.dto.ShiftScheduleDto;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shift_schedule")
@AllArgsConstructor
@Slf4j
public class ShiftScheduleController {

    private final ShiftScheduleService shiftScheduleService;

    @PostMapping
    public ResponseEntity<ShiftScheduleDto> addShiftSchedule(@RequestBody @Valid RequestShiftScheduleDto requestShiftScheduleDto) {
        log.info("Scheduling new shift for: {}", requestShiftScheduleDto);
        return ResponseEntity.ok(this.shiftScheduleService.addShiftSchedule(requestShiftScheduleDto));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ShiftScheduleDto> getShiftSchedule(@PathVariable Long id) {
        log.info("Getting shift schedule for id={}", id);
        return ResponseEntity.of(this.shiftScheduleService.getShiftSchedule(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ShiftScheduleDto> updateShiftSchedule(@PathVariable Long id, @RequestBody @Valid RequestShiftScheduleDto requestShiftScheduleDto) {
        log.info("Updating shift schedule with id={} with data={}", id, requestShiftScheduleDto);
        return ResponseEntity.of(this.shiftScheduleService.updateShiftSchedule(id, requestShiftScheduleDto));
    }
}
