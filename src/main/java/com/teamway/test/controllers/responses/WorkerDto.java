package com.teamway.test.controllers.responses;

import lombok.Builder;
@Builder
public record WorkerDto(Long id, String firstName, String lastName) {
}
