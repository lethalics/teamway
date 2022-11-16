package com.teamway.test.configuration.handlers;

import lombok.Builder;

@Builder
public record ApiSubError(String field, Object rejectedValue, String message) {
}
