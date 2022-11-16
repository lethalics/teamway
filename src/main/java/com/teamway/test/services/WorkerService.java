package com.teamway.test.services;

import com.teamway.test.controllers.requests.RequestWorkerDto;
import com.teamway.test.controllers.responses.WorkerDto;
import com.teamway.test.repositories.WorkerRepository;
import com.teamway.test.repositories.entities.WorkerEntity;
import com.teamway.test.services.mappers.WorkerMapper;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class WorkerService {
    private final WorkerRepository workerRepository;

    public WorkerDto createWorker(RequestWorkerDto workerRequest) {
        var workerEntity = WorkerEntity.builder()
            .firstName(workerRequest.firstName())
            .lastName(workerRequest.lastName())
            .active(true)
            .build();

        var worker = workerRepository.save(workerEntity);

        log.info("Saved worker: {}", worker);

        return WorkerMapper.toWorkerDto(worker);
    }

    public Optional<WorkerDto> getWorker(Long id) {
        var workerResult = workerRepository.findByIdAndActive(id, true);
        return workerResult.map(WorkerMapper::toWorkerDto);
    }

    public Optional<WorkerDto> updateWorker(Long id, RequestWorkerDto workerRequest) {
        var workerEntityResult = workerRepository.findByIdAndActive(id, true);
        if (workerEntityResult.isEmpty()) {
            return Optional.empty();
        }

        WorkerEntity workerEntity = updateWorkerEntity(workerRequest, workerEntityResult.get());

        workerRepository.save(workerEntity);

        log.info("Updated worker: {}", workerEntity);

        return Optional.of(WorkerMapper.toWorkerDto(workerEntity));
    }

    public Boolean deactivateWorker(Long id) {
        log.info("Trying to deactivate worker with id={}", id);
        var result = workerRepository.deactivateWorker(id);
        return result > 0;
    }

    private WorkerEntity updateWorkerEntity(RequestWorkerDto workerRequest, WorkerEntity workerEntity) {
        workerEntity.setFirstName(workerRequest.firstName());
        workerEntity.setLastName(workerRequest.lastName());
        return workerEntity;
    }
}
