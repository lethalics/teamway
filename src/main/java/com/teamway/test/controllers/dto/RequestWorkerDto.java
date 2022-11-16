package com.teamway.test.controllers.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record RequestWorkerDto(@NotBlank @Pattern(regexp = "[a-zA-Z]{2,255}") String firstName, @NotBlank @Pattern(regexp = "[a-zA-Z]{2,255}") String lastName){
}
