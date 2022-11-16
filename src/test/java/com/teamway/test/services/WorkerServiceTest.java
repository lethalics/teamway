package com.teamway.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.teamway.test.controllers.requests.RequestWorkerDto;
import com.teamway.test.repositories.WorkerRepository;
import com.teamway.test.repositories.entities.WorkerEntity;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WorkerServiceTest {
    private WorkerRepository workerRepositoryMock;
    private WorkerService workerService;

    @BeforeEach
    public void init() {
        this.workerRepositoryMock = mock(WorkerRepository.class);
        this.workerService = new WorkerService(this.workerRepositoryMock);
    }

    @Test
    public void testCreateWorker() {
        var requestWorkerDto = RequestWorkerDto.builder()
            .firstName("Abc")
            .lastName("Def")
            .build();

        var workerEntity = WorkerEntity.builder()
            .id(1L)
            .firstName("Abc")
            .lastName("Def")
            .active(true)
            .build();

        when(workerRepositoryMock.save(any(WorkerEntity.class))).thenReturn(workerEntity);

        var workerDtoResult = this.workerService.createWorker(requestWorkerDto);

        assertEquals(1L, workerDtoResult.id());
        assertEquals("Abc", workerDtoResult.firstName());
        assertEquals("Def", workerDtoResult.lastName());
    }

    @Test
    public void testGetWorker() {
        var workerEntity = WorkerEntity.builder()
            .id(1L)
            .firstName("Abc")
            .lastName("Def")
            .active(true)
            .build();

        when(workerRepositoryMock.findByIdAndActive(any(Long.class), any(Boolean.class))).thenReturn(Optional.of(workerEntity));

        var workerDtoResult = this.workerService.getWorker(1L);
        assertTrue(workerDtoResult.isPresent());
        var workerDto = workerDtoResult.get();
        assertEquals(1L, workerDto.id());
        assertEquals("Abc", workerDto.firstName());
        assertEquals("Def", workerDto.lastName());
    }

    @Test
    public void testUpdateWorker() {
        var requestWorkerDto = RequestWorkerDto.builder()
            .firstName("Aaa")
            .lastName("Bbb")
            .build();

        var workerEntity = WorkerEntity.builder()
            .id(1L)
            .firstName("Abc")
            .lastName("Def")
            .active(true)
            .build();

        when(workerRepositoryMock.findByIdAndActive(any(Long.class), any(Boolean.class))).thenReturn(Optional.of(workerEntity));
        when(workerRepositoryMock.save(any(WorkerEntity.class))).thenReturn(workerEntity);

        var workerDtoResult = this.workerService.updateWorker(1L, requestWorkerDto);
        assertTrue(workerDtoResult.isPresent());
        var workerDto = workerDtoResult.get();
        assertEquals(1L, workerDto.id());
        assertEquals("Aaa", workerDto.firstName());
        assertEquals("Bbb", workerDto.lastName());
    }

    @Test
    public void testDeactivateWorker() {
        when(this.workerRepositoryMock.deactivateWorker(any(Long.class))).thenReturn(1);
        var deactivateWorkerResult = this.workerService.deactivateWorker(1L);

        assertTrue(deactivateWorkerResult);
    }
}