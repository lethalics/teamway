package com.teamway.test.services.mappers;

import com.teamway.test.controllers.responses.WorkerDto;
import com.teamway.test.repositories.entities.WorkerEntity;

//We can use mapstruct for automatic mappings generation
public class WorkerMapper {

    public static WorkerDto toWorkerDto(WorkerEntity workerEntity) {
        return WorkerDto.builder()
            .id(workerEntity.getId())
            .firstName(workerEntity.getFirstName())
            .lastName(workerEntity.getLastName())
            .build();
    }
}
