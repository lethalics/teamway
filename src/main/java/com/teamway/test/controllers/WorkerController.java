package com.teamway.test.controllers;

import com.teamway.test.controllers.requests.CreateWorkerDto;
import com.teamway.test.controllers.responses.WorkerDto;
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
@RequestMapping("/api/v1/worker")
@AllArgsConstructor
@Slf4j
public class WorkerController {

    @PostMapping
    public ResponseEntity<WorkerDto> addWorker(@RequestBody @Valid CreateWorkerDto request) {
        return ResponseEntity.ok(
            WorkerDto.builder()
                .id(11L)
                .firstName("Alex")
                .lastName("G")
                .build()
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable Long id) {
        return ResponseEntity.ok(new WorkerDto(id, "dummy", "dummy"));
    }
}
