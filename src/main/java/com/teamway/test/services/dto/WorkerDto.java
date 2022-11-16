package com.teamway.test.services.dto;

import lombok.Builder;
@Builder
public record WorkerDto(Long id, String firstName, String lastName) {
}
