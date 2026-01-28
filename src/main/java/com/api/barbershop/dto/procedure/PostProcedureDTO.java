package com.api.barbershop.dto.procedure;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostProcedureDTO(
        @NotBlank String name,
        @NotNull Double price,
        @NotNull Integer estimatedTime
) {
}
