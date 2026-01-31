package com.api.barbershop.dto.error;

public record ErrorResponseDTO(
        int status,
        String message
) {
}
