package com.api.barbershop.dto.error;

public record ApiErrorDTO(
        int status,
        String message
) {
}
