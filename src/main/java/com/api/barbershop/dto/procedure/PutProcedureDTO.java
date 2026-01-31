package com.api.barbershop.dto.procedure;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PutProcedureDTO(
        @Size(max = 100) String name,
        @Digits(integer = 8, fraction = 2) @Positive BigDecimal price,
        @Positive Integer estimatedTime
) {
}
