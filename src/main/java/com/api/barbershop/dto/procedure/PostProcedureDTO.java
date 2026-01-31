package com.api.barbershop.dto.procedure;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PostProcedureDTO(
        @NotBlank @Size(max = 100) String name,
        @NotNull @Digits(integer = 8, fraction = 2) @Positive BigDecimal price,
        @NotNull @Positive Integer estimatedTime
) {
}
