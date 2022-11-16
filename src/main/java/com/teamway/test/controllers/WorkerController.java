package com.teamway.test.controllers;

import com.teamway.test.controllers.requests.RequestWorkerDto;
import com.teamway.test.controllers.responses.WorkerDto;
import com.teamway.test.services.WorkerService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/worker")
@AllArgsConstructor
@Slf4j
public class WorkerController {

    private final WorkerService workerService;

    @PostMapping
    public ResponseEntity<WorkerDto> addWorker(@RequestBody @Valid RequestWorkerDto requestWorkerDto) {
        log.info("Creating new worker with input: {}", requestWorkerDto);
        return ResponseEntity.ok(workerService.createWorker(requestWorkerDto));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<WorkerDto> getWorker(@PathVariable Long id) {
        log.info("Getting worker with id={}", id);
        return ResponseEntity.of(workerService.getWorker(id));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deactivateWorker(@PathVariable Long id) {
        log.info("Deactivating worker with id={}", id);
        var deactivated = workerService.deactivateWorker(id);
        return deactivated ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<WorkerDto> updateWorker(@PathVariable Long id, @RequestBody @Valid RequestWorkerDto requestWorkerDto) {
        log.info("Updating worker with id={}", id);
        return ResponseEntity.of(workerService.updateWorker(id, requestWorkerDto));
    }
}
