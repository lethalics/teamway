package com.teamway.test.integration.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.teamway.test.repositories.WorkerRepository;
import com.teamway.test.repositories.entities.WorkerEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class WorkerRepositoryIntegrationTest {

    @Autowired
    private WorkerRepository workerRepository;

    @Test
    public void testFindWorker() {
        var workerEntity = WorkerEntity.builder()
            .firstName("firstName")
            .lastName("lastName")
            .active(true)
            .build();

        workerRepository.save(workerEntity);

        var result = workerRepository.findByIdAndActive(workerEntity.getId(), true);
        assertTrue(result.isPresent());
        var worker = result.get();
        assertEquals("firstName", worker.getFirstName());
        assertEquals("lastName", worker.getLastName());
    }

    @Test
    public void testDeactivateWorker() {
        var workerEntity = WorkerEntity.builder()
            .firstName("firstName")
            .lastName("lastName")
            .active(true)
            .build();

        workerRepository.save(workerEntity);

        var workerId = workerEntity.getId();
        workerRepository.deactivateWorker(workerId);

        var result = workerRepository.findByIdAndActive(workerId, false);

        assertTrue(result.isPresent());
    }
}
