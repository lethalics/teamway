package com.teamway.test.controllers.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record CreateWorkerDto(@NotBlank @Pattern(regexp = "[a-zA-Z]{2,255}") String firstName, @NotBlank @Pattern(regexp = "[a-zA-Z]{2,255}") String lastName){
}
